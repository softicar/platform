package com.softicar.platform.common.io.resource.key;

import com.softicar.platform.common.io.resource.IResource;

/**
 * Abstractly identifies an {@link IResource}.
 *
 * @author Alexander Schmidt
 */
public interface IResourceKey extends Comparable<IResourceKey> {

	/**
	 * Returns the name of the package in which the {@link IResource} resides.
	 * <p>
	 * All letters in the returned {@link String} are lower-case.
	 *
	 * @return the package name of the {@link IResource} (never <i>null</i>)
	 */
	String getPackageName();

	/**
	 * Returns a new {@link ResourceKeyBasename}, derived from the file name of
	 * the {@link IResource}.
	 *
	 * @return the {@link ResourceKeyBasename} of the {@link IResource} (never
	 *         <i>null</i>)
	 */
	ResourceKeyBasename getBasename();

	/**
	 * Returns a new {@link ResourceKeySuperMimeType}, derived from the file
	 * name of the {@link IResource}.
	 *
	 * @return the {@link ResourceKeySuperMimeType} of the {@link IResource}
	 *         (never <i>null</i>)
	 */
	ResourceKeySuperMimeType getSuperMimeType();
}
