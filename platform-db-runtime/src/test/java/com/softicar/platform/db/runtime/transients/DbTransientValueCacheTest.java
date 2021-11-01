package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.object.DbTestObject;
import org.junit.Assert;
import org.junit.Test;

public class DbTransientValueCacheTest extends Assert {

	private static final Attribute ATTRIBUTE = new Attribute();
	private static final String A = "A";
	private static final String B = "B";
	private final DbTransientValueCache<DbTestObject> cache;
	private final DbTestObject object;
	private final DbTestObject otherObject;

	public DbTransientValueCacheTest() {

		this.cache = new DbTransientValueCache<>();
		this.object = new DbTestObject();
		this.otherObject = new DbTestObject();
	}

	@Test
	public void testGetTransientValueWithoutCallingSet() {

		assertNull(cache.getTransientValue(object, ATTRIBUTE));
	}

	@Test
	public void testGetTransientValueWithCallingSet() {

		cache.setTransientValue(object, ATTRIBUTE, A);
		cache.setTransientValue(otherObject, ATTRIBUTE, B);

		assertEquals(A, cache.getTransientValue(object, ATTRIBUTE));
		assertEquals(B, cache.getTransientValue(otherObject, ATTRIBUTE));
	}

	@Test
	public void testGetTransientValueWithCallingInvalidate() {

		cache.setTransientValue(object, ATTRIBUTE, A);
		cache.setTransientValue(otherObject, ATTRIBUTE, B);

		cache.invalidate(object);

		assertNull(cache.getTransientValue(object, ATTRIBUTE));
		assertEquals(B, cache.getTransientValue(otherObject, ATTRIBUTE));
	}

	@Test
	public void testGetTransientValueWithCallingInvalidateAll() {

		cache.setTransientValue(object, ATTRIBUTE, A);
		cache.setTransientValue(otherObject, ATTRIBUTE, B);

		cache.invalidateAll();

		assertNull(cache.getTransientValue(object, ATTRIBUTE));
		assertNull(cache.getTransientValue(otherObject, ATTRIBUTE));
	}

	private static class Attribute implements IDbTransientField<DbTestObject, String> {

		@Override
		public String getValue(DbTestObject object) {

			throw new UnsupportedOperationException();
		}

		@Override
		public IDisplayString getTitle() {

			throw new UnsupportedOperationException();
		}
	}
}
