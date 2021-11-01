package com.softicar.platform.emf;

public class EmfTestScope {

	private static final EmfTestScope INSTANCE = new EmfTestScope();

	private EmfTestScope() {

		// singleton
	}

	public static EmfTestScope getInstance() {

		return INSTANCE;
	}
}
