package com.softicar.platform.core.module.tinymce.scripts;

import com.softicar.platform.dom.node.IDomNode;

public class TinyMceLoadScript extends AbstractTinyMceScript {

	public TinyMceLoadScript(IDomNode node) {

		super(node);
	}

	@Override
	protected String getCode() {

		return "tinymce.get('%s').load()".formatted(node.getNodeIdString());
	}
}
