package com.softicar.platform.ajax.engine;

import com.softicar.platform.common.io.writer.IManagedWriter;

/**
 * Source code formatter for JavaScript.
 *
 * @author Oliver Richers
 */
// FIXME this class needs a cleanup
public class JavascriptFormatter implements Appendable {

	public JavascriptFormatter(IManagedWriter out, String indent, String lineBreak, boolean doDebug) {

		m_out = out;
		m_indent = indent;
		m_lineBreak = lineBreak;
		m_debug = doDebug;
	}

	public void beginBlock() {

		m_currentIndent.append(m_indent);
	}

	public void endBlock() {

		m_currentIndent.setLength(m_currentIndent.length() - m_indent.length());
	}

	public void beginRow() {

		if (m_debug) {
			System.out.append("JavaScript: " + m_currentIndent);

			// print indentation only when debugging
			m_out.write(m_currentIndent.toString());
		}
	}

	public void endRow() {

		if (m_debug) {
			System.out.append(m_lineBreak);

			// print line break only when debugging
			m_out.write(m_lineBreak);
		}
	}

	@Override
	public JavascriptFormatter append(char code) {

		if (m_debug) {
			System.out.append(code);
		}

		m_out.write(code);
		return this;
	}

	@Override
	public JavascriptFormatter append(CharSequence code) {

		if (m_debug) {
			System.out.append(code);
		}

		m_out.write(code);
		return this;
	}

	@Override
	public JavascriptFormatter append(CharSequence code, int start, int end) {

		if (m_debug) {
			System.out.append(code, start, end);
		}

		m_out.write(code, start, end);
		return this;
	}

	public void appendRow(String code) {

		beginRow();
		append(code);
		endRow();
	}

	private final IManagedWriter m_out;
	private String m_indent = "";
	private String m_lineBreak = "";
	private boolean m_debug = false;

	private final StringBuilder m_currentIndent = new StringBuilder();
}
