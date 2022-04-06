package com.softicar.platform.emf.management.importing.engine.test;

class DefaultStrategy<T> implements Strategy<T> {

	@Override
	public void execute(T t) {

		System.out.println(t);
	}
}
