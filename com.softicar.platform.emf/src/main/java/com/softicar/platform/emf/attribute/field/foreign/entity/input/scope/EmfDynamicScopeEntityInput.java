package com.softicar.platform.emf.attribute.field.foreign.entity.input.scope;

import com.softicar.platform.dom.elements.input.auto.entity.DomAutoCompleteEntityInputFilter;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.engine.AbstractEmfDependentAutoCompleteInputEngine;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.emf.table.IEmfTable;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class EmfDynamicScopeEntityInput<E extends IEmfObject<E>, S> extends EmfEntityInput<E> {

	private final IEmfTable<E, ?, S> table;
	private final Supplier<Optional<S>> scopeSupplier;
	private final Function<S, Collection<E>> itemLoader;
	private final InputEngine inputEngine;

	public EmfDynamicScopeEntityInput(IEmfTable<E, ?, S> table, Supplier<Optional<S>> scopeSupplier, Function<S, Collection<E>> itemLoader) {

		this.table = table;
		this.itemLoader = itemLoader;
		this.scopeSupplier = scopeSupplier;
		this.inputEngine = new InputEngine();
		setPlaceholder(table.getTitle());
		setInputEngine(inputEngine);
	}

	@Override
	public void refreshInputConstraints() {

		super.refreshInputConstraints();

		// auto-fill input if suitable
		if (getSelection().isBlankPattern()) {
			Collection<E> items = inputEngine.getItems();
			if (items.size() == 1) {
				setValue(items.iterator().next());
			}
		}
	}

	private class InputEngine extends AbstractEmfDependentAutoCompleteInputEngine<E> {

		private final DomAutoCompleteEntityInputFilter<S> scopeFilter;

		public InputEngine() {

			super(table);
			this.scopeFilter = addFilter(scopeSupplier)
				.setFilterTitle(
					table//
						.getScopeField()
						.map(table::getAttribute)
						.map(IEmfAttribute::getTitle)
						.orElse(EmfI18n.FILTER));
		}

		@Override
		protected Collection<E> loadItems() {

			Optional<S> scope = scopeFilter.getValue();
			if (scope.isPresent()) {
				return itemLoader.apply(scope.get());
			} else {
				return Collections.emptyList();
			}
		}
	}
}
