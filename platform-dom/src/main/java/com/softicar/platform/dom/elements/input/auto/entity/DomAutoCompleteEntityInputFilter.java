package com.softicar.platform.dom.elements.input.auto.entity;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputFilter;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class DomAutoCompleteEntityInputFilter<V> implements IDomAutoCompleteInputFilter {

	private final Supplier<Optional<V>> valueSupplier;
	private IDisplayString filterTitle;
	private Function<V, IDisplayString> valueTitleFunction;
	private Optional<V> value;

	public DomAutoCompleteEntityInputFilter(Supplier<Optional<V>> valueSupplier) {

		this.valueSupplier = valueSupplier;
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

	public DomAutoCompleteEntityInputFilter<V> setFilterTitle(IDisplayString filterTitle) {

		this.filterTitle = filterTitle;
		return this;
	}

	public DomAutoCompleteEntityInputFilter<V> setValueTitleFunction(Function<V, IDisplayString> valueTitleFunction) {

		this.valueTitleFunction = valueTitleFunction;
		return this;
	}

	public Optional<V> getValue() {

		return value;
	}

	private IDisplayString getDefaultValueTitle(V value) {

		if (value instanceof IEntity) {
			return ((IEntity) value).toDisplay();
		} else {
			return IDisplayString.create(value.toString());
		}
	}
}
