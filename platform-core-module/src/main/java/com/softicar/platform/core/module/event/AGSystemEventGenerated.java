package com.softicar.platform.core.module.event;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.event.severity.AGSystemEventSeverity;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGSystemEvent for
 * database table <i>Core.SystemEvent</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGSystemEventGenerated extends AbstractDbObject<AGSystemEvent> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGSystemEvent, AGSystemEventGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "SystemEvent", AGSystemEvent::new, AGSystemEvent.class);
	static {
		BUILDER.setTitle(CoreI18n.SYSTEM_EVENT);
		BUILDER.setPluralTitle(CoreI18n.SYSTEM_EVENTS);
	}

	public static final IDbIdField<AGSystemEvent> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGSystemEvent, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION);
	public static final IDbForeignField<AGSystemEvent, AGSystemEventSeverity> SEVERITY = BUILDER.addForeignField("severity", o->o.m_severity, (o,v)->o.m_severity=v, AGSystemEventSeverity.ID).setTitle(CoreI18n.SEVERITY);
	public static final IDbStringField<AGSystemEvent> MESSAGE = BUILDER.addStringField("message", o->o.m_message, (o,v)->o.m_message=v).setTitle(CoreI18n.MESSAGE).setLengthBits(16);
	public static final IDbBooleanField<AGSystemEvent> NEEDS_ATTENTION = BUILDER.addBooleanField("needsAttention", o->o.m_needsAttention, (o,v)->o.m_needsAttention=v).setTitle(CoreI18n.NEEDS_ATTENTION);
	public static final IDbStringField<AGSystemEvent> PROPERTIES = BUILDER.addStringField("properties", o->o.m_properties, (o,v)->o.m_properties=v).setTitle(CoreI18n.PROPERTIES).setLengthBits(16);
	public static final AGSystemEventTable TABLE = new AGSystemEventTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGSystemEvent> createSelect() {

		return TABLE.createSelect();
	}

	public static AGSystemEvent get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final AGSystemEvent setTransaction(AGTransaction value) {

		return setValue(TRANSACTION, value);
	}

	public final Integer getSeverityID() {

		return getValueId(SEVERITY);
	}

	public final AGSystemEventSeverity getSeverity() {

		return getValue(SEVERITY);
	}

	public final AGSystemEvent setSeverity(AGSystemEventSeverity value) {

		return setValue(SEVERITY, value);
	}

	public final String getMessage() {

		return getValue(MESSAGE);
	}

	public final AGSystemEvent setMessage(String value) {

		return setValue(MESSAGE, value);
	}

	public final Boolean isNeedsAttention() {

		return getValue(NEEDS_ATTENTION);
	}

	public final AGSystemEvent setNeedsAttention(Boolean value) {

		return setValue(NEEDS_ATTENTION, value);
	}

	public final String getProperties() {

		return getValue(PROPERTIES);
	}

	public final AGSystemEvent setProperties(String value) {

		return setValue(PROPERTIES, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGSystemEventTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGTransaction m_transaction;
	private AGSystemEventSeverity m_severity;
	private String m_message;
	private Boolean m_needsAttention;
	private String m_properties;
}

