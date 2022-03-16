package com.softicar.platform.common.ui.wiki.element.tag;

import com.softicar.platform.common.ui.wiki.token.WikiTag;

public enum WikiBoxType {

	ERROR(WikiTag.ERROR),
	INFO(WikiTag.INFO),
	WARNING(WikiTag.WARNING);

	private final WikiTag tag;

	private WikiBoxType(WikiTag tag) {

		this.tag = tag;
	}

	public WikiTag getTag() {

		return tag;
	}
}
