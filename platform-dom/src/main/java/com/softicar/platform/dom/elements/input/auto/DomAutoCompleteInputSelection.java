package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.input.auto.DomAutoCompleteInputValidationMode;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

/**
 * Used internally to determine the value of an {@link DomAutoCompleteInput}.
 * <p>
 * Notes:<br>
 * - {@link #isValid()} and {@link #assertValid()} must correspond to each
 * other<br>
 * - {@link #isValid()} must correspond to the value state determination logic
 * in AutoCompleteInputContext.js
 * <p>
 * TODO make {@link #assertValid()} throw meaningful exceptions
 *
 * @author Alexander Schmidt
 */
class DomAutoCompleteInputSelection<T> {

	private final IDomAutoCompleteInputEngine<T> inputEngine;
	private final DomAutoCompleteInputValidationMode validationMode;
	private final Collection<T> matchingValues;
	private final String pattern;

	public DomAutoCompleteInputSelection(IDomAutoCompleteInputEngine<T> inputEngine, DomAutoCompleteInputValidationMode validationMode,
			Function<String, Collection<T>> matchingValuesFunction, String pattern) {

		this.inputEngine = inputEngine;
		this.validationMode = validationMode;
		this.matchingValues = matchingValuesFunction.apply(pattern.toLowerCase());
		this.pattern = pattern;
	}

	/**
	 * Returns the optional value of this input element, as follows:
	 * <ul>
	 * <li>If the entered text can be mapped to a value, that value is
	 * returned.</li>
	 * <li>If the entered text is blank, {@link Optional#empty()} is
	 * returned.</li>
	 * <li>If the entered text cannot be mapped to a value, an exception is
	 * thrown.</li>
	 * </ul>
	 *
	 * @return the value as an {@link Optional} (never <i>null</i>)
	 */
	public Optional<T> getValue() {

		assertValid();
		return getMatchingValue();
	}

	/**
	 * Asserts the validity of the entered text.
	 * <p>
	 * If {@link #isValid()} would return <i>false</i>, an exception is thrown.
	 */
	public void assertValid() {

		if (!isValid()) {
			throw new SofticarUserException(DomI18n.PLEASE_SELECT_A_VALID_ENTRY);
		}
	}

	/**
	 * Determines the validity of the entered text, as follows:
	 * <ul>
	 * <li>If the entered text can be mapped to a value, <i>true</i> is
	 * returned.</li>
	 * <li>If the entered text is blank, <i>true</i> is returned.</li>
	 * <li>Otherwise, <i>false</i> is returned.</li>
	 * </ul>
	 *
	 * @return <i>true</i> if the entered text is valid; <i>false</i> otherwise
	 */
	public boolean isValid() {

		if (isValueEmptyOrModePermissive()) {
			return true;
		} else {
			return isValueValid();
		}
	}

	public boolean isBlankPattern() {

		return pattern.isEmpty();
	}

	private boolean isValueValid() {

		return isValueUnique() || isPatternInValues(false);
	}

	private boolean isValueEmptyOrModePermissive() {

		return isBlankPattern() || validationMode.isPermissive();
	}

	private boolean isValueUnique() {

		return matchingValues.size() == 1;
	}

	private boolean isPatternInValues(boolean respectCase) {

		return getFirstMatchingValue(respectCase).isPresent();
	}

	private Optional<T> getMatchingValue() {

		switch (validationMode) {
		case DEDUCTIVE:
			return getDeducedValue();
		case PERMISSIVE:
			// TODO PLAT-753 This cast should not be necessary. Permissive mode should not even be handled in the same auto-complete input implementation.
			return Optional.of(CastUtils.cast(pattern));
		}
		throw new SofticarUnknownEnumConstantException(validationMode);
	}

	/**
	 * Tries to determine value via<br>
	 * (1) unique deduction, or<br>
	 * (2) case sensitive literal match.
	 *
	 * @return the matching value, or empty if none found
	 */
	private Optional<T> getDeducedValue() {

		Optional<T> matchingValue = getSoleMatchingValue();
		if (matchingValue.isPresent()) {
			return matchingValue;
		}
		matchingValue = getFirstMatchingValue(true);
		if (matchingValue.isPresent()) {
			return matchingValue;
		}
		// possible third case: "(3) case insensitive literal match" ... would need to be implemented in JS as well
//		matchingValue = getFirstMatchingValue(false);
//		if (matchingValue.isPresent()) {
//			return matchingValue;
//		}
		return Optional.empty();
	}

	private Optional<T> getSoleMatchingValue() {

		if (matchingValues.size() == 1) {
			return Optional.ofNullable(matchingValues.iterator().next());
		} else {
			return Optional.empty();
		}
	}

	private Optional<T> getFirstMatchingValue(boolean respectCase) {

		for (T value: matchingValues) {
			if (matchesPattern(value, respectCase)) {
				return Optional.ofNullable(value);
			}
		}
		return Optional.empty();
	}

	private boolean matchesPattern(T value, boolean respectCase) {

		return Optional//
			.ofNullable(inputEngine.getDisplayString(value))
			.map(IDisplayString::toString)
			.map(String::trim)
			.map(it -> transform(it, respectCase))
			.filter(it -> !it.isEmpty())
			.filter(it -> it.equals(transform(pattern, respectCase)))
			.isPresent();
	}

	private String transform(String input, boolean respectCase) {

		return Optional//
			.ofNullable(input)
			.map(respectCase? it -> it : it -> it.toLowerCase())
			.orElse("");
	}
}
