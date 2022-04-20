package com.softicar.platform.common.core.interfaces;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.function.Consumer;
import org.junit.Test;

public class ConsumersTest extends AbstractTest {

	private static final String X = "X";
	private static final String RESULT = "RESULT";

	@Test
	public void testToFunction() {

		TestConsumer consumer = new TestConsumer();

		String result = Consumers.toFunction(consumer, RESULT).apply(X);

		assertEquals(X, consumer.getCallArgument());
		assertEquals(RESULT, result);
	}

	private class TestConsumer implements Consumer<String> {

		private String callArgument;

		public TestConsumer() {

			this.callArgument = null;
		}

		public String getCallArgument() {

			return callArgument;
		}

		@Override
		public void accept(String parameter) {

			this.callArgument = parameter;
		}
	}
}
