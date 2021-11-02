package com.softicar.platform.emf.data.table.column.handler;

import com.softicar.platform.dom.styles.CssTextAlign;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import java.math.BigDecimal;
import java.util.Currency;

public class MoneyColumnHandler extends EmfDataTableValueBasedColumnHandler<BigDecimal> {

	private final Currency currency;

	public MoneyColumnHandler(Currency currency) {

		this.currency = currency;
	}

	@Override
	public void buildCell(IEmfDataTableCell<?, BigDecimal> cell, BigDecimal value) {

		if (value != null) {
			cell.setStyle(CssTextAlign.RIGHT);
			String text = value.toPlainString().replace(".", ",");
			cell.appendText(text + " " + currency.getSymbol());
		}
	}
}
