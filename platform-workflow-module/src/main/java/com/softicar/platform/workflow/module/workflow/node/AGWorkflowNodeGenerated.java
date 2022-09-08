package com.softicar.platform.workflow.module.workflow.node;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;

/**
 * This is the automatically generated class AGWorkflowNode for
 * database table <i>Workflow.WorkflowNode</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowNodeGenerated extends AbstractDbObject<AGWorkflowNode> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGWorkflowNode, AGWorkflowNodeGenerated> BUILDER = new DbObjectTableBuilder<>("Workflow", "WorkflowNode", AGWorkflowNode::new, AGWorkflowNode.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_NODE);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_NODES);
	}

	public static final IDbIdField<AGWorkflowNode> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(WorkflowI18n.ID);
	public static final IDbForeignField<AGWorkflowNode, AGWorkflowVersion> WORKFLOW_VERSION = BUILDER.addForeignField("workflowVersion", o->o.m_workflowVersion, (o,v)->o.m_workflowVersion=v, AGWorkflowVersion.ID).setTitle(WorkflowI18n.WORKFLOW_VERSION).setForeignKeyName("WorkflowNode_ibfk_1");
	public static final IDbStringField<AGWorkflowNode> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(WorkflowI18n.NAME).setMaximumLength(255);
	public static final IDbIntegerField<AGWorkflowNode> X_COORDINATE = BUILDER.addIntegerField("xCoordinate", o->o.m_xCoordinate, (o,v)->o.m_xCoordinate=v).setTitle(WorkflowI18n.X_COORDINATE).setDefault(10);
	public static final IDbIntegerField<AGWorkflowNode> Y_COORDINATE = BUILDER.addIntegerField("yCoordinate", o->o.m_yCoordinate, (o,v)->o.m_yCoordinate=v).setTitle(WorkflowI18n.Y_COORDINATE).setDefault(150);
	public static final IDbBooleanField<AGWorkflowNode> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setDefault(true);
	public static final IDbKey<AGWorkflowNode> IK_WORKFLOW_VERSION = BUILDER.addIndexKey("workflowVersion", WORKFLOW_VERSION);
	public static final AGWorkflowNodeTable TABLE = new AGWorkflowNodeTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGWorkflowNode> createSelect() {

		return TABLE.createSelect();
	}

	public static AGWorkflowNode get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowVersionID() {

		return getValueId(WORKFLOW_VERSION);
	}

	public final AGWorkflowVersion getWorkflowVersion() {

		return getValue(WORKFLOW_VERSION);
	}

	public final AGWorkflowNode setWorkflowVersion(AGWorkflowVersion value) {

		return setValue(WORKFLOW_VERSION, value);
	}

	public final String getName() {

		return getValue(NAME);
	}

	public final AGWorkflowNode setName(String value) {

		return setValue(NAME, value);
	}

	public final Integer getXCoordinate() {

		return getValue(X_COORDINATE);
	}

	public final AGWorkflowNode setXCoordinate(Integer value) {

		return setValue(X_COORDINATE, value);
	}

	public final Integer getYCoordinate() {

		return getValue(Y_COORDINATE);
	}

	public final AGWorkflowNode setYCoordinate(Integer value) {

		return setValue(Y_COORDINATE, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGWorkflowNode setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowNodeTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGWorkflowVersion m_workflowVersion;
	private String m_name;
	private Integer m_xCoordinate;
	private Integer m_yCoordinate;
	private Boolean m_active;
}

