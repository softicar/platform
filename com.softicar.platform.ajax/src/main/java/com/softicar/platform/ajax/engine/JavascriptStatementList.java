package com.softicar.platform.ajax.engine;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of JavaScript statements, i.e. a program.
 *
 * @author Oliver Richers
 */
public class JavascriptStatementList extends AbstractJavascriptCode {

	private final List<AbstractJavascriptCode> statements;

	public JavascriptStatementList() {

		this.statements = new ArrayList<>();
	}

	public <T extends AbstractJavascriptCode> T append(T code) {

		statements.add(code);
		return code;
	}

	public JavascriptStatement appendStatement(String code) {

		return append(new JavascriptStatement(code));
	}

	public void appendStatements(JavascriptStatementList other) {

		statements.addAll(other.statements);
	}

	public void replaceStatement(int index, String code) {

		statements.set(index, new JavascriptStatement(code));
	}

	@Override
	public void appendTo(JavascriptFormatter out) {

		for (AbstractJavascriptCode code: statements) {
			code.appendTo(out);
		}
	}

	public int size() {

		return statements.size();
	}

	public void clear() {

		statements.clear();
	}
}
