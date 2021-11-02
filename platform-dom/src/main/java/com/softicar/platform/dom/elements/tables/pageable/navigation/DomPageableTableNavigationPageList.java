package com.softicar.platform.dom.elements.tables.pageable.navigation;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.tables.pageable.DomPageableTable;

class DomPageableTableNavigationPageList extends DomDiv {

	private static final int PAGE_RANGE = 4;
	private static final int ELLIPSIS_WIDTH = 1;
	private static final int ELLIPSIS_THRESHOLD = 1 + ELLIPSIS_WIDTH + PAGE_RANGE + 1 + PAGE_RANGE + ELLIPSIS_WIDTH + 1;
	private static final String ELLIPSIS_STRING = "...";

	public DomPageableTableNavigationPageList(DomPageableTable table) {

		setCssClass(DomElementsCssClasses.DOM_PAGEABLE_TABLE_NAVIGATION_PAGE_LIST);

		if (table.getPageCount() > ELLIPSIS_THRESHOLD) {
			int lastPage = table.getPageCount() - 1;

			// before current page
			int begin = Math.max(table.getCurrentPage() - PAGE_RANGE, 0);
			int end = Math.min(begin + 2 * PAGE_RANGE, lastPage);
			if (lastPage - end < 4) {
				end = lastPage;
			}

			if (begin < 4) {
				// no ellipsis on the left
				for (int i = 0; i <= end; ++i) {
					appendChild(new PageButton(table, i));
				}
			} else {
				appendChild(new PageButton(table, 0));
				appendChild(new PageButton(table, begin / 2, ELLIPSIS_STRING));
				for (int i = begin; i <= end; ++i) {
					appendChild(new PageButton(table, i));
				}
			}

			if (end < lastPage) {
				appendChild(new PageButton(table, end + (lastPage - end) / 2, ELLIPSIS_STRING));
				appendChild(new PageButton(table, lastPage));
			}
		} else {
			for (int i = 0; i < table.getPageCount(); ++i) {
				appendChild(new PageButton(table, i));
			}
		}
	}
}
