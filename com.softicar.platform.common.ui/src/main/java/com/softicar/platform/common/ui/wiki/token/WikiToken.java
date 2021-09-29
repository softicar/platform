package com.softicar.platform.common.ui.wiki.token;

import com.softicar.platform.common.core.utils.DevNull;
import java.util.Objects;

public class WikiToken {

	private final WikiTokenType tokenType;
	private final String consumedText;

	public WikiToken(WikiTokenType tokenType, String consumedText) {

		this.tokenType = tokenType;
		this.consumedText = consumedText;
	}

	public WikiTokenType getTokenType() {

		return tokenType;
	}

	public String getConsumedText() {

		return consumedText;
	}

	public boolean isCloseTag() {

		return false;
	}

	public boolean isCloseTag(WikiTag tag) {

		DevNull.swallow(tag);
		return false;
	}

	public boolean isWhiteSpace() {

		if (tokenType == WikiTokenType.TEXT) {
			for (int i = 0; i < consumedText.length(); i++) {
				if (!Character.isWhitespace(consumedText.charAt(i))) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return Objects.hash(tokenType, consumedText);
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof WikiToken) {
			WikiToken other = (WikiToken) object;
			return Objects.equals(tokenType, other.tokenType) && Objects.equals(consumedText, other.consumedText);
		} else {
			return false;
		}
	}
}
