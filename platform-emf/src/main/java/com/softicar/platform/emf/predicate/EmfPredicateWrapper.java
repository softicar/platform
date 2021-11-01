package com.softicar.platform.emf.predicate;

public class EmfPredicateWrapper {

	private final IEmfPredicate<?> predicate;

	public EmfPredicateWrapper(IEmfPredicate<?> predicate) {

		this.predicate = predicate;
	}

	public IEmfPredicate<?> getPredicate() {

		return predicate;
	}
}
