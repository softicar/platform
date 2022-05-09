package com.softicar.platform.ajax.timeout;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import java.time.Duration;
import org.junit.Test;

public class AjaxDomTimeoutEventTest extends AbstractAjaxSeleniumLowLevelTest {

	@Test
	public void testWithSofticarUserException() {

		var testDiv = openTestNode(() -> {
			var div = new AjaxDomTimeoutEventTestDiv();
			div.scheduleTimeout(0.1);
			return div;
		});

		waitUntil(() -> testDiv.isTimeoutReceived(), Duration.ofSeconds(10));
	}
}
