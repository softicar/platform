package com.softicar.platform.core.module.file.stored.download;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.StoredFileResource;
import com.softicar.platform.core.module.file.stored.preview.image.StoredFileImagePreviewPopup;
import com.softicar.platform.core.module.file.stored.preview.pdf.StoredFilePdfPreviewPopup;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.configuration.DomPopupConfiguration;
import com.softicar.platform.dom.style.CssPixel;
import com.softicar.platform.dom.style.ICssLength;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class StoredFileViewOrDownloadButton extends DomButton {

	private static final ICssLength IMAGE_INITIAL_MAX_WIDTH = new CssPixel(480);

	private final AGStoredFile file;
	private final StoredFileResource resource;
	private IDisplayString buttonLabel;
	private final FileType fileType;
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

	private FileType getFileType(StoredFileResource resource) {

		return FileType.getByIdentifier(getMimeTypeIdentifier(resource));
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
				previewPopup.show();
				break;
			case CENTERED_ON_VIEWPORT:
				previewPopup//
					.configure(DomPopupConfiguration::setPositionStrategyByViewportCenter)
					.show();
				break;
			case UPPER_LEFT:
				previewPopup//
					.configure(DomPopupConfiguration::setPositionStrategyByViewportOrigin)
					.show();
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
			case UNKNOWN:
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

	private static enum FileType {

		IMAGE(CoreImages.FILE_TYPE_IMAGE.getResource(), MimeType.IMAGE_JPEG, MimeType.IMAGE_PNG, MimeType.IMAGE_GIF, MimeType.IMAGE_BMP,
				MimeType.IMAGE_SVG_XML),
		PDF(CoreImages.FILE_TYPE_PDF.getResource(), MimeType.APPLICATION_PDF),
		UNKNOWN(CoreImages.FILE_TYPE_UNKNOWN.getResource());

		private List<IMimeType> mimeTypes;
		private final IResource icon;

		private FileType(IResource icon, IMimeType...mimeTypes) {

			this.icon = icon;
			this.mimeTypes = Arrays.asList(mimeTypes);
		}

		public IResource getIcon() {

			return icon;
		}

		private static final Map<String, FileType> MAP = new TreeMap<>();

		static {
			for (FileType fileType: FileType.values()) {
				fileType.mimeTypes.stream().forEach(mimeType -> MAP.put(mimeType.getIdentifier(), fileType));
			}
		}

		public static FileType getByIdentifier(String mimeTypeIdentifier) {

			return Optional//
				.ofNullable(MAP.get(mimeTypeIdentifier))
				.orElse(UNKNOWN);
		}
	}
}
