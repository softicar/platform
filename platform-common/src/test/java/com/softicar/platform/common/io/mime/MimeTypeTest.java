package com.softicar.platform.common.io.mime;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

public class MimeTypeTest extends AbstractTest {

	@Test
	public void testGetIdentifier() {

		for (MimeType mimeType: MimeType.values()) {

			assertEquals(mimeType.getSuperType() + '/' + mimeType.getSubType(), mimeType.getIdentifier());
		}
	}
}
