package com.softicar.platform.ajax.event;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import org.junit.Test;

public class AjaxMultiEventTest extends AbstractAjaxSeleniumLowLevelTest {

	private final AjaxMultiEventTestDiv testDiv;

	public AjaxMultiEventTest() {

		this.testDiv = openTestNode(() -> new AjaxMultiEventTestDiv(this));
	}

	@Test
	public void testSimultaneousChangeEventAndClickEvent() {

		testDiv.getInput().setBlocking(true);
		testDiv.sendInputText("hello");
		testDiv.clickButton1();
		testDiv.getInput().setBlocking(false);

		waitForServer();

		testDiv.assertLogs("""
				input changed (hello)
				button1 clicked (hello)
				""");
	}

	@Test
	public void testSimultaneousChangeEventAndClickEventOnDisappendedButton() {

		testDiv.getInput().setOnChange(() -> testDiv.getButton1().disappend());
		testDiv.getInput().setBlocking(true);
		testDiv.sendInputText("hello");
		testDiv.clickButton1();
		testDiv.getInput().setBlocking(false);

		waitForServer();

		testDiv.assertLogs("""
				input changed (hello)
				""");
	}

	@Test
	public void testSimultaneousChangeEventAndClickEventOnDisabledButton() {

		testDiv.getInput().setOnChange(() -> testDiv.getButton1().setDisabled(true));
		testDiv.getInput().setBlocking(true);
		testDiv.sendInputText("hello");
		testDiv.clickButton1();
		testDiv.getInput().setBlocking(false);

		waitForServer();

		testDiv.assertLogs("""
				input changed (hello)
					""");
	}

	@Test
	public void testChangeEventMultiClick() {

		testDiv.getInput().setBlocking(true);
		testDiv.sendInputText("hello");
		testDiv.clickButton1();
		testDiv.clickButton1();
		testDiv.clickButton1();
		testDiv.sendInputText(" world");
		testDiv.clickButton2();
		testDiv.clickButton2();
		testDiv.clickButton2();
		testDiv.sendInputText("!");
		testDiv.getInput().setBlocking(false);

		waitForServer();

		testDiv.assertLogs("""
				input changed (hello)
				button1 clicked (hello world!)
				input changed (hello world!)
				button2 clicked (hello world!)
				""");
	}
}
