package com.softicar.platform.common.string.plural;

import java.util.Map;
import java.util.TreeMap;

class InternalIrregularPluralMap {

	private static final InternalIrregularPluralMap INSTANCE = new InternalIrregularPluralMap();
	private final Map<String, String> map;

	private InternalIrregularPluralMap() {

		this.map = new TreeMap<>();

		add("child", "children");
		add("hero", "heroes");
		add("leaf", "leaves");
		add("life", "lives");
		add("man", "men");
		add("woman", "women");
	}

	public static InternalIrregularPluralMap getInstance() {

		return INSTANCE;
	}

	public String get(String word) {

		return map.get(word.toLowerCase());
	}

	private void add(String singular, String plural) {

		map.put(singular.toLowerCase(), plural);
	}
}
