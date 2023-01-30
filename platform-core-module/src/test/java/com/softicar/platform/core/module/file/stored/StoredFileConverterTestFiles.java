package com.softicar.platform.core.module.file.stored;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.io.resource.supplier.ResourceSupplierFactory;

@ResourceSupplierContainer
public interface StoredFileConverterTestFiles {

	IResourceSupplierFactory FACTORY = new ResourceSupplierFactory(StoredFileConverterTestFiles.class);

	IResourceSupplier JPG_IMAGE_300X150 = FACTORY.create("jpg-image-300x150.jpg");
	IResourceSupplier PNG_IMAGE_350X200 = FACTORY.create("png-image-350x200.png");
	IResourceSupplier TIFF_IMAGE_400X200 = FACTORY.create("tiff-image-400x200.tiff");
}
