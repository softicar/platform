package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.common.container.pair.Pair;
import com.softicar.platform.common.core.i18n.IDisplayString;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.junit.Test;

public class AbstractTransientMapFieldTest extends TransientAccumulativeFieldTestBase<Pair<String, Integer>> {

	private final TransientMapField field = new TransientMapField();

	@Test
	public void testGetValue() {

		setElementsInStore(objectA, Arrays.asList(new Pair<>("X", 1), new Pair<>("Y", 2)));

		Map<String, Integer> value = field.getValue(objectA);

		assertNotNull(value);
		assertEquals(2, value.size());
		assertEquals(1, value.get("X").intValue());
		assertEquals(2, value.get("Y").intValue());
	}

	private class TransientMapField extends AbstractTransientMapField<TestObject, String, Integer> {

		public TransientMapField() {

			super(TreeMap::new);
		}

		@Override
		protected void loadValues(Set<TestObject> objects, IValueAccumulator<TestObject, Pair<String, Integer>> accumulator) {

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
