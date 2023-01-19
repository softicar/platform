package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.image.AGWorkflowIcon;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;
import com.softicar.platform.workflow.module.workflow.version.AGWorkflowVersion;

/**
 * This is the automatically generated class AGWorkflowTransition for
 * database table <i>Workflow.WorkflowTransition</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowTransitionGenerated extends AbstractDbObject<AGWorkflowTransition> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGWorkflowTransition, AGWorkflowTransitionGenerated> BUILDER = new DbObjectTableBuilder<>("Workflow", "WorkflowTransition", AGWorkflowTransition::new, AGWorkflowTransition.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_TRANSITION);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_TRANSITIONS);
	}

	public static final IDbIdField<AGWorkflowTransition> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(WorkflowI18n.ID);
	public static final IDbForeignField<AGWorkflowTransition, AGWorkflowVersion> WORKFLOW_VERSION = BUILDER.addForeignField("workflowVersion", o->o.m_workflowVersion, (o,v)->o.m_workflowVersion=v, AGWorkflowVersion.ID).setTitle(WorkflowI18n.WORKFLOW_VERSION).setForeignKeyName("WorkflowTransition_ibfk_1");
	public static final IDbStringField<AGWorkflowTransition> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(WorkflowI18n.NAME).setMaximumLength(255);
	public static final IDbBooleanField<AGWorkflowTransition> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setDefault(true);
	public static final IDbForeignField<AGWorkflowTransition, AGWorkflowNode> SOURCE_NODE = BUILDER.addForeignField("sourceNode", o->o.m_sourceNode, (o,v)->o.m_sourceNode=v, AGWorkflowNode.ID).setTitle(WorkflowI18n.SOURCE_NODE).setForeignKeyName("WorkflowTransition_ibfk_2");
	public static final IDbForeignField<AGWorkflowTransition, AGWorkflowNode> TARGET_NODE = BUILDER.addForeignField("targetNode", o->o.m_targetNode, (o,v)->o.m_targetNode=v, AGWorkflowNode.ID).setTitle(WorkflowI18n.TARGET_NODE).setForeignKeyName("WorkflowTransition_ibfk_3");
	public static final IDbBooleanField<AGWorkflowTransition> NOTIFY = BUILDER.addBooleanField("notify", o->o.m_notify, (o,v)->o.m_notify=v).setTitle(WorkflowI18n.NOTIFY).setDefault(true);
	public static final IDbBooleanField<AGWorkflowTransition> AUTO_TRANSITION = BUILDER.addBooleanField("autoTransition", o->o.m_autoTransition, (o,v)->o.m_autoTransition=v).setTitle(WorkflowI18n.AUTO_TRANSITION).setDefault(false);
	public static final IDbBooleanField<AGWorkflowTransition> COMMENT_REQUIRED = BUILDER.addBooleanField("commentRequired", o->o.m_commentRequired, (o,v)->o.m_commentRequired=v).setTitle(WorkflowI18n.COMMENT_REQUIRED).setDefault(false);
	public static final IDbStringField<AGWorkflowTransition> REQUIRED_VOTES = BUILDER.addStringField("requiredVotes", o->o.m_requiredVotes, (o,v)->o.m_requiredVotes=v).setTitle(WorkflowI18n.REQUIRED_VOTES).setMaximumLength(255);
	public static final IDbStringField<AGWorkflowTransition> HTML_COLOR = BUILDER.addStringField("htmlColor", o->o.m_htmlColor, (o,v)->o.m_htmlColor=v).setTitle(WorkflowI18n.HTML_COLOR).setNullable().setDefault(null).setMaximumLength(7);
	public static final IDbForeignField<AGWorkflowTransition, AGWorkflowIcon> TRANSITION_ICON = BUILDER.addForeignField("transitionIcon", o->o.m_transitionIcon, (o,v)->o.m_transitionIcon=v, AGWorkflowIcon.ID).setTitle(WorkflowI18n.TRANSITION_ICON).setNullable().setDefault(null).setForeignKeyName("WorkflowTransition_ibfk_4");
	public static final IDbForeignField<AGWorkflowTransition, AGUuid> SIDE_EFFECT = BUILDER.addForeignField("sideEffect", o->o.m_sideEffect, (o,v)->o.m_sideEffect=v, AGUuid.ID).setTitle(WorkflowI18n.SIDE_EFFECT).setNullable().setDefault(null).setForeignKeyName("WorkflowTransition_ibfk_5");
	public static final IDbKey<AGWorkflowTransition> IK_WORKFLOW_VERSION = BUILDER.addIndexKey("workflowVersion", WORKFLOW_VERSION);
	public static final IDbKey<AGWorkflowTransition> IK_SOURCE_NODE = BUILDER.addIndexKey("sourceNode", SOURCE_NODE);
	public static final IDbKey<AGWorkflowTransition> IK_TARGET_NODE = BUILDER.addIndexKey("targetNode", TARGET_NODE);
	public static final IDbKey<AGWorkflowTransition> IK_TRANSITION_ICON = BUILDER.addIndexKey("transitionIcon", TRANSITION_ICON);
	public static final IDbKey<AGWorkflowTransition> IK_SIDE_EFFECT = BUILDER.addIndexKey("sideEffect", SIDE_EFFECT);
	public static final AGWorkflowTransitionTable TABLE = new AGWorkflowTransitionTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGWorkflowTransition> createSelect() {

		return TABLE.createSelect();
	}

	public static AGWorkflowTransition get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowVersionID() {

		return getValueId(WORKFLOW_VERSION);
	}

	public final AGWorkflowVersion getWorkflowVersion() {

		return getValue(WORKFLOW_VERSION);
	}

	public final AGWorkflowTransition setWorkflowVersion(AGWorkflowVersion value) {

		return setValue(WORKFLOW_VERSION, value);
	}

	public final String getName() {

		return getValue(NAME);
	}

	public final AGWorkflowTransition setName(String value) {

		return setValue(NAME, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGWorkflowTransition setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getSourceNodeID() {

		return getValueId(SOURCE_NODE);
	}

	public final AGWorkflowNode getSourceNode() {

		return getValue(SOURCE_NODE);
	}

	public final AGWorkflowTransition setSourceNode(AGWorkflowNode value) {

		return setValue(SOURCE_NODE, value);
	}

	public final Integer getTargetNodeID() {

		return getValueId(TARGET_NODE);
	}

	public final AGWorkflowNode getTargetNode() {

		return getValue(TARGET_NODE);
	}

	public final AGWorkflowTransition setTargetNode(AGWorkflowNode value) {

		return setValue(TARGET_NODE, value);
	}

	public final Boolean isNotify() {

		return getValue(NOTIFY);
	}

	public final AGWorkflowTransition setNotify(Boolean value) {

		return setValue(NOTIFY, value);
	}

	public final Boolean isAutoTransition() {

		return getValue(AUTO_TRANSITION);
	}

	public final AGWorkflowTransition setAutoTransition(Boolean value) {

		return setValue(AUTO_TRANSITION, value);
	}

	public final Boolean isCommentRequired() {

		return getValue(COMMENT_REQUIRED);
	}

	public final AGWorkflowTransition setCommentRequired(Boolean value) {

		return setValue(COMMENT_REQUIRED, value);
	}

	public final String getRequiredVotes() {

		return getValue(REQUIRED_VOTES);
	}

	public final AGWorkflowTransition setRequiredVotes(String value) {

		return setValue(REQUIRED_VOTES, value);
	}

	public final String getHtmlColor() {

		return getValue(HTML_COLOR);
	}

	public final AGWorkflowTransition setHtmlColor(String value) {

		return setValue(HTML_COLOR, value);
	}

	public final Integer getTransitionIconID() {

		return getValueId(TRANSITION_ICON);
	}

	public final AGWorkflowIcon getTransitionIcon() {

		return getValue(TRANSITION_ICON);
	}

	public final AGWorkflowTransition setTransitionIcon(AGWorkflowIcon value) {

		return setValue(TRANSITION_ICON, value);
	}

	public final Integer getSideEffectID() {

		return getValueId(SIDE_EFFECT);
	}

	public final AGUuid getSideEffect() {

		return getValue(SIDE_EFFECT);
	}

	public final AGWorkflowTransition setSideEffect(AGUuid value) {

		return setValue(SIDE_EFFECT, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowTransitionTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGWorkflowVersion m_workflowVersion;
	private String m_name;
	private Boolean m_active;
	private AGWorkflowNode m_sourceNode;
	private AGWorkflowNode m_targetNode;
	private Boolean m_notify;
	private Boolean m_autoTransition;
	private Boolean m_commentRequired;
	private String m_requiredVotes;
	private String m_htmlColor;
	private AGWorkflowIcon m_transitionIcon;
	private AGUuid m_sideEffect;
}

