package com.softicar.platform.demo.module.test.fixture;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.demo.module.AGDemoInvoiceModuleInstance;
import com.softicar.platform.demo.module.invoice.AGDemoInvoice;
import com.softicar.platform.demo.module.invoice.type.AGDemoInvoiceTypeEnum;
import java.math.BigDecimal;

public class DemoInvoicesTestFixture implements DemoModuleTestFixtureMethods {

	private final AGDemoInvoiceModuleInstance moduleInstance;

	public DemoInvoicesTestFixture(AGDemoInvoiceModuleInstance moduleInstance) {

		this.moduleInstance = moduleInstance;
	}

	public void apply() {

		AGDemoInvoice invoice1 = insertDemoInvoice(moduleInstance, AGDemoInvoiceTypeEnum.INBOUND, "00001", Day.fromYMD(2020, 1, 1));
		insertDemoInvoiceItem(invoice1, "Paper", 100, BigDecimal.valueOf(12), BigDecimal.valueOf(10));
		insertDemoInvoiceItem(invoice1, "Pens", 3, BigDecimal.valueOf(3), BigDecimal.valueOf(2));

		AGDemoInvoice invoice2 = insertDemoInvoice(moduleInstance, AGDemoInvoiceTypeEnum.INBOUND, "00002", Day.fromYMD(2020, 1, 12));
		insertDemoInvoiceItem(invoice2, "Car", 1, BigDecimal.valueOf(20123), BigDecimal.valueOf(18321));
		insertDemoInvoiceItem(invoice2, "Bikes", 2, BigDecimal.valueOf(1223), BigDecimal.valueOf(1001));

		AGDemoInvoice invoice3 = insertDemoInvoice(moduleInstance, AGDemoInvoiceTypeEnum.INBOUND, "00003", Day.fromYMD(2020, 1, 13));
		insertDemoInvoiceItem(invoice3, "Water", 99, BigDecimal.valueOf(8), BigDecimal.valueOf(7));
		insertDemoInvoiceItem(invoice3, "Stones", 33, BigDecimal.valueOf(66), BigDecimal.valueOf(58));
		insertDemoInvoiceItem(invoice3, "Grass", 223, BigDecimal.valueOf(23), BigDecimal.valueOf(20));
	}
}
