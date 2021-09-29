package com.softicar.platform.emf.action.factory;

import com.softicar.platform.emf.action.IEmfPrimaryAction;
import com.softicar.platform.emf.table.row.IEmfTableRow;

@FunctionalInterface
public interface IEmfPrimaryActionFactory<R extends IEmfTableRow<R, ?>> extends IEmfActionFactory<R, IEmfPrimaryAction<R>> {

	// nothing
}
