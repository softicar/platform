package com.softicar.platform.emf.data.table.export.conversion;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.logging.CurrentLogLevel;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.logging.LogLevel;
import com.softicar.platform.common.core.number.parser.BigDecimalParser;
import com.softicar.platform.common.core.number.parser.DoubleParser;
import com.softicar.platform.common.core.number.parser.IntegerParser;
import com.softicar.platform.common.core.number.parser.LongParser;
import com.softicar.platform.common.date.DateFormat;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTimeParser;
import com.softicar.platform.common.date.DayTimeParser.Format;
import com.softicar.platform.common.string.Padding;
import com.softicar.platform.common.string.parsing.NumberStringCleaner;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.element.DomSimpleElement;
import com.softicar.platform.dom.elements.DomIcon;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.elements.DomTextArea;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.elements.image.free.text.DomFreeTextImage;
import com.softicar.platform.dom.elements.label.DomPreformattedLabel;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.DomParentElement;
import com.softicar.platform.dom.parent.IDomParentElement;
import com.softicar.platform.dom.text.DomTextNode;
import com.softicar.platform.emf.data.table.export.node.ITableExportEmptyNode;
import com.softicar.platform.emf.data.table.export.node.ITableExportManipulatedNode;
import com.softicar.platform.emf.data.table.export.node.ITableExportTypedNode;
import com.softicar.platform.emf.data.table.export.node.TableExportNodeValueType;
import com.softicar.platform.emf.data.table.export.node.TableExportTypedNodeValue;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Converts {@link IDomNode}s to {@link TableExportTypedNodeValue} instances.
 * <p>
 * Relies on {@link ITableExportManipulatedNode}s, especially
 * {@link ITableExportTypedNode}s, {@link ITableExportEmptyNode} and such.
 * <p>
 * Can be parameterized in various ways, as performed by distinct factory
 * implementations.
 *
 * @author Alexander Schmidt
 */
public class TableExportTypedNodeConverter implements ITableExportNodeConverter<TableExportTypedNodeValue> {

	private static final String DEFAULT_CELL_CONTENT_SEPARATOR = " ";
	private static final String DAY_STRING_REGEX = "[123][0-9]{3}-[01][0-9]-[0123][0-9]";
	private static final String TIME_STRING_REGEX = "[012][0-9]:[0-5][0-9]:[0-5][0-9]";

	// ----

	private boolean enableAutomaticTypeConversion = true;
	private boolean forceStringValues = false;
	private String cellContentSeparator = DEFAULT_CELL_CONTENT_SEPARATOR;

	// ----

	private boolean debugVerboseMode = CurrentLogLevel.get() == LogLevel.VERBOSE;

	// ----

	public TableExportTypedNodeConverter setCellContentSeparator(String cellContentSeparator) {

		this.cellContentSeparator = cellContentSeparator;
		return this;
	}

	public TableExportTypedNodeConverter setEnableAutomaticTypeConversion(boolean enableAutomaticTypeConversion) {

		this.enableAutomaticTypeConversion = enableAutomaticTypeConversion;
		return this;
	}

	public TableExportTypedNodeConverter setForceStringValues(boolean forceStringValues) {

		this.forceStringValues = forceStringValues;
		return this;
	}

	public TableExportTypedNodeConverter setDebugVerboseMode(boolean debugVerboseMode) {

		this.debugVerboseMode = debugVerboseMode;
		return this;
	}

	@Override
	public NodeConverterResult<TableExportTypedNodeValue> convertNode(IDomNode node) {

		TypedExportNodeValueList nodeValueList = convert(node, 0);

		TableExportTypedNodeValue result;
		int numLines = 1;

		if (nodeValueList.isEmpty()) {
			result = createStringExportNodeValue(null);
		}

		else if (nodeValueList.size() == 1) {
			result = nodeValueList.get().iterator().next();

			if (result.getValueType() == TableExportNodeValueType.STRING) {
				result.trimString();
				numLines += countNumberOfOccurrences("\n", result.getString());
			}
		}

		else {
			NodeConverterResult<String> implodedValueList = implodeValueList(nodeValueList);
			String contentString = implodedValueList.getContent();
			numLines = Math.max(1, implodedValueList.getContentLineCount());

			if (!this.cellContentSeparator.isEmpty() && contentString.endsWith(this.cellContentSeparator)) {
				contentString = contentString.substring(0, contentString.length() - this.cellContentSeparator.length());
			}

			result = createStringExportNodeValue(contentString);
		}

		return new NodeConverterResult<>(assertValidResult(result), numLines);
	}

	/**
	 * Counts the number of occurrences of the given needle String in the given
	 * haystack String, in a hackish and speedy (due to not using regex) way.
	 *
	 * @param needle
	 * @param haystack
	 * @return the number of occurrences of the given needle String in the given
	 *         haystack String
	 */
	private int countNumberOfOccurrences(String needle, String haystack) {

		return haystack.length() - haystack.replace(needle + "", "").length();
	}

