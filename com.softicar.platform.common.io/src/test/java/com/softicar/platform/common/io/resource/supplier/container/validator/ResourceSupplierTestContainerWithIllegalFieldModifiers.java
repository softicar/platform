package com.softicar.platform.common.io.resource.supplier.container.validator;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.io.resource.supplier.ResourceSupplierFactory;

@ResourceSupplierContainer
class ResourceSupplierTestContainerWithIllegalFieldModifiers {

	private static final IResourceSupplierFactory FACTORY = new ResourceSupplierFactory(ResourceSupplierTestContainerWithIllegalFieldModifiers.class);

	public static IResourceSupplier NON_FINAL = FACTORY.create("non-final.file");
	public final IResourceSupplier NON_STATIC = FACTORY.create("non-static.file");

	static final IResourceSupplier VISIBILITY_DEFAULT = FACTORY.create("visibility-default.file");
	@SuppressWarnings("unused") private static final IResourceSupplier VISIBILITY_PRIVATE = FACTORY.create("visibility-private.file");
	@SuppressWarnings("unused") private static final IResourceSupplier VISIBILITY_PROTECTED = FACTORY.create("visibility-protected.file");
}
