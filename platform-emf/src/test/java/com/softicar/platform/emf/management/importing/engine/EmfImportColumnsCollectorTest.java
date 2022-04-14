package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class EmfImportColumnsCollectorTest extends AbstractDbTest {

	@Test
	public <R extends IEmfTableRow<R, P>, P> void testGetCsvFileColumnsToImport() {

		List<EmfImportColumn<R, ?>> csvFileColumns = new ArrayList<>();
		csvFileColumns.add(createEmfImportColumn(EmfTestInvoiceModuleInstance.NAME));
		csvFileColumns.add(createEmfImportColumn(EmfTestBusinessUnitModuleInstance.NAME));
		csvFileColumns.add(createEmfImportColumn(EmfTestBusinessPartner.NAME));
		csvFileColumns.add(createEmfImportColumn(EmfTestInvoice.NUMBER));
		csvFileColumns.add(createEmfImportColumn(EmfTestInvoiceItem.POSITION));
		csvFileColumns.add(createEmfImportColumn(EmfTestInvoiceItem.NAME));
		csvFileColumns.add(createEmfImportColumn(EmfTestInvoiceItem.QUANTITY));

		assertEquals(csvFileColumns, new EmfImportColumnsCollector<>(EmfTestInvoiceItem.TABLE).getCsvFileColumnsToImport());
	}

	@Test
	public <R extends IEmfTableRow<R, P>, P> void testGetTableColumns() {

		List<EmfImportColumn<R, ?>> tableColumns = new ArrayList<>();

		EmfImportColumn<R, ?> itemInvoiceColumn = createEmfImportColumn(EmfTestInvoiceItem.INVOICE);

		EmfImportColumn<R, ?> invoiceInvoiceModuleInstanceColumn = createEmfImportColumn(EmfTestInvoice.INVOICE_MODULE_INSTANCE);
		itemInvoiceColumn.addForeignKeyColumn(invoiceInvoiceModuleInstanceColumn);

		EmfImportColumn<R, ?> invoiceModuleInstanceNameColumn = createEmfImportColumn(EmfTestInvoiceModuleInstance.NAME);
		invoiceInvoiceModuleInstanceColumn.addForeignKeyColumn(invoiceModuleInstanceNameColumn);

		EmfImportColumn<R, ?> invoicePartnerColumn = createEmfImportColumn(EmfTestInvoice.PARTNER);
		itemInvoiceColumn.addForeignKeyColumn(invoicePartnerColumn);

		EmfImportColumn<R, ?> businessPartnerBusinessUnitModuleInstanceColumn = createEmfImportColumn(EmfTestBusinessPartner.BUSINESS_UNIT_MODULE_INSTANCE);
		invoicePartnerColumn.addForeignKeyColumn(businessPartnerBusinessUnitModuleInstanceColumn);

		EmfImportColumn<R, ?> businessUnitModuleInstanceNameColumn = createEmfImportColumn(EmfTestBusinessUnitModuleInstance.NAME);
		businessPartnerBusinessUnitModuleInstanceColumn.addForeignKeyColumn(businessUnitModuleInstanceNameColumn);

		EmfImportColumn<R, ?> businessPartnerNameColumn = createEmfImportColumn(EmfTestBusinessPartner.NAME);
		invoicePartnerColumn.addForeignKeyColumn(businessPartnerNameColumn);

		EmfImportColumn<R, ?> invoiceNumberColumn = createEmfImportColumn(EmfTestInvoice.NUMBER);
		itemInvoiceColumn.addForeignKeyColumn(invoiceNumberColumn);

		tableColumns.add(itemInvoiceColumn);
		tableColumns.add(createEmfImportColumn(EmfTestInvoiceItem.POSITION));
		tableColumns.add(createEmfImportColumn(EmfTestInvoiceItem.NAME));
		tableColumns.add(createEmfImportColumn(EmfTestInvoiceItem.QUANTITY));

		assertEquals(tableColumns, new EmfImportColumnsCollector<>(EmfTestInvoiceItem.TABLE).getTableColumns());
	}

	private <T, R extends IEmfTableRow<R, P>, P> T createEmfImportColumn(IDbField<R, ?> field) {

		return CastUtils.cast(new EmfImportColumn<>(field));
	}

	@Test
	public void testGetFieldsOfTableColumns() {

		// TODO implement
	}

	@Test
	public void testGetFieldOfTableColumnByIndex() {

		// TODO implement
	}

	@Test
	public void testGetTable() {

		assertSame(EmfTestInvoiceItem.TABLE, new EmfImportColumnsCollector<>(EmfTestInvoiceItem.TABLE).getTable());
	}
}
