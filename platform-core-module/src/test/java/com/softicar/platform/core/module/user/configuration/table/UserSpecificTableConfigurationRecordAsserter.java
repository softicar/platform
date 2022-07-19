package com.softicar.platform.core.module.user.configuration.table;

import com.softicar.platform.common.testing.Asserts;
import com.softicar.platform.core.module.user.AGUser;
import java.util.List;

class UserSpecificTableConfigurationRecordAsserter extends Asserts {

	private final List<AGUserSpecificTableConfiguration> records;
	private AGUserSpecificTableConfiguration record;
	private int index;

	public UserSpecificTableConfigurationRecordAsserter(List<AGUserSpecificTableConfiguration> records) {

		this.records = records;
		this.record = null;
		this.index = 0;
	}

	public UserSpecificTableConfigurationRecordAsserter nextRecord() {

		this.record = records.get(index);
		this.index++;
		return this;
	}

	public UserSpecificTableConfigurationRecordAsserter assertTablePathHash(String expected) {

		assertEquals(expected, record.getTablePathHash());
		return this;
	}

	public UserSpecificTableConfigurationRecordAsserter assertUser(AGUser expected) {

		assertEquals(expected, record.getUser());
		return this;
	}

	public UserSpecificTableConfigurationRecordAsserter assertColumnTitlesHash(String expected) {

		assertEquals(expected, record.getColumnTitlesHash());
		return this;
	}

	public UserSpecificTableConfigurationRecordAsserter assertSerialization(String expected) {

		String actual = record.getSerialization();
		if (!expected.equals(actual)) {
			String message =
					"Unexpected serialization. Persistent configurations in existing installations would become incompatible.\nExpected: %s\nActual: %s"
						.formatted(expected, actual);
			throw new AssertionError(message);
		}
		return this;
	}

	public void assertNoMoreRecords() {

		assertTrue(records.size() <= index);
	}
}
