package com.softicar.platform.common.core.singleton;

import com.softicar.platform.common.testing.AbstractTest;

abstract class AbstractSingletonTest extends AbstractTest {

	protected static final String X = "X";
	protected static final String Y = "Y";
	protected final SingletonSet singletonSet;
	protected final Singleton<String> singleton;

	public AbstractSingletonTest() {

		this.singletonSet = new SingletonSet();
		this.singleton = new Singleton<>();

		CurrentSingletonSet.set(singletonSet);
	}
}
