package com.softicar.platform.core.module.tinymce.scripts;

import com.softicar.platform.dom.node.IDomNode;

abstract class AbstractTinyMceScript {

	protected final IDomNode node;

	public AbstractTinyMceScript(IDomNode node) {

		this.node = node;
	}

	public void execute() {

		var script = """
				new DelayedExecutor(() => %s)
					.addWaitForVariable('tinymce')
					.addWaitForNodeWithId('%s')
					.start();
				""".formatted(getCode(), node.getNodeIdString());

		node.getDomEngine().executeScriptCode(script);
	}

	protected abstract String getCode();
}
