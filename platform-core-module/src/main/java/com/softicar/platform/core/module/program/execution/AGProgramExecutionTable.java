package com.softicar.platform.core.module.program.execution;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.program.Programs;
import com.softicar.platform.core.module.program.execution.status.ProgramExecutionStatusDisplay;
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

public class AGProgramExecutionTable extends EmfObjectTable<AGProgramExecution, AGCoreModuleInstance> {

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
			.setDisplayFactory(value -> new EmfDayTimeDisplay(value, DayTime::toDisplay));

		attributes//
			.editAttribute(AGProgramExecution.TERMINATED_AT)
			.setDisplayFactory(value -> new EmfDayTimeDisplay(value, DayTime::toDisplay));

		attributes//
			.editAttribute(AGProgramExecution.FAILED)
			.setConcealed(true);

		attributes//
			.editAttribute(AGProgramExecution.OUTPUT)
			.setDisplayFactoryByEntity(ProgramExecutionOutputDisplay::new);

		attributes//
			.editAttribute(AGProgramExecution.MAXIMUM_RUNTIME_EXCEEDED)
			.setConcealed(true);

		attributes//
			.addTransientAttribute(AGProgramExecution.RUNTIME_FIELD)
			.setDisplayFactory(EmfDurationDisplay::new);

		attributes//
			.addTransientAttribute(AGProgramExecution.STATUS_FIELD)
			.setDisplayFactory(ProgramExecutionStatusDisplay::new);
	}

	@Override
	public void customizeAttributeOrdering(EmfAttributeReorderer<AGProgramExecution> reorderer) {

		reorderer//
			.moveAttributes(AGProgramExecution.STATUS_FIELD, AGProgramExecution.RUNTIME_FIELD)
			.behind(AGProgramExecution.PROGRAM_UUID);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGProgramExecution, AGCoreModuleInstance> authorizer) {

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
			.addMapping(AGProgramExecution.MAXIMUM_RUNTIME_EXCEEDED, AGProgramExecutionLog.MAXIMUM_RUNTIME_EXCEEDED)
			.addMapping(AGProgramExecution.QUEUED_BY, AGProgramExecutionLog.QUEUED_BY);
	}
}
