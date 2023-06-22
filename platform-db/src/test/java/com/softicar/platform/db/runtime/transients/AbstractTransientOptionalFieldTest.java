package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.common.core.i18n.IDisplayString;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.Test;

public class AbstractTransientOptionalFieldTest extends TransientFieldTestBase {

	private static final Optional<String> EMPTY = Optional.empty();
	private static final Optional<String> X = Optional.of("X");
	private static final Optional<String> Y = Optional.of("Y");
	private static final Optional<String> Z = Optional.of("Z");
	private final TransientOptionalField field = new TransientOptionalField();
	private final Map<TestObject, Optional<String>> valuesInStore;

	public AbstractTransientOptionalFieldTest() {

		this.valuesInStore = new IdentityHashMap<>();
	}

	@Test
	public void testGetValue() {

		setCachedValue(objectA, field, X);
		setCachedValue(objectB, field, null);
		setCachedValue(objectC, field, null);

		setOptionalStringValueInStore(objectB, Y);

		assertEquals(X, field.getValue(objectA));
		assertEquals(Y, field.getValue(objectB));
		assertEquals(EMPTY, field.getValue(objectC));
	}

	@Test
	public void testGetValueRetention() {

		// load and cache initial value
		setOptionalStringValueInStore(objectA, X);
		assertEquals(X, field.getValue(objectA));

		// change value in store and assume retention of cached value
		setOptionalStringValueInStore(objectA, Y);
		assertEquals(X, field.getValue(objectA));

		// trigger data-change flag and assume value is reloaded
		TestObject.VALUE.setValue(objectA, "something");
		assertEquals(Y, field.getValue(objectA));

		// retain data-change flag and assume value is reloaded
		setOptionalStringValueInStore(objectA, Z);
		assertEquals(Z, field.getValue(objectA));

		// reset change flag and assume retention of cache value
		TestObject.TABLE.save(objectA);
		setOptionalStringValueInStore(objectA, X);
		assertEquals(Z, field.getValue(objectA));
	}

	private void setOptionalStringValueInStore(TestObject foo, Optional<String> value) {

		valuesInStore.put(foo, value);
	}

	private class TransientOptionalField extends AbstractTransientOptionalField<TestObject, String> {

		@Override
		protected void loadValues(Set<TestObject> objects, IOptionalValueSetter<TestObject, String> setter) {

			valuesInStore//
				.entrySet()
				.stream()
				.forEach(entry -> setter.set(entry.getKey(), entry.getValue()));
		}

		@Override
		public IDisplayString getTitle() {

			throw new UnsupportedOperationException();
		}
	}
}
