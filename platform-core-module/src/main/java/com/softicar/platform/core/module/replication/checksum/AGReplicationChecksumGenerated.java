package com.softicar.platform.core.module.replication.checksum;

import com.softicar.platform.common.container.tuple.Tuple3;
import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.db.runtime.field.IDbDayTimeField;
import com.softicar.platform.db.runtime.field.IDbFloatField;
import com.softicar.platform.db.runtime.field.IDbIntegerField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.DbTableKeyFactory;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.key.IDbTableKey;
import com.softicar.platform.db.runtime.record.AbstractDbRecord;
import com.softicar.platform.db.runtime.record.DbRecordTable;
import com.softicar.platform.db.runtime.table.DbTableBuilder;

/**
 * This is the automatically generated class AGReplicationChecksum for
 * database table <i>Core.ReplicationChecksum</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGReplicationChecksumGenerated extends AbstractDbRecord<AGReplicationChecksum, Tuple3<String, String, Integer>> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbTableBuilder<AGReplicationChecksum, AGReplicationChecksumGenerated, Tuple3<String, String, Integer>> BUILDER = new DbTableBuilder<>("Core", "ReplicationChecksum", AGReplicationChecksum::new, AGReplicationChecksum.class);
	static {
		BUILDER.setTitle(CoreI18n.REPLICATION_CHECKSUM);
		BUILDER.setPluralTitle(CoreI18n.REPLICATION_CHECKSUMS);
	}

	public static final IDbStringField<AGReplicationChecksum> DATABASE_NAME = BUILDER.addStringField("db", o->o.m_databaseName, (o,v)->o.m_databaseName=v).setTitle(CoreI18n.DATABASE_NAME).setMaximumLength(64);
	public static final IDbStringField<AGReplicationChecksum> TABLE_NAME = BUILDER.addStringField("tbl", o->o.m_tableName, (o,v)->o.m_tableName=v).setTitle(CoreI18n.TABLE_NAME).setMaximumLength(64);
	public static final IDbIntegerField<AGReplicationChecksum> CHUNK = BUILDER.addIntegerField("chunk", o->o.m_chunk, (o,v)->o.m_chunk=v).setTitle(CoreI18n.CHUNK);
	public static final IDbFloatField<AGReplicationChecksum> CHUNK_TIME = BUILDER.addFloatField("chunk_time", o->o.m_chunkTime, (o,v)->o.m_chunkTime=v).setTitle(CoreI18n.CHUNK_TIME).setNullable().setDefault(null);
	public static final IDbStringField<AGReplicationChecksum> CHUNK_INDEX = BUILDER.addStringField("chunk_index", o->o.m_chunkIndex, (o,v)->o.m_chunkIndex=v).setTitle(CoreI18n.CHUNK_INDEX).setNullable().setDefault(null).setMaximumLength(200);
	public static final IDbStringField<AGReplicationChecksum> LOWER_BOUNDARY = BUILDER.addStringField("lower_boundary", o->o.m_lowerBoundary, (o,v)->o.m_lowerBoundary=v).setTitle(CoreI18n.LOWER_BOUNDARY).setNullable().setDefault(null).setLengthBits(16);
	public static final IDbStringField<AGReplicationChecksum> UPPER_BOUNDARY = BUILDER.addStringField("upper_boundary", o->o.m_upperBoundary, (o,v)->o.m_upperBoundary=v).setTitle(CoreI18n.UPPER_BOUNDARY).setNullable().setDefault(null).setLengthBits(16);
	public static final IDbStringField<AGReplicationChecksum> CHECKSUM = BUILDER.addStringField("this_crc", o->o.m_checksum, (o,v)->o.m_checksum=v).setTitle(CoreI18n.CHECKSUM).setMaximumLength(40);
	public static final IDbIntegerField<AGReplicationChecksum> ROW_COUNT = BUILDER.addIntegerField("this_cnt", o->o.m_rowCount, (o,v)->o.m_rowCount=v).setTitle(CoreI18n.ROW_COUNT);
	public static final IDbStringField<AGReplicationChecksum> MASTER_SERVER_CHECKSUM = BUILDER.addStringField("master_crc", o->o.m_masterServerChecksum, (o,v)->o.m_masterServerChecksum=v).setTitle(CoreI18n.MASTER_SERVER_CHECKSUM).setNullable().setDefault(null).setMaximumLength(40);
	public static final IDbIntegerField<AGReplicationChecksum> MASTER_SERVER_ROW_COUNT = BUILDER.addIntegerField("master_cnt", o->o.m_masterServerRowCount, (o,v)->o.m_masterServerRowCount=v).setTitle(CoreI18n.MASTER_SERVER_ROW_COUNT).setNullable().setDefault(null);
	public static final IDbDayTimeField<AGReplicationChecksum> TIMESTAMP = BUILDER.addDayTimeField("ts", o->o.m_timestamp, (o,v)->o.m_timestamp=v).setTitle(CoreI18n.TIMESTAMP).setDefaultNow().setOnUpdateNow().setTimestamp();
	public static final IDbTableKey<AGReplicationChecksum, Tuple3<String, String, Integer>> PRIMARY_KEY = BUILDER.setPrimaryKey(DbTableKeyFactory.createKey(DATABASE_NAME, TABLE_NAME, CHUNK));
	public static final IDbKey<AGReplicationChecksum> IK_TIMESTAMP_DATABASE_NAME_TABLE_NAME = BUILDER.addIndexKey("timestampDatabaseNameTableName", TIMESTAMP, DATABASE_NAME, TABLE_NAME);
	public static final DbRecordTable<AGReplicationChecksum, Tuple3<String, String, Integer>> TABLE = new DbRecordTable<>(BUILDER);
	// @formatter:on

	// -------------------------------- CONSTRUCTORS -------------------------------- //

	protected AGReplicationChecksumGenerated() {

		// protected
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final String getDatabaseName() {

		return getValue(DATABASE_NAME);
	}

	public final String getTableName() {

		return getValue(TABLE_NAME);
	}

	public final Integer getChunk() {

		return getValue(CHUNK);
	}

	public final Float getChunkTime() {

		return getValue(CHUNK_TIME);
	}

	public final AGReplicationChecksum setChunkTime(Float value) {

		return setValue(CHUNK_TIME, value);
	}

	public final String getChunkIndex() {

		return getValue(CHUNK_INDEX);
	}

	public final AGReplicationChecksum setChunkIndex(String value) {

		return setValue(CHUNK_INDEX, value);
	}

	public final String getLowerBoundary() {

		return getValue(LOWER_BOUNDARY);
	}

	public final AGReplicationChecksum setLowerBoundary(String value) {

		return setValue(LOWER_BOUNDARY, value);
	}

	public final String getUpperBoundary() {

		return getValue(UPPER_BOUNDARY);
	}

	public final AGReplicationChecksum setUpperBoundary(String value) {

		return setValue(UPPER_BOUNDARY, value);
	}

	public final String getChecksum() {

		return getValue(CHECKSUM);
	}

	public final AGReplicationChecksum setChecksum(String value) {

		return setValue(CHECKSUM, value);
	}

	public final Integer getRowCount() {

		return getValue(ROW_COUNT);
	}

	public final AGReplicationChecksum setRowCount(Integer value) {

		return setValue(ROW_COUNT, value);
	}

	public final String getMasterServerChecksum() {

		return getValue(MASTER_SERVER_CHECKSUM);
	}

	public final AGReplicationChecksum setMasterServerChecksum(String value) {

		return setValue(MASTER_SERVER_CHECKSUM, value);
	}

	public final Integer getMasterServerRowCount() {

		return getValue(MASTER_SERVER_ROW_COUNT);
	}

	public final AGReplicationChecksum setMasterServerRowCount(Integer value) {

		return setValue(MASTER_SERVER_ROW_COUNT, value);
	}

	public final DayTime getTimestamp() {

		return getValue(TIMESTAMP);
	}

	public final AGReplicationChecksum setTimestamp(DayTime value) {

		return setValue(TIMESTAMP, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final DbRecordTable<AGReplicationChecksum, Tuple3<String, String, Integer>> table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private String m_databaseName;
	private String m_tableName;
	private Integer m_chunk;
	private Float m_chunkTime;
	private String m_chunkIndex;
	private String m_lowerBoundary;
	private String m_upperBoundary;
	private String m_checksum;
	private Integer m_rowCount;
	private String m_masterServerChecksum;
	private Integer m_masterServerRowCount;
	private DayTime m_timestamp;
}

