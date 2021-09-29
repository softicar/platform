package com.softicar.platform.emf.action.factory;

import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.table.row.IEmfTableRow;

@FunctionalInterface
public interface IEmfManagementActionFactory<R extends IEmfTableRow<R, ?>> extends IEmfActionFactory<R, IEmfManagementAction<R>> {

	// nothing
}
