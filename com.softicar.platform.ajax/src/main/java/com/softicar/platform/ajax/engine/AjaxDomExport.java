package com.softicar.platform.ajax.engine;

import com.softicar.platform.ajax.document.AjaxDocument;
import com.softicar.platform.dom.engine.AbstractDomExport;
import java.io.OutputStream;

public class AjaxDomExport extends AbstractDomExport {

	private final AjaxDocument document;

	public AjaxDomExport(AjaxDocument document) {

		this.document = document;
	}

	@Override
	public OutputStream openOutputStream() {

		return document.startBinaryExport(filename, mimeType, charset);
	}
}
