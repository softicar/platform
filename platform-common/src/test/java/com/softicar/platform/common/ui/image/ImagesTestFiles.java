package com.softicar.platform.common.ui.image;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.io.resource.supplier.ResourceSupplierFactory;

@ResourceSupplierContainer
public interface ImagesTestFiles {

	IResourceSupplierFactory FACTORY = new ResourceSupplierFactory(ImagesTestFiles.class);

	IResourceSupplier JPG_IMAGE_300X150 = FACTORY.create("jpg-image-300x150.jpg");
	IResourceSupplier PNG_IMAGE_350X200 = FACTORY.create("png-image-350x200.png");
	IResourceSupplier PNG_IMAGE_350X200_SINGLE_COLOR = FACTORY.create("png-image-350x200-single-color.png");
	IResourceSupplier TIF_IMAGE_400X200 = FACTORY.create("tif-image-400x200.tif");
	IResourceSupplier TIF_MULTI_IMAGE = FACTORY.create("tif-multi-image.tif");
}
