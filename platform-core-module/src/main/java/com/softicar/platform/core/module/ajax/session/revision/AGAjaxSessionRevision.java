package com.softicar.platform.core.module.ajax.session.revision;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.ajax.session.AGAjaxSession;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;
import com.softicar.platform.db.structure.foreign.key.DbForeignKeyAction;

/**
 * This is the automatically generated class AGAjaxSessionRevision for
 * database table <i>Core.AjaxSessionRevision</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGAjaxSessionRevision extends AbstractDbRecord<AGAjaxSessionRevision, AGAjaxSession> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGAjaxSessionRevision, AGAjaxSessionRevision, AGAjaxSession> BUILDER = new DbTableBuilder<>("Core", "AjaxSessionRevision", AGAjaxSessionRevision::new, AGAjaxSessionRevision.class);
	static {
		BUILDER.setTitle(CoreI18n.AJAX_SESSION_REVISION);
		BUILDER.setPluralTitle(CoreI18n.AJAX_SESSION_REVISIONS);
	}

	public static final IDbForeignField<AGAjaxSessionRevision, AGAjaxSession> SESSION = BUILDER.addForeignField("session", o->o.m_session, (o,v)->o.m_session=v, AGAjaxSession.ID).setTitle(CoreI18n.SESSION).setOnDelete(DbForeignKeyAction.CASCADE).setOnUpdate(DbForeignKeyAction.NO_ACTION).setForeignKeyName("AjaxSessionRevision_ibfk_1");
	public static final IDbStringField<AGAjaxSessionRevision> REVISION = BUILDER.addStringField("revision", o->o.m_revision, (o,v)->o.m_revision=v).setTitle(CoreI18n.REVISION).setDefault("").setMaximumLength(255);
	public static final IDbTableKey<AGAjaxSessionRevision, AGAjaxSession> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(SESSION));
	public static final DbRecordTable<AGAjaxSessionRevision, AGAjaxSession> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGAjaxSessionRevision() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getSessionID() {

		return getValueId(SESSION);
	}

	public final AGAjaxSession getSession() {

		return getValue(SESSION);
	}

	public final String getRevision() {

		return getValue(REVISION);
	}

	public final AGAjaxSessionRevision setRevision(String value) {

		return setValue(REVISION, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGAjaxSessionRevision, AGAjaxSession> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGAjaxSession m_session;
	private String m_revision;
}

