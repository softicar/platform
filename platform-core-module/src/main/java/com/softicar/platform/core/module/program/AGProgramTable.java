package com.softicar.platform.core.module.program;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.core.module.program.abort.ProgramAbortAction;
import com.softicar.platform.core.module.program.enqueue.ProgramEnqueueAction;
import com.softicar.platform.core.module.program.unqueue.ProgramUnqueueAction;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.action.EmfActionSet;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.attribute.field.bool.EmfBooleanDisplay;
import com.softicar.platform.emf.attribute.field.daytime.EmfDayTimeDisplay;
import com.softicar.platform.emf.attribute.field.item.EmfBasicEntityDisplay;
import com.softicar.platform.emf.authorization.role.EmfRoles;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGProgramTable extends EmfObjectTable<AGProgram, SystemModuleInstance> {

	public AGProgramTable(IDbObjectTableBuilder<AGProgram> builder) {

		super(builder);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGProgram> attributes) {

		attributes//
			.editAttribute(AGProgram.ID)
			.setConcealed(true);

		attributes//
			.editIndirectEntityAttribute(AGProgram.PROGRAM_UUID)
			.setEntityLoader(Programs::getAllProgramsAsIndirectEntities)
			.setTitle(CoreI18n.PROGRAM)
			.setImmutable(true)
			.setPredicateMandatory(EmfPredicates.always())
			.setPredicateEditable(EmfPredicates.never());

		attributes//
			.addTransientAttribute(AGProgram.QUEUED_AT)
			.setDisplayFactory(EmfDayTimeDisplay::new);

		attributes//
			.addTransientAttribute(AGProgram.QUEUED_BY)
			.setDisplayFactory(EmfBasicEntityDisplay::new);

		attributes//
			.addTransientAttribute(AGProgram.ABORT_REQUESTED)
			.setDisplayFactory(EmfBooleanDisplay::new);

		attributes//
			.addTransientAttribute(AGProgram.CURRENT_EXECUTION)
			.setDisplayFactory(EmfBasicEntityDisplay::new);

		attributes//
			.editAttribute(AGProgram.EXECUTION_RETENTION_DAYS)
			.setPredicateMandatory(EmfPredicates.always());
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGProgram, Integer, SystemModuleInstance> configuration) {

		configuration.addValidator(ProgramValidator::new);
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGProgram, SystemModuleInstance> authorizer) {

		authorizer//
			.setCreationRole(EmfRoles.nobody())
			.setEditRole(CoreRoles.SUPER_USER.toOtherEntityRole())
			.setDeleteRole(EmfRoles.nobody());
	}

	@Override
	public void customizeActionSet(EmfActionSet<AGProgram, SystemModuleInstance> actionSet) {

		actionSet//
			.addCommonAction(new ProgramAbortAction())
			.addManagementAction(new ProgramAbortAction());

		actionSet//
			.addCommonAction(new ProgramEnqueueAction())
			.addManagementAction(new ProgramEnqueueAction());

		actionSet//
			.addCommonAction(new ProgramUnqueueAction())
			.addManagementAction(new ProgramUnqueueAction());
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGProgram> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(AGProgramLog.PROGRAM, AGProgramLog.TRANSACTION)
			.addMapping(AGProgram.EXECUTION_RETENTION_DAYS, AGProgramLog.EXECUTION_RETENTION_DAYS);
	}

}
