package com.softicar.platform.emf.predicate;

import com.softicar.platform.common.core.i18n.IDisplayString;
import java.util.function.Predicate;

public class EmfPredicate<R> implements IEmfPredicate<R> {

	private final IDisplayString title;
	private final Predicate<R> predicate;

	public EmfPredicate(IDisplayString title, Predicate<R> predicate) {

		this.title = title;
		this.predicate = predicate;
	}

	@Override
	public IDisplayString getTitle() {

		return title;
	}

	@Override
	public boolean test(R tableRow) {

		return tableRow != null && predicate.test(tableRow);
	}

	@Override
	public String toString() {

		return getTitle().toString();
	}
}
