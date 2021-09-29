package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.common.core.i18n.IDisplayString;
import java.util.Set;
import org.junit.Test;

public class AbstractTransientIntegerFieldTest extends TransientAccumulativeFieldTestBase<Integer> {

	private static final Integer _0 = 0;
	private static final Integer _2 = 2;
	private static final Integer _7 = 7;
	private static final Integer _9 = 9;
	private final TransientIntegerField field = new TransientIntegerField();

	@Test
	public void test() {

		setCachedValue(objectA, field, _2);
		setCachedValue(objectB, field, null);
		setCachedValue(objectC, field, null);

		setElementsInStore(objectA, _7);
		setElementsInStore(objectB, _2, _7);

		assertEquals(_2, field.getValue(objectA));
		assertEquals(_9, field.getValue(objectB));
		assertEquals(_0, field.getValue(objectC));
	}

	private class TransientIntegerField extends AbstractTransientIntegerField<TestObject> {

		@Override
		protected void loadValues(Set<TestObject> objects, IValueAccumulator<TestObject, Integer> output) {

			elementsInStore//
				.entrySet()
				.forEach(entry -> output.addAll(entry.getKey(), entry.getValue()));
		}

		@Override
		public IDisplayString getTitle() {

			throw new UnsupportedOperationException();
		}
	}
}
