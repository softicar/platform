package com.softicar.platform.dom.text;

import com.softicar.platform.common.string.unicode.Utf8Convering;

final class DomTextNodeCommon extends DomTextNode {

	DomTextNodeCommon(String text, byte[] bytes) {

		super(text);
		m_text = bytes;
	}

	@Override
	public final String getText() {

		return Utf8Convering.fromUtf8(m_text);
	}

	private final byte[] m_text;
}
