package com.softicar.platform.common.ui.wiki.token;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public enum WikiTag {

	CODE(true),
	ERROR,
	INFO,
	PRE(true),
	WARNING;

	private static final Map<String, WikiTag> MAP = new TreeMap<>();
	private final boolean rawText;

	private WikiTag() {

		this(false);
	}

	private WikiTag(boolean isRawText) {

		this.rawText = isRawText;
	}

	public boolean isRawText() {

		return rawText;
	}

	public static Optional<WikiTag> getByName(String name) {

		return Optional.ofNullable(MAP.get(name.toUpperCase()));
	}

	static {
		for (WikiTag tagType: values()) {
			MAP.put(tagType.name(), tagType);
		}
	}
}