	/**
	 * FIXME: fix the logical chaos in this method.
	 */
	private NodeConverterResult<String> implodeValueList(TypedExportNodeValueList nodeValueList) {

		debugPrint(0, String.format("Imploding a value list of size %s...", nodeValueList.size()));

//		List<StringBuilder> lines = new ArrayList<>();
//		StringBuilder currentLine = null;

		List<LineWithSubLines> lines = new ArrayList<>();
		LineWithSubLines currentLine = null;

		for (TableExportTypedNodeValue cellValue: nodeValueList.get()) {
			Object value = cellValue.getValue();

			debugPrint(1, "value: [" + value + "]");

			if (value != null) {
				String stringValue = value.toString();

				if (currentLine == null) {
//					currentLine = new StringBuilder();
					currentLine = new LineWithSubLines();
					lines.add(currentLine);
				}

				// in case the String consisting of only white spaces comprising a newline (e.g. the representation of a
				// BR tag), cause the next value to be considered a new line
				if (stringValue.matches("\\s*\\n\\s*")) {
					currentLine = null;
				} else {
					boolean endsWithNewLine = stringValue.matches(".*\\n\\s*");

					// replace all (multi) white spaces with single spaces since the browser does the same
					stringValue = stringValue.trim().replaceAll("\\s+", " ");

					if (endsWithNewLine) {
						stringValue += "\n";
						currentLine.incrementLineCounter();
					} else {
						stringValue += this.cellContentSeparator;
					}

					currentLine.getStringBuilder().append(stringValue);

//					stringValue = stringValue.trim().replaceAll("\\s+", " ");
//					stringValue += this.cellContentSeparator;
//					currentLine.append(stringValue);
				}
			}
		}

		StringBuilder output = new StringBuilder();
		int numLines = 0;

		debugPrint(1, "aggregating results");

//		for (StringBuilder line: lines) {
//		String lineString = line.toString().trim();
		for (LineWithSubLines line: lines) {
			String lineString = line.getStringBuilder().toString().trim();

			debugPrint(2, String.format("line string: [%s]", lineString));

			if (!lineString.isEmpty()) {
				if (output.length() != 0) {
					output.append("\n");
				}

				output.append(lineString);
				++numLines;

				numLines += line.getNumSubLines();
			}
		}

		NodeConverterResult<String> result = new NodeConverterResult<>(output.toString().trim(), numLines);

		debugPrint(0, String.format("...value list imploded. Number of lines: %s. result: [%s]", numLines, result.getContent()));

		return result;
	}

