package com.softicar.platform.common.ui.wiki.token.matcher;

import com.softicar.platform.common.ui.wiki.token.WikiTag;
import com.softicar.platform.common.ui.wiki.token.WikiTagToken;
import com.softicar.platform.common.ui.wiki.token.WikiToken;
import com.softicar.platform.common.ui.wiki.token.WikiTokenType;
import com.softicar.platform.common.ui.wiki.tokenizer.IWikiTokenizer;
import java.util.Optional;

public class WikiTagMatcher implements IWikiTokenMatcher {

	@Override
	public boolean match(IWikiTokenizer tokenizer) {

		if (isTagToken(tokenizer)) {
			Optional<WikiTagToken> tagToken = parseTagToken(tokenizer);
			if (tagToken.isPresent()) {
				addToken(tokenizer, tagToken.get());
				return true;
			}
		}
		return false;
	}

	private boolean isTagToken(IWikiTokenizer tokenizer) {

		if (tokenizer.startsWith("<")) {
			char c1 = tokenizer.getCharAt(1, ' ');
			char c2 = tokenizer.getCharAt(2, ' ');
			return Character.isAlphabetic(c1) || c1 == '/' && Character.isAlphabetic(c2);
		} else {
			return false;
		}
	}

	private void addToken(IWikiTokenizer tokenizer, WikiTagToken tagToken) {

		tokenizer.addToken(tagToken);

		if (tagToken.getTag().isRawText() && !tagToken.isCloseTag()) {
			consumeRawText(tokenizer, tagToken.getTag());
		}
	}

	private void consumeRawText(IWikiTokenizer tokenizer, WikiTag tag) {

		String endTag = "</" + tag.name().toLowerCase() + ">";
		int end = tokenizer.findIndexOf(endTag).orElse(tokenizer.getLength());
		if (end > 0) {
			tokenizer.addToken(new WikiToken(WikiTokenType.TEXT, tokenizer.getSubstring(0, end)));
		}
	}

	private Optional<WikiTagToken> parseTagToken(IWikiTokenizer tokenizer) {

		int end = tokenizer.findIndexOf('>', 1);
		if (end >= 0) {
			String text = tokenizer.getSubstring(0, end + 1);
			Optional<WikiTag> tag = WikiTag.getByName(getTagName(text));
			if (tag.isPresent()) {
				return Optional.of(new WikiTagToken(tag.get(), text));
			}
		}
		return Optional.empty();
	}

	private String getTagName(String text) {

		StringBuilder name = new StringBuilder();
		for (int i = 0; i != text.length(); i++) {
			char character = text.charAt(i);
			if (Character.isAlphabetic(character)) {
				name.append(character);
			} else if (name.length() > 0) {
				break;
			}
		}
		return name.toString();
	}
}
