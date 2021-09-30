package com.softicar.platform.common.container.iterator;

/**
 * Simple skipping interface.
 * <p>
 * Implementations of this interface decide of a given element shall be skipped.
 * <p>
 * This interface is primarily used by {@link SkipIterator}.
 * 
 * @author Oliver Richers
 */
public interface ISkipper<T> {

	boolean shallSkip(T object);
}
