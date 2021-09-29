package com.softicar.platform.emf.matrix.dimension;

import com.softicar.platform.emf.matrix.IEmfSettingMatrixConfiguration;
import java.util.Map;
import java.util.Set;

/**
 * A dimension strategy of a setting matrix, providing dimension type dependent
 * functionality.
 *
 * @author Alexander Schmidt
 * @see IEmfSettingMatrixConfiguration
 */
public interface IEmfSettingMatrixDimensionStrategy {

	/**
	 * Creates a new set that contains objects of the dimension type.
	 *
	 * @param <D>
	 *            the type of the set entries
	 * @return a new set that contains objects of the dimension type (never
	 *         null)
	 */
	<D> Set<D> createSet();

	/**
	 * Creates a new map that uses objects of the dimension type as keys.
	 *
	 * @param <D>
	 *            the type of the map keys
	 * @param <V>
	 *            the type of the map values
	 * @return a new map hat uses objects of the dimension type as keys (never
	 *         null)
	 */
	<D, V> Map<D, V> createMap();
}
