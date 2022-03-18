package com.softicar.platform.common.core.number.parser;

import com.softicar.platform.common.core.CommonCoreI18n;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.locale.CurrentLocale;
import com.softicar.platform.common.core.locale.ILocale;
import com.softicar.platform.common.core.number.formatter.BigDecimalFormatter;
import com.softicar.platform.common.core.utils.DevNull;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * A parser for {@link BigDecimal} respecting {@link CurrentLocale}.
 *
 * @author Oliver Richers
 */
public class BigDecimalParser {

	private static final String LEGAL_CHARACTERS = "+-0123456789.eE";
	private static final int DIGIT_GROUP_SIZE = BigDecimalFormatter.getDigitGroupSize();
	private final String numberString;
	private ILocale locale;
	private boolean prohibitDigitGroupSeparators;

	/**
	 * Constructs this {@link BigDecimalParser}.
	 *
	 * @param numberString
	 *            the number string to parse (never <i>null</i>)
	 */
	public BigDecimalParser(String numberString) {

		this.numberString = Objects.requireNonNull(numberString);
		this.locale = CurrentLocale.get();
		this.prohibitDigitGroupSeparators = false;
	}

	/**
	 * Overrides the {@link ILocale} to use for formatting.
	 *
	 * @param locale
	 *            the {@link ILocale} to use (never <i>null</i>)
	 * @return this
	 */
	public BigDecimalParser setLocale(ILocale locale) {

		this.locale = locale;
		return this;
	}

	/**
	 * If enabled, parsing will throw an exception if a digit group separator is
	 * encountered.
	 * <p>
	 * By default, this is disabled, i.e. digit group separators are allowed.
	 *
	 * @param prohibitDigitGroupSeparators
	 *            <i>true</i> to prohibit digit group separators; <i>false</i>
	 *            to allow them
	 * @return this
	 */
	public BigDecimalParser setProhibitDigitGroupSeparators(boolean prohibitDigitGroupSeparators) {

		this.prohibitDigitGroupSeparators = prohibitDigitGroupSeparators;
		return this;
	}

	/**
	 * Parses the given number string.
	 *
	 * @return the parsed {@link BigDecimal} if successful or
	 *         {@link Optional#empty()} if parsing failed
	 */
	public Optional<BigDecimal> parse() {

		try {
			return Optional.of(parseOrThrow());
		} catch (Exception exception) {
			DevNull.swallow(exception);
			return Optional.empty();
		}
	}

	/**
	 * Parses the given number string.
	 *
	 * @return the parsed {@link BigDecimal} (never <i>null</i>)
	 * @throws RuntimeException
	 *             if the given number string is invalid
	 */
	public BigDecimal parseOrThrow() {

		if (!locale.getDigitGroupSeparator().isEmpty()) {
			validateNumberString();
		}
		var normalized = numberString//
			.replace(locale.getDigitGroupSeparator(), "")
			.replace(locale.getDecimalSeparator(), ".");
		validateCharacters(normalized);
		return new BigDecimal(normalized);
	}

	private void validateNumberString() {

		var decimalSeparator = locale.getDecimalSeparator();
		var decimalSeparatorIndex = numberString.indexOf(decimalSeparator);
		if (decimalSeparatorIndex < 0) {
			validateIntegralPart(numberString);
		} else {
			var integralPart = numberString.substring(0, decimalSeparatorIndex);
			var fractionalPart = numberString.substring(decimalSeparatorIndex + decimalSeparator.length());
			validateIntegralPart(integralPart);
			validateFractionalPart(fractionalPart);
		}
	}

	private void validateIntegralPart(String integralPart) {

		String[] groups = integralPart.replace(locale.getDigitGroupSeparator(), ",").split(",");
		if (groups.length > 1) {
			if (prohibitDigitGroupSeparators) {
				throw new SofticarUserException(CommonCoreI18n.ILLEGAL_CHARACTERS_FOR_DECIMAL_NUMBER_ARG1.toDisplay(locale.getDigitGroupSeparator()));
			}
			for (int i = 0; i < groups.length; i++) {
				if (groups[i].length() > DIGIT_GROUP_SIZE) {
					throw new SofticarUserException(CommonCoreI18n.DIGIT_GROUP_TOO_LONG);
				}
				if (i > 0 && groups[i].length() < DIGIT_GROUP_SIZE) {
					throw new SofticarUserException(CommonCoreI18n.DIGIT_GROUP_TOO_SHORT);
				}
			}
		}
	}

	private void validateFractionalPart(String fractionalPart) {

		if (fractionalPart.contains(locale.getDigitGroupSeparator())) {
			throw new SofticarUserException(CommonCoreI18n.THE_DECIMAL_PART_MUST_NOT_CONTAIN_DIGIT_GROUP_SEPARATORS);
		}
	}

	private void validateCharacters(String normalizedNumberString) {

		Set<Character> illegalCharacters = new TreeSet<>();
		for (int i = 0; i < normalizedNumberString.length(); i++) {
			char character = normalizedNumberString.charAt(i);
			if (!isValidCharacter(character)) {
				illegalCharacters.add(character);
			}
		}
		if (!illegalCharacters.isEmpty()) {
			throw new SofticarUserException(
				CommonCoreI18n.ILLEGAL_CHARACTERS_FOR_DECIMAL_NUMBER_ARG1
					.toDisplay(
						illegalCharacters//
							.stream()
							.map(Object::toString)
							.collect(Collectors.joining())));
		}
	}

	private boolean isValidCharacter(char character) {

		return LEGAL_CHARACTERS.contains("" + character);
	}
}
