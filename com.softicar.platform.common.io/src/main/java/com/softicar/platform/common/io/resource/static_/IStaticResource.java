package com.softicar.platform.common.io.resource.static_;

import com.softicar.platform.common.io.resource.IResource;

/**
 * Represents an {@link IResource} that is bundled with the Java source code.
 *
 * @author Oliver Richers
 */
public interface IStaticResource extends IResource {

	/**
	 * Returns a {@link Class} that resides in the package in which the resource
	 * file is located.
	 *
	 * @return the anchor {@link Class} (never <i>null</i>)
	 */
	Class<?> getAnchorClass();
}
