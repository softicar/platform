package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.input.auto.IDomAutoCompleteInputConfiguration;
import com.softicar.platform.dom.input.auto.IDomAutoCompleteInputSelection;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

/**
 * The standard implementation of {@link IDomAutoCompleteInputSelection}.
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
class DomAutoCompleteInputSelection<T> implements IDomAutoCompleteInputSelection<T> {

	private final IDomAutoCompleteInputEngine<T> inputEngine;
	private final IDomAutoCompleteInputConfiguration configuration;
	private final Collection<T> matchingValues;
	private final String pattern;

	public DomAutoCompleteInputSelection(IDomAutoCompleteInputEngine<T> inputEngine, IDomAutoCompleteInputConfiguration configuration,
			Function<String, Collection<T>> matchingValuesFunction, String pattern) {

		this.inputEngine = inputEngine;
		this.configuration = configuration;
		this.matchingValues = matchingValuesFunction.apply(pattern.toLowerCase());
		this.pattern = pattern;
	}

	@Override
	public Optional<T> getValue() {

		assertValid();
		return getMatchingValue();
	}

	@Override
	public T getValueOrNull() {

		return getValue().orElse(null);
	}

	@Override
	public T getValueOrThrow() {

		return getValue().orElseThrow(() -> new SofticarUserException(DomI18n.PLEASE_SELECT_A_VALID_ENTRY));
	}

	@Override
	public void assertValid() {

		if (!isValid()) {
			throw new SofticarUserException(DomI18n.PLEASE_SELECT_A_VALID_ENTRY);
		}
	}

	@Override
	public boolean isValid() {

		if (isValueEmptyOrModePermissive()) {
			return true;
		} else {
			return isValueValid();
		}
	}

	@Override
	public boolean isBlankPattern() {

		return pattern.isEmpty();
	}

	private boolean isValueValid() {

		return isValueUnique() || isPatternInValues(false);
	}

	private boolean isValueEmptyOrModePermissive() {

		return isBlankPattern() || configuration.getValidationMode().isPermissive();
	}

	private boolean isValueUnique() {

		return matchingValues.size() == 1;
	}

	private boolean isPatternInValues(boolean respectCase) {

		return getFirstMatchingValue(respectCase).isPresent();
	}

	private Optional<T> getMatchingValue() {

		var mode = configuration.getValidationMode();
		switch (mode) {
		case DEDUCTIVE:
			return getDeducedValue();
		case PERMISSIVE:
			// TODO PLAT-753 This cast should not be necessary. Permissive mode should not even be handled in the same auto-complete input implementation.
			return Optional.of(CastUtils.cast(pattern));
		}
		throw new SofticarUnknownEnumConstantException(mode);
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
