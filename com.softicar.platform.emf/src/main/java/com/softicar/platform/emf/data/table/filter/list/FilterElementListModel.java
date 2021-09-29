package com.softicar.platform.emf.data.table.filter.list;

import com.softicar.platform.common.container.data.table.DataTableFilterListOperator;

class FilterElementListModel<R> {

	private final FilterElementList<R> filterElementList;
	private DataTableFilterListOperator operator;

	public FilterElementListModel() {

		operator = DataTableFilterListOperator.AND;
		filterElementList = new FilterElementList<>();
	}

	public FilterElementList<R> getFilterElementList() {

		return filterElementList;
	}

	public DataTableFilterListOperator getOperator() {

		return operator;
	}

	public void setOperator(DataTableFilterListOperator operator) {

		this.operator = operator;
	}
}
