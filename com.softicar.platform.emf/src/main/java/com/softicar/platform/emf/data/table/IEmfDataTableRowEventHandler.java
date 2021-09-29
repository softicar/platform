package com.softicar.platform.emf.data.table;

@FunctionalInterface
public interface IEmfDataTableRowEventHandler<R> {

	void handleEvent(R row);
}
