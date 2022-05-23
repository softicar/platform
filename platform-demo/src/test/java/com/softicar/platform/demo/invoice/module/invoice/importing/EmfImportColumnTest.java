package com.softicar.platform.demo.invoice.module.invoice.importing;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.demo.core.module.AGDemoCoreModuleInstance;
import com.softicar.platform.demo.invoice.module.AGDemoInvoiceModuleInstance;
import com.softicar.platform.demo.invoice.module.invoice.AGDemoInvoice;
import com.softicar.platform.demo.invoice.module.invoice.item.AGDemoInvoiceItem;
import com.softicar.platform.demo.invoice.module.test.fixture.DemoInvoiceModuleTestFixtureMethods;
import com.softicar.platform.demo.invoice.module.type.AGDemoInvoiceTypeEnum;
import com.softicar.platform.demo.person.module.AGDemoPerson;
import com.softicar.platform.demo.person.module.AGDemoPersonModuleInstance;
import com.softicar.platform.emf.management.importing.engine.EmfImportColumn;
import com.softicar.platform.emf.management.importing.engine.EmfImportColumnLoadException;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.List;
import org.junit.Test;

public class EmfImportColumnTest<R extends IEmfTableRow<R, P>, P> extends AbstractDbTest implements DemoInvoiceModuleTestFixtureMethods {

	@Test
	public void testIsForeignKeyColumn() {

		EmfImportColumn<AGDemoInvoiceItem, ?> businessUnitModuleInstanceColumn = new EmfImportColumn<>(AGDemoInvoiceItem.INVOICE);
		assertFalse(businessUnitModuleInstanceColumn.isForeignKeyColumn());

		businessUnitModuleInstanceColumn.addParentColumn(createEmfImportColumn(AGDemoInvoiceItem.ID));
		assertTrue(businessUnitModuleInstanceColumn.isForeignKeyColumn());
	}

	@Test
	public void testIsGeneratedPrimaryKeyColumn() {

		assertTrue(new EmfImportColumn<>(AGDemoInvoice.ID).isGeneratedPrimaryKeyColumn());
		assertTrue(new EmfImportColumn<>(AGDemoInvoiceItem.ID).isGeneratedPrimaryKeyColumn());

		assertFalse(new EmfImportColumn<>(AGDemoInvoice.INVOICE_NUMBER).isGeneratedPrimaryKeyColumn());
		assertFalse(new EmfImportColumn<>(AGDemoInvoice.CONTACT).isGeneratedPrimaryKeyColumn());
		assertFalse(new EmfImportColumn<>(AGDemoInvoiceModuleInstance.MODULE_INSTANCE).isGeneratedPrimaryKeyColumn());
	}

	@Test
	public void testGetField() {

		EmfImportColumn<AGDemoInvoice, ?> invoiceNumberColumn = new EmfImportColumn<>(AGDemoInvoice.INVOICE_NUMBER);
		assertEquals(AGDemoInvoice.INVOICE_NUMBER, invoiceNumberColumn.getField());
	}

	@Test
	public void testGetParentColumns() {

		EmfImportColumn<R, ?> column = createEmfImportColumn(AGDemoInvoice.INVOICE_DATE);

		EmfImportColumn<R, ?> numberColumn = createEmfImportColumn(AGDemoInvoice.ID);
		column.addParentColumn(numberColumn);

		EmfImportColumn<R, ?> quantityColumn = createEmfImportColumn(AGDemoInvoiceItem.QUANTITY);
		column.addParentColumn(quantityColumn);

		assertEquals(List.of(numberColumn, quantityColumn), column.getParentColumns());
	}

	@Test
	public void testGetTitle() {

		EmfImportColumn<R, ?> column = createEmfImportColumn(AGDemoInvoiceItem.INVOICE);
		EmfImportColumn<R, ?> parentColumn = createEmfImportColumn(AGDemoInvoiceItem.GROSS_AMOUNT);
		EmfImportColumn<R, ?> grandParentColumn = createEmfImportColumn(AGDemoInvoice.CONTACT);

		column.addParentColumn(parentColumn);
		parentColumn.addParentColumn(grandParentColumn);

		assertEquals(IDisplayString.create("Invoice"), column.getTitle());
		assertEquals(IDisplayString.create("Invoice:[Demo Invoice Item] Gross Amount"), parentColumn.getTitle());
		assertEquals(IDisplayString.create("Invoice:[Demo Invoice Item] Gross Amount:[Demo Invoice] Contact"), grandParentColumn.getTitle());
	}

