package com.softicar.platform.ajax.waiting.div;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.utils.TestButton;
import com.softicar.platform.common.core.thread.Locker;
import com.softicar.platform.common.core.threading.InterruptedRuntimeException;
import com.softicar.platform.dom.elements.DomDiv;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.junit.Test;

public class AjaxWaitingDivTest extends AbstractAjaxSeleniumLowLevelTest {

	private static final long TIMEOUT = 3000;
	private final Lock lock;
	private final TestDiv testDiv;
	private final Condition eventHandlerStarted;
	private final Condition waitingDivChecked;
	private final TestButton button;

	public AjaxWaitingDivTest() {

		this.lock = new ReentrantLock();
		this.eventHandlerStarted = lock.newCondition();
		this.waitingDivChecked = lock.newCondition();
		this.testDiv = openTestNode(TestDiv::new);
		this.button = testDiv.getButton();
	}

	@Test
	public void test() {

		// precondition: working indicator is hidden
		assertFalse(isWorkingIndicatorDisplayed());

		// trigger working indicator
		try (Locker locker = new Locker(lock)) {
			click(button);
			waitForSignal(eventHandlerStarted);
			waitUntil(() -> isWorkingIndicatorDisplayed());
			waitingDivChecked.signal();
		}

		// postcondition: working indicator is hidden
		waitUntil(() -> !isWorkingIndicatorDisplayed());
	}

	private boolean waitForSignal(Condition condition) {

		try {
			return condition.await(TIMEOUT, TimeUnit.MILLISECONDS);
		} catch (InterruptedException exception) {
			throw new InterruptedRuntimeException(exception);
		}
	}

	private class TestDiv extends DomDiv {

		private final TestButton testButton;

		public TestDiv() {

			this.testButton = appendChild(new TestButton(this::clicked));
		}

		public TestButton getButton() {

			return testButton;
		}

		private void clicked() {

			try (Locker locker = new Locker(lock)) {
				eventHandlerStarted.signal();
				waitForSignal(waitingDivChecked);
			}
		}
	}
}
