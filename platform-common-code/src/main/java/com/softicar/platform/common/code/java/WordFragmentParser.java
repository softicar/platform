package com.softicar.platform.common.code.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Breaks a given text into its word fragments.
 *
 * @author Oliver Richers
 */
class WordFragmentParser {

	private final List<WordFragment> fragments = new ArrayList<>();
	private final StringBuilder fragmentText = new StringBuilder();
	private boolean isAllUpperCase = true;
	private boolean isNumber = true;

	public void parse(String text) {

		for (int i = 0; i != text.length(); ++i) {
			char c = text.charAt(i);
			if (c == '_' || c == ' ' || c == '-') {
				finishCurrentFragment();
			} else if (Character.isUpperCase(c)) {
				if (!isAllUpperCase) {
					finishCurrentFragment();
				}

				isNumber = false;
				fragmentText.append(c);
			} else if (Character.isDigit(c)) {
				if (!isNumber) {
					finishCurrentFragment();
				}

				isAllUpperCase = false;
				fragmentText.append(c);
			} else {
				// some digits before?
				if (isNumber && fragmentText.length() > 0) {
					finishCurrentFragment();
				}

				// some upper case letters before?
				if (isAllUpperCase && fragmentText.length() > 1) {
					int index = fragmentText.length() - 1;
					char last = fragmentText.charAt(index);
					fragmentText.deleteCharAt(index);
					finishCurrentFragment();
					fragmentText.append(last);
				}

				isAllUpperCase = false;
				isNumber = false;
				fragmentText.append(c);
			}
		}

		finishCurrentFragment();
	}

	public List<WordFragment> getFragmens() {

		return fragments;
	}

	private void finishCurrentFragment() {

		WordFragment.Type type = isNumber? WordFragment.Type.NUMBER : isAllUpperCase? WordFragment.Type.UPPER : WordFragment.Type.NORMAL;

		if (fragmentText.length() > 0) {
			fragments.add(new WordFragment(fragmentText.toString(), type));
		}

		isAllUpperCase = true;
		isNumber = true;
		fragmentText.setLength(0);
	}
}
