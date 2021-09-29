package com.softicar.platform.db.core.replication.consistency.comparer;

import com.softicar.platform.db.core.statement.DbStatement;
import java.util.Arrays;
import org.junit.Test;

public class MasterSlaveMismatchRepairerTest extends AbstractMasterSlaveComparatorTest {

	@Test
	public void testWithRowMissingOnSlave() {

		TableRow tableRow = new TableRow(tableDefinitionA, Arrays.asList("1", "foo"));

		DbStatement statement = new MasterSlaveMismatchRepairer(tableNameA, tableRow.getPrimaryKeyRow(), tableRow, null).getRepairStatement();

		assertEquals("INSERT INTO `PUBLIC`.`A` (`id`, `text`) VALUES (?,?)", statement.getText());
		assertEquals(Arrays.asList("1", "foo"), statement.getParameters());
	}

	@Test
	public void testWithRowMissingOnMaster() {

		TableRow tableRow = new TableRow(tableDefinitionA, Arrays.asList("1", "foo"));
		DbStatement statement = new MasterSlaveMismatchRepairer(tableNameA, tableRow.getPrimaryKeyRow(), null, tableRow).getRepairStatement();

		assertEquals("DELETE FROM `PUBLIC`.`A` WHERE (`id`) = (?)", statement.getText());
		assertEquals(Arrays.asList("1"), statement.getParameters());
	}

	@Test
	public void testWithPayloadMismatch() {

		TableRow masterRow = new TableRow(tableDefinitionA, Arrays.asList("1", "foo"));
		TableRow slaveRow = new TableRow(tableDefinitionB, Arrays.asList("1", "bar"));
		DbStatement statement = new MasterSlaveMismatchRepairer(tableNameA, masterRow.getPrimaryKeyRow(), masterRow, slaveRow).getRepairStatement();

		assertEquals("UPDATE `PUBLIC`.`A` SET `text` = ? WHERE (`id`) = (?)", statement.getText());
		assertEquals(Arrays.asList("foo", "1"), statement.getParameters());
	}
}
