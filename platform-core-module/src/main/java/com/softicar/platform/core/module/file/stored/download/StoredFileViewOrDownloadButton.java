package com.softicar.platform.core.module.file.stored.download;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.StoredFileResource;
import com.softicar.platform.core.module.file.stored.preview.image.StoredFileImagePreviewPopup;
import com.softicar.platform.core.module.file.stored.preview.pdf.StoredFilePdfPreviewPopup;
import com.softicar.platform.core.module.file.stored.preview.text.StoredFileTextPreviewPopup;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.style.CssPixel;
import com.softicar.platform.dom.style.ICssLength;
import java.util.Optional;

public class StoredFileViewOrDownloadButton extends DomButton {

	private static final ICssLength IMAGE_INITIAL_MAX_WIDTH = new CssPixel(480);

	private final AGStoredFile file;
	private final StoredFileResource resource;
	private final StoredFileViewType fileType;
	private IDisplayString buttonLabel;
	private INullaryVoidFunction preClickCallback;

	private PopupPosition popupPosition = PopupPosition.CENTERED_ON_VIEWPORT;

	public static enum PopupPosition {
		AT_MOUSE_CURSOR,
		CENTERED_ON_VIEWPORT,
		UPPER_LEFT;
	}

	public StoredFileViewOrDownloadButton(AGStoredFile file) {

		this.file = file;
		this.resource = new StoredFileResource(file);
		this.fileType = getFileType(resource);
		this.buttonLabel = IDisplayString.create(file.getFileName());
		this.preClickCallback = INullaryVoidFunction.NO_OPERATION;

		setIcon(fileType.getIcon());
		setClickCallback(this::handleClick);
		showFileNameAsLabel();
	}

	public StoredFileViewOrDownloadButton setButtonLabel(String label) {

		this.buttonLabel = IDisplayString.create(label);
		return this;
	}

	public StoredFileViewOrDownloadButton showFileNameAsLabel() {

		unsetAttribute("title");
		setLabel(buttonLabel);
		return this;
	}

	public StoredFileViewOrDownloadButton showFileNameAsTitle() {

		removeLabel();
		setTitle(buttonLabel);
		return this;
	}

	public StoredFileViewOrDownloadButton setPopupPosition(PopupPosition popupPosition) {

		this.popupPosition = popupPosition;
		return this;
	}

	public StoredFileViewOrDownloadButton setPreClickCallback(INullaryVoidFunction preClickCallback) {

		this.preClickCallback = preClickCallback;
		return this;
	}

	private StoredFileViewType getFileType(StoredFileResource resource) {

		return StoredFileViewType.getByIdentifier(getMimeTypeIdentifier(resource));
	}

	private String getMimeTypeIdentifier(StoredFileResource resource) {

		return Optional//
			.ofNullable(resource.getMimeType())
			.map(IMimeType::getIdentifier)
			.orElse("");
	}

	private void handleClick() {

		preClickCallback.apply();

		DomPopup previewPopup = createPreviewPopup();
		if (previewPopup != null) {
			switch (popupPosition) {
			case AT_MOUSE_CURSOR:
				previewPopup.open();
				break;
			case CENTERED_ON_VIEWPORT:
				previewPopup//
					.configure(it -> it.setPositionStrategyByViewportCenter())
					.open();
				break;
			case UPPER_LEFT:
				previewPopup//
					.configure(it -> it.setPositionStrategyByViewportOrigin())
					.open();
				break;
			}
		} else {
			downloadFile();
		}
	}

	private DomPopup createPreviewPopup() {

		try {
			switch (fileType) {
			case IMAGE:
				return new StoredFileImagePreviewPopup(file, resource, IMAGE_INITIAL_MAX_WIDTH);
			case PDF:
				return new StoredFilePdfPreviewPopup(file, IMAGE_INITIAL_MAX_WIDTH);
			case TEXT:
			case UNKNOWN:
				return new StoredFileTextPreviewPopup(file);
			}
		} catch (Exception exception) {
			Log
				.ferror(
					"Suppressed an Exception while creating a preview for file '%s' [%s] (File Type: %s):",
					file.toDisplayWithoutId(),
					file.getId(),
					fileType);
			Log.ferror(StackTraceFormatting.getStackTraceAsString(exception));
		}
		return null;
	}

	private void downloadFile() {

		CurrentDomDocument.get().getBody().appendChild(new StoredFileDownloadIFrame(file));
	}
}
