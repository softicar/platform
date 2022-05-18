package com.softicar.platform.demo.module.invoice;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.demo.module.core.module.AGDemoModuleInstance;
import com.softicar.platform.demo.module.invoice.module.AGDemoInvoiceModuleInstance;
import com.softicar.platform.demo.module.invoice.module.invoice.AGDemoInvoice;
import com.softicar.platform.demo.module.invoice.module.paid.AGDemoInvoicePayment;
import com.softicar.platform.demo.module.invoice.module.test.fixture.DemoInvoiceModuleTestFixtureMethods;
import com.softicar.platform.demo.module.invoice.module.type.AGDemoInvoiceTypeEnum;
import com.softicar.platform.demo.module.person.module.AGDemoPersonModuleInstance;
import com.softicar.platform.emf.test.IEmfTestEngineMethods;
import java.math.BigDecimal;

public abstract class AbstractDemoInvoiceModuleTest extends AbstractCoreTest implements DemoInvoiceModuleTestFixtureMethods, IEmfTestEngineMethods {

	protected final AGDemoInvoiceModuleInstance moduleInstance;

	public AbstractDemoInvoiceModuleTest() {

		AGDemoModuleInstance demoModuleInstance = insertStandardModuleInstance(AGDemoModuleInstance.TABLE);
		AGDemoPersonModuleInstance demoPersonModuleInstance = createStandardModuleInstance(AGDemoPersonModuleInstance.TABLE)//
			.setDemoModuleInstance(demoModuleInstance)
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
