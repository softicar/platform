package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.DevNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import org.junit.Test;

public class AbstractTransientObjectFieldTest extends TransientFieldTestBase {

	private static final String DEFAULT = "DEFAULT";
	private static final String X = "X";
	private static final String Y = "Y";
	private static final String Z = "Z";
	private final TransientField field;
	private final Map<TestObject, String> valuesInStore;
	private BiConsumer<Set<TestObject>, IValueSetter<TestObject, String>> currentLoader;

	public AbstractTransientObjectFieldTest() {

		this.field = new TransientField();
		this.valuesInStore = new IdentityHashMap<>();
		this.currentLoader = (objects, setter) -> {
			valuesInStore//
				.entrySet()
				.forEach(entry -> setter.set(entry.getKey(), entry.getValue()));
		};
	}

	@Test
	public void testGetValue() {

		setCachedValue(objectA, field, X);
		setCachedValue(objectB, field, null);
		setCachedValue(objectC, field, null);

		setStringValueInStore(objectB, Y);

		assertEquals(X, field.getValue(objectA));
		assertEquals(Y, field.getValue(objectB));
		assertEquals(DEFAULT, field.getValue(objectC));
	}

	@Test
	public void testInvalidateAll() {

		setCachedValue(objectA, field, X);
		setCachedValue(objectB, field, DEFAULT);
		setCachedValue(objectC, field, null);

		field.invalidateAll(Arrays.asList(objectA, objectB, objectC));

		assertEquals(DEFAULT, field.getValue(objectA));
		assertEquals(DEFAULT, field.getValue(objectB));
		assertEquals(DEFAULT, field.getValue(objectC));
	}

	@Test
	public void testPrefetchAll() {

		setCachedValue(objectA, field, X);
		setCachedValue(objectB, field, Z);
		setCachedValue(objectC, field, null);

		setStringValueInStore(objectA, Z);
		setStringValueInStore(objectB, (String) null);
		setStringValueInStore(objectC, Z);

		field.prefetchAll(Arrays.asList(objectA, objectB, objectC));

		assertEquals(X, field.getValue(objectA));
		assertEquals(Z, field.getValue(objectB));
		assertEquals(Z, field.getValue(objectC));
	}

	@Test
	public void testReloadAll() {

		setCachedValue(objectA, field, X);
		setCachedValue(objectB, field, Y);
		setCachedValue(objectC, field, null);

		setStringValueInStore(objectA, Z);
		setStringValueInStore(objectB, (String) null);
		setStringValueInStore(objectC, Z);

		field.reloadAll(Arrays.asList(objectA, objectB, objectC));

		assertEquals(Z, field.getValue(objectA));
		assertEquals(DEFAULT, field.getValue(objectB));
		assertEquals(Z, field.getValue(objectC));
	}

	@Test
	public void testGetValueWithNullValue() {

		// set any value to null
		currentLoader = (objects, setter) -> {
			for (TestObject object: objects) {
				setter.set(object, null);
			}
		};

		assertEquals(DEFAULT, field.getValue(objectA));
	}

	@Test
	public void testReloadAllWithOverzealousValueLoader() {

		// overzealous loader: set all objects to Z
		currentLoader = (objects, setter) -> {
			setter.set(objectA, Z);
			setter.set(objectB, Z);
			setter.set(objectC, Z);
		};

		// initialize values
		setCachedValue(objectA, field, X);
		setCachedValue(objectB, field, Y);
		setCachedValue(objectC, field, null);

		// reload objectB
		field.reloadAll(Collections.singleton(objectB));

		// assert that only objectB is updated
		assertEquals(X, field.getValue(objectA));
		assertEquals(Z, field.getValue(objectB));
		assertNull(getCachedValue(objectC));
	}

	@Test
	public void testReloadAllWithThrowingValueLoader() {

		// simulate an exception in the middle of loading
		currentLoader = (objects, setter) -> {
			setter.set(objectA, Z);
			throw new RuntimeException();
		};

		// initialize values
		setCachedValue(objectA, field, X);
		setCachedValue(objectB, field, Y);
		setCachedValue(objectC, field, null);

		try {
			field.reloadAll(Arrays.asList(objectA, objectB, objectC));
		} catch (RuntimeException exception) {
			// this exception is expected
			DevNull.swallow(exception);
		}

		// assert that initial values are retained
		assertEquals(X, field.getValue(objectA));
		assertEquals(Y, field.getValue(objectB));
		assertNull(getCachedValue(objectC));
	}

	private String getCachedValue(TestObject object) {

		return TestObject.TABLE.getCache().getTransientValueCache().getTransientValue(object, field);
	}

	private void setStringValueInStore(TestObject foo, String value) {

		valuesInStore.put(foo, value);
	}

	private class TransientField extends AbstractTransientObjectField<TestObject, String> {

		public TransientField() {

			super(TransientFieldValueTypes.getString());
		}

		@Override
		protected void loadValues(Set<TestObject> objects, IValueSetter<TestObject, String> setter) {

			currentLoader.accept(objects, setter);
		}

		@Override
		protected String getDefaultValue() {

			return DEFAULT;
		}

		@Override
		public IDisplayString getTitle() {

			throw new UnsupportedOperationException();
		}
	}
}
