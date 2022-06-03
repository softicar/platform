package com.softicar.platform.emf.test.simple.authorization;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.test.module.EmfTestModuleInstance;

public class EmfTestObjectAuthorizedUserTable extends EmfObjectTable<EmfTestObjectAuthorizedUser, EmfTestModuleInstance> {

	public EmfTestObjectAuthorizedUserTable(IDbObjectTableBuilder<EmfTestObjectAuthorizedUser> builder) {

		super(builder);
	}
}
