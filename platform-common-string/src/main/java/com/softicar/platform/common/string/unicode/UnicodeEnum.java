package com.softicar.platform.common.string.unicode;

/**
 * An enumeration of unicode characters.
 * <p>
 * <b>Need more?</b> http://www.ssec.wisc.edu/~tomw/java/unicode.html
 * <p>
 * Please maintain the alphabetical order.
 *
 * @author Alexander Schmidt
 */
public enum UnicodeEnum {

	ARROW_DOWN('\u2193'),
	ARROW_LEFT('\u2190'),
	ARROW_RIGHT('\u2192'),
	ARROW_UP('\u2191'),
	BYTE_ORDER_MARK('\uFEFF'),
	BULLET('\u2022'),
	CIRCLED_MINUS('\u2296'),
	CIRCLED_PLUS('\u2295'),
	DOUBLE_ARROW_DOWN('\u21D3'),
	DOUBLE_ARROW_LEFT('\u21D0'),
	DOUBLE_ARROW_RIGHT('\u21D2'),
	DOUBLE_ARROW_UP('\u21D1'),
	DOWNWARDS_ARROW('\u2193'),
	DOWNWARDS_ARROW_WITH_TIP_RIGHTWARDS('\u21B3'),
	EURO_SIGN('\u20AC'),
	FIGURE_DASH('\u2012'),
	GREEK_CAPITAL_LETTER_SIGMA('\u03A3'),
	INFINITY('\u221E'),
	MIDLINE_HORIZONTAL_ELLIPSIS('\u22EF'),
	NO_BREAK_SPACE('\u00A0'),
	RIGHTWARDS_ARROW_WITH_HOOK('\u21AA'),	// ↪
	VERTICAL_ELLIPSIS('\u22EE');

	private char character;

	private UnicodeEnum(char character) {

		this.character = character;
	}

	@Override
	public String toString() {

		return String.valueOf(character);
	}

	public char toChar() {

		return character;
	}
}
