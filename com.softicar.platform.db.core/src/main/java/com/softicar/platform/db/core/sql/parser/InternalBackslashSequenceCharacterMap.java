package com.softicar.platform.db.core.sql.parser;

import java.util.Map;
import java.util.TreeMap;

class InternalBackslashSequenceCharacterMap {

	private static final InternalBackslashSequenceCharacterMap INSTANCE = new InternalBackslashSequenceCharacterMap();
	private final Map<Character, Character> map;

	private InternalBackslashSequenceCharacterMap() {

		this.map = new TreeMap<>();

		map.put('n', '\n');
		map.put('r', '\r');
		map.put('t', '\t');
		map.put('\\', '\\');
	}

	public static InternalBackslashSequenceCharacterMap getInstance() {

		return INSTANCE;
	}

	public boolean contains(char character) {

		return map.containsKey(character);
	}

	public Character get(char character) {

		return map.getOrDefault(character, character);
	}
}
