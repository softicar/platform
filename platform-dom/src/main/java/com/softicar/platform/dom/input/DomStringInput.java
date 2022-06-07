package com.softicar.platform.dom.input;

import com.softicar.platform.dom.event.IDomChangeEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import java.util.Optional;

/**
 * An {@link IDomValueInput} for {@link String}.
 * <p>
 * This implementation does not retain <i>null</i> values. See
 * {@link #setValue(String)} and {@link #getValue()}.
 *
 * @author Oliver Richers
 */
public class DomStringInput extends AbstractDomValueInput<String> {

	private final TextInput input;

	public DomStringInput() {

		this.input = new TextInput();

		appendChild(input);
	}

	/**
	 * Sets the value of this {@link IDomValueInput}.
	 * <p>
	 * If the value is <i>null</i>, the empty string is assigned.
	 *
	 * @param value
	 *            the value (may be <i>null</i>)
	 */
	@Override
	public void setValue(String value) {

		input.setInputText(value != null? value : "");
	}

	/**
	 * Returns the value of this {@link IDomValueInput}.
	 * <p>
	 * The returned {@link Optional} value is never empty.
	 *
	 * @return the value as {@link Optional} (never empty)
	 */
	@Override
	public Optional<String> getValue() {

		return Optional.of(input.getInputText());
	}

	@Override
	protected void doSetDisabled(boolean disabled) {

		input.setDisabled(disabled);
	}

	private class TextInput extends DomTextInput implements IDomChangeEventHandler {

		@Override
		public void handleChange(IDomEvent event) {

			executeChangeCallbacks();
		}
	}
}
