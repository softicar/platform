package com.softicar.platform.common.io.resource.static_;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;

/**
 * An {@link IResourceSupplier} that provides access to an underlying static
 * {@link IResource}.
 *
 * @author Alexander Schmidt
 */
public interface IStaticResourceSupplier extends IResourceSupplier {

	/**
	 * Returns the static {@link IResource} that underlies the {@link IResource}
	 * retrieved via {@link #getResource()}.
	 *
	 * @return the static {@link IResource} (never <i>null</i>)
	 */
	IResource getStaticResource();
}
