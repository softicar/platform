package com.softicar.platform.dom.text;

import com.softicar.platform.common.math.integer.IntegerBytes;
import com.softicar.platform.common.string.unicode.Utf8Convering;

/**
 * Represents a text node with at most 4 UTF-8 bytes. The text is saved as an
 * integer to conserve memory.
 * 
 * @author Oliver Richers
 */
final class DomTextNode4 extends DomTextNode {

	DomTextNode4(String text, byte[] bytes) {

		super(text);
		m_text = IntegerBytes.toInt_LE(bytes, 0);
	}

	@Override
	public final String getText() {

		return Utf8Convering.fromUtf8_ZeroTerminated(IntegerBytes.toBytes_LE(m_text));
	}

	private final int m_text;
}
