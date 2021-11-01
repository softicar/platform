package com.softicar.platform.emf.test;

import com.softicar.platform.db.runtime.object.sub.IDbSubObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.sub.object.table.EmfSubObjectTable;
import com.softicar.platform.emf.test.module.EmfTestModuleInstance;
import com.softicar.platform.emf.test.simple.EmfTestObject;

public class EmfTestSubObjectTable extends EmfSubObjectTable<EmfTestSubObject, EmfTestObject, Integer, EmfTestModuleInstance> {

	public EmfTestSubObjectTable(IDbSubObjectTableBuilder<EmfTestSubObject, EmfTestObject, Integer> builder) {

		super(builder);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<EmfTestSubObject> attributes) {

		attributes.addAttribute(EmfTestSubObject.DAY);
		attributes.addAttribute(EmfTestSubObject.ACTIVE);

		attributes//
			.editAttribute(EmfTestSubObject.NAME)
			.setPredicateMandatory(EmfPredicates.always());
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<EmfTestSubObject> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(EmfTestSubObjectLog.SUB_OBJECT, EmfTestSubObjectLog.TRANSACTION)
			.addMapping(EmfTestSubObject.NAME, EmfTestSubObjectLog.NAME)
			.addMapping(EmfTestSubObject.VALUE, EmfTestSubObjectLog.VALUE)
			.addMapping(EmfTestSubObject.NOT_NULLABLE_VALUE, EmfTestSubObjectLog.NOT_NULLABLE_VALUE);
	}
}
