package com.softicar.platform.common.code.java;

import java.util.EnumSet;
import java.util.List;

public class IdentifierNames {

	public static enum Type {
		UPPER_CAMEL,
		LOWER_CAMEL,
		UPPER_WITH_UNDER_SCORE,
		LOWER_WITH_UNDER_SCORE,
		UPPER_WITHOUT_UNDER_SCORE,
		LOWER_WITHOUT_UNDER_SCORE;

		public String get(List<WordFragment> wordFragments) {

			return IdentifierNames.get(this, wordFragments, EnumSet.noneOf(Flag.class));
		}

		public String get(List<WordFragment> wordFragments, Flag flag, Flag...flags) {

			return IdentifierNames.get(this, wordFragments, EnumSet.of(flag, flags));
		}

		public String get(String source) {

			return IdentifierNames.get(this, source, EnumSet.noneOf(Flag.class));
		}

		public String get(String source, Flag flag, Flag...flags) {

			return IdentifierNames.get(this, source, EnumSet.of(flag, flags));
		}
	}

	public static enum Flag {
		REMOVE_ID,
		PLURAL,
		OBEY_JAVA_CONVENTIONS
	}

	public static String get(Type type, String name, EnumSet<Flag> flags) {

		// removal of ID prefix or suffix
		if (flags.contains(Flag.REMOVE_ID)) {
			if (name.endsWith("ID") || name.endsWith("Id")) {
				name = name.substring(0, name.length() - 2);
			} else if (name.toLowerCase().endsWith("_id")) {
				name = name.substring(0, name.length() - 3);
			}
		}

		// split and reconstruct
		return get(type, WordFragment.parse(name), flags);
	}

	public static String get(Type type, List<WordFragment> fragments, EnumSet<Flag> flags) {

		// build string
		int n = fragments.size();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i != n; ++i) {
			WordFragment fragment = fragments.get(i);

			// plural
			if (i == n - 1 && flags.contains(Flag.PLURAL)) {
				fragment = fragments.get(i).getPlural();
			}

			String fragmentText = fragment.getText();
			switch (type) {
			case LOWER_CAMEL:
				if (i == 0) {
					sb.append(fragmentText.toLowerCase());
					break;
				}
				//$FALL-THROUGH$
			case UPPER_CAMEL:
				switch (fragment.getType()) {
				case NUMBER:
					sb.append(fragmentText);
					break;
				case UPPER:
					if (!flags.contains(Flag.OBEY_JAVA_CONVENTIONS)) {
						sb.append(fragmentText);
						break;
					}
					//$FALL-THROUGH$
				case NORMAL:
					sb.append(Character.toUpperCase(fragmentText.charAt(0)));
					sb.append(fragmentText.substring(1).toLowerCase());
					break;
				}
				break;
			case UPPER_WITH_UNDER_SCORE:
				sb.append(fragmentText.toUpperCase());
				if (i < n - 1) {
					sb.append("_");
				}
				break;
			case LOWER_WITH_UNDER_SCORE:
				sb.append(fragmentText.toLowerCase());
				if (i < n - 1) {
					sb.append("_");
				}
				break;
			case UPPER_WITHOUT_UNDER_SCORE:
				sb.append(fragmentText.toUpperCase());
				break;
			case LOWER_WITHOUT_UNDER_SCORE:
				sb.append(fragmentText.toLowerCase());
				break;
			}
		}

		return sb.toString();
	}
}
