package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import java.util.List;
import org.junit.Test;

public class EmfImportColumnTest<R extends IEmfTableRow<R, P>, P> extends AbstractEmfTest {

	@Test
	public void testIsForeignKeyColumn() {

		EmfImportColumn<EmfTestBusinessPartner, ?> businessUnitModuleInstanceColumn =
				new EmfImportColumn<>(EmfTestBusinessPartner.BUSINESS_UNIT_MODULE_INSTANCE);
		assertFalse(businessUnitModuleInstanceColumn.isForeignKeyColumn());

		businessUnitModuleInstanceColumn.addParentColumn(createEmfImportColumn(EmfTestBusinessUnitModuleInstance.ID));
		assertTrue(businessUnitModuleInstanceColumn.isForeignKeyColumn());
	}

	@Test
	public void testGetField() {

		EmfImportColumn<EmfTestInvoice, ?> invoiceNumberColumn = new EmfImportColumn<>(EmfTestInvoice.NUMBER);
		assertEquals(EmfTestInvoice.NUMBER, invoiceNumberColumn.getField());
	}

	@Test
	public void testGetParentColumns() {

		EmfImportColumn<R, ?> column = createEmfImportColumn(EmfTestInvoice.DATE);

		EmfImportColumn<R, ?> numberColumn = createEmfImportColumn(EmfTestInvoice.NUMBER);
		column.addParentColumn(numberColumn);

		EmfImportColumn<R, ?> quantityColumn = createEmfImportColumn(EmfTestInvoiceItem.QUANTITY);
		column.addParentColumn(quantityColumn);

		assertEquals(List.of(numberColumn, quantityColumn), column.getParentColumns());
	}

	@Test
	public void testGetTitle() {

		EmfImportColumn<R, ?> column = createEmfImportColumn(EmfTestInvoiceItem.INVOICE);
		EmfImportColumn<R, ?> parentColumn = createEmfImportColumn(EmfTestInvoiceItem.DESCRIPTION);
		EmfImportColumn<R, ?> grandParentColumn = createEmfImportColumn(EmfTestInvoiceItem.QUANTITY);

		column.addParentColumn(parentColumn);
		parentColumn.addParentColumn(grandParentColumn);

		assertEquals(IDisplayString.create("Invoice"), column.getTitle());
		assertEquals(IDisplayString.create("Invoice:Description"), parentColumn.getTitle());
		assertEquals(IDisplayString.create("Invoice:Description:Quantity"), grandParentColumn.getTitle());
	}

	@Test
	public void testGetValue() {

		EmfTestInvoiceModuleInstance invoiceModuleInstance = new EmfTestInvoiceModuleInstance()//
			.setModuleInstance(new EmfTestObject().save())
			.setName("test")
			.save();

		EmfImportColumn<EmfTestInvoice, ?> invoiceInvoiceModuleInstanceColumn = new EmfImportColumn<>(EmfTestInvoice.INVOICE_MODULE_INSTANCE);

		EmfImportColumn<EmfTestInvoice, ?> invoiceModuleInstanceNameColumn = createEmfImportColumn(EmfTestInvoiceModuleInstance.NAME);
		invoiceModuleInstanceNameColumn.setValue("test");
		invoiceInvoiceModuleInstanceColumn.addParentColumn(invoiceModuleInstanceNameColumn);

		assertSame(invoiceModuleInstance, invoiceInvoiceModuleInstanceColumn.getValue());
	}

	@Test
	public void testGetValueWithComplexTableStructure() {

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
			.setNumber(12345)
			.setDate(Day.today())
			.save();

		EmfImportColumn<R, ?> invoiceItemInvoiceColumn = new InvoiceItemInvoiceColumnCreator<>()//
			.setInvoiceModuleInstanceNameColumnValue("invoiceModuleInstance")
			.setBusinessUnitModuleInstanceNameColumnValue("businessUnitModuleInstance")
			.setBusinessPartnerVatIdColumnValue("vatId")
			.setInvoiceNumberColumnValue("12345")
			.getInvoiceItemInvoiceColumn();

		assertSame(invoice, invoiceItemInvoiceColumn.getValue());
	}

	private static <T, R extends IEmfTableRow<R, P>, P> T createEmfImportColumn(IDbField<R, ?> field) {

		return CastUtils.cast(new EmfImportColumn<>(field));
	}

}
