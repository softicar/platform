package com.softicar.platform.emf.predicate;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.EmfI18n;

public class EmfPredicateOr<R> extends AbstractEmfPredicate<R> {

	@SafeVarargs
	public EmfPredicateOr(IEmfPredicate<R>...predicates) {

		super(predicates);
	}

	@Override
	public boolean test(R tableRow) {

		return predicates//
			.stream()
			.anyMatch(p -> p.test(tableRow));
	}

	@Override
	public void accept(IEmfPredicateVisitor<R> visitor) {

		visitor.visitOr(predicates);
	}

	@Override
	protected IDisplayString getOperatorTitle() {

		return EmfI18n.OR;
	}
}
