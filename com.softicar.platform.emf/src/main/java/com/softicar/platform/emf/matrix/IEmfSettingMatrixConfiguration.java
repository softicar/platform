package com.softicar.platform.emf.matrix;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.ui.color.IColor;
import com.softicar.platform.dom.style.CssColorEnum;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.matrix.dimension.EmfSettingMatrixTreeDimensionStrategy;
import com.softicar.platform.emf.matrix.dimension.IEmfSettingMatrixDimensionStrategy;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Configuration of an {@link EmfSettingMatrixDiv}.
 *
 * @param <R>
 *            the row type
 * @param <C>
 *            the column type
 * @param <V>
 *            the value type
 * @author Alexander Schmidt
 */
public interface IEmfSettingMatrixConfiguration<R, C, V> {

	/**
	 * Determines if the configuration matrix shall be editable.
	 *
	 * @return true if the configuration matrix shall be editable; false
	 *         otherwise
	 */
	boolean isEditable();

	/**
	 * Determines if input is possible for the given row and column coordinates.
	 * <p>
	 * If false is returned, {@link #createInput(Supplier)} will not be called
	 * for the respective combination of coordinates.
	 * <p>
	 * Invoked for each displayed combination of coordinates, so the
	 * implementation should be highly efficient.
	 *
	 * @param row
	 *            the row coordinate (non-null)
	 * @param column
	 *            the column coordinate (non-null)
	 * @return true if input is possible for the given row and column
	 *         coordinates; false otherwise
	 */
	default boolean isInputPossible(R row, C column) {

		DevNull.swallow(row);
		DevNull.swallow(column);
		return true;
	}

	/**
	 * Returns the row strategy of the matrix.
	 *
	 * @return the row strategy of the matrix (never null)
	 */
	default IEmfSettingMatrixDimensionStrategy getRowStrategy() {

		return new EmfSettingMatrixTreeDimensionStrategy();
	}

	/**
	 * Returns the column strategy of the matrix.
	 *
	 * @return the column strategy of the matrix (never null)
	 */
	default IEmfSettingMatrixDimensionStrategy getColumnStrategy() {

		return new EmfSettingMatrixTreeDimensionStrategy();
	}

	/**
	 * Returns a new value input element.
	 *
	 * @param originalValueSupplier
	 *            a supplier for a deep copy of the original value of this input
	 *            element (non-null)
	 * @return a new value input element for the given row and column
	 *         coordinates (never null)
	 */
	IEmfSettingMatrixModelEntryInput<V> createInput(Supplier<Optional<V>> originalValueSupplier);

	/**
	 * Returns a new wildcard value input element.
	 *
	 * @param originalValueSupplier
	 *            a supplier for a deep copy of the original value of this input
	 *            element (non-null)
	 * @return a new wildcard value input element for the given row and column
	 *         coordinates (never null)
	 */
	default IEmfSettingMatrixModelEntryInput<V> createWildcardInput(Supplier<Optional<V>> originalValueSupplier) {

		return createInput(originalValueSupplier);
	}

	/**
	 * Returns a factory to generate default values.
	 *
	 * @return a factory to generate default values (never null)
	 */
	Supplier<V> getDefaultValueSupplier();

	/**
	 * Returns the display name of the row dimension.
	 *
	 * @return the display name of the row dimension (never null)
	 */
	IDisplayString getRowTypeDisplayName();

	/**
	 * Returns the display name of the column dimension.
	 *
	 * @return the display name of the column dimension (never null)
	 */
	IDisplayString getColumnTypeDisplayName();

	/**
	 * Returns the display name of a row entry.
	 *
	 * @param row
	 *            the row for which the display name should be determined
	 *            (non-null)
	 * @return the display name of a row entry (never null)
	 */
	IDisplayString getRowDisplayName(R row);

	/**
	 * Returns the display name of a column entry.
	 *
	 * @param column
	 *            the column for which the display name should be determined
	 *            (non-null)
	 * @return the display name of a column entry (never null)
	 */
	IDisplayString getColumnDisplayName(C column);

	/**
	 * Determines if the given value corresponds to a default value.
	 *
	 * @param value
	 *            the value to be checked (may be null)
	 * @return true if the given value corresponds to a default value; false
	 *         otherwise
	 */
	boolean isDefaultValue(V value);

	/**
	 * Creates a deep copy of the given value.
	 *
	 * @param value
	 *            the value to copy (non-null)
	 * @return a deep copy of the given value (never null)
	 */
	V deepCopyValue(V value);

	/**
	 * Creates a new value from the difference between the given values.
	 * <p>
	 * For composite values (i.e. values that consist of several atomic values),
	 * corresponding atomic values of the second (superior) argument dominate
	 * atomic values of the first (inferior) argument.
	 *
	 * @param inferior
	 *            the inferior value for the delta calculation (may be null)
	 * @param superior
	 *            the superior value for the delta calculation (may be null)
	 * @return a new value from the difference between the given values
	 */
	Optional<V> calculateDeltaValue(Optional<V> inferior, Optional<V> superior);

	/**
	 * Determines if the wildcard row should be displayed.
	 *
	 * @return true if the wildcard row should be displayed; false otherwise
	 */
	default boolean isWildcardRowDisplayed() {

		return false;
	}

	/**
	 * Returns the display name of the wildcard row.
	 *
	 * @return the display name of the wildcard row (never null)
	 * @see #isWildcardRowDisplayed()
	 */
	default IDisplayString getWildcardRowDisplayName() {

		return EmfI18n.ALL;
	}

	/**
	 * Returns the color of the display name of the wildcard row label.
	 *
	 * @return the color of the display name of the wildcard row label (never
	 *         null)
	 * @see #isWildcardRowDisplayed()
	 * @see #getWildcardRowDisplayName()
	 */
	default IColor getWildcardRowDisplayNameColor() {

		return CssColorEnum.RED;
	}
}
