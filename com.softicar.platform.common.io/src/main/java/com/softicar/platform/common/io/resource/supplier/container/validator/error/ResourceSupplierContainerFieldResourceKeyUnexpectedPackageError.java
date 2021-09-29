package com.softicar.platform.common.io.resource.supplier.container.validator.error;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.constant.container.validator.error.AbstractConstantContainerValidationError;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;

public class ResourceSupplierContainerFieldResourceKeyUnexpectedPackageError extends AbstractConstantContainerValidationError<IResourceSupplier> {

	public ResourceSupplierContainerFieldResourceKeyUnexpectedPackageError(IConstantContainerField<IResourceSupplier> field, String expectedPackageName,
			String encounteredPackageName) {

		super(field, "Resource key: Unexpected package. Expected '%s' but encountered '%s'", expectedPackageName, encounteredPackageName);
	}
}
