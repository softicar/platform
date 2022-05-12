package com.softicar.platform.ajax.request;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.dom.input.DomOption;
import com.softicar.platform.dom.input.DomSelect;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.node.IDomNode;
import java.util.HashSet;
import java.util.List;
import org.junit.Test;

public class AjaxRequestInputValuesTest extends AbstractAjaxSeleniumLowLevelTest {

	// ------------------------------ text input ------------------------------ //

	@Test
	public void testWithTextInput() {

		var testDiv = openTestNode(() -> new AjaxRequestInputValuesTestDiv());
		var tester = new NodeTester(testDiv, testDiv.textInput);

		// user writes some text
		tester.send("foo");
		tester.clickButton();
		tester.assertValueParameter("foo");
		tester.assertTextValue("foo");

		// user clicks button without change
		tester.clickButton();
		tester.assertNoValueParameter();
		tester.assertTextValue("foo");

		// application changes value
		testDiv.textInput.setInputText("X");
		tester.clickButton();
		tester.assertNoValueParameter();
		tester.assertTextValue("X");

		// user writes more text
		tester.send("foo");
		tester.clickButton();
		tester.assertValueParameter("Xfoo");
		tester.assertTextValue("Xfoo");

		// user removed and writes same text again
		tester.send(Key.BACK_SPACE, Key.BACK_SPACE, Key.BACK_SPACE, Key.BACK_SPACE);
		tester.send("Xfoo");
		tester.clickButton();
		tester.assertNoValueParameter();
		tester.assertTextValue("Xfoo");
	}

	@Test
	public void testWithTextInputWithNoInitialValueAndNoInitialInput() {

		var testDiv = openTestNode(() -> new AjaxRequestInputValuesTestDiv());
		var tester = new NodeTester(testDiv, testDiv.textInput);

		tester.clickButton();
		tester.assertNoValueParameter();
		tester.assertTextValue("");

		tester.send("bar");
		tester.clickButton();
		tester.assertValueParameter("bar");
		tester.assertTextValue("bar");
	}

	@Test
	public void testWithTextInputWithInitialValueAndNoInitialInput() {

		var testDiv = openTestNode(() -> new AjaxRequestInputValuesTestDiv(div -> div.textInput.setInputText("foo")));
		var tester = new NodeTester(testDiv, testDiv.textInput);

		tester.clickButton();
		tester.assertNoValueParameter();
		tester.assertTextValue("foo");

		tester.send("bar");
		tester.clickButton();
		tester.assertValueParameter("foobar");
		tester.assertTextValue("foobar");
	}

	// ------------------------------ text area ------------------------------ //

	@Test
	public void testWithTextArea() {

		var testDiv = openTestNode(() -> new AjaxRequestInputValuesTestDiv());
		var tester = new NodeTester(testDiv, testDiv.textArea);

		// user writes some text
		tester.send("foo");
		tester.clickButton();
		tester.assertValueParameter("foo");
		tester.assertTextValue("foo");

		// user clicks button without change
		tester.clickButton();
		tester.assertNoValueParameter();
		tester.assertTextValue("foo");

		// application changes value
		testDiv.textArea.setInputText("X");
		tester.clickButton();
		tester.assertNoValueParameter();
		tester.assertTextValue("X");

		// user writes more text
		tester.send("foo");
		tester.clickButton();
		tester.assertValueParameter("Xfoo");
		tester.assertTextValue("Xfoo");

		// user removed and writes same text again
		tester.send(Key.BACK_SPACE, Key.BACK_SPACE, Key.BACK_SPACE, Key.BACK_SPACE);
		tester.send("Xfoo");
		tester.clickButton();
		tester.assertNoValueParameter();
		tester.assertTextValue("Xfoo");
	}

	// ------------------------------ select ------------------------------ //

