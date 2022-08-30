package com.softicar.platform.ajax.event;

import static org.junit.Assert.assertEquals;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.IAjaxSeleniumLowLevelTestEngineMethods;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.input.DomTextInput;

public class AjaxMultiEventTestDiv extends DomDiv {

	private final IAjaxSeleniumLowLevelTestEngineMethods testEngineMethods;
	private final StringBuilder logs;
	private final Input input;
	private final DomButton button1;
	private final DomButton button2;

	public AjaxMultiEventTestDiv(IAjaxSeleniumLowLevelTestEngineMethods testEngineMethods) {

		this.testEngineMethods = testEngineMethods;
		this.logs = new StringBuilder();
		this.input = new Input();
		this.button1 = new DomButton()//
			.setLabel(IDisplayString.create("Test1"))
			.setClickCallback(this::onButton1Clicked);
		this.button2 = new DomButton()//
			.setLabel(IDisplayString.create("Test2"))
			.setClickCallback(this::onButton2Clicked);

		appendChild(input);
		appendChild(new DomActionBar(button1, button2));
	}

	public Input getInput() {

		return input;
	}

	public DomButton getButton1() {

		return button1;
	}

	public DomButton getButton2() {

		return button2;
	}

	public void sendInputText(String text) {

		testEngineMethods.send(input, text);
	}

	public void clickButton1() {

		testEngineMethods.click(button1);
	}

	public void clickButton2() {

		testEngineMethods.click(button2);
	}

	public void assertLogs(String expectedLogs) {

		assertEquals(expectedLogs, logs.toString());
	}

	private void onButton1Clicked() {

		log("button1 clicked");
	}

	private void onButton2Clicked() {

		log("button2 clicked");
	}

	private void log(String text) {

		logs.append("%s (%s)\n".formatted(text, input.getValueText()));
	}

	public class Input extends DomTextInput {

		private volatile boolean blocking;
		private volatile INullaryVoidFunction onChange;

		public Input() {

			this.blocking = false;
			this.onChange = INullaryVoidFunction.NO_OPERATION;

			addChangeCallback(() -> {
				log("input changed");
				onChange.apply();
				testEngineMethods.waitUntil(() -> !blocking);
			});
		}

		public void setBlocking(boolean blocking) {

			this.blocking = blocking;
		}

		public void setOnChange(INullaryVoidFunction onChange) {

			this.onChange = onChange;
		}
	}
}
