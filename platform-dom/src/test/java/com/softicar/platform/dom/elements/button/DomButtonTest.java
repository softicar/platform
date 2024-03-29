package com.softicar.platform.dom.elements.button;

import com.softicar.platform.common.core.interfaces.ITestMarker;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import com.softicar.platform.dom.event.DomEventType;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

public class DomButtonTest extends AbstractTest implements IDomTestExecutionEngineMethods {

	@Rule public final IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();

	private final Button button;
	private final CallbackCounter callbackCounter;

	public DomButtonTest() {

		this.button = new Button();
		this.callbackCounter = new CallbackCounter();
		setNodeSupplier(() -> button);
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	@Test
	public void testClickWithoutHandler() {

		findButton(Button.MARKER).click();
		assertEquals(0, callbackCounter.getCount());
	}

	@Test
	public void testClickWithHandler() {

		button.setClickCallback(callbackCounter::call);
		findButton(Button.MARKER).click();
		assertEquals(1, callbackCounter.getCount());
	}

	@Test
	public void testEnterWithHandler() {

		button.setClickCallback(callbackCounter::call);
		findButton(Button.MARKER).sendEvent(DomEventType.ENTER);
		assertEquals(1, callbackCounter.getCount());
	}

	@Test
	public void testSpaceWithHandler() {

		button.setClickCallback(callbackCounter::call);
		findButton(Button.MARKER).sendEvent(DomEventType.SPACE);
		assertEquals(1, callbackCounter.getCount());
	}

	private static class Button extends DomButton {

		public static final ITestMarker MARKER = Mockito.mock(ITestMarker.class);

		public Button() {

			setLabel("click me");
			addMarker(MARKER);
		}
	}

	private static class CallbackCounter {

		private int count;

		public CallbackCounter() {

			this.count = 0;
		}

		public void call() {

			this.count++;
		}

		public int getCount() {

			return count;
		}
	}
}
