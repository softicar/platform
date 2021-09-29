package com.softicar.platform.emf.module.message.bus;

import com.softicar.platform.common.core.java.reflection.ClassHierarchyValidationException;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.emf.module.message.IEmfModuleMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class EmfModuleMessageBusTest extends AbstractDbTest {

	private final EmfModuleMessageBus bus;

	public EmfModuleMessageBusTest() {

		this.bus = new EmfModuleMessageBus();
	}

	@Test
	public void testMultipleConsumers() {

		List<Integer> values = new ArrayList<>();

		bus.registerConsumer(MessageA.class, m -> values.add(m.value));
		bus.registerConsumer(MessageA.class, m -> values.add(m.value));

		bus.sendMessage(new MessageA(13));
		bus.sendMessage(new MessageA(42));

		assertEquals(Arrays.asList(13, 13, 42, 42), values);
	}

	@Test
	public void testMultipleMessageTypes() {

		List<Integer> valuesA = new ArrayList<>();
		List<Integer> valuesB = new ArrayList<>();

		bus.registerConsumer(MessageA.class, m -> valuesA.add(m.value));
		bus.registerConsumer(MessageB.class, m -> valuesB.add(m.value));

		bus.sendMessage(new MessageA(13));
		bus.sendMessage(new MessageB(42));

		assertEquals(Arrays.asList(13), valuesA);
		assertEquals(Arrays.asList(42), valuesB);
	}

	@Test(expected = ClassHierarchyValidationException.class)
	public void testIllgealMessageTypeWhenRegistering() {

		bus.registerConsumer(IllegalMessage.class, m -> {
			// nothing to do
		});
	}

	@Test(expected = ClassHierarchyValidationException.class)
	public void testIllgealMessageTypeWhenSending() {

		bus.sendMessage(new IllegalMessage());
	}

	private static class MessageA implements IEmfModuleMessage {

		private final int value;

		public MessageA(int value) {

			this.value = value;
		}
	}

	private static class MessageB implements IEmfModuleMessage {

		private final int value;

		public MessageB(int value) {

			this.value = value;
		}
	}

	private static class IllegalMessage extends MessageA {

		public IllegalMessage() {

			super(0);
		}
	}
}