	@Test
	public void testWithSelect() {

		var testDiv = openTestNode(() -> new AjaxRequestInputValuesTestDiv());
		var tester = new NodeTester(testDiv, testDiv.select);

		tester.clickButton();
		tester.assertValueParameter(testDiv.option1.getNodeIdString());
		tester.assertSelectedOptions(testDiv.option1);

		click(testDiv.select);
		click(testDiv.option3);
		tester.clickButton();
		tester.assertValueParameter(testDiv.option3.getNodeIdString());
		tester.assertSelectedOptions(testDiv.option3);

		tester.clickButton();
		tester.assertNoValueParameter();
		tester.assertSelectedOptions(testDiv.option3);

		testDiv.select.setSelectedOption(testDiv.option1);
		tester.clickButton();
		tester.assertNoValueParameter();
		tester.assertSelectedOptions(testDiv.option1);

		click(testDiv.select);
		click(testDiv.option2);
		tester.clickButton();
		tester.assertValueParameter(testDiv.option2.getNodeIdString());
		tester.assertSelectedOptions(testDiv.option2);
	}

	// ------------------------------ multi-select ------------------------------ //

	@Test
	public void testWithMultiSelect() {

		var testDiv = openTestNode(() -> new AjaxRequestInputValuesTestDiv(div -> div.select.setMultiple(true)));
		var tester = new NodeTester(testDiv, testDiv.select);

		tester.clickButton();
		tester.assertNoValueParameter();
		tester.assertSelectedOptions();

		click(testDiv.option2);
		click(testDiv.option3, IAjaxSeleniumLowLevelTestEngineInput.Modifier.CONTROL);
		tester.clickButton();
		tester.assertValueParameter(testDiv.option2.getNodeIdString() + "," + testDiv.option3.getNodeIdString());
		tester.assertSelectedOptions(testDiv.option2, testDiv.option3);

		tester.clickButton();
		tester.assertNoValueParameter();
		tester.assertSelectedOptions(testDiv.option2, testDiv.option3);

		testDiv.select.setSelectedOptions(List.of(testDiv.option1, testDiv.option2));
		tester.clickButton();
		tester.assertNoValueParameter();
		tester.assertSelectedOptions(testDiv.option1, testDiv.option2);

		click(testDiv.option1, IAjaxSeleniumLowLevelTestEngineInput.Modifier.CONTROL);
		click(testDiv.option2, IAjaxSeleniumLowLevelTestEngineInput.Modifier.CONTROL);
		tester.clickButton();
		tester.assertValueParameter("");
		tester.assertSelectedOptions();
	}

	// ------------------------------ utility ------------------------------ //

	private class NodeTester {

		private final AjaxRequestInputValuesTestDiv testDiv;
		private final IDomNode node;

		public NodeTester(AjaxRequestInputValuesTestDiv testDiv, IDomNode node) {

			this.testDiv = testDiv;
			this.node = node;
		}

		public void clickButton() {

			click(testDiv.testButton);
		}

		public void send(IAjaxSeleniumLowLevelTestEngineInput.Key...keys) {

			AjaxRequestInputValuesTest.this.send(node, keys);
		}

		public void send(String text) {

			AjaxRequestInputValuesTest.this.send(node, text);
		}

		public void assertTextValue(String expectedValue) {

			assertEquals(expectedValue, ((IDomTextualInput) node).getInputText());
		}

		public void assertSelectedOptions(DomOption...options) {

			var expectedOptions = new HashSet<>(List.of(options));
			var actualOptions = new HashSet<>(((DomSelect<?>) node).getSelectedOptions());

			actualOptions.forEach(option -> assertTrue("unexpected selected option", expectedOptions.contains(option)));
			expectedOptions.forEach(option -> assertTrue("expected selected option", actualOptions.contains(option)));
		}

		public void assertValueParameter(String expectedValue) {

			getRequestAsserter().assertValueParameter(expectedValue, node);
		}

		public void assertNoValueParameter() {

			getRequestAsserter().assertNoValueParameter(node);
		}

		private AjaxRequestAsserter getRequestAsserter() {

			return new AjaxRequestAsserter(testDiv.getLastEvent().getAjaxRequest());
		}
	}
}
