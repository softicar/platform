package com.softicar.platform.emf.table.listener;

import com.softicar.platform.common.core.singleton.SingletonSetScope;
import com.softicar.platform.db.runtime.table.listener.IDbTableRowNotificationSet;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Test;
import org.mockito.Mockito;

public class EmfCommitHookWrapperTest extends AbstractDbTest {

	private final IDbTableRowNotificationSet<EmfTestObject> dummyNotificationSet;
	private final Collection<String> hookCalls;
	private int hookInstanceCounter;

	public EmfCommitHookWrapperTest() {

		this.dummyNotificationSet = Mockito.mock(IDbTableRowNotificationSet.class);
		this.hookCalls = new ArrayList<>();
		this.hookInstanceCounter = 0;
	}

	@Test
	public void testSingleScope() {

		var wrapper = new EmfCommitHookWrapper<>(this::createHookInstance);

		wrapper.beforeCommit(dummyNotificationSet);
		wrapper.afterCommit(dummyNotificationSet);
		wrapper.beforeCommit(dummyNotificationSet);
		wrapper.afterCommit(dummyNotificationSet);

		assertEquals("[before-0, after-0, before-0, after-0]", hookCalls.toString());
	}

	@Test
	public void testMultiScope() {

		var wrapper = new EmfCommitHookWrapper<>(this::createHookInstance);

		wrapper.beforeCommit(dummyNotificationSet);
		wrapper.afterCommit(dummyNotificationSet);
		try (SingletonSetScope scope = new SingletonSetScope()) {
			wrapper.beforeCommit(dummyNotificationSet);
			wrapper.afterCommit(dummyNotificationSet);
		}
		wrapper.beforeCommit(dummyNotificationSet);
		wrapper.afterCommit(dummyNotificationSet);

		assertEquals("[before-0, after-0, before-1, after-1, before-0, after-0]", hookCalls.toString());
	}

	private TestHook createHookInstance() {

		return new TestHook(hookInstanceCounter++);
	}

	private class TestHook implements IEmfCommitHook<EmfTestObject> {

		private final int instanceId;

		public TestHook(int instanceId) {

			this.instanceId = instanceId;
		}

		@Override
		public void beforeCommit(IDbTableRowNotificationSet<EmfTestObject> notificationSet) {

			assertSame(dummyNotificationSet, notificationSet);
			hookCalls.add("before-" + instanceId);
		}

		@Override
		public void afterCommit(IDbTableRowNotificationSet<EmfTestObject> notificationSet) {

			assertSame(dummyNotificationSet, notificationSet);
			hookCalls.add("after-" + instanceId);
		}
	}
}
