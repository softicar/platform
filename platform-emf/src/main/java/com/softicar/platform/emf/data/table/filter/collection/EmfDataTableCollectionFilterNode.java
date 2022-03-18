package com.softicar.platform.emf.data.table.filter.collection;

import com.softicar.platform.common.container.data.table.DataTableCollectionFilterOperator;
import com.softicar.platform.common.container.data.table.IDataTableFilterList;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.input.auto.IDomAutoCompleteInput;
import com.softicar.platform.emf.data.table.EmfDataTableI18n;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import com.softicar.platform.emf.data.table.column.handler.collection.IEmfDataTableCollectionAdapter;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilter;
import com.softicar.platform.emf.data.table.filter.IEmfDataTableFilterNode;
import com.softicar.platform.emf.data.table.filter.nop.EmfDataTableNopFilter;
import java.util.Collection;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Filter node for a collection of elements.
 * <p>
 * The term <i>collection</i> need not be taken literally, that is, it is not
 * necessary to implement the interface {@link Collection}. Instead, an instance
 * of {@link IEmfDataTableCollectionAdapter} should be supplied.
 *
 * @author Oliver Richers
 */
public class EmfDataTableCollectionFilterNode<R, C, E> extends DomDiv implements IEmfDataTableFilterNode<R> {

	protected final IEmfDataTableColumn<R, C> column;
	private final IEmfDataTableCollectionAdapter<C, E> adapter;
	private final IDomAutoCompleteInput<E> input;

	public EmfDataTableCollectionFilterNode(IEmfDataTableColumn<R, C> column, IEmfDataTableCollectionAdapter<C, E> adapter) {

		this.column = column;
		this.adapter = adapter;
		this.input = adapter.createInputNode(this::getAllElements);

		appendChild(input);
	}

	@Override
	public IEmfDataTableFilter<R> createFilter() {

		E element = input.getSelection().getValueOrNull();
		if (element != null) {
			return new Filter(element);
		} else {
			return new EmfDataTableNopFilter<>();
		}
	}

	// ------------------------------ private ------------------------------ //

	private Collection<E> getAllElements() {

		Collection<C> collections = column.getDistinctColumnValues();

		adapter.prefetchData(column, collections);

		return collections//
			.stream()
			.map(adapter::getElements)
			.flatMap(Collection::stream)
			.collect(Collectors.toCollection(TreeSet::new));
	}

	private void addFilterTo(IDataTableFilterList<R> filters, E element) {

		Collection<C> collections = column.getDistinctColumnValues();

		adapter.prefetchData(column, collections);

		Collection<C> matchingCollections = collections//
			.stream()
			.filter(collection -> adapter.containsElement(collection, element))
			.collect(Collectors.toCollection(TreeSet::new));

		filters.addCollectionFilter(column.getDataColumn(), DataTableCollectionFilterOperator.IN, matchingCollections);
	}

	private class Filter implements IEmfDataTableFilter<R> {

		private final E element;

		public Filter(E element) {

			this.element = element;
		}

		@Override
		public IDisplayString getDisplayString() {

			return EmfDataTableI18n.CONTAINS.concat(adapter.getDisplayStringWithoutId(element).encloseInParentheses());
		}

		@Override
		public void addTo(IDataTableFilterList<R> filters) {

			addFilterTo(filters, element);
		}

		@Override
		public void reset() {

			input.setValue(element);
		}
	}
}
