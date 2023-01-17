package com.softicar.platform.workflow.module.workflow.item.message.severity;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.db.runtime.enums.AbstractDbEnumTableRow;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.workflow.module.WorkflowI18n;

/**
 * This is the automatically generated class AGWorkflowMessageSeverity for
 * database table <i>Workflow.WorkflowMessageSeverity</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowMessageSeverityGenerated extends AbstractDbEnumTableRow<AGWorkflowMessageSeverity, AGWorkflowMessageSeverityEnum> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGWorkflowMessageSeverity, AGWorkflowMessageSeverityGenerated> BUILDER = new DbObjectTableBuilder<>("Workflow", "WorkflowMessageSeverity", AGWorkflowMessageSeverity::new, AGWorkflowMessageSeverity.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_MESSAGE_SEVERITY);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_MESSAGE_SEVERITIES);
	}

	public static final IDbIdField<AGWorkflowMessageSeverity> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(WorkflowI18n.ID);
	public static final IDbStringField<AGWorkflowMessageSeverity> NAME = BUILDER.addStringField("name", o->o.m_name, (o,v)->o.m_name=v).setTitle(WorkflowI18n.NAME);
	public static final DbEnumTable<AGWorkflowMessageSeverity, AGWorkflowMessageSeverityEnum> TABLE = new DbEnumTable<>(BUILDER, AGWorkflowMessageSeverityEnum.values());
	// @formatter:on

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final String getName() {

		return getValue(NAME);
	}

	public final AGWorkflowMessageSeverity setName(String value) {

		return setValue(NAME, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbEnumTable<AGWorkflowMessageSeverity, AGWorkflowMessageSeverityEnum> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private String m_name;
}

