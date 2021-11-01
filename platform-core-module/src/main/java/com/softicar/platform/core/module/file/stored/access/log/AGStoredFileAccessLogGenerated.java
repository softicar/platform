package com.softicar.platform.core.module.file.stored.access.log;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGStoredFileAccessLog for
 * database table <i>Core.StoredFileAccessLog</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGStoredFileAccessLogGenerated extends AbstractDbObject<AGStoredFileAccessLog> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGStoredFileAccessLog, AGStoredFileAccessLogGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "StoredFileAccessLog", AGStoredFileAccessLog::new, AGStoredFileAccessLog.class);
	static {
		BUILDER.setTitle(CoreI18n.STORED_FILE_ACCESS_LOG);
		BUILDER.setPluralTitle(CoreI18n.STORED_FILE_ACCESS_LOGS);
	}

	public static final IDbIdField<AGStoredFileAccessLog> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGStoredFileAccessLog, AGStoredFile> FILE = BUILDER.addForeignField("file", o->o.m_file, (o,v)->o.m_file=v, AGStoredFile.ID).setTitle(CoreI18n.FILE);
	public static final IDbForeignField<AGStoredFileAccessLog, AGUser> ACCESSED_BY = BUILDER.addForeignField("accessedBy", o->o.m_accessedBy, (o,v)->o.m_accessedBy=v, AGUser.ID).setTitle(CoreI18n.ACCESSED_BY);
	public static final IDbDayTimeField<AGStoredFileAccessLog> ACCESSED_AT = BUILDER.addDayTimeField("accessedAt", o->o.m_accessedAt, (o,v)->o.m_accessedAt=v).setTitle(CoreI18n.ACCESSED_AT).setDefaultNow().setTimestamp();
	public static final IDbKey<AGStoredFileAccessLog> IK_FILE = BUILDER.addIndexKey("file", FILE);
	public static final IDbKey<AGStoredFileAccessLog> IK_ACCESSED_BY = BUILDER.addIndexKey("accessedBy", ACCESSED_BY);
	public static final AGStoredFileAccessLogTable TABLE = new AGStoredFileAccessLogTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGStoredFileAccessLog> createSelect() {

		return TABLE.createSelect();
	}

	public static AGStoredFileAccessLog get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getFileID() {

		return getValueId(FILE);
	}

	public final AGStoredFile getFile() {

		return getValue(FILE);
	}

	public final AGStoredFileAccessLog setFile(AGStoredFile value) {

		return setValue(FILE, value);
	}

	public final Integer getAccessedByID() {

		return getValueId(ACCESSED_BY);
	}

	public final AGUser getAccessedBy() {

		return getValue(ACCESSED_BY);
	}

	public final AGStoredFileAccessLog setAccessedBy(AGUser value) {

		return setValue(ACCESSED_BY, value);
	}

	public final DayTime getAccessedAt() {

		return getValue(ACCESSED_AT);
	}

	public final AGStoredFileAccessLog setAccessedAt(DayTime value) {

		return setValue(ACCESSED_AT, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGStoredFileAccessLogTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGStoredFile m_file;
	private AGUser m_accessedBy;
	private DayTime m_accessedAt;
}

