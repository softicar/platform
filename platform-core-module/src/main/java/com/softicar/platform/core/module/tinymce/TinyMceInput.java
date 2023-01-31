package com.softicar.platform.core.module.tinymce;

import com.softicar.platform.core.module.tinymce.scripts.TinyMceInitScript;
import com.softicar.platform.core.module.tinymce.scripts.TinyMceLoadScript;
import com.softicar.platform.core.module.tinymce.scripts.TinyMceSetModeScript;
import com.softicar.platform.dom.elements.DomTextArea;
import com.softicar.platform.dom.input.AbstractDomValueInputDiv;
import java.util.Optional;

public class TinyMceInput extends AbstractDomValueInputDiv<String> {

	private final DomTextArea textArea;

	public TinyMceInput() {

		this.textArea = new DomTextArea();
		appendChild(textArea);

		initializeTinyMce();
	}

	@Override
	public Optional<String> getValue() {

		return textArea.getValue();
	}

	@Override
	public void setValue(String value) {

		textArea.setValue(value);
		new TinyMceLoadScript(textArea).execute();
	}

	@Override
	protected void doSetDisabled(boolean disabled) {

		var mode = disabled? "readonly" : "design";
		new TinyMceSetModeScript(textArea, mode).execute();
	}

	private void initializeTinyMce() {

		TinyMceLibrary.install(getDomDocument());
		new TinyMceInitScript(textArea).execute();
	}
}
