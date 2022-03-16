package com.softicar.platform.common.io.resource.supplier.container.validator;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.constant.container.validator.IConstantContainerValidator;
import com.softicar.platform.common.core.constant.container.validator.result.ConstantContainerValidatorResult;
import com.softicar.platform.common.io.resource.key.IResourceKey;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.container.validator.error.ResourceSupplierContainerResourceKeyAmbiguousError;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

class ResourceSupplierContainerAllFieldsValidator implements IConstantContainerValidator<IResourceSupplier> {

	private final Supplier<Collection<IConstantContainerField<IResourceSupplier>>> fieldsSupplier;

	public ResourceSupplierContainerAllFieldsValidator(Supplier<Collection<IConstantContainerField<IResourceSupplier>>> fieldsSupplier) {

		this.fieldsSupplier = Objects.requireNonNull(fieldsSupplier);
	}

	@Override
	public void validate(ConstantContainerValidatorResult<IResourceSupplier> result) {

		fieldsSupplier//
			.get()
			.stream()
			.filter(field -> field.getValue() != null)
			.collect(Collectors.groupingBy(this::getResourceKey))
			.entrySet()
			.stream()
			.filter(entry -> entry.getValue().size() > 1)
			.map(entry -> createResourceKeyAmbiguousErrors(entry.getValue(), entry.getKey()))
			.flatMap(Collection::stream)
			.forEach(result::addError);
	}

	private IResourceKey getResourceKey(IConstantContainerField<IResourceSupplier> field) {

		return field.getValue().getResourceKey();
	}

	private Collection<ResourceSupplierContainerResourceKeyAmbiguousError> createResourceKeyAmbiguousErrors(
			List<IConstantContainerField<IResourceSupplier>> fields, IResourceKey resourceKey) {

		return fields//
			.stream()
			.map(entry -> new ResourceSupplierContainerResourceKeyAmbiguousError(entry, resourceKey))
			.collect(Collectors.toList());
	}
}
