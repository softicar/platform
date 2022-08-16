package com.softicar.platform.core.module.event;

import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.db.core.transaction.DbTransaction;
import org.junit.Test;

public class SystemEventBuilderTest extends AbstractCoreTest {

	@Test
	public void testEventCreationInSeperateDBConnection() {

		try (var t = new DbTransaction()) {
			insertSystemInformationEvent("Test");
			t.rollback();
		}
		assertEquals(1, AGSystemEvent.TABLE.countAll());
	}
}
