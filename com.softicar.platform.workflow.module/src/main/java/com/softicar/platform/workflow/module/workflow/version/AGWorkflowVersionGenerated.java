package com.softicar.platform.workflow.module.workflow.version;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.AGWorkflow;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;

/**
 * This is the automatically generated class AGWorkflowVersion for
 * database table <i>Workflow.WorkflowVersion</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowVersionGenerated extends AbstractDbObject<AGWorkflowVersion> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGWorkflowVersion, AGWorkflowVersionGenerated> BUILDER = new DbObjectTableBuilder<>("Workflow", "WorkflowVersion", AGWorkflowVersion::new, AGWorkflowVersion.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_VERSION);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_VERSIONS);
	}

	public static final IDbIdField<AGWorkflowVersion> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(WorkflowI18n.ID);
	public static final IDbForeignField<AGWorkflowVersion, AGWorkflow> WORKFLOW = BUILDER.addForeignField("workflow", o->o.m_workflow, (o,v)->o.m_workflow=v, AGWorkflow.ID).setTitle(WorkflowI18n.WORKFLOW);
	public static final IDbForeignField<AGWorkflowVersion, AGWorkflowNode> ROOT_NODE = BUILDER.addForeignField("rootNode", o->o.m_rootNode, (o,v)->o.m_rootNode=v, AGWorkflowNode.ID).setTitle(WorkflowI18n.ROOT_NODE).setNullable().setDefault(null);
	public static final IDbBooleanField<AGWorkflowVersion> DRAFT = BUILDER.addBooleanField("draft", o->o.m_draft, (o,v)->o.m_draft=v).setTitle(WorkflowI18n.DRAFT).setDefault(true);
	public static final AGWorkflowVersionTable TABLE = new AGWorkflowVersionTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGWorkflowVersion> createSelect() {

		return TABLE.createSelect();
	}

	public static AGWorkflowVersion get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowID() {

		return getValueId(WORKFLOW);
	}

	public final AGWorkflow getWorkflow() {

		return getValue(WORKFLOW);
	}

	public final AGWorkflowVersion setWorkflow(AGWorkflow value) {

		return setValue(WORKFLOW, value);
	}

	public final Integer getRootNodeID() {

		return getValueId(ROOT_NODE);
	}

	public final AGWorkflowNode getRootNode() {

		return getValue(ROOT_NODE);
	}

	public final AGWorkflowVersion setRootNode(AGWorkflowNode value) {

		return setValue(ROOT_NODE, value);
	}

	public final Boolean isDraft() {

		return getValue(DRAFT);
	}

	public final AGWorkflowVersion setDraft(Boolean value) {

		return setValue(DRAFT, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowVersionTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGWorkflow m_workflow;
	private AGWorkflowNode m_rootNode;
	private Boolean m_draft;
}

