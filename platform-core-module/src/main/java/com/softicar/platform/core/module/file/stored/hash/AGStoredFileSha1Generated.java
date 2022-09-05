package com.softicar.platform.core.module.file.stored.hash;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.field.IDbByteArrayField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbLongField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGStoredFileSha1 for
 * database table <i>Core.StoredFileSha1</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGStoredFileSha1Generated extends AbstractDbObject<AGStoredFileSha1> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGStoredFileSha1, AGStoredFileSha1Generated> BUILDER = new DbObjectTableBuilder<>("Core", "StoredFileSha1", AGStoredFileSha1::new, AGStoredFileSha1.class);
	static {
		BUILDER.setTitle(CoreI18n.STORED_FILE_SHA_1);
		BUILDER.setPluralTitle(CoreI18n.STORED_FILE_SHA_1S);
	}

	public static final IDbIdField<AGStoredFileSha1> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbByteArrayField<AGStoredFileSha1> HASH = BUILDER.addByteArrayField("hash", o->o.m_hash, (o,v)->o.m_hash=v).setTitle(CoreI18n.HASH).setMaximumLength(20);
	public static final IDbLongField<AGStoredFileSha1> SIZE = BUILDER.addLongField("size", o->o.m_size, (o,v)->o.m_size=v).setTitle(CoreI18n.SIZE).setDefault(0L);
	public static final IDbKey<AGStoredFileSha1> UK_HASH = BUILDER.addUniqueKey("hash", HASH);
	public static final DbObjectTable<AGStoredFileSha1> TABLE = new DbObjectTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGStoredFileSha1> createSelect() {

		return TABLE.createSelect();
	}

	public static AGStoredFileSha1 get(Integer id) {

		return TABLE.get(id);
	}

	public static AGStoredFileSha1 loadByHash(byte[] hash) {

		return TABLE//
				.createSelect()
				.where(HASH.isEqual(hash))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final byte[] getHash() {

		return getValue(HASH);
	}

	public final AGStoredFileSha1 setHash(byte[] value) {

		return setValue(HASH, value);
	}

	public final Long getSize() {

		return getValue(SIZE);
	}

	public final AGStoredFileSha1 setSize(Long value) {

		return setValue(SIZE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbObjectTable<AGStoredFileSha1> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private byte[] m_hash;
	private Long m_size;
}

