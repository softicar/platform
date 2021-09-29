package com.softicar.platform.emf.test.user;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.test.module.EmfTestModuleInstance;

public class EmfTestUserTable extends EmfObjectTable<EmfTestUser, EmfTestModuleInstance> {

	public EmfTestUserTable(IDbObjectTableBuilder<EmfTestUser> builder) {

		super(builder);
	}
}
