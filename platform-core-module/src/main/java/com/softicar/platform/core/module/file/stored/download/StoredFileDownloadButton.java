package com.softicar.platform.core.module.file.stored.download;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.StoredFileMarker;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.elements.button.DomButton;

/**
 * A {@link DomButton} to trigger the download of an {@link AGStoredFile}.
 *
 * @author Oliver Richers
 */
public class StoredFileDownloadButton extends DomButton {

	private final AGStoredFile file;

	public StoredFileDownloadButton(AGStoredFile file) {

		this.file = file;

		setIcon(CoreImages.STORED_FILE_DOWNLOAD.getResource());
		setLabel(IDisplayString.create(file.getFileName()));
		setTitle(CoreI18n.CLICK_TO_DOWNLOAD_THE_FILE_ARG1.toDisplay(file.getFileName()));
		setClickCallback(this::download);
		addMarker(StoredFileMarker.DOWNLOAD_FILE_BUTTON);
	}

	private void download() {

		CurrentDomDocument.get().getBody().appendChild(new StoredFileDownloadIFrame(file));
	}
}
