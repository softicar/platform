package com.softicar.platform.ajax.input.auto.complete;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.dom.event.DomEventType;
import org.junit.Test;

public class AjaxAutoCompleteConflictingEventHandlerTest extends AbstractAjaxAutoCompleteStringTest {

	@Test
	public void testWithListenToCallAfterAutoComplete() {

		openTestInput(i -> {
			i.getEngine().addItems(ITEM1, ITEM2, ITEM3);
			i.getInputField().listenToEvent(DomEventType.ENTER);
		});

		// use ENTER key for selection
		send(inputField, Key.DOWN);
		waitForAutoCompletePopup();
		send(inputField, Key.DOWN, Key.DOWN, Key.ENTER);

		// assert selection worked and no ENTER event was sent
		assertPopupIsDisplayed(false);
		assertInputValue(ITEM2);
		inputDiv.assertNoEvent();
	}
}
