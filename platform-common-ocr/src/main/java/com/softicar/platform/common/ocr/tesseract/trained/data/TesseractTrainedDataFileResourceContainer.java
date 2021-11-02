package com.softicar.platform.common.ocr.tesseract.trained.data;

import com.softicar.platform.common.io.resource.IResource;
import java.util.Objects;

public class TesseractTrainedDataFileResourceContainer {

	private static final String TRAINED_DATA_FILE_SUFFIX = ".traineddata";

	private final IResource resource;
	private final String targetFilename;

	public TesseractTrainedDataFileResourceContainer(IResource resource, String iso6393Code) {

		this.resource = Objects.requireNonNull(resource);
		this.targetFilename = Objects.requireNonNull(iso6393Code) + TRAINED_DATA_FILE_SUFFIX;
	}

	public IResource getResource() {

		return resource;
	}

	public String getTargetFilename() {

		return targetFilename;
	}
}
