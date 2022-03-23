package com.softicar.platform.demo.module.test.fixture;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.demo.module.AGDemoModuleInstance;
import com.softicar.platform.demo.module.invoice.AGDemoInvoice;
import com.softicar.platform.demo.module.invoice.item.AGDemoInvoiceItem;
import com.softicar.platform.demo.module.invoice.type.AGDemoInvoiceTypeEnum;
import com.softicar.platform.workflow.module.test.fixture.WorkflowModuleTestFixtureMethods;
import java.math.BigDecimal;

public interface DemoModuleTestFixtureMethods extends WorkflowModuleTestFixtureMethods {

	default AGDemoModuleInstance insertDemoModuleInstance() {

		return insertStandardModuleInstance(AGDemoModuleInstance.TABLE);
	}

	default AGDemoInvoice insertDemoInvoice(AGDemoModuleInstance moduleInstance, AGDemoInvoiceTypeEnum type, String number, Day invoiceDate) {

		return new AGDemoInvoice()//
			.setModuleInstance(moduleInstance)
			.setType(type.getRecord())
			.setCreditor(type == AGDemoInvoiceTypeEnum.INBOUND? "ACME" : "")
			.setDebitor(type == AGDemoInvoiceTypeEnum.OUTBOUND? "ACME" : "")
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
