package com.softicar.platform.core.module.file.stored.upload;

import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.event.upload.IDomFileUploadHandler;

/**
 * Simple popup to upload files.
 * <p>
 * The popup contains a number of file inputs and a button to add event more
 * file inputs. If the user clicks on the upload button the selected files will
 * be uploaded and the popup will be closed.
 *
 * @author Oliver Richers
 */
public class DomStoredFileUploadPopup extends DomPopup {

	private final IRefreshable refreshable;
	private final boolean refreshOnCancel;

	public DomStoredFileUploadPopup(IDomFileUploadHandler uploadHandler) {

		this(
			uploadHandler,
			DomStoredFileUploadDivBuilder.DEFAULT_INITIAL_NUMBER_OF_FILES,
			DomStoredFileUploadDivBuilder.DEFAULT_MAXIMUM_NUMBER_OF_FILES,
			DomStoredFileUploadDivBuilder.DEFAULT_MULTIPLE_FILES_MODE,
			null,
			true);
	}

	public DomStoredFileUploadPopup(IDomFileUploadHandler uploadHandler, int initial, int maximum, IRefreshable refreshable) {

		this(uploadHandler, initial, maximum, false, refreshable, true);
	}

	public DomStoredFileUploadPopup(IDomFileUploadHandler uploadHandler, int initial, int maximum, boolean multipleFile, IRefreshable refreshable) {

		this(uploadHandler, initial, maximum, multipleFile, refreshable, true);
	}

	/**
	 * Builds the pop-up with the given initial and maximum number of file
	 * inputs.
	 *
	 * @param uploadHandler
	 *            the handler for the uploads
	 * @param initial
	 *            the initial number of file uploads
	 * @param maximum
	 *            the maximum number of file uploadDomFileUploadPopups
	 */
	public DomStoredFileUploadPopup(IDomFileUploadHandler uploadHandler, int initial, int maximum, boolean multipleFile, IRefreshable refreshable,
			boolean refreshOnCancel) {

		this.refreshable = refreshable;
		this.refreshOnCancel = refreshOnCancel;

		if (maximum == 1) {
			setCaption(CoreI18n.SINGLE_FILE_UPLOAD);
		} else {
			setCaption(CoreI18n.FILE_UPLOAD);
		}

		DomStoredFileUploadDiv fileUploadDiv = new DomStoredFileUploadDivBuilder()
			.setFileUploadHandler(uploadHandler)
			.setPostUploadCallback(() -> hide())
			.setInitialNumberOfFiles(initial)
			.setMaximumNumberOfFiles(maximum)
			.setMultipleFiles(multipleFile)
			.setShowHr(true)
			.build();

		appendChild(fileUploadDiv);
		appendCancelButton();
	}

	@Override
	public void hide() {

		super.hide();

		if (refreshable != null && refreshOnCancel) {
			refreshable.refresh();
		}
	}
}
