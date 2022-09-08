package com.softicar.platform.core.module.file.stored;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGStoredFileLog for
 * database table <i>Core.StoredFileLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGStoredFileLogGenerated extends AbstractDbObject<AGStoredFileLog> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGStoredFileLog, AGStoredFileLogGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "StoredFileLog", AGStoredFileLog::new, AGStoredFileLog.class);
	static {
		BUILDER.setTitle(CoreI18n.STORED_FILE_LOG);
		BUILDER.setPluralTitle(CoreI18n.STORED_FILE_LOGS);
	}

	public static final IDbIdField<AGStoredFileLog> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGStoredFileLog, AGStoredFile> FILE = BUILDER.addForeignField("file", o->o.m_file, (o,v)->o.m_file=v, AGStoredFile.ID).setTitle(CoreI18n.FILE).setCascade(true, true).setForeignKeyName("StoredFileLog_ibfk_1");
	public static final IDbDayTimeField<AGStoredFileLog> REMOVE_AT = BUILDER.addDayTimeField("removeAt", o->o.m_removeAt, (o,v)->o.m_removeAt=v).setTitle(CoreI18n.REMOVE_AT).setNullable().setDefault(null);
	public static final IDbDayTimeField<AGStoredFileLog> LOGGED_AT = BUILDER.addDayTimeField("loggedAt", o->o.m_loggedAt, (o,v)->o.m_loggedAt=v).setTitle(CoreI18n.LOGGED_AT);
	public static final IDbForeignField<AGStoredFileLog, AGUser> LOGGED_BY = BUILDER.addForeignField("loggedBy", o->o.m_loggedBy, (o,v)->o.m_loggedBy=v, AGUser.ID).setTitle(CoreI18n.LOGGED_BY).setForeignKeyName("StoredFileLog_ibfk_2");
	public static final IDbKey<AGStoredFileLog> IK_FILE = BUILDER.addIndexKey("file", FILE);
	public static final IDbKey<AGStoredFileLog> IK_LOGGED_BY = BUILDER.addIndexKey("loggedBy", LOGGED_BY);
	public static final IDbKey<AGStoredFileLog> IK_LOGGED_AT = BUILDER.addIndexKey("loggedAt", LOGGED_AT);
	public static final AGStoredFileLogTable TABLE = new AGStoredFileLogTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGStoredFileLog> createSelect() {

		return TABLE.createSelect();
	}

	public static AGStoredFileLog get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getFileID() {

		return getValueId(FILE);
	}

	public final AGStoredFile getFile() {

		return getValue(FILE);
	}

	public final AGStoredFileLog setFile(AGStoredFile value) {

		return setValue(FILE, value);
	}

	public final DayTime getRemoveAt() {

		return getValue(REMOVE_AT);
	}

	public final AGStoredFileLog setRemoveAt(DayTime value) {

		return setValue(REMOVE_AT, value);
	}

	public final DayTime getLoggedAt() {

		return getValue(LOGGED_AT);
	}

	public final AGStoredFileLog setLoggedAt(DayTime value) {

		return setValue(LOGGED_AT, value);
	}

	public final Integer getLoggedByID() {

		return getValueId(LOGGED_BY);
	}

	public final AGUser getLoggedBy() {

		return getValue(LOGGED_BY);
	}

	public final AGStoredFileLog setLoggedBy(AGUser value) {

		return setValue(LOGGED_BY, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGStoredFileLogTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGStoredFile m_file;
	private DayTime m_removeAt;
	private DayTime m_loggedAt;
	private AGUser m_loggedBy;
}

