package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.common.core.i18n.IDisplayString;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.Test;

public class AbstractTransientListFieldTest extends TransientAccumulativeFieldTestBase<String> {

	private static final List<String> EMPTY = Collections.emptyList();
	private static final List<String> XXX = Arrays.asList("X", "X", "X");
	private static final List<String> XYZ = Arrays.asList("X", "Y", "Z");
	private final TransientListField field = new TransientListField();

	@Test
	public void testGetValue() {

		setCachedValue(objectA, field, XXX);
		setCachedValue(objectB, field, null);
		setCachedValue(objectC, field, null);

		setElementsInStore(objectB, XYZ);

		assertEquals(XXX, field.getValue(objectA));
		assertEquals(XYZ, field.getValue(objectB));
		assertEquals(EMPTY, field.getValue(objectC));
	}

	private class TransientListField extends AbstractTransientListField<TestObject, String> {

		@Override
		protected void loadValues(Set<TestObject> objects, IValueAccumulator<TestObject, String> accumulator) {

			elementsInStore//
				.entrySet()
				.forEach(entry -> accumulator.addAll(entry.getKey(), entry.getValue()));
		}

		@Override
		public IDisplayString getTitle() {

			throw new UnsupportedOperationException();
		}
	}
}
