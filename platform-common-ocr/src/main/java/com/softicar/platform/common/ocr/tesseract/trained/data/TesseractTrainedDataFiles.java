package com.softicar.platform.common.ocr.tesseract.trained.data;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.io.resource.supplier.ResourceSupplierFactory;

/**
 * Enumerates language-specific Tesseract4 trained data resource files.
 * <p>
 * The resource files are taken from the basic "tessdata_fast" Tesseract4
 * trained data collection, available at
 * <a href="https://github.com/tesseract-ocr/tessdata_fast">GitHub</a>
 * <p>
 * The resource files need to be zip-compressed, and have the ".zip" extension.
 *
 * @author Alexander Schmidt
 */
@ResourceSupplierContainer
public interface TesseractTrainedDataFiles {

	IResourceSupplierFactory FACTORY = new ResourceSupplierFactory(TesseractTrainedDataFiles.class);

	IResourceSupplier DEU = FACTORY.create("deu-traineddata.zip");
	IResourceSupplier ENG = FACTORY.create("eng-traineddata.zip");
}
