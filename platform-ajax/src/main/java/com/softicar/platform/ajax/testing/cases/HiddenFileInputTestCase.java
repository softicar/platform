package com.softicar.platform.ajax.testing.cases;

import com.softicar.platform.ajax.image.AjaxImages;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomFileInput;
import com.softicar.platform.dom.elements.DomForm;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import com.softicar.platform.dom.event.upload.IDomFileUploadHandler;
import com.softicar.platform.dom.styles.CssDisplay;

public class HiddenFileInputTestCase extends AbstractTestCaseDiv {

	public HiddenFileInputTestCase() {

		UploadForm uploadForm = appendChild(new UploadForm());

		FileInput fileInput = uploadForm.getFileInput();

		UploadButton button = appendChild(new DomActionBar()).appendChild(new UploadButton());
		getDomEngine().setClickTargetForEventDelegation(button, DomEventType.CLICK, fileInput);
		getDomEngine().setClickTargetForEventDelegation(button, DomEventType.ENTER, fileInput);
		getDomEngine().setClickTargetForEventDelegation(button, DomEventType.SPACE, fileInput);
	}

	private class UploadButton extends DomButton {

		public UploadButton() {

			setIcon(AjaxImages.EMBLEM_AUTO_COMPLETE_VALUE_VALID.getResource());
			setLabel(IDisplayString.create("Click to Upload"));
			setClickCallback(this::handleClick);
		}

		private void handleClick() {

			log("upload clicked");
		}
	}

	private class UploadForm extends DomForm implements IDomFileUploadHandler {

		private final FileInput fileInput;

		public UploadForm() {

			this.fileInput = appendChild(new FileInput(this));
		}

		public FileInput getFileInput() {

			return fileInput;
		}

		@Override
		public void handleFileUploads(Iterable<IDomFileUpload> fileUploads) {

			fileUploads.forEach(upload -> log("got upload %s", upload.getFilename()));
		}
	}

	private class FileInput extends DomFileInput {

		public FileInput(UploadForm form) {

			setStyle(CssDisplay.NONE);
			setMultiple(true);
			setOnChangeHandler(() -> form.uploadFiles());
		}
	}
}
