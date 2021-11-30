package com.softicar.platform.emf.attribute.input;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.wiki.DomWikiDiv;
import com.softicar.platform.dom.elements.wiki.help.DomWikiSyntaxButton;
import com.softicar.platform.dom.input.IDomValueBasedInputNode;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.attribute.field.string.EmfMultilineStringInput;

public class EmfWikiTextInput extends AbstractEmfInputDiv<String> {

	private final EmfMultilineStringInput input;
	private PreviewDiv preview;

	public EmfWikiTextInput() {

		this.input = new EmfMultilineStringInput();
		this.input.setPlaceholder(EmfI18n.ENTER_WIKI_TEXT_HERE);
		this.preview = null;

		addCssClass(EmfCssClasses.EMF_WIKI_TEXT_INPUT);
		appendChild(input);

		DomActionBar actions = appendChild(new DomActionBar());
		actions
			.appendChild(
				new DomButton()//
					.setIcon(EmfImages.INPUT_PREVIEW.getResource())
					.setLabel(EmfI18n.PREVIEW)
					.setClickCallback(this::preview));
		actions.appendChild(new DomWikiSyntaxButton());
	}

	@Override
	public String getValueOrThrow() throws EmfInputException {

		return input.getValueOrThrow();
	}

	@Override
	public void setValue(String value) {

		input.setValue(value);
	}

	@Override
	public IDomValueBasedInputNode<String> setPlaceholder(IDisplayString placeholder) {

		return input.setPlaceholder(placeholder);
	}

	private void preview() {

		if (preview != null) {
			preview.disappend();
			preview = null;
		}

		this.preview = new PreviewDiv(input.getValueOrThrow());
		appendChild(preview);
	}

	private static class PreviewDiv extends DomWikiDiv {

		public PreviewDiv(String wikiText) {

			super(wikiText);
			addCssClass(EmfCssClasses.EMF_WIKI_TEXT_INPUT_PREVIEW);
		}
	}
}
