package com.softicar.platform.common.code.title;

import com.softicar.platform.common.code.java.WordFragment;
import com.softicar.platform.common.string.Trim;
import com.softicar.platform.common.string.plural.PluralDeterminer;
import com.softicar.platform.common.string.title.capitalizer.TitleCapitalizer;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Determines a title from a given identifier.
 * <p>
 * The given identifier will be split into individual words, which will then be
 * capitalized using {@link TitleCapitalizer}.
 * <p>
 * The identifier can be given in camel case (<i>BillOfMaterial</i>), snake case
 * (<i>bill_of_material</i>) or as words separated by space (<i>bill of
 * material</i>) or dashes (<i>bill-of-material</i>).
 * <p>
 * Numbers will be treated as individual words, e.g. <i>Foo1337</i> will be
 * treated as two words, <i>Foo</i> and <i>1337</i>.
 *
 * @author Oliver Richers
 */
public class TitleFromIdentifierDeterminer {

	private final String identifier;
	private final Set<String> prefixes;

	public TitleFromIdentifierDeterminer(String identifier) {

		this.identifier = identifier;
		this.prefixes = new TreeSet<>(
			Comparator//
				.comparing(String::length, Comparator.reverseOrder())
				.thenComparing(Function.identity()));
	}

	public TitleFromIdentifierDeterminer addPrefixToDrop(String prefix) {

		prefixes.add(prefix);
		return this;
	}

	/**
	 * Returns the capitalized title determined from the identifier.
	 *
	 * @return the title (never null)
	 */
	public String getTitle() {

		return new TitleCapitalizer(getWords()).capitalize();
	}

	/**
	 * Returns the capitalized plural title determined from the identifier.
	 * <p>
	 * Please note that the plural of identifiers with prepositions like
	 * <i>of</i> or <i>in</i> will not work as might be expected, e.g.
	 * <i>BillOfMaterial</i> will become <i>Bill of Materials</i>.
	 *
	 * @return the plural title (never null)
	 */
	public String getPluralTitle() {

		return new TitleCapitalizer(getPluralWords()).capitalize();
	}

	private List<String> getPluralWords() {

		List<String> words = getWords();
		int lastIndex = words.size() - 1;
		words.set(lastIndex, new PluralDeterminer(words.get(lastIndex)).getPlural());
		return words;
	}

	private List<String> getWords() {

		return WordFragment//
			.parse(getIdentifierWithoutPrefixes())
			.stream()
			.map(WordFragment::getText)
			.map(String::toLowerCase)
			.collect(Collectors.toList());
	}

	private String getIdentifierWithoutPrefixes() {

		String text = identifier;
		for (String prefix: prefixes) {
			text = Trim.trimPrefix(text, prefix);
		}
		return text;
	}
}
