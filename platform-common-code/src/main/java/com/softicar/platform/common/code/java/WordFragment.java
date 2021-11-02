package com.softicar.platform.common.code.java;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.string.plural.PluralDeterminer;
import java.util.List;

/**
 * Represents a fragment of a word.
 * <p>
 * This class is usually used to break an identifier into word fragments.
 *
 * @author Oliver Richers
 */
public class WordFragment {

	private final String text;
	private final Type type;

	protected WordFragment(String text, Type type) {

		this.text = text;
		this.type = type;
	}

	public enum Type {
		NORMAL,
		UPPER,
		NUMBER
	}

	public static List<WordFragment> parse(String text) {

		WordFragmentParser parser = new WordFragmentParser();
		parser.parse(text);
		return parser.getFragmens();
	}

	public String getText() {

		return text;
	}

	public Type getType() {

		return type;
	}

	public WordFragment getPlural() {

		switch (type) {
		case NORMAL:
			return new WordFragment(new PluralDeterminer(text).getPlural(), type);
		case UPPER:
			return new WordFragment(new PluralDeterminer(text).getPlural().toUpperCase(), type);
		case NUMBER:
			return this;
		}
		throw new SofticarUnknownEnumConstantException(type);
	}

	@Override
	public String toString() {

		return String.format("(%s,%s)", text, type);
	}
}
