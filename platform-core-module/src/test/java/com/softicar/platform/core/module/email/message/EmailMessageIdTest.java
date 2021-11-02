package com.softicar.platform.core.module.email.message;

import com.softicar.platform.common.core.utils.DevNull;
import org.junit.Assert;
import org.junit.Test;

public class EmailMessageIdTest extends Assert {

	@Test
	public void testWithNormalId() {

		EmailMessageId messageId = new EmailMessageId("<foo@example.com>");

		assertEquals("<foo@example.com>", messageId.getWithAngleBrackets());
		assertEquals("foo@example.com", messageId.getWithoutAngleBrackets());
	}

	@Test
	public void testWithMissingAngleBrackets() {

		EmailMessageId messageId = new EmailMessageId("foo@example.com");

		assertEquals("<foo@example.com>", messageId.getWithAngleBrackets());
		assertEquals("foo@example.com", messageId.getWithoutAngleBrackets());
	}

	@Test(expected = IllegalEmailMessageIdException.class)
	public void testWithNull() {

		DevNull.swallow(new EmailMessageId((String) null));
	}

	@Test(expected = IllegalEmailMessageIdException.class)
	public void testWithEmptyId() {

		DevNull.swallow(new EmailMessageId(""));
	}

	@Test(expected = IllegalEmailMessageIdException.class)
	public void testWithEmptyIdInAngleBracket() {

		DevNull.swallow(new EmailMessageId("<>"));
	}
}
