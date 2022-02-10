package com.softicar.platform.emf.management.importing.upload;

import com.softicar.platform.dom.elements.DomFileInput;
import com.softicar.platform.dom.elements.DomForm;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import com.softicar.platform.dom.event.upload.IDomFileUploadHandler;
import com.softicar.platform.dom.styles.CssDisplay;
import java.util.function.Consumer;

class EmfImportUploadForm extends DomForm implements IDomFileUploadHandler {

	private final Consumer<Iterable<IDomFileUpload>> consumer;
	private final FileInput fileInput;

	public EmfImportUploadForm(Consumer<Iterable<IDomFileUpload>> consumer) {

		this.consumer = consumer;
		this.fileInput = new FileInput();

		appendChild(fileInput);
	}

	public EmfImportUploadForm setupEventDelegation(DomButton button) {

		getDomEngine().setClickTargetForEventDelegation(button, DomEventType.CLICK, fileInput);
		getDomEngine().setClickTargetForEventDelegation(button, DomEventType.ENTER, fileInput);
		getDomEngine().setClickTargetForEventDelegation(button, DomEventType.SPACE, fileInput);
		return this;
	}

	@Override
	public void handleFileUploads(Iterable<IDomFileUpload> fileUploads) {

		consumer.accept(fileUploads);
	}

	private class FileInput extends DomFileInput {

		public FileInput() {

			setStyle(CssDisplay.NONE);
			setMultiple(true);

			getDomEngine().submitFormOnChange(EmfImportUploadForm.this, this);
		}
	}
}
