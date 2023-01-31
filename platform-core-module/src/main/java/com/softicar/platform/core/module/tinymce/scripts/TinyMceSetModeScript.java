package com.softicar.platform.core.module.tinymce.scripts;

import com.softicar.platform.dom.node.IDomNode;

public class TinyMceSetModeScript extends AbstractTinyMceScript {

	private final String mode;

	public TinyMceSetModeScript(IDomNode node, String mode) {

		super(node);
		this.mode = mode;
	}

	@Override
	public String getCode() {

		return "tinymce.get('%s').mode.set('%s')".formatted(node.getNodeIdString(), mode);
	}
}
