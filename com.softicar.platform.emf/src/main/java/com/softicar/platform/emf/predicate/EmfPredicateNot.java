package com.softicar.platform.emf.predicate;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.EmfI18n;

public class EmfPredicateNot<R> implements IEmfPredicate<R> {

	private final IEmfPredicate<R> predicate;

	public EmfPredicateNot(IEmfPredicate<R> predicate) {

		this.predicate = predicate;
	}

	@Override
	public IDisplayString getTitle() {

		return EmfI18n.NOT_ARG1.toDisplay(predicate.getTitle());
	}

	@Override
	public boolean test(R tableRow) {

		return !predicate.test(tableRow);
	}

	@Override
	public String toString() {

		return getTitle().toString();
	}

	@Override
	public void accept(IEmfPredicateVisitor<R> visitor) {

		visitor.visitNot(predicate);
	}
}
