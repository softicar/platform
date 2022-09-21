package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.profiler.DbConnectionTestProfiler;
import com.softicar.platform.db.runtime.record.AbstractDbRecordTest;
import com.softicar.platform.db.runtime.record.DbTinyTestRecord;
import com.softicar.platform.db.runtime.table.logic.save.DbTableRowSaver;
import org.junit.Test;

public class DbTableRowSaverWithDbRecordTest extends AbstractDbRecordTest {

	private final DbConnectionTestProfiler profiler;
	private final DbTinyTestRecord impermanentRecord;
	private final DbTinyTestRecord changedRecord;
	private final DbTinyTestRecord dirtyRecord;

	public DbTableRowSaverWithDbRecordTest() {

		insertTinyRecord(13, 33);
		insertTinyRecord(17, 77);

		this.profiler = new DbConnectionTestProfiler();
		this.impermanentRecord = DbTinyTestRecord.TABLE.getOrCreate(11);
		this.changedRecord = DbTinyTestRecord.TABLE.getOrCreate(13).setValue(44);
		this.dirtyRecord = DbTinyTestRecord.TABLE.getOrCreate(17).setValue(77);

		assertTrue(impermanentRecord.impermanent());
		assertTrue(changedRecord.dataChanged());
		assertTrue(dirtyRecord.dirty());

		DbConnections.setProfiler(profiler);
	}

	@Test
	public void testSaveWithRecordAndWithLazyMode() {

		new DbTableRowSaver<>(DbTinyTestRecord.TABLE)//
			.setLazyMode(true)
			.addRow(impermanentRecord)
			.addRow(changedRecord)
			.addRow(dirtyRecord)
			.save();

		profiler.assertStatementCount(3);
		profiler.assertStatementMatches(0, "SELECT.*", impermanentRecord.getKey(), changedRecord.getKey());
		profiler.assertStatementMatches(1, "INSERT.*", impermanentRecord.getKey(), impermanentRecord.getValue());
		profiler.assertStatementMatches(2, "UPDATE.*", changedRecord.getValue(), changedRecord.getKey());

		assertFalse(impermanentRecord.impermanent());
		assertFalse(changedRecord.dataChanged());
		assertFalse(dirtyRecord.dirty());
	}
}
