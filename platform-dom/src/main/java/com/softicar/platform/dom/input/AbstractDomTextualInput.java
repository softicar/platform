package com.softicar.platform.dom.input;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.event.IDomChangeEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.node.DomNodes;
import java.util.Optional;

public abstract class AbstractDomTextualInput extends AbstractDomValueInputElement<String> implements IDomTextualInput, IDomChangeEventHandler {

	// -------------------------------- value -------------------------------- //

	@Override
	public void setValue(String value) {

		setAttribute("value", value != null? value : "");
	}

	@Override
	public Optional<String> getValue() {

		return getAttributeValue("value");
	}

	@Override
	public String getValueText() {

		return getValue().orElse("");
	}

	@Override
	public String getValueTextTrimmed() {

		return getValueText().trim();
	}

	@Override
	public boolean isBlank() {

		return getValueText().isBlank();
	}

	// -------------------------------- caret & selection -------------------------------- //

	@Override
	public void insertTextAtCaret(String text) {

		getDomEngine().insertTextAtCaret(this, text);
	}

	@Override
	public void moveCaretToPosition(int position) {

		getDomEngine().moveCaretToPosition(this, position);
	}

	@Override
	public void selectText() {

		getDomEngine().selectText(this);
	}

	// -------------------------------- placeholder -------------------------------- //

	@Override
	public IDomTextualInput setPlaceholder(IDisplayString placeholder) {

		setAttribute("placeholder", placeholder.toString());
		return this;
	}

	// -------------------------------- required -------------------------------- //

	@Override
	public IDomTextualInput setRequired(boolean required) {

		setAttribute("required", required? "" : null);
		return this;
	}

	@Override
	public boolean isRequired() {

		return getAttributeValue("required").isPresent();
	}

	// -------------------------------- read-only -------------------------------- //

	@Override
	public void setReadonly(boolean readonly) {

		setAttribute("readonly", readonly? "" : null);
	}

	@Override
	public boolean isReadonly() {

		return getAttributeValue("readonly").isPresent();
	}

	// -------------------------------- miscellaneous -------------------------------- //

	@Override
	public final void handleChange(IDomEvent event) {

		executeChangeCallbacks();
	}

	@Override
	protected final void doSetDisabled(boolean disabled) {

		DomNodes.setDisabled(this, disabled);
	}
}
