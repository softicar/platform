package com.softicar.platform.core.module.file.stored.preview.pdf;

import com.softicar.platform.dom.style.CssEm;
import com.softicar.platform.dom.style.ICssLength;
import java.io.InputStream;
import java.util.function.Supplier;

public class StoredFilePdfDisplayConfiguration {

	private static final ICssLength DEFAULT_WIDTH = new CssEm(75);
	private final Supplier<InputStream> contentSupplier;
	private final String filename;
	private final StoredFilePdfRenderer renderer;
	private final ICssLength width;

	public StoredFilePdfDisplayConfiguration(Supplier<InputStream> contentSupplier, String filename) {

		this.contentSupplier = contentSupplier;
		this.filename = filename;
		this.renderer = new StoredFilePdfRenderer();
		this.width = DEFAULT_WIDTH;
	}

	public Supplier<InputStream> getContentSupplier() {

		return contentSupplier;
	}

	public String getFilename() {

		return filename;
	}

	public StoredFilePdfRenderer getRenderer() {

		return renderer;
	}

	public ICssLength getWidth() {

		return width;
	}
}
