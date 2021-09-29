package com.softicar.platform.db.sql.generation;

public class SqlJavaClassName {

	public SqlJavaClassName(String rawName, String arguments, String parameters) {

		_rawName = rawName;
		_arguments = arguments;
		_parameters = parameters;
	}

	public String getRawName() {

		return _rawName;
	}

	public String getArguments() {

		return _arguments;
	}

	public String getParameters() {

		return _parameters;
	}

	public String getType() {

		return _rawName + "<" + _arguments + ">";
	}

	public String getFullDeclaredType() {

		return _rawName + "<" + _parameters + ">";
	}

	private final String _rawName;
	private final String _arguments;
	private final String _parameters;
}
