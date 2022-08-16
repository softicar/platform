package com.softicar.platform.core.module.event;

import com.softicar.platform.core.module.test.AbstractCoreTest;
import com.softicar.platform.db.core.transaction.DbTransaction;
import org.junit.Test;

public class SystemEventBuilderTest extends AbstractCoreTest {

	@Test
	public void testEventCreationInSeperateDBConnection() {

		AGSystemEvent event;
		try (var t = new DbTransaction()) {
			event = insertSystemInformationEvent("Test");
			t.rollback();
		}
		var events = AGSystemEvent.TABLE.loadAll();
		assertEquals(1, events.size());
		assertSame(event, events.get(0));
	}
}
