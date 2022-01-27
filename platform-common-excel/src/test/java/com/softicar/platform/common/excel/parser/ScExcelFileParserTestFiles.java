package com.softicar.platform.common.excel.parser;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.io.resource.supplier.ResourceSupplierFactory;

@ResourceSupplierContainer
public interface ScExcelFileParserTestFiles {

	IResourceSupplierFactory FACTORY = new ResourceSupplierFactory(ScExcelFileParserTestFiles.class);

	IResourceSupplier DATE_EXTRACTION_TEST_XLS = FACTORY.create("date-extraction-test-xls.xls");
	IResourceSupplier DATE_EXTRACTION_TEST_XLSX = FACTORY.create("date-extraction-test-xlsx.xlsx");
	IResourceSupplier LAYOUT_HANDLING_TEST_XLS = FACTORY.create("layout-handling-test-xls.xls");
	IResourceSupplier LAYOUT_HANDLING_TEST_XLSX = FACTORY.create("layout-handling-test-xlsx.xlsx");
	IResourceSupplier NUMBER_EXTRACTION_TEST_XLS = FACTORY.create("number-extraction-test-xls.xls");
	IResourceSupplier NUMBER_EXTRACTION_TEST_XLSX = FACTORY.create("number-extraction-test-xlsx.xlsx");
}
