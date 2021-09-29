package com.softicar.platform.common.core.singleton;

import com.softicar.platform.common.core.threading.Threads;
import java.util.function.UnaryOperator;
import org.junit.Test;

/**
 * Unit tests for {@link CurrentSingletonSet}.
 * <p>
 * This class implicitly tests {@link SingletonSetThreadLocal}.
 *
 * @author Oliver Richers
 */
public class CurrentSingletonSetTest extends AbstractSingletonTest {

	@Test
	public void testGet() {

		assertSame(singletonSet, CurrentSingletonSet.get());
	}

	@Test(expected = Exception.class)
	public void testSetWithNull() {

		CurrentSingletonSet.set(null);
	}

	@Test
	public void testWithIdentityInheritOperatorAndChildThread() {

		singleton.setInheritOperator(UnaryOperator.identity());
		singleton.set(X);

		ChildRunnable runnable = new ChildRunnable().runAndJoin();

		assertNotSame(singletonSet, runnable.getSingletonSet());
		assertSame(X, runnable.getSingletonSet().getSingletonValue(singleton));
	}

	private static class ChildRunnable implements Runnable {

		private volatile SingletonSet singletonSet;

		@Override
		public void run() {

			this.singletonSet = CurrentSingletonSet.get();
		}

		public SingletonSet getSingletonSet() {

			return singletonSet;
		}

		private ChildRunnable runAndJoin() {

			Thread thread = new Thread(this);
			thread.start();
			Threads.join(thread);

			return this;
		}
	}
}
