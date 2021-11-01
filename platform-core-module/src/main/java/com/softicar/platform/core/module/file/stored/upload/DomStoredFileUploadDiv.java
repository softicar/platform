package com.softicar.platform.core.module.file.stored.upload;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomFileInput;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.button.form.DomFormSubmitButton;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import com.softicar.platform.dom.event.upload.IDomFileUploadHandler;

/**
 * Instantiate with {@link DomStoredFileUploadDivBuilder}.
 */
public class DomStoredFileUploadDiv extends DomDiv implements IDomFileUploadHandler {

	private final IDomFileUploadHandler fileUploadHandler;
	private final DomStoredFileUploadForm form;
	private final int initial;
	private final int maximum;
	private final boolean multipleFile;
	private int count = 0;
	private final INullaryVoidFunction postUploadCallback;

	DomStoredFileUploadDiv(IDomFileUploadHandler fileUploadHandler, INullaryVoidFunction postUploadCallback, int initial, int maximum, boolean multipleFile,
			boolean showHr) {

		this.fileUploadHandler = fileUploadHandler;
		this.postUploadCallback = postUploadCallback;
		this.form = new DomStoredFileUploadForm(this);
		this.initial = initial;
		this.maximum = maximum;
		this.multipleFile = multipleFile;

		if (maximum == 0 || maximum > 1) {
			appendChild(new AddFileInputButton());
			appendNewChild(DomElementTag.BR);
		}

		appendChild(form);
		appendFileInputs();

		if (showHr) {
			appendNewChild(DomElementTag.HR);
		}

		appendNewChild(DomElementTag.BR);

		IDisplayString submitLabel = multipleFile || maximum != 1? CoreI18n.UPLOAD_FILES : CoreI18n.UPLOAD_FILE;
		appendChild(new DomFormSubmitButton(form, CoreImages.STORED_FILE_UPLOAD.getResource(), submitLabel));
	}

	@Override
	public void handleFileUploads(Iterable<IDomFileUpload> fileUploads) {

		fileUploadHandler.handleFileUploads(fileUploads);

		if (postUploadCallback != null) {
			postUploadCallback.apply();
		}
	}

	private void appendFileInputs() {

		for (int i = 0; i < initial; ++i) {
			appendFileInput();
		}
	}

	private void appendFileInput() {

		if (maximum == 0 || count < maximum) {
			if (count > 0) {
				form.appendNewChild(DomElementTag.BR);
			}
			form.appendChild(new DomFileInput(multipleFile));
			++count;
		} else {
			throw new SofticarUserException(CoreI18n.MAXIMUM_OF_ARG1_FILES_REACHED.toDisplay(maximum));
		}
	}

	private class AddFileInputButton extends DomButton {

		public AddFileInputButton() {

			setIcon(CoreImages.STORED_FILE_ADD.getResource());
			setLabel(CoreI18n.MORE_FILES);
			setClickCallback(() -> appendFileInput());
		}
	}
}
