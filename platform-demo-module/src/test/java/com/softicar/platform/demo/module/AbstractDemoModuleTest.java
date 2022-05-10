package com.softicar.platform.demo.module;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.demo.module.invoice.AGDemoInvoice;
import com.softicar.platform.demo.module.invoice.paid.AGDemoInvoicePayment;
import com.softicar.platform.demo.module.invoice.type.AGDemoInvoiceTypeEnum;
import com.softicar.platform.demo.module.test.fixture.DemoModuleTestFixtureMethods;
import com.softicar.platform.emf.test.IEmfTestEngineMethods;
import java.math.BigDecimal;

public abstract class AbstractDemoModuleTest extends AbstractCoreTest implements DemoModuleTestFixtureMethods, IEmfTestEngineMethods {

	protected final AGDemoInvoiceModuleInstance moduleInstance;

	public AbstractDemoModuleTest() {

		this.moduleInstance = insertDemoModuleInstance();
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
