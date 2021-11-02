package com.softicar.platform.dom.elements.tables.scrollable;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.dom.elements.tables.DomDataTable;
import com.softicar.platform.dom.elements.tables.pageable.DomPageableTable;
import com.softicar.platform.dom.elements.tables.scrollable.hooks.IPostPagingHook;
import com.softicar.platform.dom.elements.tables.scrollable.hooks.IPostRowAppendingHook;
import com.softicar.platform.dom.elements.tables.scrollable.hooks.IPrePagingHook;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * A dom table that can be used to scroll through a number of rows.
 * <p>
 * The rows will be cached by this class, so that subsequent requests of the
 * same row will be very fast. Don't forget to clear the cache when you're
 * changing the content of the table.
 * <p>
 * If you don't need to scroll by row but by page, see {@link DomPageableTable}.
 *
 * @author Oliver Richers
 */
public abstract class DomScrollableTable extends DomDataTable {

	private final Map<Integer, DomRow> rowCache = new TreeMap<>();

	private IPrePagingHook prePagingHook = null;
	private IPostRowAppendingHook postRowAppendingHook = null;
	private IPostPagingHook postPagingHook = null;

	/**
	 * Displays the rows with the index in the range [first,last].
	 *
	 * @param first
	 *            the index of the first row to display
	 * @param last
	 *            the index of the last row to display
	 */
	public void show(int first, int last) {

		// first, remove all rows from the table
		getBody().removeChildren();

		// check boundaries
		if (first < 0 || last < 0 || first > last) {
			return;
		}

		if (this.prePagingHook != null) {
			this.prePagingHook.handlePrePaging();
		}

		// append rows
		Collection<DomRow> rows = getRows(first, last);

		for (DomRow row: rows) {
			getBody().appendChild(row);

			if (this.postRowAppendingHook != null) {
				this.postRowAppendingHook.handlePostRowAppending(row);
			}
		}

		if (this.postPagingHook != null) {
			this.postPagingHook.handlePostPaging();
		}
	}

	/**
	 * Removes all rows from the row cache.
	 * <p>
	 * You should call this if the previously shown rows are invalidated because
	 * the content of the table has changed.
	 */
	public void clearRowCache() {

		rowCache.clear();
	}

	public List<DomRow> getDisplayedRows() {

		return getBody().getChildren().stream().map(node -> (DomRow) node).collect(Collectors.toList());
	}

	public Collection<DomRow> getRows(int first, int last) {

		Collection<DomRow> rows = new ArrayList<>(last - first + 1);

		// iterate from first to last and append rows
		Integer batchStart = null;
		for (int i = first; i <= last; ++i) {
			// try to use cache
			DomRow row = rowCache.get(i);
			if (row != null) {
				// create rows in batch mode
				if (batchStart != null) {
					rows.addAll(createAndCacheRows(batchStart, i - 1));
					batchStart = null;
				}

				rows.add(row);
			} else {
				// don't create row, postpone for creation in batch mode
				if (batchStart == null) {
					batchStart = i;
				}
			}
		}

		// create rows in batch mode
		if (batchStart != null) {
			rows.addAll(createAndCacheRows(batchStart, last));
		}

		return rows;
	}

	public Collection<DomRow> getRowsUncached(int first, int last) {

		return Collections.unmodifiableCollection(createRows(first, last));
	}

	/**
	 * Creates the row with the specified index.
	 *
	 * @param index
	 *            the index of the row starting from 0
	 * @return returns the created row
	 */
	protected abstract DomRow createRow(int index);

	/**
	 * You can override this function if you want to speed-up the creation of
	 * rows by creating them in batch mode.
	 *
	 * @param first
	 *            the index of the first row
	 * @param last
	 *            the index of the last row
	 * @return a collection containing all created rows
	 */
	protected Collection<? extends DomRow> createRows(int first, int last) {

		if (first < 0 || last < 0 || first > last) {
			throw new SofticarDeveloperException("Invalid first %s and last %s: ", first, last);
		}

		Collection<DomRow> rows = new ArrayList<>(last - first + 1);
		for (int i = first; i <= last; ++i) {
			rows.add(createRow(i));
		}
		return rows;
	}

	private Collection<? extends DomRow> createAndCacheRows(int first, int last) {

		if (first < 0 || last < 0 || first > last) {
			throw new SofticarDeveloperException("Invalid first %s and last %s: ", first, last);
		}

		// create rows
		Collection<? extends DomRow> rows = createRows(first, last);

		// add rows to cache
		for (DomRow row: rows) {
			rowCache.put(first++, row);
		}

		return rows;
	}

	//
	// HOOKS
	//

	public void setPostRowAppendingHook(IPostRowAppendingHook postRowAppendingHook) {

		this.postRowAppendingHook = postRowAppendingHook;
	}

	public void setPrePagingHook(IPrePagingHook prePagingHook) {

		this.prePagingHook = prePagingHook;
	}

	public void setPostPagingHook(IPostPagingHook postPagingHook) {

		this.postPagingHook = postPagingHook;
	}
}
