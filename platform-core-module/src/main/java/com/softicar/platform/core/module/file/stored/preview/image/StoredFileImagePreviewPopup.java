package com.softicar.platform.core.module.file.stored.preview.image;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.StoredFileResource;
import com.softicar.platform.core.module.file.stored.download.StoredFileDownloadButton;
import com.softicar.platform.core.module.file.stored.preview.AbstractStoredFilePreviewPopup;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.image.viewer.DomImageViewer;
import com.softicar.platform.dom.style.CssEm;
import com.softicar.platform.dom.style.ICssLength;
import java.util.List;

public class StoredFileImagePreviewPopup extends AbstractStoredFilePreviewPopup {

	private static final ICssLength DEFAULT_WIDTH = new CssEm(75);

	public StoredFileImagePreviewPopup(AGStoredFile file, StoredFileResource resource) {

		super(file);

		appendChild(new StoredFileDownloadButton(file));
		appendNewChild(DomElementTag.HR);
		appendChild(new DomImageViewer(List.of(resource), DEFAULT_WIDTH));
	}
}
