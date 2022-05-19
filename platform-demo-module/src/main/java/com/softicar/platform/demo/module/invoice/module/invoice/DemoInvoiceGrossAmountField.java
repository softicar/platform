package com.softicar.platform.demo.module.invoice.module.invoice;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.transients.AbstractTransientBigDecimalField;
import com.softicar.platform.db.runtime.transients.IValueAccumulator;
import com.softicar.platform.demo.module.DemoI18n;
import com.softicar.platform.demo.module.invoice.module.invoice.item.AGDemoInvoiceItem;
import java.math.BigDecimal;
import java.util.Set;

public class DemoInvoiceGrossAmountField extends AbstractTransientBigDecimalField<AGDemoInvoice> {

	@Override
	public IDisplayString getTitle() {

		return DemoI18n.GROSS_AMOUNT;
	}

	@Override
	protected void loadValues(Set<AGDemoInvoice> invoices, IValueAccumulator<AGDemoInvoice, BigDecimal> accumulator) {

		AGDemoInvoiceItem.TABLE//
			.createSelect()
			.where(AGDemoInvoiceItem.INVOICE.isIn(invoices))
			.forEach(item -> accumulator.add(item.getInvoice(), item.getGrossAmount()));
	}
}
