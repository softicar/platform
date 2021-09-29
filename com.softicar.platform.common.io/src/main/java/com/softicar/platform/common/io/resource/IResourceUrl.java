package com.softicar.platform.common.io.resource;

/**
 * Instances of this interface describe the location of a resource.
 * 
 * @author Oliver Richers
 */
public interface IResourceUrl extends Comparable<IResourceUrl> {

	/**
	 * Returns the textual representation of this resource URL.
	 * 
	 * @return the resource URL as a string
	 */
	@Override
	String toString();

	/**
	 * Compares the textual representation of this resource URL to the textual
	 * representation of the other resource.
	 * 
	 * @param other
	 *            the resource URL to compare this resource URL to
	 */
	@Override
	int compareTo(IResourceUrl other);
}
