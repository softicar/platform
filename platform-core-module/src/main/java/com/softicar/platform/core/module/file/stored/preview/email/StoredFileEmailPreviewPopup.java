package com.softicar.platform.core.module.file.stored.preview.email;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.preview.AbstractStoredFilePreviewPopup;
import com.softicar.platform.core.module.file.stored.preview.pdf.StoredFilePdfDisplay;
import com.softicar.platform.core.module.file.stored.preview.pdf.StoredFilePdfDisplayConfiguration;
import com.softicar.platform.core.module.file.stored.preview.text.StoredFileTextPreviewDiv;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.elements.tab.DomTab;
import com.softicar.platform.dom.elements.tab.DomTabBar;
import java.io.ByteArrayInputStream;

public class StoredFileEmailPreviewPopup extends AbstractStoredFilePreviewPopup {

	public StoredFileEmailPreviewPopup(AGStoredFile file) {

		super(file);

		var bar = new DomTabBar();
		bar.appendTab(new PdfTab(file));
		bar.appendTab(new SourceTab(file));
		appendChild(bar);
	}

	private class PdfTab extends DomTab {

		public PdfTab(AGStoredFile file) {

			super(CoreI18n.PDF);
			file.convert().toPdfBytes().ifPresentOrElse(this::appendPdfDisplay, this::appendFailureMessage);
		}

		private void appendPdfDisplay(byte[] pdfBytes) {

			var configuration = new StoredFilePdfDisplayConfiguration(() -> new ByteArrayInputStream(pdfBytes), file.getFileName() + ".pdf");
			appendChild(new StoredFilePdfDisplay(configuration));
		}

		private void appendFailureMessage() {

			appendChild(new DomMessageDiv(DomMessageType.WARNING, CoreI18n.FAILED_TO_GENERATE_PREVIEW));
		}
	}

	private class SourceTab extends DomTab {

		public SourceTab(AGStoredFile file) {

			super(CoreI18n.RAW_DATA);

			appendChild(new StoredFileTextPreviewDiv(file));
		}
	}
}
