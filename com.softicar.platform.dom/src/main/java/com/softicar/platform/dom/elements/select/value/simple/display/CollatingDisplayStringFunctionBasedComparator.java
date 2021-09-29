package com.softicar.platform.dom.elements.select.value.simple.display;

import com.softicar.platform.common.core.i18n.IDisplayString;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;

class CollatingDisplayStringFunctionBasedComparator<T> implements Comparator<T> {

	private static final int COLLATOR_DECOMPOSITION = Collator.CANONICAL_DECOMPOSITION;
	private static final int COLLATOR_STRENGTH = Collator.SECONDARY;

	private final Function<T, IDisplayString> displayStringFunction;
	private final Collator collator;

	public CollatingDisplayStringFunctionBasedComparator(Function<T, IDisplayString> displayStringFunction) {

		this.displayStringFunction = Objects.requireNonNull(displayStringFunction);

		// TODO: Better use the CurrentUser's locale. However, CurrentUser is currently not available here.
//		Locale locale = CurrentUser.get().getLocale();
		Locale locale = Locale.ENGLISH;
		this.collator = Collator.getInstance(locale);
		this.collator.setStrength(COLLATOR_STRENGTH);
		this.collator.setDecomposition(COLLATOR_DECOMPOSITION);
	}

	@Override
	public int compare(T first, T second) {

		String firstDisplayString = displayStringFunction.apply(first).toString();
		String secondDisplayString = displayStringFunction.apply(second).toString();
		return collator.compare(firstDisplayString, secondDisplayString);
	}
}
