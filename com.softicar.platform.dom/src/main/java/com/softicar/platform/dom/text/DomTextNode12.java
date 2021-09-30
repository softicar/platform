package com.softicar.platform.dom.text;

import com.softicar.platform.common.math.integer.IntegerBytes;
import com.softicar.platform.common.string.unicode.Utf8Convering;

/**
 * Represents a text node with at most 12 UTF-8 bytes. The text is saved in
 * three integers to conserve memory.
 * 
 * @author Oliver Richers
 */
final class DomTextNode12 extends DomTextNode {

	DomTextNode12(String text, byte[] bytes) {

		super(text);
		m_text1 = IntegerBytes.toInt_LE(bytes, 0);
		m_text2 = IntegerBytes.toInt_LE(bytes, 4);
		m_text3 = IntegerBytes.toInt_LE(bytes, 8);
	}

	@Override
	public final String getText() {

		byte[] bytes = new byte[12];
		IntegerBytes.toBytes_LE(m_text1, bytes, 0);
		IntegerBytes.toBytes_LE(m_text2, bytes, 4);
		IntegerBytes.toBytes_LE(m_text3, bytes, 8);
		return Utf8Convering.fromUtf8_ZeroTerminated(bytes);
	}

	private final int m_text1, m_text2, m_text3;
}
