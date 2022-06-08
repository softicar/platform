package com.softicar.platform.core.module.program.execution.scheduled;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.program.Programs;
import com.softicar.platform.core.module.program.execution.scheduled.information.CronExpressionInformationAction;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.action.EmfActionSet;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGScheduledProgramExecutionTable extends EmfObjectTable<AGScheduledProgramExecution, AGCoreModuleInstance> {

	public AGScheduledProgramExecutionTable(IDbObjectTableBuilder<AGScheduledProgramExecution> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGScheduledProgramExecution, Integer, AGCoreModuleInstance> configuration) {

		configuration.addValidator(ScheduledProgramExecutionValidator::new);
	}

	@Override
	public void customizeActionSet(EmfActionSet<AGScheduledProgramExecution, AGCoreModuleInstance> actionSet) {

		actionSet.addScopeAction(new CronExpressionInformationAction());
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGScheduledProgramExecution> attributes) {

		attributes//
			.editIndirectEntityAttribute(AGScheduledProgramExecution.PROGRAM_UUID)
			.setEntityLoader(Programs::getAllProgramsAsIndirectEntities)
			.setTitle(CoreI18n.PROGRAM)
			.setImmutable(true)
			.setPredicateMandatory(EmfPredicates.always());
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGScheduledProgramExecution> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(AGScheduledProgramExecutionLog.SCHEDULED_PROGRAM_EXECUTION, AGScheduledProgramExecutionLog.TRANSACTION)
			.addMapping(AGScheduledProgramExecution.ACTIVE, AGScheduledProgramExecutionLog.ACTIVE)
			.addMapping(AGScheduledProgramExecution.CRON_EXPRESSION, AGScheduledProgramExecutionLog.CRON_EXPRESSION);
	}
}
