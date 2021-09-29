package com.softicar.platform.workflow.module.workflow.substitute;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbDayField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.workflow.module.WorkflowI18n;

/**
 * This is the automatically generated class AGWorkflowSubstitute for
 * database table <i>Workflow.WorkflowSubstitute</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGWorkflowSubstituteGenerated extends AbstractDbRecord<AGWorkflowSubstitute, AGUser> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGWorkflowSubstitute, AGWorkflowSubstituteGenerated, AGUser> BUILDER = new DbTableBuilder<>("Workflow", "WorkflowSubstitute", AGWorkflowSubstitute::new, AGWorkflowSubstitute.class);
	static {
		BUILDER.setTitle(WorkflowI18n.WORKFLOW_SUBSTITUTE);
		BUILDER.setPluralTitle(WorkflowI18n.WORKFLOW_SUBSTITUTES);
	}

	public static final IDbForeignField<AGWorkflowSubstitute, AGUser> USER = BUILDER.addForeignField("user", o->o.m_user, (o,v)->o.m_user=v, AGUser.ID).setTitle(WorkflowI18n.USER);
	public static final IDbBooleanField<AGWorkflowSubstitute> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(WorkflowI18n.ACTIVE).setDefault(true);
	public static final IDbForeignField<AGWorkflowSubstitute, AGUser> SUBSTITUTE = BUILDER.addForeignField("substitute", o->o.m_substitute, (o,v)->o.m_substitute=v, AGUser.ID).setTitle(WorkflowI18n.SUBSTITUTE);
	public static final IDbDayField<AGWorkflowSubstitute> VALID_FROM = BUILDER.addDayField("validFrom", o->o.m_validFrom, (o,v)->o.m_validFrom=v).setTitle(WorkflowI18n.VALID_FROM);
	public static final IDbDayField<AGWorkflowSubstitute> VALID_TO = BUILDER.addDayField("validTo", o->o.m_validTo, (o,v)->o.m_validTo=v).setTitle(WorkflowI18n.VALID_TO);
	public static final IDbTableKey<AGWorkflowSubstitute, AGUser> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(USER));
	public static final AGWorkflowSubstituteTable TABLE = new AGWorkflowSubstituteTable(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGWorkflowSubstituteGenerated() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getUserID() {

		return getValueId(USER);
	}

	public final AGUser getUser() {

		return getValue(USER);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGWorkflowSubstitute setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getSubstituteID() {

		return getValueId(SUBSTITUTE);
	}

	public final AGUser getSubstitute() {

		return getValue(SUBSTITUTE);
	}

	public final AGWorkflowSubstitute setSubstitute(AGUser value) {

		return setValue(SUBSTITUTE, value);
	}

	public final Day getValidFrom() {

		return getValue(VALID_FROM);
	}

	public final AGWorkflowSubstitute setValidFrom(Day value) {

		return setValue(VALID_FROM, value);
	}

	public final Day getValidTo() {

		return getValue(VALID_TO);
	}

	public final AGWorkflowSubstitute setValidTo(Day value) {

		return setValue(VALID_TO, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGWorkflowSubstituteTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGUser m_user;
	private Boolean m_active;
	private AGUser m_substitute;
	private Day m_validFrom;
	private Day m_validTo;
}

