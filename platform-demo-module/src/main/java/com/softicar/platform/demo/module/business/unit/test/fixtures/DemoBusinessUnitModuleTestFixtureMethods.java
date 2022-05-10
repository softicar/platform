package com.softicar.platform.demo.module.business.unit.test.fixtures;

import com.softicar.platform.demo.module.business.unit.AGDemoBusinessUnitModuleInstance;
import com.softicar.platform.workflow.module.test.fixture.WorkflowModuleTestFixtureMethods;

public interface DemoBusinessUnitModuleTestFixtureMethods extends WorkflowModuleTestFixtureMethods {

	default AGDemoBusinessUnitModuleInstance insertDemoBusinessUnitModuleInstance() {

		return insertStandardModuleInstance(AGDemoBusinessUnitModuleInstance.TABLE);
	}

//	default AGDemoInvoice insertDemoInvoice(AGDemoModuleInstance moduleInstance, AGDemoInvoiceTypeEnum type, String number, Day invoiceDate) {
//
//		return new AGDemoInvoice()//
//			.setModuleInstance(moduleInstance)
//			.setType(type.getRecord())
//			.setCreditor(type == AGDemoInvoiceTypeEnum.INBOUND? "ACME" : "")
//			.setDebtor(type == AGDemoInvoiceTypeEnum.OUTBOUND? "ACME" : "")
//			.setInvoiceNumber(number)
//			.setInvoiceDate(invoiceDate)
//			.save();
//	}
//
//	default AGDemoInvoiceItem insertDemoInvoiceItem(AGDemoInvoice invoice, String item, int quantity, BigDecimal grossAmount, BigDecimal netAmount) {
//
//		return new AGDemoInvoiceItem()//
//			.setInvoice(invoice)
//			.setItem(item)
//			.setQuantity(quantity)
//			.setGrossAmount(grossAmount)
//			.setNetAmount(netAmount)
//			.save();
//	}
}
