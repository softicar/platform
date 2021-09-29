package com.softicar.platform.emf.editor;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.transients.ITransientField;
import com.softicar.platform.dom.node.DomNode;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.test.EmfNodeTester;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;

/**
 * This class can be used in unit test to verify the attributes shown in an
 * {@link EmfAttributesDiv}.
 * <p>
 * This class assumes that every attribute row implements the interface
 * {@link IEmfAttributeValueFrame}. All other children of the given
 * {@link EmfAttributesDiv} will be ignored.
 *
 * @author Oliver Richers
 */
public class EmfAttributesDivAsserter<R extends IEmfTableRow<R, ?>> {

	private final IEmfTable<R, ?, ?> entityTable;
	private final List<AttributeRow> rows;
	private int index;

	/**
	 * Constructs this asserter for the given entity and root {@link DomNode}.
	 * <p>
	 * The given
	 *
	 * @param tableRow
	 *            the {@link IEmfTableRow} shown in the attributes table (never
	 *            null)
	 * @param root
	 *            the root node in form of a {@link EmfNodeTester} (never null)
	 */
	public EmfAttributesDivAsserter(R tableRow, EmfNodeTester root) {

		this.entityTable = tableRow.table();
		this.rows = new ArrayList<>();
		this.index = 0;

		EmfAttributesDiv<?> attributesDiv = root.findNode(EmfAttributesDiv.class).assertType(EmfAttributesDiv.class);
		for (IDomNode node: attributesDiv.getChildren()) {
			if (node instanceof IEmfAttributeValueFrame<?, ?>) {
				rows.add(new AttributeRow((IEmfAttributeValueFrame<?, ?>) node));
			}
		}
	}

	// ------------------------------ assert display ------------------------------ //

	public EmfAttributesDivAsserter<R> assertDisplay(IDbField<R, ?> field) {

		return assertAttributeRow(field, AttributeRowType.DISPLAY, true);
	}

	public EmfAttributesDivAsserter<R> assertDisplay(IDbField<R, ?> field, boolean enabled) {

		return assertAttributeRow(field, AttributeRowType.DISPLAY, enabled);
	}

	public EmfAttributesDivAsserter<R> assertDisplay(IEmfAttribute<R, ?> attribute) {

		return assertAttributeRow(attribute, AttributeRowType.DISPLAY, true);
	}

	public EmfAttributesDivAsserter<R> assertDisplay(IEmfAttribute<R, ?> attribute, boolean enabled) {

		return assertAttributeRow(attribute, AttributeRowType.DISPLAY, enabled);
	}

	public EmfAttributesDivAsserter<R> assertDisplay(ITransientField<R, ?> field) {

		return assertAttributeRow(field, AttributeRowType.DISPLAY, true);
	}

	public EmfAttributesDivAsserter<R> assertDisplay(ITransientField<R, ?> field, boolean enabled) {

		return assertAttributeRow(field, AttributeRowType.DISPLAY, enabled);
	}

	// ------------------------------ assert optional input ------------------------------ //

	public EmfAttributesDivAsserter<R> assertOptionalInput(IDbField<R, ?> field) {

		return assertAttributeRow(field, AttributeRowType.OPTIONAL_INPUT, true);
	}

	public EmfAttributesDivAsserter<R> assertOptionalInput(IDbField<R, ?> field, boolean enabled) {

		return assertAttributeRow(field, AttributeRowType.OPTIONAL_INPUT, enabled);
	}

	public EmfAttributesDivAsserter<R> assertOptionalInput(IEmfAttribute<R, ?> attribute) {

		return assertAttributeRow(attribute, AttributeRowType.OPTIONAL_INPUT, true);
	}

	public EmfAttributesDivAsserter<R> assertOptionalInput(IEmfAttribute<R, ?> attribute, boolean enabled) {

		return assertAttributeRow(attribute, AttributeRowType.OPTIONAL_INPUT, enabled);
	}

	// ------------------------------ assert mandatory input ------------------------------ //

	public EmfAttributesDivAsserter<R> assertMandatoryInput(IDbField<R, ?> field) {

		return assertAttributeRow(field, AttributeRowType.MANDATORY_INPUT, true);
	}

	public EmfAttributesDivAsserter<R> assertMandatoryInput(IDbField<R, ?> field, boolean enabled) {

		return assertAttributeRow(field, AttributeRowType.MANDATORY_INPUT, enabled);
	}

