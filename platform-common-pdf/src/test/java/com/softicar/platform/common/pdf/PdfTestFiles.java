package com.softicar.platform.common.pdf;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.io.resource.supplier.ResourceSupplierFactory;

@ResourceSupplierContainer
public interface PdfTestFiles {

	IResourceSupplierFactory FACTORY = new ResourceSupplierFactory(PdfTestFiles.class);

	IResourceSupplier PDF_WITH_TWO_LEADING_BLANK_PAGES = FACTORY.create("pdf-with-two-leading-blank-pages.pdf");
	IResourceSupplier PDF_WITHOUT_LEADING_BLANK_PAGES = FACTORY.create("pdf-without-leading-blank-pages.pdf");
	IResourceSupplier TIF_MULTI_IMAGE = FACTORY.create("tif-multi-image.tif");
}
