package com.softicar.platform.ajax.engine;

/**
 * Represents a simple JavaScript statement.
 *
 * @author Oliver Richers
 */
public class JavascriptStatement extends AbstractJavascriptCode {

	public JavascriptStatement(String code) {

		m_code = code;
	}

	@Override
	public void appendTo(JavascriptFormatter out) {

		out.appendRow(m_code);
	}

	private final String m_code;
}
