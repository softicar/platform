package com.softicar.platform.common.string.scanning;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public abstract class AbstractLiteralMatcher implements ISimpleTextMatcher {

	private final Set<String> literals;

	public AbstractLiteralMatcher() {

		this.literals = new TreeSet<>(LiteralsComparator.SINGLETON);
	}

	public AbstractLiteralMatcher(String literal) {

		this.literals = new TreeSet<>(LiteralsComparator.SINGLETON);
		addLiteral(literal);
	}

	public void addLiteral(String literal) {

		literals.add(literal);
	}

	@Override
	public int getMatchingLength(String text) {

		for (String literal: literals) {
			if (text.startsWith(literal)) {
				return literal.length();
			}
		}
		return 0;
	}

	@Override
	public void consumeMatchingText(String text) {

		consumeLiteral(text);
	}

	protected abstract void consumeLiteral(String literal);

	private static class LiteralsComparator implements Comparator<String> {

		public static final LiteralsComparator SINGLETON = new LiteralsComparator();

		@Override
		public int compare(String left, String right) {

			// compare descending by length first
			int cmp = -Integer.compare(left.length(), right.length());
			return cmp != 0? cmp : left.compareTo(right);
		}
	}
}
