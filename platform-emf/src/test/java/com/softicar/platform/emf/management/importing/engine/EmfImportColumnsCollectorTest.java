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
	public void testGetTableColumns() {

		List<EmfImportColumn<EmfTestInvoiceItem, ?>> expectedTableColumns = new ArrayList<>();

		expectedTableColumns.add(new EmfImportColumn<>(EmfTestInvoiceItem.ID));
		expectedTableColumns.add(CastUtils.cast(createInvoiceColumn()));
		expectedTableColumns.add(new EmfImportColumn<>(EmfTestInvoiceItem.POSITION));
		expectedTableColumns.add(new EmfImportColumn<>(EmfTestInvoiceItem.NAME));
		expectedTableColumns.add(new EmfImportColumn<>(EmfTestInvoiceItem.QUANTITY));

		assertEquals(expectedTableColumns, new EmfImportColumnsCollector<>(EmfTestInvoiceItem.TABLE).getTableColumns());
	}

	@Test
	public void testGetTableColumnsWithFieldSubSet() {

		List<EmfImportColumn<EmfTestInvoiceItem, ?>> expectedTableColumns = new ArrayList<>();
		expectedTableColumns.add(new EmfImportColumn<>(EmfTestInvoiceItem.POSITION));
		expectedTableColumns.add(new EmfImportColumn<>(EmfTestInvoiceItem.QUANTITY));
		expectedTableColumns.add(CastUtils.cast(createInvoiceColumn()));

		List<IDbField<EmfTestInvoiceItem, ?>> fieldsToImport = new ArrayList<>();
		fieldsToImport.add(EmfTestInvoiceItem.POSITION);
		fieldsToImport.add(EmfTestInvoiceItem.QUANTITY);
		fieldsToImport.add(EmfTestInvoiceItem.INVOICE);

		assertEquals(expectedTableColumns, new EmfImportColumnsCollector<>(EmfTestInvoiceItem.TABLE, fieldsToImport).getTableColumns());
	}

	private EmfImportColumn<R, ?> createInvoiceColumn() {

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

		return itemInvoiceColumn;
	}

	@Test
	public void testGetCsvFileColumnsToImport() {

		List<EmfImportColumn<R, ?>> expectedColumns = new ArrayList<>();
		expectedColumns.add(createEmfImportColumn(EmfTestInvoiceItem.ID));
		expectedColumns.add(createEmfImportColumn(EmfTestInvoiceModuleInstance.NAME));
		expectedColumns.add(createEmfImportColumn(EmfTestBusinessUnitModuleInstance.NAME));
		expectedColumns.add(createEmfImportColumn(EmfTestBusinessPartner.NAME));
		expectedColumns.add(createEmfImportColumn(EmfTestInvoice.NUMBER));
		expectedColumns.add(createEmfImportColumn(EmfTestInvoiceItem.POSITION));
		expectedColumns.add(createEmfImportColumn(EmfTestInvoiceItem.NAME));
		expectedColumns.add(createEmfImportColumn(EmfTestInvoiceItem.QUANTITY));

		assertEquals(expectedColumns, new EmfImportColumnsCollector<>(EmfTestInvoiceItem.TABLE).getCsvFileColumnsToImport());
	}

	@Test
	public void testGetCsvFileColumnsToImportWithFieldSubSet() {

		List<EmfImportColumn<R, ?>> expectedColumns = new ArrayList<>();
		expectedColumns.add(createEmfImportColumn(EmfTestInvoiceItem.ID));
		expectedColumns.add(createEmfImportColumn(EmfTestInvoiceItem.POSITION));
		expectedColumns.add(createEmfImportColumn(EmfTestInvoiceItem.NAME));
		expectedColumns.add(createEmfImportColumn(EmfTestInvoiceItem.QUANTITY));

		List<IDbField<EmfTestInvoiceItem, ?>> fieldsToImport = new ArrayList<>();
		fieldsToImport.add(EmfTestInvoiceItem.ID);
		fieldsToImport.add(EmfTestInvoiceItem.POSITION);
		fieldsToImport.add(EmfTestInvoiceItem.NAME);
		fieldsToImport.add(EmfTestInvoiceItem.QUANTITY);

		assertEquals(expectedColumns, new EmfImportColumnsCollector<>(EmfTestInvoiceItem.TABLE, fieldsToImport).getCsvFileColumnsToImport());
	}

	private static <T, R extends IEmfTableRow<R, P>, P> T createEmfImportColumn(IDbField<R, ?> field) {

		return CastUtils.cast(new EmfImportColumn<>(field));
	}

	@Test
	public void testGetFieldOfTableColumnByIndex() {

		var collector = new EmfImportColumnsCollector<>(EmfTestInvoiceItem.TABLE);

		assertEquals(EmfTestInvoiceItem.TABLE.getAllFields().size(), collector.getTableColumns().size());

		for (int index = 0; index < collector.getTableColumns().size(); index++) {
			assertSame(EmfTestInvoiceItem.TABLE.getAllFields().get(index), collector.getFieldOfTableColumnByIndex(index));
		}
	}

	@Test
	public void testGetFieldOfTableColumnByIndexWithFieldSubSet() {

		List<IDbField<EmfTestInvoiceItem, ?>> fieldsToImport = new ArrayList<>();
		fieldsToImport.add(EmfTestInvoiceItem.NAME);
		fieldsToImport.add(EmfTestInvoiceItem.POSITION);
		fieldsToImport.add(EmfTestInvoiceItem.QUANTITY);

		var collector = new EmfImportColumnsCollector<>(EmfTestInvoiceItem.TABLE, fieldsToImport);

		assertSame(EmfTestInvoiceItem.NAME, collector.getFieldOfTableColumnByIndex(0));
		assertSame(EmfTestInvoiceItem.POSITION, collector.getFieldOfTableColumnByIndex(1));
		assertSame(EmfTestInvoiceItem.QUANTITY, collector.getFieldOfTableColumnByIndex(2));
	}

	@Test
	public void testGetTable() {

		assertSame(EmfTestInvoiceItem.TABLE, new EmfImportColumnsCollector<>(EmfTestInvoiceItem.TABLE).getTable());
	}
}
