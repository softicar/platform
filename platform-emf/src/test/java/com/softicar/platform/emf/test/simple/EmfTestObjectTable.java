package com.softicar.platform.emf.test.simple;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.EmfTestPermissions;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.test.module.EmfTestModuleInstance;

public class EmfTestObjectTable extends EmfObjectTable<EmfTestObject, EmfTestModuleInstance> {

	public EmfTestObjectTable(IDbObjectTableBuilder<EmfTestObject> builder) {

		super(builder);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<EmfTestObject, EmfTestModuleInstance> authorizer) {

		authorizer.setViewPermission(EmfTestPermissions.AUTHORIZED_USER);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<EmfTestObject> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(EmfTestObjectLog.SIMPLE_ENTITY, EmfTestObjectLog.TRANSACTION)
			.addMapping(EmfTestObject.ACTIVE, EmfTestObjectLog.ACTIVE)
			.addMapping(EmfTestObject.NAME, EmfTestObjectLog.NAME)
			.addMapping(EmfTestObject.DAY, EmfTestObjectLog.DAY);
	}
}
