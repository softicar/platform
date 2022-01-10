package com.softicar.platform.core.module.program.execution.manual;

import com.softicar.platform.core.module.program.AGProgram;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGProgramManualExecutionTable extends EmfObjectTable<AGProgramManualExecution, AGProgram> {

	public AGProgramManualExecutionTable(IDbObjectTableBuilder<AGProgramManualExecution> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGProgramManualExecution, Integer, AGProgram> configuration) {

		configuration.setScopeField(AGProgramManualExecution.PROGRAM);
		configuration.setCreationPredicate(EmfPredicates.never());
		configuration.setEditPredicate(EmfPredicates.never());
	}
}
