package com.softicar.platform.emf.table.configuration;

import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.IEmfValidator;
import com.softicar.platform.emf.validation.result.IEmfValidationResult;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Wraps the factory of a custom {@link IEmfValidator} into a new
 * {@link IEmfValidator}.
 * <p>
 * When dealing with custom code, care must be taken with respect to class
 * instance fields of an {@link IEmfValidator} implementation. Such fields
 * become effectively static, in the context of {@link EmfTableConfiguration}.
 * So, we wrap the factory here and ensure that a new instance of the custom
 * {@link IEmfValidator} implementation is created for every validation call.
 *
 * @author Oliver Richers
 */
class EmfValidatorWrapper<R extends IEmfTableRow<R, ?>> implements IEmfValidator<R> {

	private final Supplier<? extends IEmfValidator<R>> factory;

	public EmfValidatorWrapper(Supplier<? extends IEmfValidator<R>> factory) {

		this.factory = Objects.requireNonNull(factory);
	}

	@Override
	public void validate(R tableRow, IEmfValidationResult result) {

		factory.get().validate(tableRow, result);
	}
}
