package com.softicar.platform.common.ui.wiki.token;

public class WikiTagToken extends WikiToken {

	private final WikiTag tag;
	private final boolean closeTag;

	public WikiTagToken(WikiTag tag, String consumedText) {

		super(WikiTokenType.TAG, consumedText);

		this.tag = tag;
		this.closeTag = consumedText.startsWith("</");
	}

	public WikiTag getTag() {

		return tag;
	}

	@Override
	public boolean isCloseTag() {

		return closeTag;
	}

	@Override
	public boolean isCloseTag(WikiTag tag) {

		return closeTag && tag == this.tag;
	}
}
