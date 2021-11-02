package com.softicar.platform.common.io.resource.supplier.container.validator.error;

import com.softicar.platform.common.core.constant.container.validator.error.AbstractConstantContainerValidationError;
import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;

public class ResourceSupplierContainerMissingAnnotationError extends AbstractConstantContainerValidationError<IResourceSupplier> {

	public ResourceSupplierContainerMissingAnnotationError(Class<?> containerClass) {

		super(//
			containerClass,
			"Resource container: Class '%s' lacks annotation '%s'.",
			containerClass.getCanonicalName(),
			ResourceSupplierContainer.class.getCanonicalName());
	}
}
