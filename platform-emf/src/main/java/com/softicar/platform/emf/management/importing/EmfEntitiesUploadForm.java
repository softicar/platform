package com.softicar.platform.emf.management.importing;

import com.softicar.platform.dom.elements.DomFileInput;
import com.softicar.platform.dom.elements.DomForm;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import com.softicar.platform.dom.event.upload.IDomFileUploadHandler;
import com.softicar.platform.dom.styles.CssDisplay;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import java.util.function.Consumer;

class EmfEntitiesUploadForm extends DomForm implements IDomFileUploadHandler {

	private final Consumer<Iterable<IDomFileUpload>> consumer;

	public EmfEntitiesUploadForm(Consumer<Iterable<IDomFileUpload>> consumer) {

		this.consumer = consumer;

		FileInput fileInput = new FileInput();
		DomButton uploadButton = new DomButton()//
			.setIcon(EmfImages.ENTITY_IMPORT.getResource())
			.setLabel(EmfI18n.IMPORT);

		appendChild(fileInput);
		appendChild(uploadButton);

		getDomEngine().setClickTargetForEventDelegation(uploadButton, DomEventType.CLICK, fileInput);
		getDomEngine().setClickTargetForEventDelegation(uploadButton, DomEventType.ENTER, fileInput);
		getDomEngine().setClickTargetForEventDelegation(uploadButton, DomEventType.SPACE, fileInput);
	}

	@Override
	public void handleFileUploads(Iterable<IDomFileUpload> fileUploads) {

		consumer.accept(fileUploads);
	}

	private class FileInput extends DomFileInput {

		public FileInput() {

			setStyle(CssDisplay.NONE);
			setMultiple(true);

			getDomEngine().submitFormOnChange(EmfEntitiesUploadForm.this, this);
		}
	}
}
