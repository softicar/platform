package com.softicar.platform.db.runtime.table.validator;

import com.softicar.platform.common.testing.AssertionErrorMessageCollector;

public interface IDbTableValidator {

	AssertionErrorMessageCollector validate();
}
