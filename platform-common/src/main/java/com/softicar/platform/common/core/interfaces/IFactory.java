package com.softicar.platform.common.core.interfaces;

/**
 * The interface of a dynamic factory.
 * 
 * @param <T>
 *            the type of the created elements
 * @author Torsten Sommerfeld
 */
public interface IFactory<T> {

	/**
	 * Creates a new object.
	 * 
	 * @return a newly created object
	 */
	T create();
}
