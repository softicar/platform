package com.softicar.platform.emf.action.factory;

import com.softicar.platform.emf.action.IEmfCommonAction;
import com.softicar.platform.emf.table.row.IEmfTableRow;

@FunctionalInterface
public interface IEmfCommonActionFactory<R extends IEmfTableRow<R, ?>> extends IEmfActionFactory<R, IEmfCommonAction<R>> {

	// nothing
}
