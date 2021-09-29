package com.softicar.platform.core.module.program;

import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.core.module.program.abort.ProgramAbortAction;
import com.softicar.platform.core.module.program.unqueue.ProgramUnqueueAction;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.action.EmfActionSet;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.authorization.role.EmfRoles;
import com.softicar.platform.emf.authorizer.EmfAuthorizer;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;

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
			.setPredicateMandatory(EmfPredicates.always());
	}

	@Override
	public void customizeAuthorizer(EmfAuthorizer<AGProgram, SystemModuleInstance> authorizer) {

		authorizer//
			.setCreationRole(EmfRoles.nobody())
			.setEditRole(EmfRoles.nobody())
			.setDeleteRole(EmfRoles.nobody());
	}

	@Override
	public void customizeActionSet(EmfActionSet<AGProgram, SystemModuleInstance> actionSet) {

		actionSet//
			.addCommonAction(new ProgramAbortAction())
			.addManagementAction(new ProgramAbortAction());

		actionSet//
			.addCommonAction(new ProgramUnqueueAction())
			.addManagementAction(new ProgramUnqueueAction());
	}
}
