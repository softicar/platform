package com.softicar.platform.common.ui.wiki.tokenizer;

import com.softicar.platform.common.ui.wiki.token.WikiToken;
import java.util.Optional;

public interface IWikiTokenizer {

	int getLength();

	// -------------------- match -------------------- //

	boolean startsWith(String pattern);

	// -------------------- search -------------------- //

	Optional<Integer> findIndexOf(String pattern);

	int findIndexOf(char character, int offset);

	int findIndexOfOther(char character, int offset);

	// -------------------- extract -------------------- //

	String getSubstring(int beginOffset, int endOffset);

	char getCharAt(int offset, char fallback);

	// -------------------- modify -------------------- //

	void addToken(WikiToken token);
}
