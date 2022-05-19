package com.softicar.platform.demo.invoice.module;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.demo.core.module.AGDemoCoreModuleInstance;
import com.softicar.platform.demo.invoice.module.invoice.AGDemoInvoice;
import com.softicar.platform.demo.invoice.module.paid.AGDemoInvoicePayment;
import com.softicar.platform.demo.invoice.module.test.fixture.DemoInvoiceModuleTestFixtureMethods;
import com.softicar.platform.demo.invoice.module.type.AGDemoInvoiceTypeEnum;
import com.softicar.platform.demo.person.module.AGDemoPersonModuleInstance;
import com.softicar.platform.emf.test.IEmfTestEngineMethods;
import java.math.BigDecimal;

public abstract class AbstractDemoInvoiceModuleTest extends AbstractCoreTest implements DemoInvoiceModuleTestFixtureMethods, IEmfTestEngineMethods {

	protected final AGDemoInvoiceModuleInstance moduleInstance;

	public AbstractDemoInvoiceModuleTest() {

		AGDemoCoreModuleInstance demoCoreModuleInstance = insertStandardModuleInstance(AGDemoCoreModuleInstance.TABLE);
		AGDemoPersonModuleInstance demoPersonModuleInstance = createStandardModuleInstance(AGDemoPersonModuleInstance.TABLE)//
			.setDemoCoreModuleInstance(demoCoreModuleInstance)
			.save();
		this.moduleInstance = insertDemoInvoiceModuleInstance(demoPersonModuleInstance);
	}

	protected AGDemoInvoice insertDemoInvoice(String invoiceNumber, Day invoiceDate) {

		return new AGDemoInvoice()//
			.setModuleInstance(moduleInstance)
			.setInvoiceNumber(invoiceNumber)
			.setInvoiceDate(invoiceDate)
			.setType(AGDemoInvoiceTypeEnum.OUTBOUND.getRecord())
			.setCreditor("Creditor")
			.setDebtor("Debitor")
			.save();
	}

	protected void insertDemoInvoicePayment(AGDemoInvoice invoice, Day paidAt, BigDecimal paidAmount) {

		AGDemoInvoicePayment.TABLE//
			.getOrCreate(invoice)
			.setPaidAt(paidAt)
			.setPaidAmount(paidAmount)
			.save();
	}
}
