package com.softicar.platform.demo.invoice.module.invoice.importing;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.demo.invoice.module.AGDemoInvoiceModuleInstance;
import com.softicar.platform.demo.invoice.module.invoice.AGDemoInvoice;
import com.softicar.platform.demo.invoice.module.invoice.item.AGDemoInvoiceItem;
import com.softicar.platform.demo.person.module.AGDemoPerson;
import com.softicar.platform.demo.person.module.AGDemoPersonModuleInstance;
import com.softicar.platform.emf.management.importing.engine.EmfImportColumn;
import com.softicar.platform.emf.management.importing.engine.EmfImportColumnsStructure;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class EmfImportColumnsStructureTest<R extends IEmfTableRow<R, P>, P> extends AbstractDbTest {

	@Test
	public void testGetTableColumns() {

		List<EmfImportColumn<AGDemoInvoiceItem, ?>> expectedTableColumns = new ArrayList<>();

		expectedTableColumns.add(new EmfImportColumn<>(AGDemoInvoiceItem.ID));
		expectedTableColumns.add(new InvoiceItemInvoiceColumnCreator<>().getInvoiceItemInvoiceColumn());
		expectedTableColumns.add(new EmfImportColumn<>(AGDemoInvoiceItem.ITEM));
		expectedTableColumns.add(new EmfImportColumn<>(AGDemoInvoiceItem.QUANTITY));
		expectedTableColumns.add(new EmfImportColumn<>(AGDemoInvoiceItem.GROSS_AMOUNT));
		expectedTableColumns.add(new EmfImportColumn<>(AGDemoInvoiceItem.NET_AMOUNT));
		expectedTableColumns.add(new InvoiceItemClerkColumnCreator<>().getInvoiceItemClerkColumn());

		assertEquals(expectedTableColumns, new EmfImportColumnsStructure<>(AGDemoInvoiceItem.TABLE).getTableColumns());
	}

	@Test
	public void testGetTableColumnsWithFieldSubSet() {

		List<EmfImportColumn<AGDemoInvoiceItem, ?>> expectedTableColumns = new ArrayList<>();
		expectedTableColumns.add(new EmfImportColumn<>(AGDemoInvoiceItem.ITEM));
		expectedTableColumns.add(new EmfImportColumn<>(AGDemoInvoiceItem.QUANTITY));
		expectedTableColumns.add(new InvoiceItemInvoiceColumnCreator<>().getInvoiceItemInvoiceColumn());

		List<IDbField<AGDemoInvoiceItem, ?>> fieldsToImport = new ArrayList<>();
		fieldsToImport.add(AGDemoInvoiceItem.ITEM);
		fieldsToImport.add(AGDemoInvoiceItem.QUANTITY);
		fieldsToImport.add(AGDemoInvoiceItem.INVOICE);

		assertEquals(expectedTableColumns, new EmfImportColumnsStructure<>(AGDemoInvoiceItem.TABLE, fieldsToImport).getTableColumns());
	}

	@Test
	public void testGetCsvFileColumns() {

		List<EmfImportColumn<R, ?>> expectedColumns = new ArrayList<>();
		expectedColumns.add(createEmfImportColumn(AGDemoInvoiceItem.ID));
		expectedColumns.add(createEmfImportColumn(AGDemoInvoiceModuleInstance.TITLE));
		expectedColumns.add(createEmfImportColumn(AGDemoInvoice.CREDITOR));
		expectedColumns.add(createEmfImportColumn(AGDemoInvoice.DEBTOR));
		expectedColumns.add(createEmfImportColumn(AGDemoInvoice.INVOICE_NUMBER));
		expectedColumns.add(createEmfImportColumn(AGDemoInvoice.INVOICE_DATE));
		expectedColumns.add(createEmfImportColumn(AGDemoInvoiceItem.ITEM));
		expectedColumns.add(createEmfImportColumn(AGDemoInvoiceItem.QUANTITY));
		expectedColumns.add(createEmfImportColumn(AGDemoInvoiceItem.GROSS_AMOUNT));
		expectedColumns.add(createEmfImportColumn(AGDemoInvoiceItem.NET_AMOUNT));
		expectedColumns.add(createEmfImportColumn(AGDemoPersonModuleInstance.TITLE));
		expectedColumns.add(createEmfImportColumn(AGDemoPerson.IDENTITY_CARD_NUMBER));

		assertEquals(expectedColumns, new EmfImportColumnsStructure<>(AGDemoInvoiceItem.TABLE).getCsvFileColumns());
	}

	@Test
	public void testGetCsvFileColumnsWithFieldSubSet() {

		List<EmfImportColumn<R, ?>> expectedColumns = new ArrayList<>();
		expectedColumns.add(createEmfImportColumn(AGDemoInvoiceItem.ID));
		expectedColumns.add(createEmfImportColumn(AGDemoInvoiceItem.QUANTITY));
		expectedColumns.add(createEmfImportColumn(AGDemoInvoiceItem.ITEM));
		expectedColumns.add(createEmfImportColumn(AGDemoInvoiceItem.GROSS_AMOUNT));

		List<IDbField<AGDemoInvoiceItem, ?>> fieldsToImport = new ArrayList<>();
		fieldsToImport.add(AGDemoInvoiceItem.ID);
		fieldsToImport.add(AGDemoInvoiceItem.QUANTITY);
		fieldsToImport.add(AGDemoInvoiceItem.ITEM);
		fieldsToImport.add(AGDemoInvoiceItem.GROSS_AMOUNT);

		assertEquals(expectedColumns, new EmfImportColumnsStructure<>(AGDemoInvoiceItem.TABLE, fieldsToImport).getCsvFileColumns());
	}

	private static <T, R extends IEmfTableRow<R, P>, P> T createEmfImportColumn(IDbField<R, ?> field) {

		return CastUtils.cast(new EmfImportColumn<>(field));
	}

	@Test
	public void testGetTable() {

		assertSame(AGDemoInvoiceItem.TABLE, new EmfImportColumnsStructure<>(AGDemoInvoiceItem.TABLE).getTable());
	}
}
