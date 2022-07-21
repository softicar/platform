package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.dom.DomI18n;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Default implementation of {@link IDomAutoCompleteInputFilter}.
 *
 * @author Oliver Richers
 */
public class DomAutoCompleteInputFilter<V> implements IDomAutoCompleteInputFilter {

	private final Supplier<Optional<V>> valueSupplier;
	private IDisplayString filterTitle;
	private Function<V, IDisplayString> valueTitleFunction;
	private Optional<V> value;

	public DomAutoCompleteInputFilter(Supplier<Optional<V>> valueSupplier) {

		this.valueSupplier = Objects.requireNonNull(valueSupplier);
		this.filterTitle = DomI18n.FILTER;
		this.valueTitleFunction = this::getDefaultValueTitle;
		this.value = valueSupplier.get();
	}

	@Override
	public boolean isActive() {

		return value.isPresent();
	}

	@Override
	public IDisplayString getFilterTitle() {

		return filterTitle;
	}

	@Override
	public IDisplayString getValueTitle() {

		return value.isPresent()? valueTitleFunction.apply(value.get()) : IDisplayString.EMPTY;
	}

	@Override
	public void refresh() {

		this.value = valueSupplier.get();
	}

	public DomAutoCompleteInputFilter<V> setFilterTitle(IDisplayString filterTitle) {

		this.filterTitle = filterTitle;
		return this;
	}

	public DomAutoCompleteInputFilter<V> setValueTitleFunction(Function<V, IDisplayString> valueTitleFunction) {

		this.valueTitleFunction = valueTitleFunction;
		return this;
	}

	public Optional<V> getValue() {

		return value;
	}

	private IDisplayString getDefaultValueTitle(V value) {

		if (value instanceof IDisplayable) {
			return ((IDisplayable) value).toDisplay();
		} else {
			return IDisplayString.create(value.toString());
		}
	}
}
