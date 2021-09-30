package com.softicar.platform.common.io.resource.supplier.container.validator.error;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.constant.container.validator.error.AbstractConstantContainerValidationError;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;

public class ResourceSupplierContainerFieldResourceKeyIllegalBasenameError extends AbstractConstantContainerValidationError<IResourceSupplier> {

	public ResourceSupplierContainerFieldResourceKeyIllegalBasenameError(IConstantContainerField<IResourceSupplier> field, String basename) {

		super(field, "Resource key: Illegal basename: '%s'", basename);
	}
}