	public EmfAttributesDivAsserter<R> assertMandatoryInput(IEmfAttribute<R, ?> attribute) {

		return assertAttributeRow(attribute, AttributeRowType.MANDATORY_INPUT, true);
	}

	public EmfAttributesDivAsserter<R> assertMandatoryInput(IEmfAttribute<R, ?> attribute, boolean enabled) {

		return assertAttributeRow(attribute, AttributeRowType.MANDATORY_INPUT, enabled);
	}

	// ------------------------------ assert no further attributes ------------------------------ //

	public EmfAttributesDivAsserter<R> assertNoFurtherAttributes() {

		if (index < rows.size()) {
			String unexpectedAttributes = rows//
				.subList(index, rows.size())
				.stream()
				.map(row -> row.toString())
				.collect(Collectors.joining("\n"));
			throw new AssertionError(String.format("Unexpected attributes:\n%s", unexpectedAttributes));
		}
		return this;
	}

	// ------------------------------ private ------------------------------ //

	private EmfAttributesDivAsserter<R> assertAttributeRow(IEmfAttribute<R, ?> attribute, AttributeRowType expectedType, boolean enabled) {

		if (enabled) {
			assertAttributeRow(attribute).assertRowType(expectedType);
		}
		return this;
	}

	private EmfAttributesDivAsserter<R> assertAttributeRow(IDbField<R, ?> field, AttributeRowType expectedType, boolean enabled) {

		return assertAttributeRow(entityTable.getAttribute(field), expectedType, enabled);
	}

	private EmfAttributesDivAsserter<R> assertAttributeRow(ITransientField<R, ?> field, AttributeRowType expectedType, boolean enabled) {

		return assertAttributeRow(entityTable.getAttribute(field), expectedType, enabled);
	}

	private AttributeRow assertAttributeRow(IEmfAttribute<R, ?> expectedAttribute) {

		if (index < rows.size()) {
			AttributeRow attributeRow = rows.get(index);
			Assert.assertSame(getMissingAttributeMessage(expectedAttribute), expectedAttribute, attributeRow.getAttribute());
			index++;
			return attributeRow;
		} else {
			throw new AssertionError(getMissingAttributeMessage(expectedAttribute));
		}
	}

	private String getMissingAttributeMessage(IEmfAttribute<R, ?> expectedAttribute) {

		return String
			.format(//
				"Missing attribute '%s' in row %s. Encountered rows:\n%s",
				expectedAttribute.getTitle(),
				index,
				getAllRowsAsString());
	}

	private String getAllRowsAsString() {

		int index = 0;
		StringBuilder builder = new StringBuilder();
		for (AttributeRow row: rows) {
			builder//
				.append(index)
				.append(": ")
				.append(row.toString())
				.append("\n");
			index++;
		}
		return builder.toString();
	}

	private static class AttributeRow {

		private final IEmfAttribute<?, ?> attribute;
		private final AttributeRowType rowType;

		public AttributeRow(IEmfAttributeValueFrame<?, ?> attributeRow) {

			this.attribute = attributeRow.getAttribute();
			this.rowType = AttributeRowType.getRowType(attributeRow);
		}

		public IEmfAttribute<?, ?> getAttribute() {

			return attribute;
		}

		public void assertRowType(AttributeRowType expectedRowType) {

			if (rowType != expectedRowType) {
				Assert.fail(String.format("Wrong row type: %s\nExpected: %s", toString(), expectedRowType));
			}
		}

		@Override
		public String toString() {

			return String.format("%s (%s)", attribute.getTitle(), rowType.toString());
		}
	}

	private static enum AttributeRowType {

		DISPLAY("display"),
		OPTIONAL_INPUT("optional input"),
		MANDATORY_INPUT("mandatory input");

		private String name;

		private AttributeRowType(String name) {

			this.name = name;
		}

		public static AttributeRowType getRowType(IEmfAttributeValueFrame<?, ?> attributeRow) {

			if (attributeRow.isEditable()) {
				return attributeRow.isMandatory()? MANDATORY_INPUT : OPTIONAL_INPUT;
			} else {
				return DISPLAY;
			}
		}

		@Override
		public String toString() {

			return name;
		}
	}
}
