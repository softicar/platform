package com.softicar.platform.core.module.file.stored.preview.pdf;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.preview.AbstractStoredFilePreviewPopup;
import com.softicar.platform.dom.elements.image.viewer.DomImageViewer;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.style.CssPixel;
import com.softicar.platform.dom.style.ICssLength;
import com.softicar.platform.emf.EmfI18n;
import java.util.List;

public class StoredFilePdfPreviewPopup extends AbstractStoredFilePreviewPopup {

	private static final ICssLength MAX_WIDTH_DEFAULT_VALUE = new CssPixel(1280);

	public StoredFilePdfPreviewPopup(AGStoredFile file) {

		this(file, MAX_WIDTH_DEFAULT_VALUE);
	}

	public StoredFilePdfPreviewPopup(AGStoredFile file, ICssLength imageInitialMaxWidth) {

		super(file);

		List<IResource> previewImages = StoredFilePdfRenderer.renderPages(file);
		if (!previewImages.isEmpty()) {
			appendChild(new DomImageViewer(previewImages, imageInitialMaxWidth));
		} else {
			appendChild(new DomMessageDiv(DomMessageType.WARNING, EmfI18n.NO_IMAGE_FOUND_FOR_ARG1.toDisplay(file.getFileName())));
		}
	}
}
