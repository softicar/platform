package com.softicar.platform.emf.data.table.export.implementation.excel;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.ui.color.IColor;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.element.DomSimpleElement;
import com.softicar.platform.dom.elements.DomCell;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.DomHeaderCell;
import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.DomTextArea;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.cell.separator.DomSeparatorHeaderCell;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.elements.image.free.text.DomFreeTextImage;
import com.softicar.platform.dom.elements.label.DomPreformattedLabel;
import com.softicar.platform.dom.elements.tables.DomDataTable;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.style.CssColorEnum;
import com.softicar.platform.dom.text.DomTextNode;
import com.softicar.platform.emf.data.table.export.node.ITableExportTypedNode;
import com.softicar.platform.emf.data.table.export.node.TableExportNodeValueType;
import com.softicar.platform.emf.data.table.export.node.TableExportTypedNodeValue;
import com.softicar.platform.emf.data.table.export.node.style.ITableExportStyledNode;
import com.softicar.platform.emf.data.table.export.node.style.TableExportNodeStyle;
import java.util.ArrayList;
import java.util.List;

/**
 * A {@link DomTable} containing various elements and kinds of data in its
 * cells. Used in the context of {@link TableExportExcelXlsEngineFactoryTest} .
 *
 * @author Alexander Schmidt
 */
public class TableExportComplexTestTable extends DomDataTable {

	public TableExportComplexTestTable() {

		DomRow headerRow1 = getHead().appendRow();
		headerRow1.appendHeaderCell(generate_0x0_String()).setRowSpan(2);
		headerRow1.appendHeaderCell(generate_0x1_String()).setColSpan(3);
		headerRow1.appendHeaderCell();
		headerRow1.appendHeaderCell();

		RedHeaderCell redHeaderCell = headerRow1.appendChild(new RedHeaderCell());
		redHeaderCell.appendText(generate_0x6_String());
		redHeaderCell.setColSpan(2);

		DomRow headerRow2 = getHead().appendRow();
		headerRow2.appendHeaderCells(generate_1x1_String());
		headerRow2.appendHeaderCell(generate_1x2_String());
		headerRow2.appendHeaderCells(generate_1x3_String());
		headerRow2.appendHeaderCells(generate_1x4_String());
		headerRow2.appendChild(new DomSeparatorHeaderCell());
		headerRow2.appendHeaderCells(generate_1x6_String(), generate_1x7_String());

		// ----

		DomRow row1 = getBody().appendRow();
		row1.appendCell(new DomPreformattedLabel(generate_2x0_String())).setColSpan(3);
		row1.appendCells(generate_2x3_String(), generate_2x4_String());
		row1.appendCell("this should not be in the export");
		row1.appendCells(generate_2x6_String(), generate_2x7_String());

		DomRow row2 = getBody().appendRow();
		row2.appendCells(generate_3x0_String());
		DomCell articleLabelsCell = row2.appendCell();
		generate_3x1_IDomNodes().stream().forEach((c) -> articleLabelsCell.appendChild(c));
		DomCell cellRow2 = row2.appendCell(generate_3x2_String());
		cellRow2.setColSpan(2);
		DomCell cellRow2_2 = row2.appendCell();
		generate_3x3_IDomNodes().stream().forEach((c) -> cellRow2_2.appendChild(c));
		row2.appendCell();
		row2.appendCell("");
		row2.appendCell(generate_3x7_DomTextArea());

		DomRow row3 = getBody().appendRow();
		row3.appendChild(new FontColoredCell("seven"));
		row3.appendHeaderCell(generate_4x1_String()).setRowSpan(4);
		row3.appendChild(new DoubleCell(generate_4x2_Double()));
		row3.appendCell("c - y").setColSpan(2);
		row3.appendCell();
		row3.appendCell("");
		row3.appendCell(generate_4x7_DomTextInput());

		getBody().appendRow().appendCells("10", "12", "d", "z", "", "", "");

		DomRow row5 = getBody().appendRow();
		row5.appendCell("I");
		row5.appendChild(new DoubleCell(4.5)).setColSpan(2);
		row5.appendCell("III");
		row5.appendCell();
		row5.appendCells("", "");

		DomRow row6 = getBody().appendRow();
		row6.appendCell("ä");
		row6.appendCell("ö");
		row6.appendCell("š").setColSpan(2);
		row6.appendCell();
		row6.appendCells("", "");

		DomRow row7 = getBody().appendRow();

		DomCell row7cell0 = row7.appendCell();
		generate_8x0_IDomNodes().stream().forEach((it) -> row7cell0.appendChild(it));
		row7.appendCell(generate_8x1_IDomNode());
		row7.appendCell(generate_8x2_IDomNode());
		row7.appendCell("25.8");
		row7.appendCell("");
		row7.appendCell("");
		row7.appendCell(generate_8x5_DOMFreeText());
		row7.appendCell();

		DomRow row8 = getBody().appendRow();
		row8.appendCell(new DayCell(generate_9x0_Day()));
		row8.appendCell(new NullNumberCell());
		row8.appendCell(new DayTimeCell(generate_9x2_DayTime())).setColSpan(2);
		DomCell lastCell = row8.appendCell();
		generate_9x4_IDomNodes().stream().forEach((it) -> lastCell.appendChild(it));
		row8.appendCell();
		row8.appendCells("", "");

		DomRow row9 = getBody().appendRow();
		row9.appendCell(generate_10x0_Day());
		row9.appendCell(generate_10x1_DayTime()).setColSpan(4);
		row9.appendCell();
		row9.appendCells("", "");
	}

