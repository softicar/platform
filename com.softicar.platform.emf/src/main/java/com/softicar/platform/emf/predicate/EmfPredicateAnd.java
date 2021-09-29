package com.softicar.platform.emf.predicate;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.EmfI18n;

public class EmfPredicateAnd<R> extends AbstractEmfPredicate<R> {

	@SafeVarargs
	public EmfPredicateAnd(IEmfPredicate<R>...predicates) {

		super(predicates);
	}

	@Override
	public boolean test(R tableRow) {

		return predicates//
			.stream()
			.allMatch(p -> p.test(tableRow));
	}

	@Override
	public void accept(IEmfPredicateVisitor<R> visitor) {

		visitor.visitAnd(predicates);
	}

	@Override
	protected IDisplayString getOperatorTitle() {

		return EmfI18n.AND;
	}
}
