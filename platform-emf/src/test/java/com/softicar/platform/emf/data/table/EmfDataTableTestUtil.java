package com.softicar.platform.emf.data.table;

class EmfDataTableTestUtil {

	public static int calculateTotalPageCount(int numTotalRows, int pageSize) {

		if (numTotalRows >= 0 && pageSize > 0) {
			return (numTotalRows / pageSize) + (numTotalRows % pageSize > 0? 1 : 0);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public static int calculateNumberOfRowsOnLastPage(int numTotalRows, int pageSize) {

		if (numTotalRows >= 0 && pageSize > 0) {
			int modulo = numTotalRows % pageSize;
			if (modulo == 0) {
				return Math.min(pageSize, numTotalRows);
			} else {
				return modulo;
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	public static int calculateRowCountOnPage(final int pageIndex, final int totalRowCount, final int pageSize) {

		final int lastPageIndex = EmfDataTableTestUtil.calculateTotalPageCount(totalRowCount, pageSize) - 1;
		if (pageIndex < lastPageIndex) {
			return pageSize;
		} else if (pageIndex == lastPageIndex) {
			return EmfDataTableTestUtil.calculateNumberOfRowsOnLastPage(totalRowCount, pageSize);
		} else {
			throw new IllegalArgumentException();
		}
	}
}
