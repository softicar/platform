package com.softicar.platform.core.module.file.stored.upload;

import com.softicar.platform.dom.event.upload.IDomFileUpload;
import com.softicar.platform.dom.event.upload.IDomFileUploadHandler;

/**
 * An extension to {@link AbstractDomStoredFileUploadForm}.
 * <p>
 * Instead of sub-classing {@link AbstractDomStoredFileUploadForm}, you can
 * specify your own {@link IDomFileUploadHandler}.
 *
 * @see AbstractDomStoredFileUploadForm
 * @author Oliver Richers
 */
public class DomStoredFileUploadForm extends AbstractDomStoredFileUploadForm {

	private final IDomFileUploadHandler uploadHandler;

	public DomStoredFileUploadForm(IDomFileUploadHandler uploadHandler) {

		this.uploadHandler = uploadHandler;
	}

	@Override
	public void handleFileUploads(Iterable<IDomFileUpload> fileUploads) {

		uploadHandler.handleFileUploads(fileUploads);
	}
}
