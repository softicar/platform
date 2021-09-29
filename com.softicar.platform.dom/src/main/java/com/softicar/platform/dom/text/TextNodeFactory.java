package com.softicar.platform.dom.text;

import com.softicar.platform.common.string.unicode.Utf8Convering;
import java.util.Arrays;

/**
 * Contains some utility functions for DOMTextNode.
 * 
 * @author Oliver Richers
 */
public class TextNodeFactory {

	/**
	 * Encodes the string to UTF-8 and create a text node, depending on the
	 * length of the UTF-8 string. Depending on the length of the string, the
	 * amount of conserved memory can be huge.
	 * 
	 * @param text
	 *            the text of the text node
	 * @return a new instance of a text node with the specified text
	 */
	public static DomTextNode create(String text) {

		if (text == null) {
			return new DomTextNode0();
		}

		byte[] bytes = Utf8Convering.toUtf8(text);
		switch (bytes.length) {
		case 0:
			return new DomTextNode0();
		case 1:
		case 2:
		case 3:
			bytes = Arrays.copyOf(bytes, 4);
			//$FALL-THROUGH$
		case 4:
			return new DomTextNode4(text, bytes);
		case 5:
		case 6:
		case 7:
			bytes = Arrays.copyOf(bytes, 8);
			//$FALL-THROUGH$
		case 8:
			return new DomTextNode8(text, bytes);
		case 9:
		case 10:
		case 11:
			bytes = Arrays.copyOf(bytes, 12);
			//$FALL-THROUGH$
		case 12:
			return new DomTextNode12(text, bytes);
		default:
			return new DomTextNodeCommon(text, bytes);
		}
	}
}
