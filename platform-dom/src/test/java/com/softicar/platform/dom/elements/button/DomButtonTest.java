package com.softicar.platform.dom.elements.button;

import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.common.testing.Asserts;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestEngine;
import com.softicar.platform.dom.event.DomEventType;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

public class DomButtonTest extends Asserts implements IDomTestEngineMethods {

	@Rule public final IDomTestEngine engine = new DomDocumentTestEngine();

	private final Button button;
	private final CallbackCounter callbackCounter;

	public DomButtonTest() {

		this.button = new Button();
		this.callbackCounter = new CallbackCounter();
		setNodeSupplier(() -> button);
	}

	@Override
	public IDomTestEngine getEngine() {

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

		public static final IStaticObject MARKER = Mockito.mock(IStaticObject.class);

		public Button() {

			setLabel("click me");
			setMarker(MARKER);
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
