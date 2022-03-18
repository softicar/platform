package com.softicar.platform.common.core.i18n;

import java.util.Map;
import java.util.TreeMap;

class TestTranslator implements ILanguageTranslator {

	private final Map<Key, String> map = new TreeMap<>();

	public void put(LanguageEnum language, String text, String translation) {

		map.put(new Key(language, text), translation);
	}

	@Override
	public String translate(LanguageEnum language, String text) {

		String translated = map.get(new Key(language, text));
		return translated != null? translated : text;
	}

	private class Key implements Comparable<Key> {

		private final LanguageEnum languageEnum;
		private final String text;

		public Key(LanguageEnum languageEnum, String text) {

			this.languageEnum = languageEnum;
			this.text = text;
		}

		@Override
		public int compareTo(Key other) {

			int cmp = languageEnum.compareTo(other.languageEnum);
			return cmp != 0? cmp : text.compareTo(other.text);
		}
	}
}
