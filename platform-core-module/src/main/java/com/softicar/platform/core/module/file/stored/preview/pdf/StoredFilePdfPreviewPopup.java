package com.softicar.platform.core.module.file.stored.preview.pdf;

import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.preview.AbstractStoredFilePreviewPopup;
import com.softicar.platform.dom.elements.image.viewer.DomImageViewer;
import com.softicar.platform.dom.elements.image.viewer.DomImageViewerImage;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.style.CssPixel;
import com.softicar.platform.dom.style.ICssLength;
import com.softicar.platform.emf.EmfI18n;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StoredFilePdfPreviewPopup extends AbstractStoredFilePreviewPopup {

	private static final ICssLength MAX_WIDTH_DEFAULT_VALUE = new CssPixel(1280);

	public StoredFilePdfPreviewPopup(AGStoredFile file) {

		this(file, MAX_WIDTH_DEFAULT_VALUE);
	}

	public StoredFilePdfPreviewPopup(AGStoredFile file, ICssLength imageInitialMaxWidth) {

		super(file);
		List<DomImageViewerImage> previewImages = getPreviewImages(file, imageInitialMaxWidth);
		if (!previewImages.isEmpty()) {
			appendChild(new DomImageViewer(previewImages));
		} else {
			appendChild(new DomMessageDiv(DomMessageType.WARNING, EmfI18n.NO_IMAGE_FOUND_FOR_ARG1.toDisplay(file.getFileName())));
		}
	}

	private List<DomImageViewerImage> getPreviewImages(AGStoredFile file, ICssLength maxWidth) {

		return Optional//
			.of(file)
			.map(it -> StoredFilePdfToZoomableImagesConverter.convertPagesToImages(file, maxWidth))
			.orElseGet(ArrayList::new);
	}
}
