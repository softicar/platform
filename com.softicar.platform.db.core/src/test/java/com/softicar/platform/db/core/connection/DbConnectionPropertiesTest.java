package com.softicar.platform.db.core.connection;

import java.util.Properties;
import org.junit.Assert;
import org.junit.Test;

public class DbConnectionPropertiesTest extends Assert {

	@Test
	public void testCopyProperties() {

		DbConnectionProperties connectionProperties = new DbConnectionProperties();
		connectionProperties.setUsername("foo");
		connectionProperties.setPassword("bar");
		connectionProperties.setProperty("baz", "42");

		Properties properties = new Properties();
		connectionProperties.copyPropertiesTo(properties);

		assertEquals("foo", properties.get("user"));
		assertEquals("bar", properties.get("password"));
		assertEquals("42", properties.get("baz"));
	}

	@Test
	public void testCopyPropertiesToWithUndefinedUsernameAndPassword() {

		Properties properties = new Properties();
		new DbConnectionProperties().copyPropertiesTo(properties);

		assertEquals("", properties.get("user"));
		assertEquals("", properties.get("password"));
	}
}
