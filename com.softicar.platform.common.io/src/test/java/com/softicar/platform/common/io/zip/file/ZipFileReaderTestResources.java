package com.softicar.platform.common.io.zip.file;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.io.resource.supplier.ResourceSupplierFactory;

@ResourceSupplierContainer
public interface ZipFileReaderTestResources {

	IResourceSupplierFactory FACTORY = new ResourceSupplierFactory(ZipFileReaderTestResources.class);

	IResourceSupplier TEST_FILE = FACTORY.create("zip-file-reader-test-file.zip");
}
