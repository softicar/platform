package com.softicar.platform.common.io.resource.supplier.container.validator.error;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.constant.container.validator.error.AbstractConstantContainerValidationError;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;

public class ResourceSupplierContainerFieldDefaultResourceLoadingExceptionError extends AbstractConstantContainerValidationError<IResourceSupplier> {

	public ResourceSupplierContainerFieldDefaultResourceLoadingExceptionError(IConstantContainerField<IResourceSupplier> field, Exception exception) {

		super(//
			field,
			"Default resource: Exception while loading: %s",
			StackTraceFormatting.getStackTraceAsString(exception));
	}
}
