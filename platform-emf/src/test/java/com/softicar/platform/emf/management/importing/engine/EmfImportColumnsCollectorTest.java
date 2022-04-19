package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class EmfImportColumnsCollectorTest<R extends IEmfTableRow<R, P>, P> extends AbstractDbTest {

	@Test
	public void testGetCsvFileColumnsToImport() {

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
	public void testGetTableColumns() {

		assertEquals(createExpectedTableColumns(), new EmfImportColumnsCollector<>(EmfTestInvoiceItem.TABLE).getTableColumns());
	}

	private List<EmfImportColumn<R, ?>> createExpectedTableColumns() {

		List<EmfImportColumn<R, ?>> tableColumns = new ArrayList<>();

		EmfImportColumn<R, ?> itemInvoiceColumn = createEmfImportColumn(EmfTestInvoiceItem.INVOICE);

		EmfImportColumn<R, ?> invoiceInvoiceModuleInstanceColumn = createEmfImportColumn(EmfTestInvoice.INVOICE_MODULE_INSTANCE);
		itemInvoiceColumn.addParentColumn(invoiceInvoiceModuleInstanceColumn);

		EmfImportColumn<R, ?> invoiceModuleInstanceNameColumn = createEmfImportColumn(EmfTestInvoiceModuleInstance.NAME);
		invoiceInvoiceModuleInstanceColumn.addParentColumn(invoiceModuleInstanceNameColumn);

		EmfImportColumn<R, ?> invoicePartnerColumn = createEmfImportColumn(EmfTestInvoice.PARTNER);
		itemInvoiceColumn.addParentColumn(invoicePartnerColumn);

		EmfImportColumn<R, ?> businessPartnerBusinessUnitModuleInstanceColumn = createEmfImportColumn(EmfTestBusinessPartner.BUSINESS_UNIT_MODULE_INSTANCE);
		invoicePartnerColumn.addParentColumn(businessPartnerBusinessUnitModuleInstanceColumn);

		EmfImportColumn<R, ?> businessUnitModuleInstanceNameColumn = createEmfImportColumn(EmfTestBusinessUnitModuleInstance.NAME);
		businessPartnerBusinessUnitModuleInstanceColumn.addParentColumn(businessUnitModuleInstanceNameColumn);

		EmfImportColumn<R, ?> businessPartnerNameColumn = createEmfImportColumn(EmfTestBusinessPartner.NAME);
		invoicePartnerColumn.addParentColumn(businessPartnerNameColumn);

		EmfImportColumn<R, ?> invoiceNumberColumn = createEmfImportColumn(EmfTestInvoice.NUMBER);
		itemInvoiceColumn.addParentColumn(invoiceNumberColumn);

		tableColumns.add(itemInvoiceColumn);
		tableColumns.add(createEmfImportColumn(EmfTestInvoiceItem.POSITION));
		tableColumns.add(createEmfImportColumn(EmfTestInvoiceItem.NAME));
		tableColumns.add(createEmfImportColumn(EmfTestInvoiceItem.QUANTITY));
		return tableColumns;
	}

	private static <T, R extends IEmfTableRow<R, P>, P> T createEmfImportColumn(IDbField<R, ?> field) {

		return CastUtils.cast(new EmfImportColumn<>(field));
	}

	@Test
	public void testGetFieldsOfTableColumns() {

//		List<IDbField<R, ?>> expectedTableColumnFields = createExpectedTableColumns().stream().map(EmfImportColumn::getField).collect(Collectors.toList());

		assertEquals(new EmfImportColumnsCollector<>(EmfTestInvoiceItem.TABLE).getFieldsOfTableColumns(), EmfTestInvoiceItem.TABLE.getAllFields());

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
