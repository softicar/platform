package com.softicar.platform.db.runtime.field;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;
import org.mockito.Mockito;

public class DbFieldsTest extends AbstractTest {

	@Test
	public void testGetFallbackTitle() {

		IDbField<?, ?> field = Mockito.mock(IDbField.class);
		Mockito.when(field.getName()).thenReturn("fooBar");
		assertEquals("Foo Bar", DbFields.getFallbackTitle(field).toString());
	}
}
