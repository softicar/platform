package com.softicar.platform.common.ui.wiki.element.tag;

import com.softicar.platform.common.ui.wiki.token.WikiTag;

public enum WikiRawTextType {

	CODE(WikiTag.CODE),
	PRE(WikiTag.PRE);

	private final WikiTag tag;

	private WikiRawTextType(WikiTag tag) {

		this.tag = tag;
	}

	public WikiTag getTag() {

		return tag;
	}
}
