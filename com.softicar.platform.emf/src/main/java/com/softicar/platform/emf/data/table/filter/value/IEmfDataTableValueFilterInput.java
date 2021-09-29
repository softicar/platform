package com.softicar.platform.emf.data.table.filter.value;

import com.softicar.platform.emf.data.table.filter.IInputNode;

public interface IEmfDataTableValueFilterInput<T> extends IInputNode {

	T getFilterValue();

	void setFilterValue(T value);
}
