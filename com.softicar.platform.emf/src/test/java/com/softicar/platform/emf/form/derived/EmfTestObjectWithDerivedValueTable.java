package com.softicar.platform.emf.form.derived;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;
import com.softicar.platform.emf.test.module.EmfTestModuleInstance;

public class EmfTestObjectWithDerivedValueTable extends EmfObjectTable<EmfTestObjectWithDerivedValue, EmfTestModuleInstance> {

	public EmfTestObjectWithDerivedValueTable(IDbObjectTableBuilder<EmfTestObjectWithDerivedValue> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<EmfTestObjectWithDerivedValue, Integer, EmfTestModuleInstance> configuration) {

		configuration.addSaveHook(EmfTestObjectWithDerivedValueSaveHook::new);
		configuration.addValidator(EmfTestObjectWithDerivedValueValidator::new);
	}
}
