package com.softicar.platform.emf.predicate;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.function.Function;

public class EmfPredicateAdapter<R extends IEmfTableRow<R, ?>, F extends IEmfTableRow<F, ?>> implements IEmfPredicate<R> {

	private final Function<R, F> ownerGetter;
	private final IEmfPredicate<F> predicate;

	public EmfPredicateAdapter(IEmfPredicate<F> predicate, Function<R, F> ownerGetter) {

		this.ownerGetter = ownerGetter;
		this.predicate = predicate;
	}

	@Override
	public IDisplayString getTitle() {

		return predicate.getTitle();
	}

	@Override
	public boolean test(R tableRow) {

		return predicate.test(ownerGetter.apply(tableRow));
	}
}
