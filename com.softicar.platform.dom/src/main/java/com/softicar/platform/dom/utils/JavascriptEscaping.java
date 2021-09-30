package com.softicar.platform.dom.utils;

import com.softicar.platform.common.string.Escaping;

public class JavascriptEscaping {

	public static String getEscaped(String string) {

		if (string != null) {
			string = Escaping.getEscaped(string);

			// This is really ridiculous, we have to escape any ETAGO,
			// to prevent cross-site scripting attacks.
			return string.replace("</", "<\\/");
		} else {
			return null;
		}
	}
}
