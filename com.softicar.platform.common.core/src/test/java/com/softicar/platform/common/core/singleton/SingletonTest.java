package com.softicar.platform.common.core.singleton;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import org.junit.Test;

/**
 * Unit tests for {@link Singleton}.
 * <p>
 * This class implicitly tests {@link SingletonSet}.
 *
 * @author Oliver Richers
 */
public class SingletonTest extends AbstractSingletonTest {

	@Test
	public void testGetWithDefaultInitialValueSupplier() {

		String value = singleton.get();

		assertNull(value);
	}

	@Test
	public void testSet() {

		singleton.set(X);

		assertSame(X, singleton.get());
	}

	@Test
	public void testSetToNull() {

		singleton.set(X);
		singleton.set(null);

		assertNull(singleton.get());
	}

	@Test
	public void testSetToNullWithInitialValueSupplier() {

		singleton.setInitialValueSupplier(() -> Y);
		singleton.set(X);
		singleton.set(null);

		assertSame(Y, singleton.get());
	}

	@Test
	public void testReset() {

		singleton.setInitialValueSupplier(() -> Y);
		singleton.set(X);
		singleton.reset();

		assertSame(Y, singleton.get());
	}

	@Test
	public void testWithDefaultInheritOperator() {

		singleton.set(X);

		try (SingletonSetScope scope = new SingletonSetScope()) {
			assertNull(singleton.get());
		}
	}

	@Test
	public void testWithIdentityInheritOperator() {

		singleton.setInheritOperator(UnaryOperator.identity());
		singleton.set(X);

		try (SingletonSetScope scope = new SingletonSetScope()) {
			assertSame(X, singleton.get());
		}
	}

	@Test
	public void testWithTransfomationalInheritOperator() {

		singleton.setInheritOperator(value -> new String(value));
		singleton.set(X);

		try (SingletonSetScope scope = new SingletonSetScope()) {
			assertNotSame(X, singleton.get());
			assertEquals(X, singleton.get());
		}
	}

	@Test
	public void testWithScopeCloseHandler() {

		ScopeCloseHandler handler = new ScopeCloseHandler();
		singleton.setScopeCloseHandler(handler);

		singleton.set(X);
		try (SingletonSetScope scope = new SingletonSetScope()) {
			singleton.set(Y);
		}

		assertSame(Y, handler.getValue());
	}

	private static class ScopeCloseHandler implements Consumer<String> {

		private String value = null;

		@Override
		public void accept(String value) {

			this.value = value;
		}

		public String getValue() {

			return value;
		}
	}
}
