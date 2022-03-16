package com.softicar.platform.common.core.singleton;

import java.util.function.UnaryOperator;
import org.junit.Test;

/**
 * Unit tests for {@link SingletonSetScope}.
 *
 * @author Oliver Richers
 */
public class SingletonSetScopeTest extends AbstractSingletonTest {

	@Test
	public void testWithDefaultInheritOperator() {

		singleton.set(X);

		try (SingletonSetScope scope = new SingletonSetScope()) {
			assertNull(singleton.get());
			singleton.set(Y);
			assertSame(Y, singleton.get());
		}

		assertSame(X, singleton.get());
	}

	@Test
	public void testWithIdentityInheritOperator() {

		singleton.setInheritOperator(UnaryOperator.identity());
		singleton.set(X);

		try (SingletonSetScope scope = new SingletonSetScope()) {
			assertSame(X, singleton.get());
			singleton.set(Y);
			assertSame(Y, singleton.get());
		}

		assertSame(X, singleton.get());
	}
}
