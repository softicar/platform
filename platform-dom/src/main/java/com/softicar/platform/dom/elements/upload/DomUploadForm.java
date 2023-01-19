package com.softicar.platform.dom.elements.upload;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomFileInput;
import com.softicar.platform.dom.elements.DomForm;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import com.softicar.platform.dom.event.upload.IDomFileUploadHandler;

public class DomUploadForm extends DomForm implements IDomFileUploadHandler {

	private final DomUploadDiv uploadDiv;
	private IDomFileUploadHandler uploadHandler;

	public DomUploadForm() {

		this(true);
	}

	public DomUploadForm(boolean multiple) {

		super(true);

		this.uploadDiv = new DomUploadDiv(this);
		this.uploadHandler = files -> { /* nothing to do*/ };

		addCssClass(DomCssClasses.DOM_UPLOAD_FORM);
		appendChild(uploadDiv);
		setMultiple(multiple);
	}

	@Override
	public final void handleFileUploads(Iterable<IDomFileUpload> fileUploads) {

		uploadHandler.handleFileUploads(fileUploads);
	}

	public DomUploadForm setUploadHandler(IDomFileUploadHandler uploadHandler) {

		this.uploadHandler = uploadHandler;
		return this;
	}

	public DomUploadForm setMultiple(boolean multiple) {

		uploadDiv.setMultiple(multiple);
		return this;
	}

	private static class DomUploadDiv extends DomDiv {

		private final FileInput input;

		public DomUploadDiv(DomUploadForm form) {

			this.input = new FileInput(form);

			appendChild(new DomImage(DomImages.UPLOAD.getResource()));
			appendText(DomI18n.CHOOSE_OR_DROP_FILE);
			appendChild(input);
		}

		public void setMultiple(boolean multiple) {

			input.setMultiple(multiple);
		}
	}

	private static class FileInput extends DomFileInput {

		public FileInput(DomUploadForm form) {

			setTabIndex(-1);
			setOnChangeHandler(() -> form.uploadFiles());
		}
	}
}
