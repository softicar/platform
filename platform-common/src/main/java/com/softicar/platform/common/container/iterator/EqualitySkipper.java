package com.softicar.platform.common.container.iterator;

/**
 * Simple implementation of the {@link ISkipper} interface.
 * <p>
 * The {@link #shallSkip(Object)} function returns true if and only if the
 * example object is the same as the tested object or equal to it.
 * 
 * @author Oliver Richers
 * @param <T>
 *            the type of the example and test objects
 */
public class EqualitySkipper<T> implements ISkipper<T> {

	private final T exampleObject;

	/**
	 * Construct the skipper with the specified example object.
	 * 
	 * @param exampleObject
	 *            an object to compare the test objects to; may be null
	 */
	public EqualitySkipper(T exampleObject) {

		this.exampleObject = exampleObject;
	}

	@Override
	public boolean shallSkip(T testObject) {

		return exampleObject == testObject || (exampleObject != null && exampleObject.equals(testObject));
	}
}
