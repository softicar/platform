package com.softicar.platform.core.module.file.stored.preview.image;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.StoredFileResource;
import com.softicar.platform.core.module.file.stored.preview.AbstractStoredFilePreviewPopup;
import com.softicar.platform.dom.elements.image.viewer.DomImageViewer;
import com.softicar.platform.dom.style.ICssLength;
import java.util.List;

public class StoredFileImagePreviewPopup extends AbstractStoredFilePreviewPopup {

	public StoredFileImagePreviewPopup(AGStoredFile file, StoredFileResource resource, ICssLength imageInitialMaxWidth) {

		super(file);
		appendChild(new DomImageViewer(List.of(resource), imageInitialMaxWidth));
	}
}
