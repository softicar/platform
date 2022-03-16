package com.softicar.platform.common.io.resource.supplier.container.validator;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.io.resource.supplier.ResourceSupplierFactory;

@ResourceSupplierContainer
public interface ResourceSupplierTestContainerWithIllegalFieldNames {

	IResourceSupplierFactory FACTORY = new ResourceSupplierFactory(ResourceSupplierTestContainerWithIllegalFieldNames.class);

	IResourceSupplier _LEADING_UNSERSCORE = FACTORY.create("leading-underscore.file");
	IResourceSupplier DOUBLE__UNSERSCORE = FACTORY.create("double-underscore.file");
	IResourceSupplier TRAILING_UNSERSCORE_ = FACTORY.create("trailing-underscore.file");
	IResourceSupplier lower_case = FACTORY.create("lower-case.file");
}
