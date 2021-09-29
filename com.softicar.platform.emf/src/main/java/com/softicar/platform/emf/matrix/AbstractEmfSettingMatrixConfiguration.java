package com.softicar.platform.emf.matrix;

import java.util.Optional;

/**
 * Base class of a configuration of an {@link EmfSettingMatrixDiv}.
 *
 * @author Alexander Schmidt
 */
public abstract class AbstractEmfSettingMatrixConfiguration<R, C, V> implements IEmfSettingMatrixConfiguration<R, C, V> {

	/**
	 * Determines if the given values are equal.
	 *
	 * @param first
	 *            the first value (non-null)
	 * @param second
	 *            the second value (non-null)
	 * @return true if the given values are equal; false otherwise
	 */
	protected abstract boolean isEqualValues(V first, V second);

	/**
	 * Creates a new value by merging the given inferior and superior values.
	 * <p>
	 * For atomic values, merging boils down to returning a deep copy of the
	 * second value (see {@link #deepCopyValue(Object)}). For composite values
	 * (i.e. values that consist of several atomic values), contained
	 * corresponding atomic values need to be merged, prioritizing those from
	 * the second (superior) argument.
	 *
	 * @param inferior
	 *            the inferior value for the merge (may be null)
	 * @param superior
	 *            the superior value for the merge (may be null)
	 * @return a new value as the result of merging the given inferior and
	 *         superior values (may be null)
	 */
	protected abstract V mergeValues(V inferior, V superior);

	@Override
	public Optional<V> calculateDeltaValue(Optional<V> inferior, Optional<V> superior) {

		if (inferior.isPresent() && superior.isPresent()) {
			V inferiorValue = inferior.get();
			V superiorValue = superior.get();
			if (isEqualValues(inferiorValue, superiorValue)) {
				return Optional.empty();
			} else {
				return Optional.of(mergeValues(inferiorValue, superiorValue));
			}
		} else if (superior.isPresent()) {
			return Optional.of(mergeValues(getDefaultValueSupplier().get(), superior.get()));
		} else {
			return Optional.empty();
		}
	}
}
