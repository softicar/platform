package com.softicar.platform.common.string.title.capitalizer;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Set of most English prepositions.
 * <p>
 * Contains only short prepositions up to 4 letters.
 *
 * @author Oliver Richers
 */
class Prepositions {

	private final Set<String> prepositions = new TreeSet<>();

	public Prepositions() {

		prepositions.addAll(Arrays.asList("amid", "apud", "as", "at", "atop", "by", "down"));
		prepositions.addAll(Arrays.asList("from", "in", "into", "like", "near", "next"));
		prepositions.addAll(Arrays.asList("of", "off", "on", "onto", "out", "over", "pace", "past", "per", "plus", "pro"));
		prepositions.addAll(Arrays.asList("qua", "sans", "save", "than", "till", "to"));
		prepositions.addAll(Arrays.asList("unto", "up", "upon", "via", "vice", "with"));
	}

	public boolean contains(String word) {

		return prepositions.contains(word);
	}
}
