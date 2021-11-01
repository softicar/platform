package com.softicar.platform.emf.data.table.column.handler;

import com.softicar.platform.dom.styles.CssTextAlign;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class QuantityDoubleColumnHandler extends EmfDataTableValueBasedColumnHandler<Double> {

	@Override
	public void buildCell(IEmfDataTableCell<?, Double> cell, Double value) {

		if (value != null) {
			String text = BigDecimal.valueOf(value).setScale(3, RoundingMode.HALF_UP).toPlainString().replace(".", ",");
			cell.appendText(text);
			cell.setStyle(cell.getColumn().getSettings().getAlignmentOrDefault(CssTextAlign.RIGHT));
		}
	}
}
