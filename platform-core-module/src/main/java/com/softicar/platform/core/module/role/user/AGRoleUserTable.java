package com.softicar.platform.core.module.role.user;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;

public class AGRoleUserTable extends EmfObjectTable<AGRoleUser, AGCoreModuleInstance> {

	public AGRoleUserTable(IDbObjectTableBuilder<AGRoleUser> builder) {

		super(builder);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGRoleUser> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(AGRoleUserLog.ROLE_USER, AGRoleUserLog.TRANSACTION)
			.addMapping(AGRoleUser.ACTIVE, AGRoleUserLog.ACTIVE)
			.addMapping(AGRoleUser.ROLE, AGRoleUserLog.ROLE)
			.addMapping(AGRoleUser.USER, AGRoleUserLog.USER);
	}
}
