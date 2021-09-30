package com.softicar.platform.emf.data.table.filter;

import com.softicar.platform.dom.node.IDomNode;

public interface IEmfDataTableFilterTypeSelect<T> extends IDomNode {

	T getSelectedType();

	void setSelectedType(T type);
}
