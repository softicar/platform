package com.softicar.platform.emf.data.table.column.handler.collection;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.emf.data.table.column.IEmfDataTableColumn;
import java.util.Collection;
import java.util.function.Supplier;

public interface IEmfDataTableCollectionAdapter<C, E> {

	/**
	 * Returns the {@link IDisplayString} for the given element.
	 *
	 * @param element
	 *            the element (never null)
	 * @return the {@link IDisplayString} (never null)
	 */
	IDisplayString getDisplayStringWithoutId(E element);

	/**
	 * Creates a new input node an element.
	 * <p>
	 * Optionally, the given supplier can be used to supply all valid elements
	 * for the input node.
	 *
	 * @param validElementsSupplier
	 * @return the newly created input node
	 */
	DomAutoCompleteInput<E> createInputNode(Supplier<Collection<E>> validElementsSupplier);

	/**
	 * Pre-fetches all data for the given collections.
	 * <p>
	 * The goals is to make the subsequent calls to {@link #getElements} more
	 * efficient.
	 *
	 * @param column
	 *            the column (never null)
	 * @param collections
	 *            the set of collection for which {@link #getElements} will be
	 *            called (never null)
	 */
	void prefetchData(IEmfDataTableColumn<?, C> column, Collection<C> collections);

	/**
	 * Returns the elements in the given collection.
	 *
	 * @param collection
	 *            the collection (never null)
	 * @return all contained elements
	 */
	Collection<E> getElements(C collection);

	/**
	 * Checks whether the given element is contained in the given collection.
	 *
	 * @param collection
	 *            the collection to test (never null)
	 * @param element
	 *            the element to search for (never null)
	 * @return whether the given element is contained
	 */
	boolean containsElement(C collection, E element);
}
