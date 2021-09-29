package com.softicar.platform.common.container.pair;

/**
 * General interface of a pair of two elements.
 * <p>
 * A pair is a 2-tuple.
 * 
 * @param <A>
 *            type of the first element
 * @param <B>
 *            type of the second element
 * @author Oliver Richers
 */
public interface IPair<A, B> {

	/**
	 * @return the first element, may be null
	 */
	A getFirst();

	/**
	 * @return the second element, may be null
	 */
	B getSecond();

	/**
	 * Sets the value of the first element.
	 * 
	 * @param first
	 *            the new value of the first element, may be null
	 */
	void setFirst(A first);

	/**
	 * Sets the value of the second element.
	 * 
	 * @param second
	 *            the new value of the second element, may be null
	 */
	void setSecond(B second);
}
