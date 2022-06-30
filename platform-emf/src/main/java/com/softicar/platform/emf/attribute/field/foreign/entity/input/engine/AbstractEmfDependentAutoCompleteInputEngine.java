package com.softicar.platform.emf.attribute.field.foreign.entity.input.engine;

import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputFilter;
import com.softicar.platform.dom.elements.input.auto.entity.DomAutoCompleteEntityInMemoryInputEngine;
import com.softicar.platform.dom.elements.input.auto.entity.DomAutoCompleteEntityInputFilter;
import com.softicar.platform.emf.entity.IEmfEntity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * An input engine for {@link IEmfEntity} instances.
 *
 * @author Alexander Schmidt
 */
public abstract class AbstractEmfDependentAutoCompleteInputEngine<E extends IEmfEntity<E, ?>> extends DomAutoCompleteEntityInMemoryInputEngine<E> {

	private final Collection<IDomAutoCompleteInputFilter> filters;

	public AbstractEmfDependentAutoCompleteInputEngine(IDbTable<E, ?> table) {

		this.filters = new ArrayList<>();

		setLoader(this::loadItems);
		addDependsOn(table);
	}

	/**
	 * Creates a {@link DomAutoCompleteEntityInputFilter} from the given value
	 * supplier, and adds it to the internal list of filters.
	 *
	 * @param valueSupplier
	 *            the value supplier from which a
	 *            {@link DomAutoCompleteEntityInputFilter} should be created
	 *            (never null)
	 * @return the created {@link DomAutoCompleteEntityInputFilter} (never null)
	 */
	protected <V> DomAutoCompleteEntityInputFilter<V> addFilter(Supplier<Optional<V>> valueSupplier) {

		DomAutoCompleteEntityInputFilter<V> filter = new DomAutoCompleteEntityInputFilter<>(Objects.requireNonNull(valueSupplier));
		this.filters.add(filter);
		return filter;
	}

	@Override
	public void refresh() {

		filters.forEach(IDomAutoCompleteInputFilter::refresh);
		refreshCache();
	}

	@Override
	public Collection<IDomAutoCompleteInputFilter> getFilters() {

		return Collections.unmodifiableCollection(filters);
	}

	/**
	 * Loads all items that match the current filter settings.
	 *
	 * @return all items that match the current filter settings (never null)
	 */
	protected abstract Collection<E> loadItems();
}
