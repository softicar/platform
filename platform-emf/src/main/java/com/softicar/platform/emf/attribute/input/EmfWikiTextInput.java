package com.softicar.platform.emf.attribute.input;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.wiki.DomWikiDiv;
import com.softicar.platform.dom.elements.wiki.help.DomWikiSyntaxButton;
import com.softicar.platform.dom.input.AbstractDomValueInputDiv;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.attribute.field.string.EmfMultilineStringInput;
import java.util.Optional;

public class EmfWikiTextInput extends AbstractDomValueInputDiv<String> {

	private final EmfMultilineStringInput input;
	private PreviewDiv preview;

	public EmfWikiTextInput() {

		this.input = new EmfMultilineStringInput();
		this.input.setPlaceholder(EmfI18n.ENTER_WIKI_TEXT_HERE);
		this.input.addChangeCallback(this::executeChangeCallbacks);
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
	public Optional<String> getValue() {

		return input.getValue();
	}

	@Override
	public void setValue(String value) {

		input.setValue(value);
	}

	public EmfWikiTextInput setPlaceholder(IDisplayString placeholder) {

		input.setPlaceholder(placeholder);
		return this;
	}

	@Override
	protected void doSetDisabled(boolean disabled) {

		input.setDisabled(disabled);
	}

	private void preview() {

		if (preview != null) {
			preview.disappend();
			preview = null;
		}

		this.preview = new PreviewDiv(input.getValue().orElse(""));
		appendChild(preview);
	}

	private static class PreviewDiv extends DomWikiDiv {

		public PreviewDiv(String wikiText) {

			super(wikiText);
			addCssClass(EmfCssClasses.EMF_WIKI_TEXT_INPUT_PREVIEW);
		}
	}
}
