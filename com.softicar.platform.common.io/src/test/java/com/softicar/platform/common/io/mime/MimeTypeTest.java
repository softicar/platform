package com.softicar.platform.common.io.mime;

import org.junit.Assert;
import org.junit.Test;

public class MimeTypeTest extends Assert {

	@Test
	public void testGetIdentifier() {

		for (MimeType mimeType: MimeType.values()) {

			assertEquals(mimeType.getSuperType() + '/' + mimeType.getSubType(), mimeType.getIdentifier());
		}
	}
}
