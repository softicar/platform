package com.softicar.platform.common.core.uuid;

import org.junit.Assert;
import org.junit.Test;

public class UuidsTest extends Assert {

	@Test
	public void isUuid() {

		assertFalse(Uuids.isUuid(null));
		assertFalse(Uuids.isUuid(""));
		assertFalse(Uuids.isUuid("foo"));
		assertFalse(Uuids.isUuid("e8609614-c39a-424c-9d69-05aad3f474"));
		assertTrue(Uuids.isUuid("e8609614-c39a-424c-9d69-05aad3f474a4"));
	}
}
