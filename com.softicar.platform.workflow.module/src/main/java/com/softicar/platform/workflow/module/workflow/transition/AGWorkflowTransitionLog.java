package com.softicar.platform.workflow.module.workflow.transition;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.workflow.module.WorkflowI18n;
import com.softicar.platform.workflow.module.workflow.image.AGWorkflowIcon;
import com.softicar.platform.workflow.module.workflow.node.AGWorkflowNode;

/**
 * This is the automatically generated class AGWorkflowTransitionLog for
 * database table <i>Workflow.WorkflowTransitionLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowTransitionLog extends AbstractDbRecord<AGWorkflowTransitionLog, Tuple2<AGWorkflowTransition, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGWorkflowTransitionLog, AGWorkflowTransitionLog, Tuple2<AGWorkflowTransition, AGTransaction>> BUILDER = new DbTableBuilder<>("Workflow", "WorkflowTransitionLog", AGWorkflowTransitionLog::new, AGWorkflowTransitionLog.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_TRANSITION_LOG);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_TRANSITION_LOGS);
	}

	public static final IDbForeignField<AGWorkflowTransitionLog, AGWorkflowTransition> WORKFLOW_TRANSITION = BUILDER.addForeignField("workflowTransition", o->o.m_workflowTransition, (o,v)->o.m_workflowTransition=v, AGWorkflowTransition.ID).setTitle(WorkflowI18n.WORKFLOW_TRANSITION);
	public static final IDbForeignField<AGWorkflowTransitionLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION);
	public static final IDbStringField<AGWorkflowTransitionLog> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(WorkflowI18n.NAME).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbStringField<AGWorkflowTransitionLog> HTML_COLOR = BUILDER.addStringField("htmlColor", o->o.m_htmlColor, (o,v)->o.m_htmlColor=v).setTitle(WorkflowI18n.HTML_COLOR).setNullable().setDefault(null).setMaximumLength(7);
	public static final IDbBooleanField<AGWorkflowTransitionLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbForeignField<AGWorkflowTransitionLog, AGWorkflowNode> SOURCE_NODE = BUILDER.addForeignField("sourceNode", o->o.m_sourceNode, (o,v)->o.m_sourceNode=v, AGWorkflowNode.ID).setTitle(WorkflowI18n.SOURCE_NODE).setNullable().setDefault(null);
	public static final IDbForeignField<AGWorkflowTransitionLog, AGWorkflowNode> TARGET_NODE = BUILDER.addForeignField("targetNode", o->o.m_targetNode, (o,v)->o.m_targetNode=v, AGWorkflowNode.ID).setTitle(WorkflowI18n.TARGET_NODE).setNullable().setDefault(null);
	public static final IDbBooleanField<AGWorkflowTransitionLog> NOTIFY = BUILDER.addBooleanField("notify", o->o.m_notify, (o,v)->o.m_notify=v).setTitle(WorkflowI18n.NOTIFY).setNullable().setDefault(null);
	public static final IDbBooleanField<AGWorkflowTransitionLog> AUTO_TRANSITION = BUILDER.addBooleanField("autoTransition", o->o.m_autoTransition, (o,v)->o.m_autoTransition=v).setTitle(WorkflowI18n.AUTO_TRANSITION).setNullable().setDefault(null);
	public static final IDbStringField<AGWorkflowTransitionLog> REQUIRED_VOTES = BUILDER.addStringField("requiredVotes", o->o.m_requiredVotes, (o,v)->o.m_requiredVotes=v).setTitle(WorkflowI18n.REQUIRED_VOTES).setNullable().setDefault(null).setMaximumLength(255);
	public static final IDbForeignField<AGWorkflowTransitionLog, AGWorkflowIcon> TRANSITION_ICON = BUILDER.addForeignField("transitionIcon", o->o.m_transitionIcon, (o,v)->o.m_transitionIcon=v, AGWorkflowIcon.ID).setTitle(WorkflowI18n.TRANSITION_ICON).setNullable().setDefault(null);
	public static final IDbForeignField<AGWorkflowTransitionLog, AGUuid> SIDE_EFFECT = BUILDER.addForeignField("sideEffect", o->o.m_sideEffect, (o,v)->o.m_sideEffect=v, AGUuid.ID).setTitle(WorkflowI18n.SIDE_EFFECT).setNullable().setDefault(null);
	public static final IDbTableKey<AGWorkflowTransitionLog, Tuple2<AGWorkflowTransition, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(WORKFLOW_TRANSITION, TRANSACTION));
	public static final DbRecordTable<AGWorkflowTransitionLog, Tuple2<AGWorkflowTransition, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGWorkflowTransitionLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getWorkflowTransitionID() {

		return getValueId(WORKFLOW_TRANSITION);
	}

	public final AGWorkflowTransition getWorkflowTransition() {

		return getValue(WORKFLOW_TRANSITION);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final String getName() {

		return getValue(NAME);
	}

	public final AGWorkflowTransitionLog setName(String value) {

		return setValue(NAME, value);
	}

	public final String getHtmlColor() {

		return getValue(HTML_COLOR);
	}

	public final AGWorkflowTransitionLog setHtmlColor(String value) {

		return setValue(HTML_COLOR, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGWorkflowTransitionLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getSourceNodeID() {

		return getValueId(SOURCE_NODE);
	}

	public final AGWorkflowNode getSourceNode() {

		return getValue(SOURCE_NODE);
	}

	public final AGWorkflowTransitionLog setSourceNode(AGWorkflowNode value) {

		return setValue(SOURCE_NODE, value);
	}

	public final Integer getTargetNodeID() {

		return getValueId(TARGET_NODE);
	}

	public final AGWorkflowNode getTargetNode() {

		return getValue(TARGET_NODE);
	}

	public final AGWorkflowTransitionLog setTargetNode(AGWorkflowNode value) {

		return setValue(TARGET_NODE, value);
	}

	public final Boolean isNotify() {

		return getValue(NOTIFY);
	}

	public final AGWorkflowTransitionLog setNotify(Boolean value) {

		return setValue(NOTIFY, value);
	}

	public final Boolean isAutoTransition() {

		return getValue(AUTO_TRANSITION);
	}

	public final AGWorkflowTransitionLog setAutoTransition(Boolean value) {

		return setValue(AUTO_TRANSITION, value);
	}

	public final String getRequiredVotes() {

		return getValue(REQUIRED_VOTES);
	}

	public final AGWorkflowTransitionLog setRequiredVotes(String value) {

		return setValue(REQUIRED_VOTES, value);
	}

	public final Integer getTransitionIconID() {

		return getValueId(TRANSITION_ICON);
	}

	public final AGWorkflowIcon getTransitionIcon() {

		return getValue(TRANSITION_ICON);
	}

	public final AGWorkflowTransitionLog setTransitionIcon(AGWorkflowIcon value) {

		return setValue(TRANSITION_ICON, value);
	}

	public final Integer getSideEffectID() {

		return getValueId(SIDE_EFFECT);
	}

	public final AGUuid getSideEffect() {

		return getValue(SIDE_EFFECT);
	}

	public final AGWorkflowTransitionLog setSideEffect(AGUuid value) {

		return setValue(SIDE_EFFECT, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGWorkflowTransitionLog, Tuple2<AGWorkflowTransition, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGWorkflowTransition m_workflowTransition;
	private AGTransaction m_transaction;
	private String m_name;
	private String m_htmlColor;
	private Boolean m_active;
	private AGWorkflowNode m_sourceNode;
	private AGWorkflowNode m_targetNode;
	private Boolean m_notify;
	private Boolean m_autoTransition;
	private String m_requiredVotes;
	private AGWorkflowIcon m_transitionIcon;
	private AGUuid m_sideEffect;
}