	public static String generate_0x0_String() {

		return "upper 1";
	}

	public static String generate_0x1_String() {

		return "upper 2";
	}

	public static String generate_0x6_String() {

		return "lorem ipsum dolor est amet";
	}

	public static String generate_1x1_String() {

		return "second";
	}

	public static String generate_1x2_String() {

		return "3";
	}

	public static String generate_1x3_String() {

		return "fourth";
	}

	public static String generate_1x4_String() {

		return "fifth öqweß asd";
	}

	public static String generate_1x6_String() {

		return "1";
	}

	public static String generate_1x7_String() {

		return "2";
	}

	public static String generate_2x0_String() {

		return "QWE ASD ZXC 9  876";
	}

	public static String generate_2x3_String() {

		return "a";
	}

	public static String generate_2x4_String() {

		return "w";
	}

	public static String generate_2x6_String() {

		return ".";
	}

	public static String generate_2x7_String() {

		return "..";
	}

	public static String generate_3x0_String() {

		return "09876543.135";
	}

	public static List<IDomNode> generate_3x1_IDomNodes() {

		List<IDomNode> nodes = new ArrayList<>();

		nodes.add(new DomPreformattedLabel("QWE ASD ZXC 1  ABC"));
		nodes.add(new DomSimpleElement(DomElementTag.BR));
		nodes.add(new DomPreformattedLabel("Q   A       1    C"));
		nodes.add(new DomSimpleElement(DomElementTag.BR));
		nodes.add(new DomPreformattedLabel("QWE ASD ZXC       "));

		return nodes;
	}

	public static String generate_3x1_validationString() {

		return "QWE ASD ZXC 1  ABC\nQ   A       1    C\nQWE ASD ZXC       ";
	}

	public static String generate_3x2_String() {

		return "six - b";
	}

	public static List<IDomNode> generate_3x3_IDomNodes() {

		List<IDomNode> nodes = new ArrayList<>();

		nodes.add(DomTextNode.create("x;asemi,acomma\nanewline\tatab\"aquotes"));
		nodes.add(new DomSimpleElement(DomElementTag.BR));
		nodes.add(DomTextNode.create("after br1"));
		nodes.add(new DomSimpleElement(DomElementTag.BR));
		nodes.add(DomTextNode.create("after br2"));
		nodes.add(new DomSimpleElement(DomElementTag.BR));
		nodes.add(DomTextNode.create("after br3"));
		nodes.add(new DomSimpleElement(DomElementTag.BR));
		nodes.add(DomTextNode.create("after br4"));

		return nodes;
	}

	public static String generate_3x3_validationString() {

		return "x;asemi,acomma anewline atab\"aquotes\nafter br1\nafter br2\nafter br3\nafter br4";
	}

	public static DomTextArea generate_3x7_DomTextArea() {

		DomTextArea textArea = new DomTextArea();
		textArea.setInputText("009988.123");

		return textArea;
	}

	public static String generate_3x7_validationString() {

		return "009988.123";
	}

	public static String generate_4x1_String() {

		return "8 - 11";
	}

	public static Double generate_4x2_Double() {

		return 123.456d;
	}

	public static DomTextInput generate_4x7_DomTextInput() {

		DomTextInput textInput = new DomTextInput();
		textInput.setInputText("001122.345");

		return textInput;
	}

	public static String generate_4x7_validationString() {

		return "1122.345";
	}

	public static List<IDomNode> generate_8x0_IDomNodes() {

		List<IDomNode> nodes = new ArrayList<>();

		nodes.add(new DomCheckbox());
		nodes.add(DomTextNode.create("test"));

		return nodes;
	}

