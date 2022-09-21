package com.softicar.platform.common.io.resource.supplier.container.validator;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.constant.container.validator.AbstractConstantContainerValidator;
import com.softicar.platform.common.core.constant.container.validator.IConstantContainerValidator;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;

class ResourceSupplierContainerValidator extends AbstractConstantContainerValidator<IResourceSupplier> {

	public ResourceSupplierContainerValidator(Class<?> containerClass) {

		super(containerClass, IResourceSupplier.class);
		addAdditionalValidator(new ResourceSupplierContainerResourceKeyUniquenessValidator(createFieldExtractor()::extractFields));
		addAdditionalValidator(new ResourceSupplierContainerAnnotationValidator(containerClass));
	}

	@Override
	protected IConstantContainerValidator<IResourceSupplier> createFieldValidator(IConstantContainerField<IResourceSupplier> currentField,
			IConstantContainerField<IResourceSupplier> previousField) {

		return new ResourceSupplierContainerFieldValidator(currentField, previousField);
	}
}
