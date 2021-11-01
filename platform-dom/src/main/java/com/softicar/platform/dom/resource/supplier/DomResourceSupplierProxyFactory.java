package com.softicar.platform.dom.resource.supplier;

import com.softicar.platform.common.io.resource.static_.IStaticResource;
import com.softicar.platform.common.io.resource.supplier.AbstractResourceSupplierFactory;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import java.nio.charset.Charset;

/**
 * Convenience factory for proxy-based instances of {@link IResourceSupplier}.
 *
 * @author Oliver Richers
 */
public class DomResourceSupplierProxyFactory extends AbstractResourceSupplierFactory {

	public DomResourceSupplierProxyFactory(Class<?> anchorClass) {

		super(anchorClass);
	}

	public DomResourceSupplierProxyFactory(Class<?> anchorClass, Charset charset) {

		super(anchorClass, charset);
	}

	@Override
	protected IResourceSupplier createSupplier(IStaticResource staticResource) {

		return new DomResourceSupplierProxy(staticResource);
	}
}
