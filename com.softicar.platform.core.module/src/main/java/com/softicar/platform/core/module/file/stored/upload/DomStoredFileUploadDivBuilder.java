package com.softicar.platform.core.module.file.stored.upload;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.event.upload.IDomFileUploadHandler;
import java.util.Objects;

public class DomStoredFileUploadDivBuilder {

	public static final int DEFAULT_INITIAL_NUMBER_OF_FILES = 1;
	public static final int DEFAULT_MAXIMUM_NUMBER_OF_FILES = 255;
	public static final boolean DEFAULT_MULTIPLE_FILES_MODE = false;

	private IDomFileUploadHandler fileUploadHandler = null;
	private INullaryVoidFunction postUploadCallback = null;
	private int initialNumberOfFiles = DEFAULT_INITIAL_NUMBER_OF_FILES;
	private int maximumNumberOfFiles = DEFAULT_INITIAL_NUMBER_OF_FILES;
	private boolean multipleFiles = DEFAULT_MULTIPLE_FILES_MODE;
	private boolean showHr = false;

	public DomStoredFileUploadDivBuilder setFileUploadHandler(IDomFileUploadHandler fileUploadHandler) {

		this.fileUploadHandler = fileUploadHandler;
		return this;
	}

	public DomStoredFileUploadDivBuilder setPostUploadCallback(INullaryVoidFunction postUploadCallback) {

		this.postUploadCallback = postUploadCallback;
		return this;
	}

	public DomStoredFileUploadDivBuilder setInitialNumberOfFiles(int initialNumberOfFiles) {

		if (initialNumberOfFiles <= 0) {
			initialNumberOfFiles = DEFAULT_INITIAL_NUMBER_OF_FILES;
		}
		this.initialNumberOfFiles = initialNumberOfFiles;
		return this;
	}

	public DomStoredFileUploadDivBuilder setMaximumNumberOfFiles(int maximumNumberOfFiles) {

		if (maximumNumberOfFiles <= 0) {
			maximumNumberOfFiles = DEFAULT_MAXIMUM_NUMBER_OF_FILES;
		}
		this.maximumNumberOfFiles = maximumNumberOfFiles;
		return this;
	}

	public DomStoredFileUploadDivBuilder setMultipleFiles(boolean multipleFiles) {

		this.multipleFiles = multipleFiles;
		return this;
	}

	public DomStoredFileUploadDivBuilder setShowHr(boolean showHr) {

		this.showHr = showHr;
		return this;
	}

	public DomStoredFileUploadDiv build() {

		validate();

		return new DomStoredFileUploadDiv(fileUploadHandler, postUploadCallback, initialNumberOfFiles, maximumNumberOfFiles, multipleFiles, showHr);
	}

	private void validate() {

		Objects.requireNonNull(fileUploadHandler, "missing file upload handler");

		if (initialNumberOfFiles < 0) {
			throw new SofticarDeveloperException("The initial number of files must not be negative.");
		}

		if (maximumNumberOfFiles < 1) {
			throw new SofticarDeveloperException("The maximum number of files must be equal to or greater than 1.");
		}

		if (initialNumberOfFiles > maximumNumberOfFiles) {
			throw new SofticarDeveloperException("The initial number of files must not exceed the maximum number of files.");
		}
	}
}
