package com.softicar.platform.common.io.resource.supplier.container.validator.error;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.constant.container.validator.error.AbstractConstantContainerValidationError;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;

public class ResourceSupplierContainerFieldResourceUnexpectedSuperMimeTypeError extends AbstractConstantContainerValidationError<IResourceSupplier> {

	public ResourceSupplierContainerFieldResourceUnexpectedSuperMimeTypeError(IConstantContainerField<IResourceSupplier> field, String resourceMimeType,
			String resourceKeySuperMimeType) {

		super(//
			field,
			"Resource: Unexpected super mime type: Resource mime type '%s' was expected to start with super mime type '%s', as defined in the resource key.",
			resourceMimeType,
			resourceKeySuperMimeType);
	}
}
