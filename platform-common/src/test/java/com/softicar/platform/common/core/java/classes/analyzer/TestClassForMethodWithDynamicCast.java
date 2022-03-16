package com.softicar.platform.common.core.java.classes.analyzer;

class TestClassForMethodWithDynamicCast {

	@SuppressWarnings("unused")
	public void foo() {

		// cast to Double
		Number d = (Double) getSome();

		// this use of primitive type should be ignored
		byte[] b = (byte[]) getSome();
	}

	private Object getSome() {

		return null;
	}
}
