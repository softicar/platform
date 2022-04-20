package com.softicar.platform.dom.elements.tables.pageable;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.element.DomElement;
import com.softicar.platform.dom.elements.tables.pageable.navigation.DomPageableTableNavigation;
import com.softicar.platform.dom.elements.tables.pageable.navigation.IDomPageableTableNavigation;
import com.softicar.platform.dom.elements.tables.scrollable.DomScrollableTable;
import java.util.ArrayList;
import java.util.List;

/**
 * A {@link DomScrollableTable} table that scrolls by pages.
 * <p>
 * In addition to {@link DomScrollableTable} this table manages its own
 * navigations. You can create an unlimited number of navigations anywhere on
 * the web page.
 *
 * @author Oliver Richers
 */
public abstract class DomPageableTable extends DomScrollableTable {

	public static final int DEFAULT_PAGE_SIZE = 20;
	private static final int MAX_LINES_PER_PAGE = 5000;
	private final List<IDomPageableTableNavigation> navigations = new ArrayList<>();
	private int currentPage;
	private int totalRowCount;
	private int pageSize;

	// -------------------- constructors -------------------- //

	/**
	 * Constructs the table without showing any rows.
	 */
	public DomPageableTable() {

		setMarker(DomPageableTableMarker.TABLE);
	}

	/**
	 * Constructs the table with the given configuration.
	 */
	public DomPageableTable(IDomPageableTableConfiguration config) {

		this.pageSize = config.getPageSize();
	}

	// -------------------- resetting -------------------- //

	/**
	 * Resets the complete table.
	 * <p>
	 * This resets the total number of rows, the number of rows to show per
	 * page, sets the currently shown page to zero and clears the internal row
	 * cache.
	 *
	 * @param totalRowCount
	 *            the total number of rows
	 * @param pageSize
	 *            the number of rows to show per page
	 */
	public void reset(int totalRowCount, int pageSize) {

		this.currentPage = 0;
		this.totalRowCount = totalRowCount;
		this.pageSize = pageSize;

		clearRowCache();
		updateAll();
	}

	// -------------------- navigation -------------------- //

	/**
	 * @return a new Navigation {@link DomElement} to page through this table
	 */
	public DomElement createNavigation() {

		return createNavigation(new IDomPageableTableNavigationButtonBuilder[0]);
	}

	public DomElement createNavigation(IDomPageableTableNavigationButtonBuilder...customButtonBuilders) {

		DomPageableTableNavigation navigation = new DomPageableTableNavigation(this, List.of(customButtonBuilders));
		navigations.add(navigation);
		navigation.update();

		return navigation;
	}

	// -------------------- current page -------------------- //

	/**
	 * Returns the index of the current page.
	 *
	 * @return the current page index in the range [0, n)
	 */
	public int getCurrentPage() {

		return currentPage;
	}

	/**
	 * Sets the index of the currently shown page. Caps to a range of [0,n).
	 *
	 * @param pageIndex
	 *            the zero-based index of the page to display
	 */
	public void setCurrentPage(int pageIndex) {

		this.currentPage = Math.max(Math.min(pageIndex, getPageCount() - 1), 0);
		updateAll();
	}

	// -------------------- page size -------------------- //

	/**
	 * Returns the current page size.
	 *
	 * @return the number of rows per page
	 */
	public int getPageSize() {

		return pageSize;
	}

	public void setPageSize(int pageSize) {

		setPageSize(pageSize, true);
	}

	/**
	 * Sets the page size to the specified value.
	 *
	 * @param pageSize
	 *            the number of row to show on one page
	 * @param doSetCurrentPage
	 *            whether the current page should be altered
	 */
	public void setPageSize(int pageSize, boolean doSetCurrentPage) {

		if (pageSize != this.pageSize) {
			if (pageSize > MAX_LINES_PER_PAGE) {
				throw new SofticarUserException(DomI18n.PLEASE_ENTER_A_LOWER_VALUE_FOR_THE_NUMBER_OF_ROWS_PER_PAGE_MAXIMUM_ARG1.toDisplay(MAX_LINES_PER_PAGE));
			}

			// get the index of the first row of the current page
			int currentFirst = currentPage * this.pageSize;

			// set new page size and set currently displayed page
			this.pageSize = pageSize;

			if (doSetCurrentPage) {
				setCurrentPage(currentFirst % this.pageSize);
			}
		}
	}

	// -------------------- page and row count -------------------- //

	/**
	 * Returns the number of pages that will be shown using the current
	 * configuration of pages per site and number of entries.
	 *
	 * @return page count
	 */
	public int getPageCount() {

		int count = totalRowCount / pageSize;
		return totalRowCount % pageSize != 0? count + 1 : count;
	}

	/**
	 * Returns the total number of rows of this table.
	 * <p>
	 * This number includes the shown as well as the currently not shown table
	 * rows.
	 *
	 * @return the total number of rows
	 */
	public int getTotalRowCount() {

		return totalRowCount;
	}

	protected void updateAll() {

		// update table
		int last = Math.min((currentPage + 1) * pageSize, totalRowCount) - 1;
		int first = currentPage * pageSize;
		show(first, last);
		// update navigations
		for (IDomPageableTableNavigation navigation: navigations) {
			navigation.update();
		}
	}
}
