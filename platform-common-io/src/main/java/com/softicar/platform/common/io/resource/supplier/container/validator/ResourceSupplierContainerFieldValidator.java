package com.softicar.platform.common.io.resource.supplier.container.validator;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.constant.container.validator.AbstractConstantContainerFieldValidator;
import com.softicar.platform.common.core.constant.container.validator.IConstantContainerValidator;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;

class ResourceSupplierContainerFieldValidator extends AbstractConstantContainerFieldValidator<IResourceSupplier> {

	public ResourceSupplierContainerFieldValidator(IConstantContainerField<IResourceSupplier> currentField,
			IConstantContainerField<IResourceSupplier> previousField) {

		super(currentField, previousField);
	}

	@Override
	protected IConstantContainerValidator<IResourceSupplier> createFieldValueValidator(IConstantContainerField<IResourceSupplier> field) {

		return new ResourceSupplierContainerFieldValueValidator(field);
	}
}
