package com.softicar.platform.ajax.engine;

import com.softicar.platform.common.io.writer.ManagedWriter;

/**
 * Represents a piece of JavaScript code.
 *
 * @author Oliver Richers
 */
public abstract class AbstractJavascriptCode {

	/**
	 * Converts the JavaScript code to {@link String} using the
	 * {@link JavascriptFormatter}.
	 */
	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();
		JavascriptFormatter out = new JavascriptFormatter(new ManagedWriter(builder), "    ", "\n", false);
		appendTo(out);
		return builder.toString();
	}

	public abstract void appendTo(JavascriptFormatter out);
}
