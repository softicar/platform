package com.softicar.platform.emf.data.table;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Supplier;

public class EmfDataTableLazyRowCache<R> implements IEmfDataTableRowProvider<R> {

	private final Supplier<Collection<R>> rowLoader;
	private Collection<R> rows;
	private boolean valid;

	public EmfDataTableLazyRowCache(Supplier<Collection<R>> rowLoader) {

		this.rowLoader = Objects.requireNonNull(rowLoader);
		invalidate();
	}

	@Override
	public Collection<R> getAllRows() {

		if (!valid) {
			reloadRows();
		}
		return rows;
	}

	public void invalidate() {

		this.rows = Collections.emptyList();
		this.valid = false;
	}

	private void reloadRows() {

		this.rows = rowLoader.get();
		this.valid = true;
	}
}
