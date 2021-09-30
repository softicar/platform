package com.softicar.platform.emf.attribute.field.foreign.entity.input.engine;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.db.runtime.exception.DbException;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputFilter;
import com.softicar.platform.dom.elements.input.auto.entity.DomAutoCompleteEntityInMemoryInputEngine;
import com.softicar.platform.dom.elements.input.auto.entity.DomAutoCompleteEntityInputFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * An input engine for dependent auto-complete inputs, which works around a
 * problem with multi-threaded access to the AG cache.
 * <p>
 * Symptoms of the problem are:
 * <ul>
 * <li>the dependent auto-complete does not offer any items for selection</li>
 * <li>interacting with the dependent auto-complete prints a {@link DbException}
 * to the console, with a message like "Trying to load a table row belonging to
 * another thread."</li>
 * </ul>
 * <p>
 * This class will be obsolete as soon as i50084 is fixed properly.
 *
 * @author Alexander Schmidt
 */
public abstract class AbstractEmfDependentAutoCompleteInputEngine<I extends IEntity> extends DomAutoCompleteEntityInMemoryInputEngine<I> {

	private final IDbTable<I, ?> table;
	private final Collection<IDomAutoCompleteInputFilter> filters;

	public AbstractEmfDependentAutoCompleteInputEngine(IDbTable<I, ?> table) {

		this.table = Objects.requireNonNull(table);
		this.filters = new ArrayList<>();
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
		unstubAndPrefetchAndSetItems();
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
	protected abstract Collection<I> loadItems();

	/**
	 * Performs additional un-stubbing and pre-fetching with the loaded items.
	 * <p>
	 * Must be implemented such that the display-name could be determined for
	 * any given loaded item, without having to un-stub any record.
	 * <p>
	 * Does nothing by default.
	 *
	 * @param items
	 *            the loaded items (see {@link #loadItems()}) (never null)
	 */
	protected void unstubAndPrefetchMore(Collection<I> items) {

		DevNull.swallow(items);
	}

	private void unstubAndPrefetchAndSetItems() {

		Collection<I> items = loadItems();
		unstubLoadedItems(items);
		unstubAndPrefetchMore(items);
		setItems(items);
	}

	private void unstubLoadedItems(Collection<I> items) {

		table.unstubAll(items);
	}
}
