package com.softicar.platform.emf.validation;

import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.result.IEmfValidationResult;

/**
 * Interface to validate a given {@link IEmfTableRow}.
 *
 * @author Oliver Richers
 */
@FunctionalInterface
public interface IEmfValidator<R extends IEmfTableRow<R, ?>> {

	void validate(R tableRow, IEmfValidationResult result);
}