	@Test
	public void testGetOrLoadValueSimplestCase() {

		AGDemoInvoiceModuleInstance invoiceModuleInstance = insertInvoiceModuleInstance();

		EmfImportColumn<AGDemoInvoice, ?> invoiceModuleInstanceColumn = new EmfImportColumn<>(AGDemoInvoice.MODULE_INSTANCE);

		EmfImportColumn<AGDemoInvoice, ?> invoiceModuleInstanceTitleColumn = createEmfImportColumn(AGDemoInvoiceModuleInstance.TITLE);
		invoiceModuleInstanceTitleColumn.setValue("invoiceModuleInstance");
		invoiceModuleInstanceColumn.addParentColumn(invoiceModuleInstanceTitleColumn);

		assertSame(invoiceModuleInstance, invoiceModuleInstanceColumn.getOrLoadValue());
	}

	@Test
	public void testGetOrLoadValueWithThreeInvolvedTables() {

		AGDemoPerson person = new AGDemoPerson()//
			.setModuleInstance(insertPersonModuleInstance())
			.setFirstName("firstName")
			.setLastName("lastName")
			.setIdentityCardNumber(1234567890)
			.save();

		EmfImportColumn<AGDemoInvoice, ?> invoiceContactColumn = new EmfImportColumn<>(AGDemoInvoice.CONTACT);

		EmfImportColumn<AGDemoInvoice, ?> personModuleInstanceColumn = createEmfImportColumn(AGDemoPerson.MODULE_INSTANCE);
		invoiceContactColumn.addParentColumn(personModuleInstanceColumn);

		EmfImportColumn<AGDemoInvoice, ?> personModuleInstanceTitleColumn = createEmfImportColumn(AGDemoPersonModuleInstance.TITLE);
		personModuleInstanceTitleColumn.setValue("personModuleInstance");
		personModuleInstanceColumn.addParentColumn(personModuleInstanceTitleColumn);

		EmfImportColumn<AGDemoInvoice, ?> personIdentityCardNumberColumn = createEmfImportColumn(AGDemoPerson.IDENTITY_CARD_NUMBER);
		personIdentityCardNumberColumn.setValue("1234567890");
		invoiceContactColumn.addParentColumn(personIdentityCardNumberColumn);

		assertSame(person, invoiceContactColumn.getOrLoadValue());
	}

	private static <T, R extends IEmfTableRow<R, P>, P> T createEmfImportColumn(IDbField<R, ?> field) {

		return CastUtils.cast(new EmfImportColumn<>(field));
	}

	@Test
	public void testGetOrLoadValueWithComplexTableStructure() {

		AGDemoInvoice invoice = insertInvoice();

		EmfImportColumn<R, ?> invoiceItemInvoiceColumn = new InvoiceItemInvoiceColumnCreator<>()//
			.setInvoiceModuleInstanceTitleColumnValue("invoiceModuleInstance")
			.setInvoiceCreditorColumnValue("creditor")
			.setInvoiceDebtorColumnValue("debtor")
			.setInvoiceInvoiceDateValue(Day.fromYMD(2022, 2, 22).toISOString())
			.setInvoiceInvoiceNumberValue("invoiceNumber")
			.getInvoiceItemInvoiceColumn();

		assertSame(invoice, invoiceItemInvoiceColumn.getOrLoadValue());
	}

	@Test
	public void testGetOrLoadValueWithWrongValue() {

		insertInvoice();

		var invoiceItemInvoiceColumnCreator = new InvoiceItemInvoiceColumnCreator<>()//
			.setInvoiceModuleInstanceTitleColumnValue("WRONG invoiceModuleInstance")
			.setInvoiceCreditorColumnValue("creditor")
			.setInvoiceDebtorColumnValue("debtor")
			.setInvoiceInvoiceDateValue(Day.fromYMD(2022, 2, 22).toISOString())
			.setInvoiceInvoiceNumberValue("invoiceNumber");

		EmfImportColumn<R, ?> invoiceItemInvoiceColumn = invoiceItemInvoiceColumnCreator.getInvoiceItemInvoiceColumn();

		assertException(EmfImportColumnLoadException.class, () -> invoiceItemInvoiceColumn.getOrLoadValue());
		try {
			invoiceItemInvoiceColumn.getOrLoadValue();
		} catch (EmfImportColumnLoadException exception) {
			assertEquals(invoiceItemInvoiceColumnCreator.getInvoiceModuleInstanceColumn(), exception.getColumn());
		}
	}

