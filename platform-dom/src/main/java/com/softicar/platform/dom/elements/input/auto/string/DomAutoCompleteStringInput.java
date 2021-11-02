package com.softicar.platform.dom.elements.input.auto.string;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputEngine;
import com.softicar.platform.dom.input.auto.DomAutoCompleteInputValidationMode;
import java.util.Optional;

/**
 * {@link String} based standard implementation of {@link DomAutoCompleteInput}.
 * <p>
 * The default validation mode is
 * {@link DomAutoCompleteInputValidationMode#RESTRICTIVE}.
 * <p>
 * <b>Recommended validation modes:</b><br>
 * - {@link DomAutoCompleteInputValidationMode#PERMISSIVE} if the values listed
 * in the auto-complete popup are mere <i>suggestions</i> but <i>any</i> entered
 * string would be considered valid.<br>
 * - {@link DomAutoCompleteInputValidationMode#RESTRICTIVE} if the user is
 * expected to <i>choose</i> between the values that are listed in the
 * auto-complete popup, and any <i>non-listed</i> entered string would be
 * considered invalid (case sensitive).
 * <p>
 * <b>Discouraged validation modes:</b><br>
 * - {@link DomAutoCompleteInputValidationMode#DEDUCTIVE} leads to confusing
 * behavior for String based implementations of {@link DomAutoCompleteInput}.
 *
 * @author Alexander Schmidt
 */
public class DomAutoCompleteStringInput extends AbstractDomAutoCompleteLiteralInput<String> {

	public DomAutoCompleteStringInput(IDomAutoCompleteInputEngine<String> inputEngine) {

		super(inputEngine);
	}

	@Override
	public Optional<String> getValueFromSelection() {

		return super.getSelection().getValue();
	}

	@Override
	public void setValueString(String string) {

		super.setValue(string);
	}
}
