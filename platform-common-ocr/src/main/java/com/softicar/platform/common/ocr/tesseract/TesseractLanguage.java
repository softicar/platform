package com.softicar.platform.common.ocr.tesseract;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.ocr.tesseract.trained.data.TesseractTrainedDataFileResourceContainer;
import com.softicar.platform.common.ocr.tesseract.trained.data.TesseractTrainedDataFiles;

/**
 * Enumerates supported document languages for {@link TesseractOcrTextExtractor}
 * processing.
 *
 * @author Alexander Schmidt
 */
public enum TesseractLanguage {

	DEU("deu", TesseractTrainedDataFiles.DEU.getResource()),
	ENG("eng", TesseractTrainedDataFiles.ENG.getResource()),
	//
	;

	private final String iso6393Code;
	private final IResource trainedDataResource;

	private TesseractLanguage(String iso6393Code, IResource trainedDataResource) {

		this.iso6393Code = iso6393Code;
		this.trainedDataResource = trainedDataResource;
	}

	public String getIso6393Code() {

		return iso6393Code;
	}

	public TesseractTrainedDataFileResourceContainer getResourceContainer() {

		return new TesseractTrainedDataFileResourceContainer(trainedDataResource, iso6393Code);
	}
}
