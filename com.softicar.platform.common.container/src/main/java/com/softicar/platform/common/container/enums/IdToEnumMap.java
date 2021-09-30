package com.softicar.platform.common.container.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Efficient mapping of {@link Integer} to {@link Enum} constants.
 *
 * @author Oliver Richers
 */
public class IdToEnumMap<E extends Enum<E>> {

	private final Map<Integer, E> map;

	public IdToEnumMap(Class<E> enumClass, Function<E, Integer> idMapper) {

		this.map = new HashMap<>(enumClass.getEnumConstants().length);
		for (E enumerator: enumClass.getEnumConstants()) {
			map.put(idMapper.apply(enumerator), enumerator);
		}
	}

	/**
	 * Returns the matching enumerator or <i>null</i>.
	 * <p>
	 * If the given ID is null, this method return null.
	 *
	 * @param id
	 *            the enumerator ID (may be null)
	 * @return the matching enumerator or null
	 */
	public E get(Integer id) {

		if (id != null) {
			return map.get(id);
		} else {
			return null;
		}
	}

	public Optional<E> getOptional(Integer id) {

		return Optional.ofNullable(get(id));
	}
}
