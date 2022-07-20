package com.softicar.platform.emf.test.simple.scoped;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.emf.test.simple.EmfTestObject;

public class EmfScopedTestObjectTable extends EmfObjectTable<EmfScopedTestObject, EmfTestObject> {

	public EmfScopedTestObjectTable(IDbObjectTableBuilder<EmfScopedTestObject> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<EmfScopedTestObject, Integer, EmfTestObject> configuration) {

		configuration.setScopeField(EmfScopedTestObject.SCOPE);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<EmfScopedTestObject> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(EmfScopedTestObjectLog.SIMPLE_ENTITY, EmfScopedTestObjectLog.TRANSACTION)
			.addMapping(EmfScopedTestObject.ACTIVE, EmfScopedTestObjectLog.ACTIVE)
			.addMapping(EmfScopedTestObject.NAME, EmfScopedTestObjectLog.NAME);
	}
}
