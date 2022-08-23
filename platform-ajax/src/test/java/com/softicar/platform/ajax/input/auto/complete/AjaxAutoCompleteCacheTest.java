package com.softicar.platform.ajax.input.auto.complete;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.ajax.utils.TestButton;
import com.softicar.platform.dom.document.CurrentDomDocument;
import org.junit.Test;

public class AjaxAutoCompleteCacheTest extends AbstractAjaxAutoCompleteStringTest {

	private TestButton button;

	public AjaxAutoCompleteCacheTest() {

		openTestInput(i -> {
			this.button = new TestButton(() -> {
				i.getEngine().clearValues();
				i.getEngine().addValues(VALUE3);
			});
			CurrentDomDocument.get().getBody().appendChild(button);

			i.getEngine().addValues(VALUE1, VALUE2);
		});
	}

	@Test
	public void testCacheIsClearedOnFocus() {

		// open popup to fill cache
		click(inputField);
		send(inputField, Key.DOWN);
		waitForServer();
		assertPopupValues(VALUE1, VALUE2);

		// blur and change list of values
		send(inputField, Key.ESCAPE);
		click(button);
		waitForServer();

		// open popup again
		click(inputField);
		send(inputField, Key.DOWN);
		waitForServer();
		assertPopupValues(VALUE3);
	}
}
