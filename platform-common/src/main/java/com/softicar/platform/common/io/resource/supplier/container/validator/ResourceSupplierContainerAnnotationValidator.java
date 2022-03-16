package com.softicar.platform.common.io.resource.supplier.container.validator;

import com.softicar.platform.common.core.constant.container.validator.IConstantContainerValidator;
import com.softicar.platform.common.core.constant.container.validator.result.ConstantContainerValidatorResult;
import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.container.validator.error.ResourceSupplierContainerMissingAnnotationError;
import java.util.stream.Stream;

public class ResourceSupplierContainerAnnotationValidator implements IConstantContainerValidator<IResourceSupplier> {

	private final Class<?> containerClass;

	public ResourceSupplierContainerAnnotationValidator(Class<?> containerClass) {

		this.containerClass = containerClass;
	}

	@Override
	public void validate(ConstantContainerValidatorResult<IResourceSupplier> result) {

		boolean lacksAnnotation = Stream//
			.of(containerClass.getAnnotations())
			.noneMatch(annotation -> annotation.annotationType().equals(ResourceSupplierContainer.class));
		if (lacksAnnotation) {
			result.addError(new ResourceSupplierContainerMissingAnnotationError(containerClass));
		}
	}
}
