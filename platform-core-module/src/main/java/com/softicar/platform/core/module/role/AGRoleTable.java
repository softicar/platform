package com.softicar.platform.core.module.role;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;

public class AGRoleTable extends EmfObjectTable<AGRole, AGCoreModuleInstance> {

	public AGRoleTable(IDbObjectTableBuilder<AGRole> builder) {

		super(builder);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGRole> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(AGRoleLog.ROLE, AGRoleLog.TRANSACTION)
			.addMapping(AGRole.ACTIVE, AGRoleLog.ACTIVE)
			.addMapping(AGRole.NAME, AGRoleLog.NAME);
	}
}
