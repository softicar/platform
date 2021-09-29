package com.softicar.platform.emf.data.table.column.handler;

import com.softicar.platform.dom.styles.CssTextAlign;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import java.math.BigDecimal;

public class QuantityBigDecimalColumnHandler extends EmfDataTableValueBasedColumnHandler<BigDecimal> {

	@Override
	public void buildCell(IEmfDataTableCell<?, BigDecimal> cell, BigDecimal value) {

		if (value != null) {
			cell.appendText(value.toPlainString().replace(".", ","));
			cell.setStyle(CssTextAlign.RIGHT);
		}
	}
}