	/**
	 * Converts the given {@link IDomNode} (and child nodes, as appropriate) to
	 * a List of {@link TableExportTypedNodeValue}s.
	 * <p>
	 * The type of the given {@link IDomNode} n has a strong influence on its
	 * processing. The latter is done wrt. the following priorities:<br>
	 * (1.) If n is instanceof {@link ITableExportManipulatedNode}, special
	 * conversion actions are performed for n. Afterwards, n is not processed
	 * any further, regardless of probably containing nested elements (i.e.
	 * being a {@link DomParentElement}).<br>
	 * (2.) If n is instanceof a {@link DomParentElement} that was not covered
	 * by the above step, the children of n are processed recursively with the
	 * results being accumulated.<br>
	 * (3.) If n is instanceof some specific non-{@link DomParentElement} (like
	 * {@link DomTextInput}, {@link DomTextNode} etc.), special conversion
	 * actions are performed.
	 *
	 * @param node
	 *            the node to derive {@link TableExportTypedNodeValue}s from
	 * @param currentDepth
	 *            pass 0 for the initial call here
	 * @return List of {@link TableExportTypedNodeValue}s extracted from the
	 *         given {@link IDomNode}.
	 */
	private TypedExportNodeValueList convert(IDomNode node, int currentDepth) {

		TypedExportNodeValueList values = new TypedExportNodeValueList();

		//
		// IExportNodes (direct / non-nested conversion)
		//
		if (node instanceof ITableExportManipulatedNode) {

			if (node instanceof ITableExportEmptyNode) {
				// explicitly ignore that node
			}

			// prefer exact conversion of nodes
			else if (node instanceof ITableExportTypedNode) {
				debugPrint(currentDepth, "ITypedExportNode");

				values.add(((ITableExportTypedNode) node).getTypedExportNodeValue());

				debugPrint(
					currentDepth + 1,
					"EXTRACTED: " + ((ITableExportTypedNode) node).getTypedExportNodeValue().getValueType().get().getCanonicalName() + ": ["
							+ ((ITableExportTypedNode) node).getTypedExportNodeValue().getValue() + "]");
			}

			else {
				throw new TableExportTypedNodeHandlingException((ITableExportManipulatedNode) node);
			}
		}

		//
		// PARENT ELEMENTS (direct / non-nested conversion)
		//

		else if (node instanceof DomTextArea) {
			debugPrint(currentDepth, "DomTextArea");
			values.add(createStringExportNodeValue(((DomTextArea) node).getValueTrimmed()));
		}

		else if (node instanceof DomTextInput) {
			debugPrint(currentDepth, DomTextInput.class.getSimpleName());
			values.add(autoConvert(((DomTextInput) node).getValueTrimmed(), currentDepth + 1));
		}

		else if (node instanceof DomPreformattedLabel) {
			debugPrint(currentDepth, DomPreformattedLabel.class.getSimpleName());
			String nodeString = ((DomPreformattedLabel) node).toString();
			if (nodeString != null) {
				values.add(createStringExportNodeValue(nodeString.trim()));
			}
		}

		else if (node instanceof DomCheckbox) {
			debugPrint(currentDepth, DomCheckbox.class.getSimpleName());
			boolean checked = ((DomCheckbox) node).isChecked();
			String output = (checked? DomI18n.YES : DomI18n.NO).toString();
			values.add(createStringExportNodeValue(output));
		}

		//
		// SIMPLE (PARENT) ELEMENTS (direct / non-nested conversion)
		//

		else if (node instanceof DomSimpleElement) {
			if (((DomSimpleElement) node).getTag() == DomElementTag.BR) {
				debugPrint(currentDepth, "DomSimpleElement / BR");
				values.add(createStringExportNodeValue("\n"));
			}

			else if (((DomSimpleElement) node).getTag() == DomElementTag.PRE) {
				debugPrint(currentDepth, "DomSimpleElement / PRE");

				for (IDomNode childNode: ((DomSimpleElement) node).getChildren()) {
					if (childNode instanceof DomTextNode) {
						String str = ((DomTextNode) childNode).getText() + "\n";
						debugPrint(currentDepth + 1, "force-detected: String: [" + str + "]");

						values.add(createStringExportNodeValue(str));
					}
				}
			}

			else {
				debugPrint(
					currentDepth,
					"WARNING: Don't know how to convert %s of concrete type %s.",
					DomSimpleElement.class.getSimpleName(),
					((DomSimpleElement) node).getTag());
			}
		}

		//
		// any other PARENT ELEMENTS (indirect / nested conversion)
		//
		else if (node instanceof IDomParentElement) {
			debugPrint(currentDepth, "IDomParentElement");

			for (IDomNode child: ((IDomParentElement) node).getChildren()) {
				values.addAll(convert(child, currentDepth + 1));
			}
		}

		//
		// NON-PARENT ELEMENTS
		//
		else if (node instanceof DomTextNode) {
			debugPrint(currentDepth, DomTextNode.class.getSimpleName());
			values.add(autoConvert(((DomTextNode) node).getText().trim(), currentDepth + 1));
		}

		else if (node instanceof DomFreeTextImage) {
			debugPrint(currentDepth, DomFreeTextImage.class.getSimpleName());
			String output = ((DomFreeTextImage) node).getText().trim();
			values.add(createStringExportNodeValue(output));
		}

		else if (node instanceof DomImage) {
			// ignore but avoid warning
		}

		else if (node instanceof DomIcon) {
			// ignore but avoid warning
		}

		else {
			if (node != null) {
				debugPrint(//
					currentDepth,
					"WARNING: Could not convert node of type: %s",
					node.getClass().getCanonicalName());
			} else {
				debugPrint(//
					currentDepth,
					"WARNING: Could not convert null node.");
			}
		}

		return assertValidResult(values);
	}

	private static boolean isDayString(String str) {

		return str.matches(DAY_STRING_REGEX);
	}

	private static boolean isDayTimeString(String str) {

		return str.matches(DAY_STRING_REGEX + " " + TIME_STRING_REGEX);
	}

