package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;

public class WorkflowTestDocumentPermissionAssignment extends AbstractDbObject<WorkflowTestDocumentPermissionAssignment> {

	// @formatter:off
	public static final DbObjectTableBuilder<WorkflowTestDocumentPermissionAssignment, WorkflowTestDocumentPermissionAssignment> BUILDER = new DbObjectTableBuilder<>("Workflow", "TestDocumentPermissionAssignment", WorkflowTestDocumentPermissionAssignment::new, WorkflowTestDocumentPermissionAssignment.class);
	static {
		BUILDER.setTitle(IDisplayString.create("Test Document Permission Assignment"));
		BUILDER.setTitle(IDisplayString.create("Test Document Permission Assignments"));
	}
	public static final IDbIdField<WorkflowTestDocumentPermissionAssignment> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbForeignField<WorkflowTestDocumentPermissionAssignment, WorkflowTestDocument> DOCUMENT = BUILDER.addForeignField("document", o -> o.document, (o, v) -> o.document = v, WorkflowTestDocument.ID);
	public static final IDbForeignField<WorkflowTestDocumentPermissionAssignment, AGUser> USER = BUILDER.addForeignField("user", o -> o.user, (o, v) -> o.user = v, AGUser.ID);
	public static final IDbStringField<WorkflowTestDocumentPermissionAssignment> PERMISSION = BUILDER.addStringField("permission", o -> o.permission, (o, v) -> o.permission = v);
	public static final DbObjectTable<WorkflowTestDocumentPermissionAssignment> TABLE = new DbObjectTable<>(BUILDER);
	// @formatter:on

	private Integer id;
	private WorkflowTestDocument document;
	private AGUser user;
	private String permission;

	@Override
	public DbObjectTable<WorkflowTestDocumentPermissionAssignment> table() {

		return TABLE;
	}

	public WorkflowTestDocument getDocument() {

		return getValue(DOCUMENT);
	}

	public WorkflowTestDocumentPermissionAssignment setDocument(WorkflowTestDocument document) {

		return setValue(DOCUMENT, document);
	}

	public AGUser getUser() {

		return getValue(USER);
	}

	public WorkflowTestDocumentPermissionAssignment setUser(AGUser user) {

		return setValue(USER, user);
	}

	public String getPermission() {

		return getValue(PERMISSION);
	}

	public WorkflowTestDocumentPermissionAssignment setPermission(String permission) {

		return setValue(PERMISSION, permission);
	}
}
