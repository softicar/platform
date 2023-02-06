package com.softicar.platform.core.module.tinymce;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePointUuid;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.core.module.CoreCssFiles;
import com.softicar.platform.core.module.web.library.WebLibrary;
import com.softicar.platform.core.module.web.service.WebServiceUrlBuilder;
import com.softicar.platform.dom.document.IDomDocument;

@SourceCodeReferencePointUuid("12ddc505-76c1-4e7f-957d-5f5480e22414")
public class TinyMceLibrary extends WebLibrary {

	private static final String ROOT = "/META-INF/resources/webjars/tinymce/6.3.1";

	public TinyMceLibrary() {

		super(ROOT);
	}

	public static void install(IDomDocument document) {

		registerScript(document);
		registerCss(document);
	}

	private static void registerScript(IDomDocument document) {

		var scriptUrl = new WebServiceUrlBuilder(TinyMceLibrary.class)//
			.addPathElement("tinymce.min.js")
			.build()
			.getStartingFromPath();
		document.registerScript(scriptUrl, MimeType.TEXT_JAVASCRIPT);
	}

	private static void registerCss(IDomDocument document) {

		document.getEngine().registerCssResourceLink(CoreCssFiles.TINY_MCE_STYLE.getResource());
	}
}
