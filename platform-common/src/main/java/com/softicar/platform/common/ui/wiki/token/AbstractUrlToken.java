package com.softicar.platform.common.ui.wiki.token;

public class AbstractUrlToken extends WikiToken {

	private final String url;
	private final String label;

	public AbstractUrlToken(WikiTokenType tokenType, String consumedText) {

		super(tokenType, consumedText);

		int pipeIndex = consumedText.indexOf('|', 2);
		if (pipeIndex > 0) {
			this.url = consumedText.substring(2, pipeIndex);
			this.label = consumedText.substring(pipeIndex + 1, consumedText.length() - 2);
		} else {
			this.url = consumedText.substring(2, consumedText.length() - 2);
			this.label = "";
		}
	}

	public String getUrl() {

		return url;
	}

	public String getLabel() {

		return label;
	}
}
