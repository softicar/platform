package com.softicar.platform.common.code.java;

import com.softicar.platform.common.core.exceptions.SofticarException;

public class IllegalJavaQualifiedName extends SofticarException {

	private static final long serialVersionUID = 1L;

	public IllegalJavaQualifiedName(String format, Object...args) {

		super(format, args);
	}
}
