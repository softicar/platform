package com.softicar.platform.ajax.input.auto.complete;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.dom.event.DomEventType;
import org.junit.Test;

public class AjaxAutoCompleteConflictingEventHandlerTest extends AbstractAjaxAutoCompleteStringTest {

	@Test
	public void testWithListenToCallAfterAutoComplete() {

		openTestInput(i -> {
			i.getEngine().addValues(VALUE1, VALUE2, VALUE3);
			i.getInputField().listenToEvent(DomEventType.ENTER);
		});

		// use ENTER key for selection
		send(inputField, Key.DOWN);
		waitForServer();
		send(inputField, Key.DOWN, Key.DOWN, Key.ENTER);
		waitForServer();

		// assert selection worked and no ENTER event was sent
		assertPopupIsDisplayed(false);
		assertInputValue(VALUE2);
		inputDiv.assertNoEvent();
	}
}
