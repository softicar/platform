package com.softicar.platform.workflow.module.test;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;

public class WorkflowTestObjectPermissionAssignment extends AbstractDbObject<WorkflowTestObjectPermissionAssignment> {

	// @formatter:off
	public static final DbObjectTableBuilder<WorkflowTestObjectPermissionAssignment, WorkflowTestObjectPermissionAssignment> BUILDER = new DbObjectTableBuilder<>("Workflow", "TestObjectPermissionAssignment", WorkflowTestObjectPermissionAssignment::new, WorkflowTestObjectPermissionAssignment.class);
	static {
		BUILDER.setTitle(IDisplayString.create("Test Object Permission Assignment"));
		BUILDER.setTitle(IDisplayString.create("Test Object Permission Assignments"));
	}
	public static final IDbIdField<WorkflowTestObjectPermissionAssignment> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbForeignField<WorkflowTestObjectPermissionAssignment, WorkflowTestObject> OBJECT = BUILDER.addForeignField("object", o -> o.object, (o, v) -> o.object = v, WorkflowTestObject.ID);
	public static final IDbForeignField<WorkflowTestObjectPermissionAssignment, AGUser> USER = BUILDER.addForeignField("user", o -> o.user, (o, v) -> o.user = v, AGUser.ID);
	public static final IDbStringField<WorkflowTestObjectPermissionAssignment> PERMISSION = BUILDER.addStringField("permission", o -> o.permission, (o, v) -> o.permission = v);
	public static final DbObjectTable<WorkflowTestObjectPermissionAssignment> TABLE = new DbObjectTable<>(BUILDER);
	// @formatter:on

	private Integer id;
	private WorkflowTestObject object;
	private AGUser user;
	private String permission;

	@Override
	public DbObjectTable<WorkflowTestObjectPermissionAssignment> table() {

		return TABLE;
	}

	public WorkflowTestObject getObject() {

		return getValue(OBJECT);
	}

	public WorkflowTestObjectPermissionAssignment setObject(WorkflowTestObject object) {

		return setValue(OBJECT, object);
	}

	public AGUser getUser() {

		return getValue(USER);
	}

	public WorkflowTestObjectPermissionAssignment setUser(AGUser user) {

		return setValue(USER, user);
	}

	public String getPermission() {

		return getValue(PERMISSION);
	}

	public WorkflowTestObjectPermissionAssignment setPermission(String permission) {

		return setValue(PERMISSION, permission);
	}
}
