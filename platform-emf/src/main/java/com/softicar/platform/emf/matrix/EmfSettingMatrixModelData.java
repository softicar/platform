package com.softicar.platform.emf.matrix;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * The standard implementation of the data container of an
 * {@link IEmfSettingMatrixModel}.
 *
 * @author Alexander Schmidt
 */
public class EmfSettingMatrixModelData<R, C, V> implements IEmfSettingMatrixModelData<R, C, V> {

	private final IEmfSettingMatrixConfiguration<R, C, V> configuration;
	private final Map<R, Map<C, V>> valueMap;
	private final Map<C, V> wildcardValueMap;
	private final Set<R> rows;
	private final Set<C> columns;

	public EmfSettingMatrixModelData(IEmfSettingMatrixConfiguration<R, C, V> configuration) {

		this.configuration = configuration;
		this.valueMap = configuration.getRowStrategy().createMap();
		this.wildcardValueMap = configuration.getColumnStrategy().createMap();
		this.rows = configuration.getRowStrategy().createSet();
		this.columns = configuration.getColumnStrategy().createSet();
	}

	@Override
	public Optional<V> getValue(R row, C column) {

		return Optional//
			.ofNullable(valueMap.getOrDefault(row, Collections.emptyMap()).get(column));
	}

	@Override
	public Optional<V> getWildcardValue(C column) {

		return Optional.ofNullable(wildcardValueMap.get(column));
	}

	@Override
	public void setValue(R row, C column, V value) {

		setValue(row, column, Optional.ofNullable(value));
	}

	@Override
	public void setValue(R row, C column, Optional<V> value) {

		if (value.isPresent()) {
			valueMap//
				.computeIfAbsent(row, dummy -> configuration.getColumnStrategy().createMap())
				.put(column, value.get());
			rows.add(row);
			columns.add(column);
		}
	}

	@Override
	public void setWildcardValue(C column, V value) {

		setWildcardValue(column, Optional.ofNullable(value));
	}

	@Override
	public void setWildcardValue(C column, Optional<V> value) {

		if (value.isPresent()) {
			wildcardValueMap.put(column, value.get());
			columns.add(column);
		}
	}

	@Override
	public Set<R> getRows() {

		return rows;
	}

	@Override
	public Set<C> getColumns() {

		return columns;
	}

	@Override
	public void clearAll() {

		this.valueMap.clear();
		this.wildcardValueMap.clear();
		this.rows.clear();
		this.columns.clear();
	}

	@Override
	public V createDefaultValue() {

		return configuration.getDefaultValueSupplier().get();
	}
}
