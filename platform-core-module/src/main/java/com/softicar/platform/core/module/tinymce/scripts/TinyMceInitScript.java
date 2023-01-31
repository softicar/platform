package com.softicar.platform.core.module.tinymce.scripts;

import com.softicar.platform.dom.node.IDomNode;

public class TinyMceInitScript extends AbstractTinyMceScript {

	public TinyMceInitScript(IDomNode node) {

		super(node);
	}

	@Override
	public String getCode() {

		return """
				tinymce.init({
					selector: '#%s',
					promotion: false,
					plugins: [],
					content_style: "@import url('https://fonts.googleapis.com/css2?family=Roboto&family=Source+Sans+Pro&display=swap');",
					font_family_formats: "Andale Mono=andale mono,times; Arial=arial,helvetica,sans-serif; Arial Black=arial black,avant garde; Book Antiqua=book antiqua,palatino; Comic Sans MS=comic sans ms,sans-serif; Courier New=courier new,courier; Georgia=georgia,palatino; Helvetica=helvetica; Impact=impact,chicago; Roboto=roboto; Source Sans Pro=source sans pro; Symbol=symbol; Tahoma=tahoma,arial,helvetica,sans-serif; Terminal=terminal,monaco; Times New Roman=times new roman,times; Trebuchet MS=trebuchet ms,geneva; Verdana=verdana,geneva",
					setup: (editor) => {
						editor.on('Change', (event) => {
							editor.save();
						});
					}
				})
				"""
			.formatted(node.getNodeIdString());
	}
}
