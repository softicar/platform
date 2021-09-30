package com.softicar.platform.core.module.file.stored.download;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.StoredFileResource;
import com.softicar.platform.dom.elements.DomIFrame;
import com.softicar.platform.dom.styles.CssDisplay;

/**
 * An HTML <i>IFRAME</i> to initiate the download of an {@link AGStoredFile}.
 *
 * @author Oliver Richers
 */
public class StoredFileDownloadIFrame extends DomIFrame {

	public StoredFileDownloadIFrame(AGStoredFile storedFile) {

		String url = getDomEngine().getResourceUrl(new StoredFileResource(storedFile)).toString();

		setStyle(CssDisplay.NONE);
		setAddress(url);
	}
}
