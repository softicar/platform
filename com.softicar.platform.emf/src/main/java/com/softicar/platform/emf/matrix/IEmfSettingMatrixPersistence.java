package com.softicar.platform.emf.matrix;

import com.softicar.platform.db.core.transaction.DbTransaction;

/**
 * Handles persistence for a {@link IEmfSettingMatrixModelData} object.
 *
 * @author Alexander Schmidt
 */
public interface IEmfSettingMatrixPersistence<R, C, V> {

	/**
	 * Loads persistently stored settings and applies them to the given
	 * {@link IEmfSettingMatrixModelData} object.
	 * <p>
	 * Calls are wrapped in a {@link DbTransaction}.
	 *
	 * @param modelData
	 *            the model data object to which the settings should be applied
	 *            (non-null)
	 */
	void load(IEmfSettingMatrixModelData<R, C, V> modelData);

	/**
	 * Persistently stores the settings contained in the given delta
	 * {@link IEmfSettingMatrixModelData} object.
	 * <p>
	 * Calls are wrapped in a {@link DbTransaction}.
	 *
	 * @param deltaModelData
	 *            the delta model data object that contains the settings to be
	 *            stored; only contains delta/changed values (non-null)
	 */
	void save(IEmfSettingMatrixModelData<R, C, V> deltaModelData);
}
