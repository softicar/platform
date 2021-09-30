package com.softicar.platform.common.io.resource.supplier;

import com.softicar.platform.common.io.resource.static_.IStaticResource;
import java.nio.charset.Charset;

/**
 * Convenience factory for instances of {@link IResourceSupplier}.
 *
 * @author Alexander Schmidt
 */
public class ResourceSupplierFactory extends AbstractResourceSupplierFactory {

	public ResourceSupplierFactory(Class<?> anchorClass) {

		super(anchorClass);
	}

	public ResourceSupplierFactory(Class<?> anchorClass, Charset charset) {

		super(anchorClass, charset);
	}

	@Override
	protected IResourceSupplier createSupplier(IStaticResource staticResource) {

		return new ResourceSupplier(staticResource);
	}
}
