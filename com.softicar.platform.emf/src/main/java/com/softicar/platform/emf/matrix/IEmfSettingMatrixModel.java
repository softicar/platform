package com.softicar.platform.emf.matrix;

/**
 * Data model of an {@link EmfSettingMatrixDiv}.
 *
 * @author Alexander Schmidt
 */
public interface IEmfSettingMatrixModel<R, C, V> {

	/**
	 * Returns the original data container.
	 *
	 * @return the original data container (never null)
	 */
	IEmfSettingMatrixModelData<R, C, V> getOriginalData();

	/**
	 * Returns the current data container.
	 *
	 * @return the current data container (never null)
	 */
	IEmfSettingMatrixModelData<R, C, V> getCurrentData();

	/**
	 * Calculates a new delta data container that contains the difference
	 * between the original and the current data container.
	 * <p>
	 * If values in those two containers are in conflict, values from the
	 * current data container are prioritized.
	 *
	 * @return a new delta data container that contains the difference between
	 *         the original and the current data container (never null)
	 */
	IEmfSettingMatrixModelData<R, C, V> calculateDeltaData();

	/**
	 * Applies the content of the original data container to the current data
	 * containers, resulting in their contents being the same.
	 */
	void applyOriginalToCurrent();

	/**
	 * Applies the content of the current data container to the original data
	 * containers, resulting in their contents being the same.
	 */
	void applyCurrentToOriginal();
}
