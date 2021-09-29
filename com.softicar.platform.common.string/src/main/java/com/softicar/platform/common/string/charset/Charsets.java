package com.softicar.platform.common.string.charset;

import java.nio.charset.Charset;

/**
 * Enumeration of common {@link Charset} objects.
 *
 * @author Oliver Richers
 */
public interface Charsets {

	Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
	Charset ISO_8859_2 = Charset.forName("ISO-8859-2");
	Charset ISO_8859_15 = Charset.forName("ISO-8859-15");
	Charset UTF8 = Charset.forName("UTF-8");
	Charset UTF16 = Charset.forName("UTF-16");
	Charset UTF32 = Charset.forName("UTF-32");
	Charset WINDOWS_1250 = Charset.forName("windows-1250");
	Charset WINDOWS_1252 = Charset.forName("windows-1252");
}
