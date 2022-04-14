package com.softicar.platform.common.core.entity;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.Optional;
import org.junit.Test;

public class EntityIdFromDisplayStringExtractorTest extends AbstractTest {

	@Test
	public void test() {

		assertId(null, "");
		assertId(null, "123");
		assertId(123, "[123]");
		assertId(123, "abc [123]");
	}

	private void assertId(Integer expectedId, String text) {

		assertEquals(Optional.ofNullable(expectedId), new EntityIdFromDisplayStringExtractor(text).extractId());
	}
}
