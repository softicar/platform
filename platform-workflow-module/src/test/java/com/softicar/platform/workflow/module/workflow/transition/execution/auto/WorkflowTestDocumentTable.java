package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.permission.statik.EmfStaticPermissionBuilder;
import com.softicar.platform.emf.permission.statik.IEmfStaticPermission;
import com.softicar.platform.workflow.module.AGWorkflowModuleInstance;

public class WorkflowTestDocumentTable extends EmfObjectTable<WorkflowTestDocument, AGWorkflowModuleInstance> {

	public final static IEmfStaticPermission<WorkflowTestDocument> PERMISSION_A =//
			new EmfStaticPermissionBuilder<WorkflowTestDocument>((document, user) -> document.hasPermission(user, "A"))//
				.setTitle(IDisplayString.create("Permission A"))
				.setUuid("cc4a113b-ee87-471c-a0f6-c332f2afee72")
				.build();
	public final static IEmfStaticPermission<WorkflowTestDocument> PERMISSION_B =//
			new EmfStaticPermissionBuilder<WorkflowTestDocument>((document, user) -> document.hasPermission(user, "B"))//
				.setTitle(IDisplayString.create("Permission B"))
				.setUuid("9bc19f45-ac86-4a61-99a8-c2e003d1fae8")
				.build();

	public WorkflowTestDocumentTable(IDbObjectTableBuilder<WorkflowTestDocument> builder) {

		super(builder);
	}
}
