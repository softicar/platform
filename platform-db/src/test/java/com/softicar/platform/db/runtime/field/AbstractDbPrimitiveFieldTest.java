package com.softicar.platform.db.runtime.field;

import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.runtime.table.IDbTableBuilder;
import com.softicar.platform.db.sql.type.SqlFieldType;
import org.junit.Test;
import org.mockito.Mockito;

public class AbstractDbPrimitiveFieldTest extends AbstractTest {

	private static final String A = "A";
	private final Field field;

	public AbstractDbPrimitiveFieldTest() {

		this.field = new Field(Mockito.mock(IDbTableBuilder.class));
	}

	@Test
	public void testGetDefault() {

		field.setDefault(A);
		assertEquals(A, field.getDefault());
	}

	@Test
	public void testGetDefaultWithNull() {

		field.setDefault(null);
		assertNull(field.getDefault());
	}

	@Test
	public void testGetDefaultWithoutDefaultAndNullable() {

		field.setNullable();
		assertNull(field.getDefault());
	}

	@Test
	public void testGetDefaultWithoutDefaultAndNotNullable() {

		assertNull(field.getDefault());
	}

	// ------------------------------ private ------------------------------ //

	private class Field extends AbstractDbPrimitiveField<Void, String, Field> {

		public Field(IDbTableBuilder<Void, Void> builder) {

			super(builder, SqlFieldType.STRING, "", null, null);
		}

		@Override
		protected Field getThis() {

			return this;
		}
	}
}
