package com.softicar.platform.workflow.module.workflow.substitute;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbDayField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.workflow.module.WorkflowI18n;

/**
 * This is the automatically generated class AGWorkflowSubstituteLog for
 * database table <i>Workflow.WorkflowSubstituteLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowSubstituteLog extends AbstractDbRecord<AGWorkflowSubstituteLog, Tuple2<AGWorkflowSubstitute, AGTransaction>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGWorkflowSubstituteLog, AGWorkflowSubstituteLog, Tuple2<AGWorkflowSubstitute, AGTransaction>> BUILDER = new DbTableBuilder<>("Workflow", "WorkflowSubstituteLog", AGWorkflowSubstituteLog::new, AGWorkflowSubstituteLog.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_SUBSTITUTE_LOG);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_SUBSTITUTE_LOGS);
	}

	public static final IDbForeignRowField<AGWorkflowSubstituteLog, AGWorkflowSubstitute, AGUser> WORKFLOW_SUBSTITUTE = BUILDER.addForeignRowField("workflowSubstitute", o->o.m_workflowSubstitute, (o,v)->o.m_workflowSubstitute=v, AGWorkflowSubstitute.USER).setTitle(WorkflowI18n.WORKFLOW_SUBSTITUTE);
	public static final IDbForeignField<AGWorkflowSubstituteLog, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(WorkflowI18n.TRANSACTION);
	public static final IDbBooleanField<AGWorkflowSubstituteLog> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setNullable().setDefault(null);
	public static final IDbForeignField<AGWorkflowSubstituteLog, AGUser> SUBSTITUTE = BUILDER.addForeignField("substitute", o->o.m_substitute, (o,v)->o.m_substitute=v, AGUser.ID).setTitle(WorkflowI18n.SUBSTITUTE).setNullable().setDefault(null);
	public static final IDbDayField<AGWorkflowSubstituteLog> VALID_FROM = BUILDER.addDayField("validFrom", o->o.m_validFrom, (o,v)->o.m_validFrom=v).setTitle(WorkflowI18n.VALID_FROM).setNullable().setDefault(null);
	public static final IDbDayField<AGWorkflowSubstituteLog> VALID_TO = BUILDER.addDayField("validTo", o->o.m_validTo, (o,v)->o.m_validTo=v).setTitle(WorkflowI18n.VALID_TO).setNullable().setDefault(null);
	public static final IDbTableKey<AGWorkflowSubstituteLog, Tuple2<AGWorkflowSubstitute, AGTransaction>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(WORKFLOW_SUBSTITUTE, TRANSACTION));
	public static final DbRecordTable<AGWorkflowSubstituteLog, Tuple2<AGWorkflowSubstitute, AGTransaction>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGWorkflowSubstituteLog() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final AGWorkflowSubstitute getWorkflowSubstitute() {

		return getValue(WORKFLOW_SUBSTITUTE);
	}

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGWorkflowSubstituteLog setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getSubstituteID() {

		return getValueId(SUBSTITUTE);
	}

	public final AGUser getSubstitute() {

		return getValue(SUBSTITUTE);
	}

	public final AGWorkflowSubstituteLog setSubstitute(AGUser value) {

		return setValue(SUBSTITUTE, value);
	}

	public final Day getValidFrom() {

		return getValue(VALID_FROM);
	}

	public final AGWorkflowSubstituteLog setValidFrom(Day value) {

		return setValue(VALID_FROM, value);
	}

	public final Day getValidTo() {

		return getValue(VALID_TO);
	}

	public final AGWorkflowSubstituteLog setValidTo(Day value) {

		return setValue(VALID_TO, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGWorkflowSubstituteLog, Tuple2<AGWorkflowSubstitute, AGTransaction>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGWorkflowSubstitute m_workflowSubstitute;
	private AGTransaction m_transaction;
	private Boolean m_active;
	private AGUser m_substitute;
	private Day m_validFrom;
	private Day m_validTo;
}

