package com.softicar.platform.workflow.module.workflow.item.message.severity;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.db.runtime.enums.DbEnumTable;
import com.softicar.platform.db.runtime.enums.IDbEnumTableRowEnum;
import com.softicar.platform.db.sql.field.ISqlFieldValueConsumer;
import com.softicar.platform.workflow.module.WorkflowI18n;

@Generated
public enum AGWorkflowMessageSeverityEnum implements IDbEnumTableRowEnum<AGWorkflowMessageSeverityEnum, AGWorkflowMessageSeverity>, IDisplayable {

	VERBOSE(1, "VERBOSE"),
	INFORMATION(2, "INFORMATION"),
	WARNING(3, "WARNING"),
	ERROR(4, "ERROR"),
	//
	;

	private Integer id;
	private String name;

	private AGWorkflowMessageSeverityEnum(Integer id, String name) {

		this.id = id;
		this.name = name;
	}

	@Override
	public DbEnumTable<AGWorkflowMessageSeverity, AGWorkflowMessageSeverityEnum> getTable() {

		return AGWorkflowMessageSeverity.TABLE;
	}

	@Override
	public Integer getId() {

		return id;
	}

	@Override
	public IDisplayString toDisplay() {

		switch (this) {
		case VERBOSE:
			return WorkflowI18n.VERBOSE;
		case INFORMATION:
			return WorkflowI18n.INFORMATION;
		case WARNING:
			return WorkflowI18n.WARNING;
		case ERROR:
			return WorkflowI18n.ERROR;
		}

		throw new IllegalArgumentException("Unknown enumerator: " + name());
	}

	@Override
	public void setFields(ISqlFieldValueConsumer<AGWorkflowMessageSeverity> consumer) {

		consumer.consumeFieldValue(AGWorkflowMessageSeverity.ID, id);
		consumer.consumeFieldValue(AGWorkflowMessageSeverity.NAME, name);
	}
}

