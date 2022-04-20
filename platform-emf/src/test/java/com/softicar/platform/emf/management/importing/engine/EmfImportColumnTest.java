package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import org.junit.Test;

public class EmfImportColumnTest<R extends IEmfTableRow<R, P>, P> extends AbstractEmfTest {

	@Test
	public void testIsForeignKeyColumn() {

		// implement
	}

	@Test
	public void testGetField() {

		// implement
	}

	@Test
	public void testGetParentColumns() {

		// implement
	}

	@Test
	public void testGetTitle() {

		// implement
	}

	@Test
	public void testGetValue() {

		EmfTestInvoiceModuleInstance invoiceModuleInstance = new EmfTestInvoiceModuleInstance()//
			.setModuleInstance(new EmfTestObject().save())
			.setName("test")
			.save();

		EmfImportColumn<EmfTestInvoice, ?> invoiceInvoiceModuleInstanceColumn = new EmfImportColumn<>(EmfTestInvoice.INVOICE_MODULE_INSTANCE);
		EmfImportColumn<EmfTestInvoice, ?> invoiceModuleInstanceNameColumn = createEmfImportColumn(EmfTestInvoiceModuleInstance.NAME);
		invoiceInvoiceModuleInstanceColumn.addParentColumn(invoiceModuleInstanceNameColumn);
		invoiceModuleInstanceNameColumn.setValue("test");

		assertSame(invoiceModuleInstance, invoiceInvoiceModuleInstanceColumn.getValue());
	}

	@Test
	public void testGetValue2() {

		EmfTestObject moduleInstance = new EmfTestObject().save();

		EmfTestInvoiceModuleInstance invoiceModuleInstance = new EmfTestInvoiceModuleInstance()//
			.setModuleInstance(moduleInstance)
			.setName("invoiceModuleInstance")
			.save();

		EmfTestBusinessUnitModuleInstance businessUnitModuleInstance = new EmfTestBusinessUnitModuleInstance()//
			.setModuleInstance(moduleInstance)
			.setName("businessUnitModuleInstance")
			.save();

		EmfTestBusinessPartner businessPartner = new EmfTestBusinessPartner()//
			.setBusinessUnitModuleInstance(businessUnitModuleInstance)
			.setVatId("vatId")
			.save();

		EmfTestInvoice invoice = new EmfTestInvoice()//
			.setInvoiceModuleInstance(invoiceModuleInstance)
			.setPartner(businessPartner)
			.setNumber("number")
			.setDate(Day.today())
			.save();

		EmfImportColumn<R, ?> invoiceItemInvoiceColumn = new InvoiceItemInvoiceColumnCreator<>()//
			.setInvoiceModuleInstanceNameColumnValue("invoiceModuleInstance")
			.setBusinessUnitModuleInstanceNameColumnValue("businessUnitModuleInstance")
			.setBusinessPartnerVatIdColumnValue("vatId")
			.setInvoiceNumberColumnValue("number")
			.getInvoiceItemInvoiceColumn();

		assertSame(invoice, invoiceItemInvoiceColumn.getValue());
	}

	private static <T, R extends IEmfTableRow<R, P>, P> T createEmfImportColumn(IDbField<R, ?> field) {

		return CastUtils.cast(new EmfImportColumn<>(field));
	}

}
