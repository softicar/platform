package com.softicar.platform.emf.data.table;

import com.softicar.platform.common.ui.color.RgbColor;
import com.softicar.platform.dom.elements.DomCell;
import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.dom.styles.CssFontStyle;

public class EmfDataTableEmptyRow extends DomRow {

	public EmfDataTableEmptyRow(int columnCount) {

		setMarker(EmfDataTableDivMarker.EMPTY_BODY_ROW);
		appendChild(new EmptyTableDummyCell(columnCount));
	}

	private class EmptyTableDummyCell extends DomCell {

		public EmptyTableDummyCell(int columnCount) {

			setMarker(EmfDataTableDivMarker.EMPTY_BODY_CELL);
			appendText("(");
			appendText(EmfDataTableI18n.NO_ENTRIES_FOUND);
			appendText(")");
			setColSpan(columnCount);
			setStyle(CssFontStyle.ITALIC);
			setColor(new RgbColor(0x888888));
		}
	}
}
