package com.softicar.platform.core.module.file.stored.preview.image;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.StoredFileResource;
import com.softicar.platform.core.module.file.stored.preview.AbstractStoredFilePreviewPopup;
import com.softicar.platform.core.module.file.stored.preview.LimitedWidthZoomableImage;
import com.softicar.platform.dom.style.ICssLength;

public class StoredFileImagePreviewPopup extends AbstractStoredFilePreviewPopup {

	public StoredFileImagePreviewPopup(AGStoredFile file, StoredFileResource resource, ICssLength imageInitialMaxWidth) {

		super(file);
		appendChild(new LimitedWidthZoomableImage(resource, imageInitialMaxWidth));
	}
}
