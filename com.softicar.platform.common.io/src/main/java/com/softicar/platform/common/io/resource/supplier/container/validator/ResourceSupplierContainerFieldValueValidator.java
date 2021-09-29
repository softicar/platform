package com.softicar.platform.common.io.resource.supplier.container.validator;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.constant.container.validator.AbstractConstantContainerFieldValueValidator;
import com.softicar.platform.common.core.constant.container.validator.result.ConstantContainerValidatorResult;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.default_.DefaultResourceFilename;
import com.softicar.platform.common.io.resource.key.IResourceKey;
import com.softicar.platform.common.io.resource.key.ResourceKeyBasename;
import com.softicar.platform.common.io.resource.key.ResourceKeySuperMimeType;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.container.validator.error.ResourceSupplierContainerFieldDefaultResourceEmptyError;
import com.softicar.platform.common.io.resource.supplier.container.validator.error.ResourceSupplierContainerFieldDefaultResourceIllegalFilenameError;
import com.softicar.platform.common.io.resource.supplier.container.validator.error.ResourceSupplierContainerFieldDefaultResourceLoadingExceptionError;
import com.softicar.platform.common.io.resource.supplier.container.validator.error.ResourceSupplierContainerFieldDefaultResourceMissingFilenameError;
import com.softicar.platform.common.io.resource.supplier.container.validator.error.ResourceSupplierContainerFieldDefaultResourceMissingMimeTypeError;
import com.softicar.platform.common.io.resource.supplier.container.validator.error.ResourceSupplierContainerFieldDefaultResourceNotFoundError;
import com.softicar.platform.common.io.resource.supplier.container.validator.error.ResourceSupplierContainerFieldResourceKeyIllegalBasenameError;
import com.softicar.platform.common.io.resource.supplier.container.validator.error.ResourceSupplierContainerFieldResourceKeyMissingSuperMimeTypeError;
import com.softicar.platform.common.io.resource.supplier.container.validator.error.ResourceSupplierContainerFieldResourceKeyUnexpectedPackageError;
import com.softicar.platform.common.io.resource.supplier.container.validator.error.ResourceSupplierContainerFieldResourceUnexpectedSuperMimeTypeError;
import java.io.InputStream;
import java.util.Optional;

class ResourceSupplierContainerFieldValueValidator extends AbstractConstantContainerFieldValueValidator<IResourceSupplier> {

	private ConstantContainerValidatorResult<IResourceSupplier> result;
	private IResourceSupplier fieldValue;

	public ResourceSupplierContainerFieldValueValidator(IConstantContainerField<IResourceSupplier> field) {

		super(field);
	}

	@Override
	protected void validate(ConstantContainerValidatorResult<IResourceSupplier> result, IResourceSupplier fieldValue) {

		this.result = result;
		this.fieldValue = fieldValue;

		validateResourceKey();
		validateResource();
		validateConformityOfResourceKeyAndResource();
	}

	private void validateResourceKey() {

		IResourceKey key = fieldValue.getResourceKey();
		validateResourceKeyPackage(key);
		validateResourceKeyBasename(key);
		validateResourceKeySuperMimeType(key);
	}

	private void validateResourceKeyPackage(IResourceKey key) {

		String packageName = key.getPackageName();
		String containerPackage = field.getContainerClass().getPackageName();
		if (!packageName.equals(containerPackage)) {
			result.addError(new ResourceSupplierContainerFieldResourceKeyUnexpectedPackageError(field, containerPackage, packageName));
		}
	}

	private void validateResourceKeyBasename(IResourceKey key) {

		ResourceKeyBasename basename = key.getBasename();
		if (!basename.isValid()) {
			result.addError(new ResourceSupplierContainerFieldResourceKeyIllegalBasenameError(field, basename.get()));
		}
	}

	private void validateResourceKeySuperMimeType(IResourceKey key) {

		ResourceKeySuperMimeType superMimeType = key.getSuperMimeType();
		if (superMimeType == null || superMimeType.get().equals("")) {
			result.addError(new ResourceSupplierContainerFieldResourceKeyMissingSuperMimeTypeError(field));
		}
	}

	private void validateResource() {

		IResource resource = fieldValue.getResource();
		validateResourceLoading(resource);
		validateResourceMimeType(resource);
		validateResourceFilename(resource);
	}

	private void validateResourceLoading(IResource defaultResource) {

		try (InputStream stream = defaultResource.getResourceAsStream()) {
			if (stream == null) {
				result.addError(new ResourceSupplierContainerFieldDefaultResourceNotFoundError(field));
			} else {
				if (stream.read() <= -1) {
					result.addError(new ResourceSupplierContainerFieldDefaultResourceEmptyError(field));
				}
			}
		} catch (Exception exception) {
			result.addError(new ResourceSupplierContainerFieldDefaultResourceLoadingExceptionError(field, exception));
		}
	}

	private void validateResourceMimeType(IResource defaultResource) {

		if (defaultResource.getMimeType() == null) {
			result.addError(new ResourceSupplierContainerFieldDefaultResourceMissingMimeTypeError(field));
		}
	}

	private void validateResourceFilename(IResource defaultResource) {

		Optional<String> filename = defaultResource.getFilename();
		if (filename.isPresent()) {
			if (!new DefaultResourceFilename(filename.get()).isValid()) {
				result.addError(new ResourceSupplierContainerFieldDefaultResourceIllegalFilenameError(field, filename.get()));
			}
		} else {
			result.addError(new ResourceSupplierContainerFieldDefaultResourceMissingFilenameError(field));
		}
	}

	private void validateConformityOfResourceKeyAndResource() {

		String resourceKeySuperMimeType = fieldValue.getResourceKey().getSuperMimeType().get();
		String resourceMimeType = fieldValue.getResource().getMimeType().getIdentifier();

		if (!resourceMimeType.startsWith(resourceKeySuperMimeType)) {
			result
				.addError(
					new ResourceSupplierContainerFieldResourceUnexpectedSuperMimeTypeError(field, resourceMimeType, resourceKeySuperMimeType));
		}
	}
}
