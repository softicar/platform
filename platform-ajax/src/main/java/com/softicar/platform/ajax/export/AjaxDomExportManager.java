package com.softicar.platform.ajax.export;

import com.softicar.platform.ajax.document.AjaxDocument;
import com.softicar.platform.common.io.mime.IMimeType;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class AjaxDomExportManager {

	private final AjaxDocument document;
	private final List<AjaxDomExportNode> exportNodes;

	public AjaxDomExportManager(AjaxDocument document) {

		this.document = document;
		this.exportNodes = new ArrayList<>();
	}

	public final OutputStream startBinaryExport(String filename, IMimeType mimeType, Charset charset) {

		AjaxDomExportNode exportIFrame = new AjaxDomExportNode(document.getAjaxFramework(), filename, mimeType, charset);
		exportNodes.add(exportIFrame);
		return exportIFrame.createOutputStream();
	}

	public final void finishExports() {

		for (AjaxDomExportNode exportNode: exportNodes) {
			exportNode.closeOutput();
			document.appendToBody(exportNode);
		}

		exportNodes.clear();
	}
}
