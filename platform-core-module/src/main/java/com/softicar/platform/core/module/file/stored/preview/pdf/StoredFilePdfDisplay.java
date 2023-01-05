package com.softicar.platform.core.module.file.stored.preview.pdf;

import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.image.viewer.DomImageViewer;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.emf.EmfI18n;
import java.io.IOException;
import java.util.List;

public class StoredFilePdfDisplay extends DomDiv {

	public StoredFilePdfDisplay(StoredFilePdfDisplayConfiguration configuration) {

		try (var inputStream = configuration.getContentSupplier().get()) {
			List<IResource> previewImages = configuration.getRenderer().renderPages(inputStream, configuration.getFilename());
			if (!previewImages.isEmpty()) {
				appendChild(new DomImageViewer(previewImages, configuration.getWidth()).addButton(new StoredFilePdfDisplayDownloadButton(configuration)));
			} else {
				appendChild(new DomMessageDiv(DomMessageType.WARNING, EmfI18n.NO_IMAGE_FOUND_FOR_ARG1.toDisplay(configuration.getFilename())));
			}
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}

	private static class StoredFilePdfDisplayDownloadButton extends DomButton {

		private final StoredFilePdfDisplayConfiguration configuration;

		public StoredFilePdfDisplayDownloadButton(StoredFilePdfDisplayConfiguration configuration) {

			this.configuration = configuration;

			setIcon(CoreImages.STORED_FILE_DOWNLOAD.getResource());
			setTitle(CoreI18n.DOWNLOAD_FILE);
			setClickCallback(this::download);
		}

		private void download() {

			var export = getDomDocument()//
				.getEngine()
				.createExport()
				.setMimeType(MimeType.APPLICATION_PDF)
				.setFilename(configuration.getFilename());
			try (var input = configuration.getContentSupplier().get(); var output = export.openOutputStream()) {
				StreamUtils.copy(input, output);
			} catch (IOException exception) {
				throw new RuntimeException(exception);
			}
		}
	}
}
