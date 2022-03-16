package com.softicar.platform.common.io.resource.supplier.container.validator;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.io.resource.supplier.ResourceSupplierFactory;
import java.util.ArrayList;
import java.util.function.Supplier;

@ResourceSupplierContainer
class ResourceSupplierTestContainerWithExtendsAndImplements extends ArrayList<Void> implements Supplier<Object> {

	public static final IResourceSupplierFactory FACTORY = new ResourceSupplierFactory(ResourceSupplierTestContainerWithExtendsAndImplements.class);

	@Override
	public Object get() {

		throw new UnsupportedOperationException();
	}
}
