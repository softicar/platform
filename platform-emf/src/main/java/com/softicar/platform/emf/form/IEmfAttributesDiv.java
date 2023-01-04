package com.softicar.platform.emf.form;

import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.validation.IEmfValidator;
import java.util.Collection;

public interface IEmfAttributesDiv<R extends IEmfTableRow<R, ?>> {

	void refresh();

	boolean tryToApplyValidateAndSave();

	void addAdditionalValidators(Collection<IEmfValidator<R>> validators);
}
