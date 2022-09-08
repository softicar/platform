package com.softicar.platform.core.module.file.stored.chunk;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.db.runtime.field.IDbByteArrayField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGStoredFileChunk for
 * database table <i>Core.StoredFileChunk</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGStoredFileChunk extends AbstractDbObject<AGStoredFileChunk> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGStoredFileChunk, AGStoredFileChunk> BUILDER = new DbObjectTableBuilder<>("Core", "StoredFileChunk", AGStoredFileChunk::new, AGStoredFileChunk.class);
	static {
		BUILDER.setTitle(CoreI18n.STORED_FILE_CHUNK);
		BUILDER.setPluralTitle(CoreI18n.STORED_FILE_CHUNKS);
	}

	public static final IDbIdField<AGStoredFileChunk> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGStoredFileChunk, AGStoredFile> FILE = BUILDER.addForeignField("file", o->o.m_file, (o,v)->o.m_file=v, AGStoredFile.ID).setTitle(CoreI18n.FILE).setCascade(true, true).setForeignKeyName("StoredFileChunk_ibfk_1");
	public static final IDbIntegerField<AGStoredFileChunk> CHUNK_INDEX = BUILDER.addIntegerField("chunkIndex", o->o.m_chunkIndex, (o,v)->o.m_chunkIndex=v).setTitle(CoreI18n.CHUNK_INDEX).setDefault(0);
	public static final IDbByteArrayField<AGStoredFileChunk> CHUNK_DATA = BUILDER.addByteArrayField("chunkData", o->o.m_chunkData, (o,v)->o.m_chunkData=v).setTitle(CoreI18n.CHUNK_DATA).setLengthBits(16);
	public static final IDbIntegerField<AGStoredFileChunk> CHUNK_SIZE = BUILDER.addIntegerField("chunkSize", o->o.m_chunkSize, (o,v)->o.m_chunkSize=v).setTitle(CoreI18n.CHUNK_SIZE).setDefault(0);
	public static final IDbKey<AGStoredFileChunk> UK_FILE_CHUNK_INDEX = BUILDER.addUniqueKey("fileChunkIndex", FILE, CHUNK_INDEX);
	public static final DbObjectTable<AGStoredFileChunk> TABLE = new DbObjectTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGStoredFileChunk> createSelect() {

		return TABLE.createSelect();
	}

	public static AGStoredFileChunk get(Integer id) {

		return TABLE.get(id);
	}

	public static AGStoredFileChunk loadByFileAndChunkIndex(AGStoredFile file, Integer chunkIndex) {

		return TABLE//
				.createSelect()
				.where(FILE.equal(file))
				.where(CHUNK_INDEX.equal(chunkIndex))
				.getOne();
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getFileID() {

		return getValueId(FILE);
	}

	public final AGStoredFile getFile() {

		return getValue(FILE);
	}

	public final AGStoredFileChunk setFile(AGStoredFile value) {

		return setValue(FILE, value);
	}

	public final Integer getChunkIndex() {

		return getValue(CHUNK_INDEX);
	}

	public final AGStoredFileChunk setChunkIndex(Integer value) {

		return setValue(CHUNK_INDEX, value);
	}

	public final byte[] getChunkData() {

		return getValue(CHUNK_DATA);
	}

	public final AGStoredFileChunk setChunkData(byte[] value) {

		return setValue(CHUNK_DATA, value);
	}

	public final Integer getChunkSize() {

		return getValue(CHUNK_SIZE);
	}

	public final AGStoredFileChunk setChunkSize(Integer value) {

		return setValue(CHUNK_SIZE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbObjectTable<AGStoredFileChunk> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGStoredFile m_file;
	private Integer m_chunkIndex;
	private byte[] m_chunkData;
	private Integer m_chunkSize;
}

