package com.softicar.platform.emf.action.factory;

import com.softicar.platform.emf.action.IEmfCommonAction;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfCommonActionGenerator<R extends IEmfTableRow<R, ?>> extends AbstractEmfActionGenerator<R, IEmfCommonAction<R>> {

	public EmfCommonActionGenerator() {

		super(IEmfTable::getCommonActionFactories);
	}
}
