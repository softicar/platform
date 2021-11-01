package com.softicar.platform.emf.data.table;

import java.util.Collection;

/**
 * Pre-fetches data for several rows.
 *
 * @author Alexander Schmidt
 */
@FunctionalInterface
public interface IDataTablePrefetcher<R> {

	void prefetchData(Collection<R> rows);
}
