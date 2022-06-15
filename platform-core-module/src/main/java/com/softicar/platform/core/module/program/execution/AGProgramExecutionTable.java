package com.softicar.platform.core.module.program.execution;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.core.module.program.Programs;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.EmfAttributeReorderer;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.attribute.field.daytime.EmfDayTimeDisplay;
import com.softicar.platform.emf.attribute.field.duration.EmfDurationDisplay;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.management.EmfManagementConfiguration;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.permission.EmfPermissions;

public class AGProgramExecutionTable extends EmfObjectTable<AGProgramExecution, SystemModuleInstance> {

	public AGProgramExecutionTable(IDbObjectTableBuilder<AGProgramExecution> builder) {

		super(builder);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGProgramExecution> attributes) {

		attributes//
			.editIndirectEntityAttribute(AGProgramExecution.PROGRAM_UUID)
			.setEntityLoader(Programs::getAllProgramsAsIndirectEntities)
			.setTitle(CoreI18n.PROGRAM)
			.setImmutable(true);

		attributes//
			.editAttribute(AGProgramExecution.STARTED_AT)
			.setDisplayFactory(value -> new EmfDayTimeDisplay(value, DayTime::toString));

		attributes//
			.editAttribute(AGProgramExecution.TERMINATED_AT)
			.setDisplayFactory(value -> new EmfDayTimeDisplay(value, DayTime::toString));

		attributes//
			.editAttribute(AGProgramExecution.OUTPUT)
			.setDisplayFactoryByEntity(ProgramExecutionOutputDisplay::new);

		attributes//
			.addTransientAttribute(AGProgramExecution.RUNTIME_FIELD)
			.setDisplayFactory(EmfDurationDisplay::new);
	}

	@Override
	public void customizeAttributeOrdering(EmfAttributeReorderer<AGProgramExecution> reorderer) {

		reorderer.moveAttribute(AGProgramExecution.RUNTIME_FIELD).behind(AGProgramExecution.TERMINATED_AT);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGProgramExecution, SystemModuleInstance> authorizer) {

		authorizer//
			.setCreationPermission(EmfPermissions.never())
			.setEditPermission(EmfPermissions.never())
			.setDeletePermission(EmfPermissions.never());
	}

	@Override
	public void customizeManagementConfiguraton(EmfManagementConfiguration<AGProgramExecution> configuration) {

		configuration.addOrderBy(AGProgramExecution.STARTED_AT, OrderDirection.DESCENDING);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGProgramExecution> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(AGProgramExecutionLog.PROGRAM_EXECUTION, AGProgramExecutionLog.TRANSACTION)
			.addMapping(AGProgramExecution.STARTED_AT, AGProgramExecutionLog.STARTED_AT)
			.addMapping(AGProgramExecution.TERMINATED_AT, AGProgramExecutionLog.TERMINATED_AT)
			.addMapping(AGProgramExecution.FAILED, AGProgramExecutionLog.FAILED)
			.addMapping(AGProgramExecution.OUTPUT, AGProgramExecutionLog.OUTPUT)
			.addMapping(AGProgramExecution.EXCEEDED_MAXIMUM_RUNTIME, AGProgramExecutionLog.EXCEEDED_MAXIMUM_RUNTIME)
			.addMapping(AGProgramExecution.QUEUED_BY, AGProgramExecutionLog.QUEUED_BY);
	}
}
