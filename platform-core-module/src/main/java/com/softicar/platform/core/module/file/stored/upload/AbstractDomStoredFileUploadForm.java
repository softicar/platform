package com.softicar.platform.core.module.file.stored.upload;

import com.softicar.platform.dom.elements.DomForm;
import com.softicar.platform.dom.elements.button.form.DomFormSubmitButton;
import com.softicar.platform.dom.event.upload.IDomFileUpload;
import com.softicar.platform.dom.event.upload.IDomFileUploadHandler;

/**
 * A form for uploading files.
 * <p>
 * You can submit this form via a {@link DomFormSubmitButton}.
 *
 * @author Oliver Richers
 */
public abstract class AbstractDomStoredFileUploadForm extends DomForm implements IDomFileUploadHandler {

	public AbstractDomStoredFileUploadForm() {

		super(true);
	}

	@Override
	public abstract void handleFileUploads(Iterable<IDomFileUpload> fileUploads);
}
