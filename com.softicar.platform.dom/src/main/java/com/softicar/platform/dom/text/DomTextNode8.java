package com.softicar.platform.dom.text;

import com.softicar.platform.common.math.integer.IntegerBytes;
import com.softicar.platform.common.string.unicode.Utf8Convering;

/**
 * Represents a text node with at most 8 UTF-8 bytes. The text is saved in
 * fields of type int to conserve memory.
 * 
 * @author Oliver Richers
 */
final class DomTextNode8 extends DomTextNode {

	DomTextNode8(String text, byte[] bytes) {

		super(text);
		m_text1 = IntegerBytes.toInt_LE(bytes, 0);
		m_text2 = IntegerBytes.toInt_LE(bytes, 4);
	}

	@Override
	public final String getText() {

		byte[] bytes = new byte[8];
		IntegerBytes.toBytes_LE(m_text1, bytes, 0);
		IntegerBytes.toBytes_LE(m_text2, bytes, 4);
		return Utf8Convering.fromUtf8_ZeroTerminated(bytes);
	}

	private final int m_text1, m_text2;
}
