package com.softicar.platform.emf.action.factory;

import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfManagementActionGenerator<R extends IEmfTableRow<R, ?>> extends AbstractEmfActionGenerator<R, IEmfManagementAction<R>> {

	public EmfManagementActionGenerator() {

		super(IEmfTable::getManagementActionFactories);
	}
}
