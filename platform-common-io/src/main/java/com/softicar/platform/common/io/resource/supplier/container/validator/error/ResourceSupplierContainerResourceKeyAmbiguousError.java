package com.softicar.platform.common.io.resource.supplier.container.validator.error;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.constant.container.validator.error.AbstractConstantContainerValidationError;
import com.softicar.platform.common.io.resource.key.IResourceKey;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;

public class ResourceSupplierContainerResourceKeyAmbiguousError extends AbstractConstantContainerValidationError<IResourceSupplier> {

	public ResourceSupplierContainerResourceKeyAmbiguousError(IConstantContainerField<IResourceSupplier> field, IResourceKey resourceKey) {

		super(//
			field,
			"Resource key: Resource key '%s' is implied by another field in the same container class.",
			resourceKey);
	}
}
