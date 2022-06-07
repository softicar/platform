package com.softicar.platform.workflow.module.test;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.permission.statik.EmfStaticPermissionBuilder;
import com.softicar.platform.emf.permission.statik.IEmfStaticPermission;
import com.softicar.platform.workflow.module.AGWorkflowModuleInstance;

public class WorkflowTestObjectTable extends EmfObjectTable<WorkflowTestObject, AGWorkflowModuleInstance> {

	public final static IEmfStaticPermission<WorkflowTestObject> PERMISSION_A =//
			new EmfStaticPermissionBuilder<WorkflowTestObject>((object, user) -> object.hasPermission(user, "A"))//
				.setTitle(IDisplayString.create("Permission A"))
				.setUuid("4712121c-a067-4109-a447-202888a3bbe3")
				.build();
	public final static IEmfStaticPermission<WorkflowTestObject> PERMISSION_B =//
			new EmfStaticPermissionBuilder<WorkflowTestObject>((object, user) -> object.hasPermission(user, "B"))//
				.setTitle(IDisplayString.create("Permission B"))
				.setUuid("dd1bbe7d-5bfb-4d6c-8fc3-4bd9a0627993")
				.build();

	public WorkflowTestObjectTable(IDbObjectTableBuilder<WorkflowTestObject> builder) {

		super(builder);
	}
}
