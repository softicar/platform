package com.softicar.platform.emf.matrix;

import java.util.Optional;
import java.util.Set;

/**
 * The standard implementation of the data model of an
 * {@link EmfSettingMatrixDiv}.
 *
 * @author Alexander Schmidt
 */
public class EmfSettingMatrixModel<R, C, V> implements IEmfSettingMatrixModel<R, C, V> {

	private final IEmfSettingMatrixConfiguration<R, C, V> configuration;
	private final EmfSettingMatrixModelData<R, C, V> originalData;
	private final EmfSettingMatrixModelData<R, C, V> currentData;

	public EmfSettingMatrixModel(IEmfSettingMatrixConfiguration<R, C, V> configuration) {

		this.configuration = configuration;
		this.originalData = new EmfSettingMatrixModelData<>(configuration);
		this.currentData = new EmfSettingMatrixModelData<>(configuration);
	}

	@Override
	public IEmfSettingMatrixModelData<R, C, V> getOriginalData() {

		return originalData;
	}

	@Override
	public IEmfSettingMatrixModelData<R, C, V> getCurrentData() {

		return currentData;
	}

	@Override
	public IEmfSettingMatrixModelData<R, C, V> calculateDeltaData() {

		return calculateDelta(originalData, currentData);
	}

	@Override
	public void applyOriginalToCurrent() {

		applyModelData(originalData, currentData);
	}

	@Override
	public void applyCurrentToOriginal() {

		applyModelData(currentData, originalData);
	}

	private void applyModelData(EmfSettingMatrixModelData<R, C, V> fromData, EmfSettingMatrixModelData<R, C, V> toData) {

		toData.clearAll();
		Set<R> rows = fromData.getRows();
		Set<C> columns = fromData.getColumns();
		for (C column: columns) {
			for (R row: rows) {
				fromData//
					.getValue(row, column)
					.map(configuration::deepCopyValue)
					.ifPresent(value -> toData.setValue(row, column, value));
			}
			fromData//
				.getWildcardValue(column)
				.map(configuration::deepCopyValue)
				.ifPresent(value -> toData.setWildcardValue(column, value));
		}
	}

	private EmfSettingMatrixModelData<R, C, V> calculateDelta(EmfSettingMatrixModelData<R, C, V> first, EmfSettingMatrixModelData<R, C, V> second) {

		Set<R> rows = configuration.getRowStrategy().createSet();
		rows.addAll(first.getRows());
		rows.addAll(second.getRows());

		Set<C> columns = configuration.getColumnStrategy().createSet();
		columns.addAll(first.getColumns());
		columns.addAll(second.getColumns());

		EmfSettingMatrixModelData<R, C, V> delta = new EmfSettingMatrixModelData<>(configuration);
		for (R row: rows) {
			for (C column: columns) {
				Optional<V> firstValue = first.getValue(row, column);
				Optional<V> secondValue = second.getValue(row, column);
				Optional<V> deltaValue = configuration.calculateDeltaValue(firstValue, secondValue);
				delta.setValue(row, column, deltaValue);
			}
		}
		return delta;
	}
}