	@Test
	public void testGetOrLoadValueWithNullValue() {

		insertInvoice();

		var invoiceItemInvoiceColumnCreator = new InvoiceItemInvoiceColumnCreator<>()//
			.setInvoiceModuleInstanceTitleColumnValue(null);

		assertNull(invoiceItemInvoiceColumnCreator.getInvoiceModuleInstanceColumn().getOrLoadValue());

		invoiceItemInvoiceColumnCreator//
			.setInvoiceModuleInstanceTitleColumnValue("invoiceModuleInstance");

		assertNotNull(invoiceItemInvoiceColumnCreator.getInvoiceModuleInstanceColumn().getOrLoadValue());
	}

	@Test
	public void testGetOrLoadValueWithNullValueInAllColumns() {

		insertInvoice();

		var invoiceItemInvoiceColumnCreator = new InvoiceItemInvoiceColumnCreator<>()//
			.setInvoiceModuleInstanceTitleColumnValue(null)
			.setInvoiceCreditorColumnValue(null)
			.setInvoiceDebtorColumnValue(null)
			.setInvoiceInvoiceDateValue(null)
			.setInvoiceInvoiceNumberValue(null);

		assertNull(invoiceItemInvoiceColumnCreator.getInvoiceModuleInstanceColumn().getOrLoadValue());

		EmfImportColumn<R, ?> invoiceItemInvoiceColumn = invoiceItemInvoiceColumnCreator.getInvoiceItemInvoiceColumn();
		assertNull(invoiceItemInvoiceColumn.getOrLoadValue());
	}

	@Test
	public void testGetOrLoadValueWithNullValueInOneColumn() {

		insertInvoice();

		var invoiceItemInvoiceColumnCreator = new InvoiceItemInvoiceColumnCreator<>()//
			.setInvoiceModuleInstanceTitleColumnValue("invoiceModuleInstance")
			.setInvoiceCreditorColumnValue(null)
			.setInvoiceDebtorColumnValue("debtor")
			.setInvoiceInvoiceDateValue(Day.fromYMD(2022, 2, 22).toISOString())
			.setInvoiceInvoiceNumberValue("invoiceNumber");

		assertNotNull(invoiceItemInvoiceColumnCreator.getInvoiceModuleInstanceColumn().getOrLoadValue());

		EmfImportColumn<R, ?> invoiceItemInvoiceColumn = invoiceItemInvoiceColumnCreator.getInvoiceItemInvoiceColumn();
		assertException(EmfImportColumnLoadException.class, () -> invoiceItemInvoiceColumn.getOrLoadValue());
	}

	private AGDemoInvoice insertInvoice() {

		return new AGDemoInvoice()//
			.setModuleInstance(insertInvoiceModuleInstance())
			.setCreditor("creditor")
			.setDebtor("debtor")
			.setInvoiceNumber("invoiceNumber")
			.setInvoiceDate(Day.fromYMD(2022, 2, 22))
			.setType(AGDemoInvoiceTypeEnum.INBOUND.getRecord())
			.save();
	}

	private AGDemoInvoiceModuleInstance insertInvoiceModuleInstance() {

		return insertDemoInvoiceModuleInstance(insertPersonModuleInstance())//
			.setTitle("invoiceModuleInstance")
			.save();
	}

	private AGDemoPersonModuleInstance insertPersonModuleInstance() {

		AGDemoCoreModuleInstance coreModuleInstance = createStandardModuleInstance(AGDemoCoreModuleInstance.TABLE)//
			.setTitle("coreModuleInstance")
			.save();
		return createStandardModuleInstance(AGDemoPersonModuleInstance.TABLE)//
			.setDemoCoreModuleInstance(coreModuleInstance)
			.setTitle("personModuleInstance")
			.save();
	}
}
