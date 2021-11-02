package com.softicar.platform.emf.table.listener;

import com.softicar.platform.common.core.singleton.SingletonSetScope;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.junit.Test;

public class EmfSaveHookWrapperTest extends AbstractDbTest {

	private final Collection<EmfTestObject> dummyTableRowCollection;
	private final Collection<String> hookCalls;
	private int hookInstanceCounter;

	public EmfSaveHookWrapperTest() {

		this.dummyTableRowCollection = Collections.emptyList();
		this.hookCalls = new ArrayList<>();
		this.hookInstanceCounter = 0;
	}

	@Test
	public void testSingleScope() {

		var wrapper = new EmfSaveHookWrapper<>(this::createHookInstance);

		wrapper.beforeSave(dummyTableRowCollection);
		wrapper.afterSave(dummyTableRowCollection);
		wrapper.beforeSave(dummyTableRowCollection);
		wrapper.afterSave(dummyTableRowCollection);

		assertEquals("[before-0, after-0, before-0, after-0]", hookCalls.toString());
	}

	@Test
	public void testMultiScope() {

		var wrapper = new EmfSaveHookWrapper<>(this::createHookInstance);

		wrapper.beforeSave(dummyTableRowCollection);
		wrapper.afterSave(dummyTableRowCollection);
		try (SingletonSetScope scope = new SingletonSetScope()) {
			wrapper.beforeSave(dummyTableRowCollection);
			wrapper.afterSave(dummyTableRowCollection);
		}
		wrapper.beforeSave(dummyTableRowCollection);
		wrapper.afterSave(dummyTableRowCollection);

		assertEquals("[before-0, after-0, before-1, after-1, before-0, after-0]", hookCalls.toString());
	}

	private TestHook createHookInstance() {

		return new TestHook(hookInstanceCounter++);
	}

	private class TestHook implements IEmfSaveHook<EmfTestObject> {

		private final int instanceId;

		public TestHook(int instanceId) {

			this.instanceId = instanceId;
		}

		@Override
		public void beforeSave(Collection<EmfTestObject> tableRows) {

			assertSame(dummyTableRowCollection, tableRows);
			hookCalls.add("before-" + instanceId);
		}

		@Override
		public void afterSave(Collection<EmfTestObject> tableRows) {

			assertSame(dummyTableRowCollection, tableRows);
			hookCalls.add("after-" + instanceId);
		}
	}
}