	/**
	 * Tries to derive typed values from textual cell contents.
	 *
	 * @param cellContent
	 * @param currentDepth
	 * @return A {@link TableExportTypedNodeValue} with the
	 *         {@link TableExportNodeValueType} as which the given cell content
	 *         String was interpreted. {@link TableExportNodeValueType#STRING}
	 *         is used in case no special interpretation applied.
	 */
	private TableExportTypedNodeValue autoConvert(String cellContent, int currentDepth) {

		TableExportTypedNodeValue typedExportNodeValue;

		// FIXME: this kind of reverse type inference is not a clean solution but mandatory as long as we don't have
		// strict types in tables
		if (this.enableAutomaticTypeConversion) {
			cellContent = NumberStringCleaner.convertToCleanNumberString(cellContent.trim());

			Optional<BigDecimal> decimal = new BigDecimalParser(cellContent).parse();

			if (IntegerParser.isInteger(cellContent)) {
				debugPrint(currentDepth, "detected: Integer: [" + cellContent + "]");
				typedExportNodeValue = createTypedExportNodeValue(TableExportNodeValueType.NUMBER, Integer.parseInt(cellContent));
			} else if (LongParser.isLong(cellContent)) {
				debugPrint(currentDepth, "detected: Long: [" + cellContent + "]");
				typedExportNodeValue = createTypedExportNodeValue(TableExportNodeValueType.NUMBER, Long.parseLong(cellContent));
			} else if (decimal.isPresent()) {
				debugPrint(currentDepth, "detected: Decimal: [" + cellContent + "]");
				typedExportNodeValue = createTypedExportNodeValue(TableExportNodeValueType.NUMBER, decimal.get());
			} else if (DoubleParser.isDouble(cellContent)) {
				debugPrint(currentDepth, "detected: Double: [" + cellContent + "]");
				typedExportNodeValue = createTypedExportNodeValue(TableExportNodeValueType.NUMBER, Double.parseDouble(cellContent));
			} else if (isDayString(cellContent)) {
				debugPrint(currentDepth, "detected: Day String: [" + cellContent + "]");
				typedExportNodeValue = createTypedExportNodeValue(TableExportNodeValueType.DAY, Day.parse(DateFormat.YYYY_MM_DD, cellContent));
			} else if (isDayTimeString(cellContent)) {
				debugPrint(currentDepth, "detected: DayTime String: [" + cellContent + "]");
				typedExportNodeValue =
						createTypedExportNodeValue(TableExportNodeValueType.DAYTIME, DayTimeParser.parse(cellContent, Format.YYYY_MM_DD_HH_MM_SS));
			} else {
				debugPrint(currentDepth, "detected: String: [" + cellContent + "]");
				typedExportNodeValue = createStringExportNodeValue(cellContent);
			}
		}

		else {
			typedExportNodeValue = createStringExportNodeValue(cellContent);
		}

		debugPrint(currentDepth + 1, "DETERMINED typedExportNodeValue: " + typedExportNodeValue.getValueType().name() + ": " + typedExportNodeValue.getValue());

		return typedExportNodeValue;
	}

	private TableExportTypedNodeValue createTypedExportNodeValue(TableExportNodeValueType valueType, Object value) {

		if (this.forceStringValues) {
			return createStringExportNodeValue(value);
		} else {
			return new TableExportTypedNodeValue(valueType, value);
		}
	}

	private TableExportTypedNodeValue createStringExportNodeValue(Object value) {

		return new TableExportTypedNodeValue(TableExportNodeValueType.STRING, value != null? value.toString() : null);
	}

	// ----

	private TableExportTypedNodeValue assertValidResult(TableExportTypedNodeValue nodeValue) {

		if (nodeValue != null) {
			assertStringNodeValue(nodeValue);
		}

		return nodeValue;
	}

	private TypedExportNodeValueList assertValidResult(TypedExportNodeValueList nodeValues) {

		if (this.forceStringValues) {
			for (TableExportTypedNodeValue nodeValue: nodeValues.get()) {
				assertStringNodeValue(nodeValue);
			}
		}

		return nodeValues;
	}

	private void assertStringNodeValue(TableExportTypedNodeValue nodeValue) {

		if (this.forceStringValues) {
			if (nodeValue != null && nodeValue.getValueType() != TableExportNodeValueType.STRING) {
				throw new SofticarDeveloperException(
					"Expected a value of type %s but encountered %s.",
					TableExportNodeValueType.STRING,
					nodeValue.getValueType());
			}
		}
	}

	private void debugPrint(int indentation, String message, Object...args) {

		if (this.debugVerboseMode) {
			Log.finfo(Padding.padLeft("", ' ', indentation * 2) + String.format(message, args));
		}
	}

	// ----

	private class TypedExportNodeValueList {

		private final List<TableExportTypedNodeValue> values = new ArrayList<>();

		public void add(TableExportTypedNodeValue value) {

			if (forceStringValues) {
				value = createStringExportNodeValue(value.getValue());
			}

			this.values.add(value);
		}

		public void addAll(TypedExportNodeValueList nodeValueList) {

			this.values.addAll(nodeValueList.get());
		}

		public List<TableExportTypedNodeValue> get() {

			return values;
		}

		public boolean isEmpty() {

			return get().isEmpty();
		}

		public int size() {

			return get().size();
		}
	}

	private class LineWithSubLines {

		private final StringBuilder stringBuilder = new StringBuilder();
		private int numSubLines = 0;

		public void incrementLineCounter() {

			++numSubLines;
		}

		public StringBuilder getStringBuilder() {

			return stringBuilder;
		}

		public int getNumSubLines() {

			return numSubLines;
		}
	}
}
