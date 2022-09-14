package com.softicar.platform.common.io.resource.supplier.container.validator;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.constant.container.validator.IConstantContainerValidator;
import com.softicar.platform.common.core.constant.container.validator.result.ConstantContainerValidatorResult;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.container.validator.error.ResourceSupplierContainerResourceKeyAmbiguousError;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

class ResourceSupplierContainerResourceKeyUnambiguityValidator implements IConstantContainerValidator<IResourceSupplier> {

	private final Supplier<Collection<IConstantContainerField<IResourceSupplier>>> fieldsSupplier;

	public ResourceSupplierContainerResourceKeyUnambiguityValidator(Supplier<Collection<IConstantContainerField<IResourceSupplier>>> fieldsSupplier) {

		this.fieldsSupplier = Objects.requireNonNull(fieldsSupplier);
	}

	@Override
	public void validate(ConstantContainerValidatorResult<IResourceSupplier> result) {

		var resourceKeyToFields = fieldsSupplier//
			.get()
			.stream()
			.filter(field -> field.getValue() != null)
			.collect(Collectors.groupingBy(field -> field.getValue().getResourceKey()));

		var ambiguousEntries = resourceKeyToFields//
			.entrySet()
			.stream()
			.filter(entry -> entry.getValue().size() > 1)
			.collect(Collectors.toList());

		for (var entry: ambiguousEntries) {
			for (var field: entry.getValue()) {
				result.addError(new ResourceSupplierContainerResourceKeyAmbiguousError(field, entry.getKey()));
			}
		}
	}
}
