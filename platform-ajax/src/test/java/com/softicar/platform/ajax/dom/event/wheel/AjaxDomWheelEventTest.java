package com.softicar.platform.ajax.dom.event.wheel;

import com.softicar.platform.ajax.dom.event.AbstractAjaxDomEventTestDiv;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.event.IDomWheelEventHandler;
import java.time.Duration;
import java.util.Collection;
import java.util.Iterator;
import org.junit.Test;

public class AjaxDomWheelEventTest extends AbstractAjaxSeleniumLowLevelTest {

	@Test
	public void testMouseWheelDown() {

		var testDiv = openTestNode(() -> new TestDiv());

		mouseWheelDown(testDiv);
		waitForServer();

		var events = testDiv.getEvents();
		new WheelEventAsserter(events)//
			.nextEvent()
			.assertDeltaXZero()
			.assertDeltaYPositive()
			.assertDeltaZZero()
			.assertNoMoreEvents();
	}

	@Test
	public void testMouseWheelUp() {

		var testDiv = openTestNode(() -> new TestDiv());

		mouseWheelUp(testDiv);
		waitForServer();

		var events = testDiv.getEvents();
		new WheelEventAsserter(events)//
			.nextEvent()
			.assertDeltaXZero()
			.assertDeltaYNegative()
			.assertDeltaZZero()
			.assertNoMoreEvents();
	}

	@Test
	public void testMouseWheelDeduplication() {

		var testDiv = openTestNode(() -> new TestDiv().setSleepDuration(Duration.ofSeconds(1)));

		mouseWheelDown(testDiv);
		mouseWheelDown(testDiv);
		mouseWheelDown(testDiv);
		mouseWheelDown(testDiv);
		mouseWheelUp(testDiv);
		mouseWheelUp(testDiv);
		mouseWheelDown(testDiv);
		mouseWheelDown(testDiv);
		waitForServer();

		var events = testDiv.getEvents();
		new WheelEventAsserter(events)//

			.nextEvent()
			.assertDeltaXZero()
			.assertDeltaYPositive()
			.assertDeltaZZero()

			.nextEvent()
			.assertDeltaXZero()
			.assertDeltaYNegative()
			.assertDeltaZZero()

			// Expect no further "wheel down" event because it is swallowed by
			// the current client-side implementation of wheel event deduplication.

			.assertNoMoreEvents();
	}

	private static class WheelEventAsserter {

		private final Iterator<IDomEvent> eventIterator;
		private IDomEvent currentEvent;

		public WheelEventAsserter(Collection<IDomEvent> events) {

			this.eventIterator = events.iterator();
			this.currentEvent = null;
		}

		public WheelEventAsserter assertDeltaXZero() {

			assertEquals(0d, currentEvent.getDeltaX(), Double.MIN_VALUE);
			return this;
		}

		public WheelEventAsserter assertDeltaYPositive() {

			assertTrue(currentEvent.getDeltaY() > 0);
			return this;
		}

		public WheelEventAsserter assertDeltaYNegative() {

			assertTrue(currentEvent.getDeltaY() < 0);
			return this;
		}

		public WheelEventAsserter assertDeltaZZero() {

			assertEquals(0d, currentEvent.getDeltaZ(), Double.MIN_VALUE);
			return this;
		}

		public WheelEventAsserter nextEvent() {

			assertTrue(eventIterator.hasNext());
			this.currentEvent = eventIterator.next();
			assertEquals(DomEventType.WHEEL, currentEvent.getType());
			return this;
		}

		public void assertNoMoreEvents() {

			assertFalse(eventIterator.hasNext());
		}
	}

	private static class TestDiv extends AbstractAjaxDomEventTestDiv implements IDomWheelEventHandler {

		private Duration sleepDuration;

		public TestDiv() {

			this.sleepDuration = Duration.ZERO;
		}

		public TestDiv setSleepDuration(Duration sleepDuration) {

			this.sleepDuration = sleepDuration;
			return this;
		}

		@Override
		public void handleWheel(IDomEvent event) {

			addEvent(event);
			Sleep.sleep(sleepDuration);
		}
	}
}
