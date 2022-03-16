package com.softicar.platform.common.io.resource.supplier.container.validator;

import com.softicar.platform.common.core.constant.container.validator.AbstractConstantContainersValidator;
import com.softicar.platform.common.core.constant.container.validator.IConstantContainerValidator;
import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClassCache;
import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClassFetcher;
import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationEnvironment;
import com.softicar.platform.common.core.java.code.validator.JavaCodeValidator;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;

@JavaCodeValidator
public class ResourceSupplierContainersValidator extends AbstractConstantContainersValidator<IResourceSupplier> {

	private AnalyzedJavaClassCache cache;

	public ResourceSupplierContainersValidator() {

		super(IResourceSupplier.class);
		this.cache = null;
	}

	@Override
	protected void prepareForEnvironment(JavaCodeValidationEnvironment environment) {

		this.cache = new AnalyzedJavaClassCache(environment.getClassPath());
	}

	@Override
	protected AnalyzedJavaClassFetcher createClassFetcher() {

		return new ResourceSupplierContainerAnalyzedJavaClassFetcher(cache);
	}

	@Override
	protected IConstantContainerValidator<IResourceSupplier> createValidator(Class<?> containerClass) {

		return new ResourceSupplierContainerValidator(containerClass);
	}
}
