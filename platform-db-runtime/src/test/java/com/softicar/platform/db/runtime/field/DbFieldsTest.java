package com.softicar.platform.db.runtime.field;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class DbFieldsTest extends Assert {

	@Test
	public void testGetFallbackTitle() {

		IDbField<?, ?> field = Mockito.mock(IDbField.class);
		Mockito.when(field.getName()).thenReturn("fooBar");
		assertEquals("Foo Bar", DbFields.getFallbackTitle(field).toString());
	}
}
