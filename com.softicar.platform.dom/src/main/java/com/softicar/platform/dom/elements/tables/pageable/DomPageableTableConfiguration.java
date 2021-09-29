package com.softicar.platform.dom.elements.tables.pageable;

public class DomPageableTableConfiguration implements IDomPageableTableConfiguration {

	private int pageSize;

	public DomPageableTableConfiguration() {

		this.pageSize = DomPageableTable.DEFAULT_PAGE_SIZE;
	}

	@Override
	public int getPageSize() {

		return pageSize;
	}

	public DomPageableTableConfiguration setPageSize(int pageSize) {

		this.pageSize = pageSize;
		return this;
	}
}
