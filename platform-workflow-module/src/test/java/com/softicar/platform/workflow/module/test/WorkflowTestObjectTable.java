package com.softicar.platform.workflow.module.test;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.authorization.role.statik.EmfStaticRoleBuilder;
import com.softicar.platform.emf.authorization.role.statik.IEmfStaticRole;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.workflow.module.AGWorkflowModuleInstance;

public class WorkflowTestObjectTable extends EmfObjectTable<WorkflowTestObject, AGWorkflowModuleInstance> {

	public final static IEmfStaticRole<WorkflowTestObject> ROLE_A =//
			new EmfStaticRoleBuilder<WorkflowTestObject>((object, user) -> object.isRoleMember(user, "A"))//
				.setTitle(IDisplayString.create("Role A"))
				.setUuid("4712121c-a067-4109-a447-202888a3bbe3")
				.build();
	public final static IEmfStaticRole<WorkflowTestObject> ROLE_B =//
			new EmfStaticRoleBuilder<WorkflowTestObject>((object, user) -> object.isRoleMember(user, "B"))//
				.setTitle(IDisplayString.create("Role B"))
				.setUuid("dd1bbe7d-5bfb-4d6c-8fc3-4bd9a0627993")
				.build();

	public WorkflowTestObjectTable(IDbObjectTableBuilder<WorkflowTestObject> builder) {

		super(builder);
	}
}
