package com.softicar.platform.common.io.resource.supplier.container.validator;

import com.softicar.platform.common.core.constant.container.validator.result.ConstantContainerValidatorResult;
import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationEnvironment;
import com.softicar.platform.common.core.java.code.validator.IJavaCodeValidator;
import com.softicar.platform.common.core.java.code.validator.JavaCodeValidator;
import com.softicar.platform.common.io.resource.classpath.validator.ClasspathResourcesValidator;
import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.container.ResourceSupplierContainers;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;

/**
 * Validates all classes that have a {@link ResourceSupplierContainer}
 * annotation.
 * <p>
 * Please note that classes with missing annotation will be discovered by
 * {@link ClasspathResourcesValidator}, because their resources will appear to
 * be orphaned.
 *
 * @author Oliver Richers
 */
@JavaCodeValidator
public class ResourceSupplierContainerCodeValidator implements IJavaCodeValidator {

	@Override
	public void validate(JavaCodeValidationEnvironment environment) {

		var result = new ConstantContainerValidatorResult<IResourceSupplier>();

		for (var containerClass: ResourceSupplierContainers.findAll()) {
			environment.logVerbose("Validating resource container: %s", containerClass.getCanonicalName());
			new ResourceSupplierContainerValidator(containerClass).validate(result);
		}

		result.throwExceptionIfNotEmpty();
	}
}
