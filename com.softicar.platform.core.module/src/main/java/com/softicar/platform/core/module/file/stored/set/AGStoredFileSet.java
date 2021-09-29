package com.softicar.platform.core.module.file.stored.set;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.field.IDbByteArrayField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGStoredFileSet for
 * database table <i>Core.StoredFileSet</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGStoredFileSet extends AbstractAGStoredFileSet {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGStoredFileSet, AGStoredFileSet> BUILDER = new DbObjectTableBuilder<>("Core", "StoredFileSet", AGStoredFileSet::new, AGStoredFileSet.class);
	static {
		BUILDER.setTitle(CoreI18n.STORED_FILE_SET);
		BUILDER.setPluralTitle(CoreI18n.STORED_FILE_SETS);
	}

	public static final IDbIdField<AGStoredFileSet> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbByteArrayField<AGStoredFileSet> HASH = BUILDER.addByteArrayField("hash", o->o.m_hash, (o,v)->o.m_hash=v).setTitle(CoreI18n.HASH).setMaximumLength(16);
	public static final IDbKey<AGStoredFileSet> UK_HASH = BUILDER.addUniqueKey("hash", HASH);
	public static final AGStoredFileSetTable TABLE = new AGStoredFileSetTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGStoredFileSet> createSelect() {

		return TABLE.createSelect();
	}

	public static AGStoredFileSet get(Integer id) {

		return TABLE.get(id);
	}

	public static AGStoredFileSet loadByHash(byte[] hash) {

		return TABLE//
				.createSelect()
				.where(HASH.equal(hash))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final byte[] getHash() {

		return getValue(HASH);
	}

	public final AGStoredFileSet setHash(byte[] value) {

		return setValue(HASH, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGStoredFileSetTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private byte[] m_hash;
}

