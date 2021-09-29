package com.softicar.platform.common.core.constant.container.validator.error;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import java.util.Optional;

public interface IConstantContainerValidationError<T> {

	Optional<IConstantContainerField<T>> getField();

	String getMessage();
}
