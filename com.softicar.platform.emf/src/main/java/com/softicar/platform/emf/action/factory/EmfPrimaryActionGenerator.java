package com.softicar.platform.emf.action.factory;

import com.softicar.platform.emf.action.IEmfPrimaryAction;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfPrimaryActionGenerator<R extends IEmfTableRow<R, ?>> extends AbstractEmfActionGenerator<R, IEmfPrimaryAction<R>> {

	public EmfPrimaryActionGenerator() {

		super(IEmfTable::getPrimaryActionFactories);
	}
}
