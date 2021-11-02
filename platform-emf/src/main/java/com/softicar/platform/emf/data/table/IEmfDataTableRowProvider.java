package com.softicar.platform.emf.data.table;

import java.util.Collection;

public interface IEmfDataTableRowProvider<R> {

	Collection<R> getAllRows();
}
