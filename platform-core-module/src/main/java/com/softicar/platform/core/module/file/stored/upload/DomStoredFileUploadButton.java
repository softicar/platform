package com.softicar.platform.core.module.file.stored.upload;

import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.event.upload.IDomFileUploadHandler;

/**
 * A button that opens a {@link DomStoredFileUploadPopup} when clicked.
 *
 * @author Oliver Richers
 */
public class DomStoredFileUploadButton extends DomButton {

	private final IDomFileUploadHandler uploadHandler;
	private final IRefreshable refreshable;
	private final boolean multipleFile;
	private final Integer initialNumberOfFields;
	private final Integer maximumNumberOfFields;
	private boolean refreshOnCancel = true;

	public DomStoredFileUploadButton(IDomFileUploadHandler uploadHandler) {

		this(uploadHandler, null, null, null, false);
	}

	public DomStoredFileUploadButton(IDomFileUploadHandler uploadHandler, boolean multipleFile) {

		this(uploadHandler, null, null, null, multipleFile);
	}

	public DomStoredFileUploadButton(IDomFileUploadHandler uploadHandler, IRefreshable refreshable) {

		this(uploadHandler, refreshable, null, null, false);
	}

	public DomStoredFileUploadButton(IDomFileUploadHandler uploadHandler, IRefreshable refreshable, boolean multipleFile) {

		this(uploadHandler, refreshable, multipleFile? 1 : null, multipleFile? 1 : null, multipleFile);
	}

	public DomStoredFileUploadButton(IDomFileUploadHandler uploadHandler, IRefreshable refreshable, Integer initialNumberOfFields,
			Integer maximumNumberOfFields) {

		this(uploadHandler, refreshable, initialNumberOfFields, maximumNumberOfFields, false);
	}

	public DomStoredFileUploadButton(IDomFileUploadHandler uploadHandler, IRefreshable refreshable, Integer initialNumberOfFields,
			Integer maximumNumberOfFields, boolean multipleFile) {

		this.uploadHandler = uploadHandler;
		this.refreshable = refreshable;
		this.initialNumberOfFields = initialNumberOfFields != null? initialNumberOfFields : DomStoredFileUploadDivBuilder.DEFAULT_INITIAL_NUMBER_OF_FILES;
		this.maximumNumberOfFields = maximumNumberOfFields != null? maximumNumberOfFields : DomStoredFileUploadDivBuilder.DEFAULT_MAXIMUM_NUMBER_OF_FILES;
		this.multipleFile = multipleFile;
		//
		setIcon(CoreImages.STORED_FILE_UPLOAD.getResource());
		if (maximumNumberOfFields != null && maximumNumberOfFields == 1) {
			setLabel(CoreI18n.UPLOAD_FILE.concat("..."));
		} else {
			setLabel(CoreI18n.UPLOAD_FILES.concat("..."));
		}
		setClickCallback(this::showPopup);
	}

	/**
	 * @param refreshOnCancel
	 *            If true, the "Cancel" button in the pop-up will not trigger
	 *            the given refreshable, if any.
	 */
	public void setPopupTriggersRefreshableOnCancel(boolean refreshOnCancel) {

		this.refreshOnCancel = refreshOnCancel;
	}

	private void showPopup() {

		new DomStoredFileUploadPopup(uploadHandler, initialNumberOfFields, maximumNumberOfFields, multipleFile, refreshable, refreshOnCancel).show();
	}
}
