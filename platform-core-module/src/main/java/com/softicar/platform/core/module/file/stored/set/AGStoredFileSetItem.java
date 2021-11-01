package com.softicar.platform.core.module.file.stored.set;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

/**
 * This is the automatically generated class AGStoredFileSetItem for
 * database table <i>Core.StoredFileSetItem</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGStoredFileSetItem extends AbstractDbRecord<AGStoredFileSetItem, Tuple2<AGStoredFileSet, AGStoredFile>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGStoredFileSetItem, AGStoredFileSetItem, Tuple2<AGStoredFileSet, AGStoredFile>> BUILDER = new DbTableBuilder<>("Core", "StoredFileSetItem", AGStoredFileSetItem::new, AGStoredFileSetItem.class);
	static {
		BUILDER.setTitle(CoreI18n.STORED_FILE_SET_ITEM);
		BUILDER.setPluralTitle(CoreI18n.STORED_FILE_SET_ITEMS);
	}

	public static final IDbForeignField<AGStoredFileSetItem, AGStoredFileSet> FILE_SET = BUILDER.addForeignField("fileSet", o->o.m_fileSet, (o,v)->o.m_fileSet=v, AGStoredFileSet.ID).setTitle(CoreI18n.FILE_SET);
	public static final IDbForeignField<AGStoredFileSetItem, AGStoredFile> FILE = BUILDER.addForeignField("file", o->o.m_file, (o,v)->o.m_file=v, AGStoredFile.ID).setTitle(CoreI18n.FILE);
	public static final IDbTableKey<AGStoredFileSetItem, Tuple2<AGStoredFileSet, AGStoredFile>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(FILE_SET, FILE));
	public static final IDbKey<AGStoredFileSetItem> IK_FILE = BUILDER.addIndexKey("file", FILE);
	public static final DbRecordTable<AGStoredFileSetItem, Tuple2<AGStoredFileSet, AGStoredFile>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGStoredFileSetItem() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getFileSetID() {

		return getValueId(FILE_SET);
	}

	public final AGStoredFileSet getFileSet() {

		return getValue(FILE_SET);
	}

	public final Integer getFileID() {

		return getValueId(FILE);
	}

	public final AGStoredFile getFile() {

		return getValue(FILE);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGStoredFileSetItem, Tuple2<AGStoredFileSet, AGStoredFile>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGStoredFileSet m_fileSet;
	private AGStoredFile m_file;
}

