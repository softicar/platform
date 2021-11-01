package com.softicar.platform.emf.matrix;

/**
 * Interface of the primary, matrix based view of an
 * {@link EmfSettingMatrixDiv}.
 *
 * @author Alexander Schmidt
 */
public interface IEmfSettingMatrixView {

	/**
	 * Applies the current {@link IEmfSettingMatrixModelData} to the values of
	 * the input elements in this view.
	 */
	void applyFromModel();

	/**
	 * Applies the values from input elements in this view to the current
	 * {@link IEmfSettingMatrixModelData}.
	 */
	void applyToModel();
}
