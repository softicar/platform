package com.softicar.platform.workflow.module.test;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;

public class WorkflowTestObjectRoleMember extends AbstractDbObject<WorkflowTestObjectRoleMember> {

	// @formatter:off
	public static final DbObjectTableBuilder<WorkflowTestObjectRoleMember, WorkflowTestObjectRoleMember> BUILDER = new DbObjectTableBuilder<>("Workflow", "TestObjectRoleMember", WorkflowTestObjectRoleMember::new, WorkflowTestObjectRoleMember.class);
	static {
		BUILDER.setTitle(IDisplayString.create("Test Object Role Member"));
		BUILDER.setTitle(IDisplayString.create("Test Object Role Members"));
	}
	public static final IDbIdField<WorkflowTestObjectRoleMember> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbForeignField<WorkflowTestObjectRoleMember, WorkflowTestObject> OBJECT = BUILDER.addForeignField("object", o -> o.object, (o, v) -> o.object = v, WorkflowTestObject.ID);
	public static final IDbForeignField<WorkflowTestObjectRoleMember, AGUser> USER = BUILDER.addForeignField("user", o -> o.user, (o, v) -> o.user = v, AGUser.ID);
	public static final IDbStringField<WorkflowTestObjectRoleMember> ROLE = BUILDER.addStringField("role", o -> o.role, (o, v) -> o.role = v);
	public static final DbObjectTable<WorkflowTestObjectRoleMember> TABLE = new DbObjectTable<>(BUILDER);
	// @formatter:on

	private Integer id;
	private WorkflowTestObject object;
	private AGUser user;
	private String role;

	@Override
	public DbObjectTable<WorkflowTestObjectRoleMember> table() {

		return TABLE;
	}

	public WorkflowTestObject getObject() {

		return getValue(OBJECT);
	}

	public WorkflowTestObjectRoleMember setObject(WorkflowTestObject object) {

		return setValue(OBJECT, object);
	}

	public AGUser getUser() {

		return getValue(USER);
	}

	public WorkflowTestObjectRoleMember setUser(AGUser user) {

		return setValue(USER, user);
	}

	public String getRole() {

		return getValue(ROLE);
	}

	public WorkflowTestObjectRoleMember setRole(String role) {

		return setValue(ROLE, role);
	}
}
