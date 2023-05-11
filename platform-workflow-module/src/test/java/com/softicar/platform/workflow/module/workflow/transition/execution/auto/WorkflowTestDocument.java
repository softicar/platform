package com.softicar.platform.workflow.module.workflow.transition.execution.auto;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.workflow.module.workflow.item.AGWorkflowItem;
import com.softicar.platform.workflow.module.workflow.item.IWorkflowableObject;

public class WorkflowTestDocument extends AbstractDbObject<WorkflowTestDocument> implements IWorkflowableObject<WorkflowTestDocument> {

	// @formatter:off
	public static final DbObjectTableBuilder<WorkflowTestDocument, WorkflowTestDocument> BUILDER = new DbObjectTableBuilder<>("Workflow", "TestDocument", WorkflowTestDocument::new, WorkflowTestDocument.class);
	static {
		BUILDER.setTitle(IDisplayString.create("Test Document"));
		BUILDER.setTitle(IDisplayString.create("Test Documents"));
	}
	public static final IDbIdField<WorkflowTestDocument> ID = BUILDER.addIdField("id", o -> o.id, (o, v) -> o.id = v);
	public static final IDbStringField<WorkflowTestDocument> NAME = BUILDER.addStringField("name", o -> o.name, (o, v) -> o.name = v);
	public static final IDbIntegerField<WorkflowTestDocument> QUANTITY = BUILDER.addIntegerField("quantity", o -> o.quantity, (o, v) -> o.quantity = v);
	public static final IDbForeignField<WorkflowTestDocument, AGWorkflowItem> ITEM = BUILDER.addForeignField("item", o -> o.item, (o, v) -> o.item = v,AGWorkflowItem.ID).setNullable();
	public static final WorkflowTestDocumentTable TABLE = new WorkflowTestDocumentTable(BUILDER);
	// @formatter:on

	private Integer id;
	private String name;
	private Integer quantity;
	private AGWorkflowItem item;

	@Override
	public WorkflowTestDocumentTable table() {

		return TABLE;
	}

	public String getName() {

		return getValue(NAME);
	}

	public WorkflowTestDocument setName(String name) {

		return setValue(NAME, name);
	}

	public Integer getQuantity() {

		return getValue(QUANTITY);
	}

	public WorkflowTestDocument setQuantity(Integer quantity) {

		return setValue(QUANTITY, quantity);
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(name);
	}

	@Override
	public Boolean isActive() {

		return true;
	}

	@Override
	public AGWorkflowItem getWorkflowItem() {

		return getValue(ITEM);
	}

	@Override
	public WorkflowTestDocument setWorkflowItem(AGWorkflowItem item) {

		return setValue(ITEM, item);
	}

	public boolean hasPermission(IBasicUser user, String permission) {

		return WorkflowTestDocumentPermissionAssignment.TABLE//
			.createSelect()
			.where(WorkflowTestDocumentPermissionAssignment.PERMISSION.isEqual(permission))
			.where(WorkflowTestDocumentPermissionAssignment.DOCUMENT.isEqual(this))
			.where(WorkflowTestDocumentPermissionAssignment.USER.isEqual(AGUser.get(user)))
			.exists();
	}
}
