package com.softicar.platform.core.module.tinymce;

import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.dom.elements.DomScript;
import com.softicar.platform.dom.elements.DomTextArea;

public class TinyMceInput extends DomTextArea {

	public TinyMceInput() {

		var script = new DomScript()//
//			.setAsync(true)
			.setType(MimeType.TEXT_JAVASCRIPT)
			.setSrc("service/12ddc505-76c1-4e7f-957d-5f5480e22414/tinymce.js");
		getDomDocument().getHead().appendChild(script);

//		getDomEngine().executeScriptCode("tinymce.init({selector: '#%s',plugins: ['code']});".formatted(getNodeIdString()));
//		getDomEngine().loadScriptLibrary();
	}
}
