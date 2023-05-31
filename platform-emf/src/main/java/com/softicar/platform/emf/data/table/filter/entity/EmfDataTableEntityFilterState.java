package com.softicar.platform.emf.data.table.filter.entity;

import java.util.Optional;

class EmfDataTableEntityFilterState<T> {

	private final EmfDataTableEntityFilterType filterType;
	private final T filterEntity;
	private final String filterText;

	public EmfDataTableEntityFilterState(EmfDataTableEntityFilterType filterType, T filterEntity, String filterText) {

		this.filterType = filterType;
		this.filterEntity = filterEntity;
		this.filterText = filterText;
	}

	public EmfDataTableEntityFilterType getFilterType() {

		return filterType;
	}

	public Optional<T> getFilterEntity() {

		return Optional.ofNullable(filterEntity);
	}

	public String getFilterText() {

		return filterText;
	}
}
