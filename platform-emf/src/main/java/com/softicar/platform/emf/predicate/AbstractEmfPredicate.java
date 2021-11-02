package com.softicar.platform.emf.predicate;

import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.DisplayStrings;
import com.softicar.platform.common.core.i18n.IDisplayString;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractEmfPredicate<E> implements IEmfPredicate<E> {

	protected final List<IEmfPredicate<E>> predicates;

	@SafeVarargs
	public AbstractEmfPredicate(IEmfPredicate<E>...predicates) {

		this.predicates = Arrays.asList(predicates);
	}

	@Override
	public IDisplayString getTitle() {

		return new DisplayString()//
			.append("(")
			.append(
				predicates//
					.stream()
					.map(p -> p.getTitle())
					.collect(DisplayStrings.joining(getSeparator())))
			.append(")");
	}

	@Override
	public String toString() {

		return getTitle().toString();
	}

	protected abstract IDisplayString getOperatorTitle();

	private DisplayString getSeparator() {

		return new DisplayString().append(" ").append(getOperatorTitle()).append(" ");
	}
}
