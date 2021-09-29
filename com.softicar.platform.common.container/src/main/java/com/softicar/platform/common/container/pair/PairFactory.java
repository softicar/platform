package com.softicar.platform.common.container.pair;

import java.util.Map.Entry;

/**
 * Some static factory methods for {@link Pair}.
 * 
 * @author Oliver Richers
 */
public abstract class PairFactory {

	public static <A, B> Pair<A, B> create(Entry<A, B> mapEntry) {

		return create(mapEntry.getKey(), mapEntry.getValue());
	}

	public static <A, B> Pair<A, B> create(A first, B second) {

		return new Pair<>(first, second);
	}
}
