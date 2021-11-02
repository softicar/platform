package com.softicar.platform.emf.matrix;

/**
 * Options for an {@link IEmfSettingMatrixView}.
 *
 * @author Alexander Schmidt
 */
public interface IEmfSettingMatrixViewOptions {

	/**
	 * Returns the entered row filter string.
	 *
	 * @return the entered row filter string (never null)
	 */
	String getRowFilterString();

	/**
	 * Returns the entered column filter string.
	 *
	 * @return the entered column filter string (never null)
	 */
	String getColumnFilterString();

	/**
	 * Determines if the "hide rows with default values" option is enabled.
	 *
	 * @return true if the "hide rows with default values" option is enabled;
	 *         false otherwise
	 */
	boolean isHideRowsWithDefaultValues();

	/**
	 * Determines if the "hide columns with default values" option is enabled.
	 *
	 * @return true if the "hide columns with default values" option is enabled;
	 *         false otherwise
	 */
	boolean isHideColumnsWithDefaultValues();

	/**
	 * Determines if the "flip rows and column" option is enabled.
	 *
	 * @return true if the "flip rows and column" option is enabled; false
	 *         otherwise
	 */
	boolean isFlipRowsAndColumns();
}
