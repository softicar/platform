package com.softicar.platform.demo.invoice.module.test.fixture;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.demo.invoice.module.AGDemoInvoiceModuleInstance;
import com.softicar.platform.demo.invoice.module.invoice.AGDemoInvoice;
import com.softicar.platform.demo.invoice.module.invoice.item.AGDemoInvoiceItem;
import com.softicar.platform.demo.invoice.module.type.AGDemoInvoiceTypeEnum;
import com.softicar.platform.demo.person.module.AGDemoPersonModuleInstance;
import com.softicar.platform.demo.person.module.test.fixtures.DemoPersonModuleTestFixtureMethods;
import java.math.BigDecimal;

public interface DemoInvoiceModuleTestFixtureMethods extends DemoPersonModuleTestFixtureMethods {

	default AGDemoInvoiceModuleInstance insertDemoInvoiceModuleInstance(AGDemoPersonModuleInstance demoPersonModuleInstance) {

		return createStandardModuleInstance(AGDemoInvoiceModuleInstance.TABLE)//
			.setDemoPersonModuleInstance(demoPersonModuleInstance)
			.save();
	}

	default AGDemoInvoice insertDemoInvoice(AGDemoInvoiceModuleInstance moduleInstance, AGDemoInvoiceTypeEnum type, String number, Day invoiceDate) {

		return new AGDemoInvoice()//
			.setModuleInstance(moduleInstance)
			.setType(type.getRecord())
			.setCreditor(type == AGDemoInvoiceTypeEnum.INBOUND? "ACME" : "")
			.setDebtor(type == AGDemoInvoiceTypeEnum.OUTBOUND? "ACME" : "")
			.setInvoiceNumber(number)
			.setInvoiceDate(invoiceDate)
			.save();
	}

	default AGDemoInvoiceItem insertDemoInvoiceItem(AGDemoInvoice invoice, String item, int quantity, BigDecimal grossAmount, BigDecimal netAmount) {

		return new AGDemoInvoiceItem()//
			.setInvoice(invoice)
			.setItem(item)
			.setQuantity(quantity)
			.setGrossAmount(grossAmount)
			.setNetAmount(netAmount)
			.save();
	}
}
