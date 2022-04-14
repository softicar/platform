package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import org.junit.Test;

public class EmfImportColumnsCollectorTest extends AbstractDbTest {

	@Test
	public void testGetCsvFileColumnsToImport() {

		EmfImportColumnsCollector<?, ?, ?> collector = new EmfImportColumnsCollector<>(EmfTestInvoiceItem.TABLE);
		collector.getCsvFileColumnsToImport().stream().forEach(column -> Log.finfo(column.getTitle()));
	}

	@Test
	public void testGetTableColumns() {

		// TODO implement
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
