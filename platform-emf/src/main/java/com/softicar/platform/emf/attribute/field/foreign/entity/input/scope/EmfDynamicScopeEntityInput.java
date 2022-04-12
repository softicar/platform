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

	public EmfDynamicScopeEntityInput(IEmfTable<E, ?, S> table, Supplier<Optional<S>> scopeSupplier, Function<S, Collection<E>> itemLoader) {

		super(new InputEngine<>(table, scopeSupplier, itemLoader));
		setPlaceholder(table.getTitle());
	}

	@Override
	public void refreshInputConstraints() {

		super.refreshInputConstraints();

		// auto-fill input if suitable
		if (getSelection().isBlankPattern()) {
			Collection<E> items = inputEngine.findMatches("", 2);
			if (items.size() == 1) {
				setValue(items.iterator().next());
			}
		}
	}

	private static class InputEngine<E extends IEmfObject<E>, S> extends AbstractEmfDependentAutoCompleteInputEngine<E> {

		private final Function<S, Collection<E>> itemLoader;
		private final DomAutoCompleteEntityInputFilter<S> scopeFilter;

		public InputEngine(IEmfTable<E, ?, S> table, Supplier<Optional<S>> scopeSupplier, Function<S, Collection<E>> itemLoader) {

			super(table);
			this.itemLoader = itemLoader;
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
