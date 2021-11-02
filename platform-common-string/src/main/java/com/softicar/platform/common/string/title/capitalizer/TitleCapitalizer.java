package com.softicar.platform.common.string.title.capitalizer;

import com.softicar.platform.common.string.Imploder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Capitalization for titles.
 * <p>
 * The rules are to capitalize everything except articles, conjunctions and
 * prepositions. The first and last word are also capitalized.
 *
 * @author Oliver Richers
 */
public class TitleCapitalizer {

	private static final Set<String> ARTICLES = new TreeSet<>(Arrays.asList("a", "an", "the"));
	private static final Set<String> COORDINATING_CONJUNCTIONS = new TreeSet<>(Arrays.asList("and", "but", "for", "nor", "or", "so", "yet"));
	private static final Prepositions PREPOSITIONS = new Prepositions();
	private final List<String> words;

	public TitleCapitalizer(String input) {

		this(
			Arrays//
				.asList(input.split("\\s+"))
				.stream()
				.map(String::trim)
				.filter(TitleCapitalizer::isNotEmpty)
				.collect(Collectors.toList()));
	}

	public TitleCapitalizer(List<String> words) {

		this.words = words;
	}

	public String capitalize() {

		List<String> output = new ArrayList<>();
		for (int i = 0; i < words.size(); ++i) {
			String word = words.get(i);
			boolean firstOrLastWord = i == 0 || i == words.size() - 1;
			boolean capitalize = firstOrLastWord || isCapitalizationNecessary(word.toLowerCase());
			output.add(capitalize? toUpper(word) : toLower(word));
		}
		return Imploder.implode(output, " ");
	}

	private String toUpper(String word) {

		return Character.toUpperCase(word.charAt(0)) + word.substring(1);
	}

	private String toLower(String word) {

		return Character.toLowerCase(word.charAt(0)) + word.substring(1);
	}

	private static boolean isCapitalizationNecessary(String word) {

		if (word.length() >= 5) {
			return true;
		} else if (ARTICLES.contains(word)) {
			return false;
		} else if (COORDINATING_CONJUNCTIONS.contains(word)) {
			return false;
		} else if (PREPOSITIONS.contains(word)) {
			return false;
		} else {
			return true;
		}
	}

	private static boolean isNotEmpty(String string) {

		return !string.isEmpty();
	}
}
