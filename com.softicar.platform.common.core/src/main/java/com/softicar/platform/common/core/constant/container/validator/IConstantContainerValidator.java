package com.softicar.platform.common.core.constant.container.validator;

import com.softicar.platform.common.core.constant.container.validator.result.ConstantContainerValidatorResult;

public interface IConstantContainerValidator<T> {

	void validate(ConstantContainerValidatorResult<T> result);
}
