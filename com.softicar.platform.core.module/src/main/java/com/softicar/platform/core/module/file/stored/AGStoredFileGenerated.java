package com.softicar.platform.core.module.file.stored;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.file.stored.hash.AGStoredFileSha1;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGStoredFile for
 * database table <i>Core.StoredFile</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGStoredFileGenerated extends AbstractDbObject<AGStoredFile> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGStoredFile, AGStoredFileGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "StoredFile", AGStoredFile::new, AGStoredFile.class);
	static {
		BUILDER.setTitle(CoreI18n.STORED_FILE);
		BUILDER.setPluralTitle(CoreI18n.STORED_FILES);
	}

	public static final IDbIdField<AGStoredFile> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbStringField<AGStoredFile> FILE_NAME = BUILDER.addStringField("fileName", o->o.m_fileName, (o,v)->o.m_fileName=v).setTitle(CoreI18n.FILE_NAME).setMaximumLength(255);
	public static final IDbStringField<AGStoredFile> CONTENT_TYPE = BUILDER.addStringField("contentType", o->o.m_contentType, (o,v)->o.m_contentType=v).setTitle(CoreI18n.CONTENT_TYPE).setDefault("").setMaximumLength(255);
	public static final IDbForeignField<AGStoredFile, AGStoredFileSha1> SHA_1 = BUILDER.addForeignField("sha1", o->o.m_sha1, (o,v)->o.m_sha1=v, AGStoredFileSha1.ID).setTitle(CoreI18n.SHA_1).setNullable().setDefault(null);
	public static final IDbForeignField<AGStoredFile, AGUser> CREATED_BY = BUILDER.addForeignField("createdBy", o->o.m_createdBy, (o,v)->o.m_createdBy=v, AGUser.ID).setTitle(CoreI18n.CREATED_BY);
	public static final IDbDayTimeField<AGStoredFile> CREATED_AT = BUILDER.addDayTimeField("createdAt", o->o.m_createdAt, (o,v)->o.m_createdAt=v).setTitle(CoreI18n.CREATED_AT).setDefaultNow();
	public static final IDbDayTimeField<AGStoredFile> REMOVE_AT = BUILDER.addDayTimeField("removeAt", o->o.m_removeAt, (o,v)->o.m_removeAt=v).setTitle(CoreI18n.REMOVE_AT).setNullable().setDefault(null);
	public static final AGStoredFileTable TABLE = new AGStoredFileTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGStoredFile> createSelect() {

		return TABLE.createSelect();
	}

	public static AGStoredFile get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final String getFileName() {

		return getValue(FILE_NAME);
	}

	public final AGStoredFile setFileName(String value) {

		return setValue(FILE_NAME, value);
	}

	public final String getContentType() {

		return getValue(CONTENT_TYPE);
	}

	public final AGStoredFile setContentType(String value) {

		return setValue(CONTENT_TYPE, value);
	}

	public final Integer getSha1ID() {

		return getValueId(SHA_1);
	}

	public final AGStoredFileSha1 getSha1() {

		return getValue(SHA_1);
	}

	public final AGStoredFile setSha1(AGStoredFileSha1 value) {

		return setValue(SHA_1, value);
	}

	public final Integer getCreatedByID() {

		return getValueId(CREATED_BY);
	}

	public final AGUser getCreatedBy() {

		return getValue(CREATED_BY);
	}

	public final AGStoredFile setCreatedBy(AGUser value) {

		return setValue(CREATED_BY, value);
	}

	public final DayTime getCreatedAt() {

		return getValue(CREATED_AT);
	}

	public final AGStoredFile setCreatedAt(DayTime value) {

		return setValue(CREATED_AT, value);
	}

	public final DayTime getRemoveAt() {

		return getValue(REMOVE_AT);
	}

	public final AGStoredFile setRemoveAt(DayTime value) {

		return setValue(REMOVE_AT, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGStoredFileTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private String m_fileName;
	private String m_contentType;
	private AGStoredFileSha1 m_sha1;
	private AGUser m_createdBy;
	private DayTime m_createdAt;
	private DayTime m_removeAt;
}