	public static String generate_8x0_validationString() {

		return DomI18n.NO + " test";
	}

	public static IDomNode generate_8x1_IDomNode() {

		return new DomCheckbox().setChecked(true);
	}

	public static String generate_8x1_validationString() {

		return DomI18n.YES.toString();
	}

	public static IDomNode generate_8x2_IDomNode() {

		return new TestButton();
	}

	public static String generate_8x2_validationString() {

		return TestButton.LABEL;
	}

	public static DomFreeTextImage generate_8x5_DOMFreeText() {

		return DomFreeTextImage.getVerticalHeader("qwe");
	}

	public static String generate_8x5_validationString() {

		return "qwe";
	}

	public static Day generate_9x0_Day() {

		return Day.fromYMD(2014, 10, 6);
	}

	public static DayTime generate_9x2_DayTime() {

		return DayTime.fromDayAndSeconds(Day.fromYMD(2014, 10, 6), 12345);
	}

	public static List<IDomNode> generate_9x4_IDomNodes() {

		List<IDomNode> nodes = new ArrayList<>();

		nodes.add(DomTextNode.create("qwe"));
		nodes.add(DomTextNode.create("123.456"));
		nodes.add(DomTextNode.create("zxc"));

		return nodes;
	}

	public static String generate_9x4_validationString() {

		return "qwe123.456zxc";
	}

	public static Day generate_10x0_Day() {

		return Day.fromYMD(2014, 10, 8);
	}

	public static DayTime generate_10x1_DayTime() {

		return DayTime.fromDayAndSeconds(Day.fromYMD(2014, 10, 8), 23456);
	}

	// ----

	private class NullNumberCell extends DomCell implements ITableExportTypedNode {

		@Override
		public TableExportTypedNodeValue getTypedExportNodeValue() {

			return new TableExportTypedNodeValue(TableExportNodeValueType.NUMBER, null);
		}
	}

	private class FontColoredCell extends DomCell implements ITableExportStyledNode {

		private final IColor color = CssColorEnum.RED;

		public FontColoredCell(String content) {

			appendText(content);
			setColor(color);
		}

		@Override
		public TableExportNodeStyle getExportNodeStyle() {

			return new TableExportNodeStyle().setFontColor(color);
		}
	}

	private class DoubleCell extends DomCell implements ITableExportTypedNode, ITableExportStyledNode {

		private final Double cellValue;
		private final IColor bgColor = CssColorEnum.RED.get();

		public DoubleCell(Double cellValue) {

			this.cellValue = cellValue;
			appendText(cellValue + "");

			setBackgroundColor(bgColor);
		}

		@Override
		public TableExportTypedNodeValue getTypedExportNodeValue() {

			return new TableExportTypedNodeValue(TableExportNodeValueType.NUMBER, this.cellValue);
		}

		@Override
		public TableExportNodeStyle getExportNodeStyle() {

			return new TableExportNodeStyle().setBackgroundColor(bgColor);
		}
	}

	private class DayCell extends DomCell implements ITableExportTypedNode {

		private final Day day;

		public DayCell(Day day) {

			this.day = day;
			appendText(day.toString());

			setBackgroundColor(CssColorEnum.GREEN.get());
		}

		@Override
		public TableExportTypedNodeValue getTypedExportNodeValue() {

			return new TableExportTypedNodeValue(TableExportNodeValueType.DAY, day);
		}
	}

	private class DayTimeCell extends DomCell implements ITableExportTypedNode {

		private final DayTime dayTime;

		public DayTimeCell(DayTime dayTime) {

			this.dayTime = dayTime;
			appendText(dayTime.toString());

			setBackgroundColor(CssColorEnum.YELLOW.get());
		}

		@Override
		public TableExportTypedNodeValue getTypedExportNodeValue() {

			return new TableExportTypedNodeValue(TableExportNodeValueType.DAYTIME, dayTime);
		}
	}

	private static class TestButton extends DomButton {

		public static final String LABEL = "this is a label";

		public TestButton() {

			setIcon(DomElementsImages.DIALOG_OKAY.getResource());
			setLabel(IDisplayString.create(LABEL));
		}
	}

	private class RedHeaderCell extends DomHeaderCell implements ITableExportStyledNode {

		public RedHeaderCell() {

			setColor(CssColorEnum.RED);
		}

		@Override
		public TableExportNodeStyle getExportNodeStyle() {

			return new TableExportNodeStyle().setFontColor(CssColorEnum.RED);
		}
	}
}
