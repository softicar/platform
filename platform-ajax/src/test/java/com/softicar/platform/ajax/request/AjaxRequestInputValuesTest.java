package com.softicar.platform.ajax.request;

import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngineInput;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.dom.event.DomModifier;
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
		tester.assertNodeValueParameter("foo");
		tester.assertTextValue("foo");

		// user clicks button without change
		tester.clickButton();
		tester.assertNoNodeValueParameter();
		tester.assertTextValue("foo");

		// application changes value
		testDiv.textInput.setValue("X");
		tester.clickButton();
		tester.assertNoNodeValueParameter();
		tester.assertTextValue("X");

		// user writes more text
		tester.send("foo");
		tester.clickButton();
		tester.assertNodeValueParameter("Xfoo");
		tester.assertTextValue("Xfoo");

		// user removed and writes same text again
		tester.send(Key.BACK_SPACE, Key.BACK_SPACE, Key.BACK_SPACE, Key.BACK_SPACE);
		tester.send("Xfoo");
		tester.clickButton();
		tester.assertNoNodeValueParameter();
		tester.assertTextValue("Xfoo");
	}

	@Test
	public void testWithTextInputWithNoInitialValueAndNoInitialInput() {

		var testDiv = openTestNode(() -> new AjaxRequestInputValuesTestDiv());
		var tester = new NodeTester(testDiv, testDiv.textInput);

		tester.clickButton();
		tester.assertNoNodeValueParameter();
		tester.assertTextValue("");

		tester.send("bar");
		tester.clickButton();
		tester.assertNodeValueParameter("bar");
		tester.assertTextValue("bar");
	}

	@Test
	public void testWithTextInputWithInitialValueAndNoInitialInput() {

		var testDiv = openTestNode(() -> new AjaxRequestInputValuesTestDiv(div -> div.textInput.setValue("foo")));
		var tester = new NodeTester(testDiv, testDiv.textInput);

		tester.clickButton();
		tester.assertNoNodeValueParameter();
		tester.assertTextValue("foo");

		tester.send("bar");
		tester.clickButton();
		tester.assertNodeValueParameter("foobar");
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
		tester.assertNodeValueParameter("foo");
		tester.assertTextValue("foo");

		// user clicks button without change
		tester.clickButton();
		tester.assertNoNodeValueParameter();
		tester.assertTextValue("foo");

		// application changes value
		testDiv.textArea.setValue("X");
		tester.clickButton();
		tester.assertNoNodeValueParameter();
		tester.assertTextValue("X");

		// user writes more text
		tester.send("foo");
		tester.clickButton();
		tester.assertNodeValueParameter("Xfoo");
		tester.assertTextValue("Xfoo");

		// user removed and writes same text again
		tester.send(Key.BACK_SPACE, Key.BACK_SPACE, Key.BACK_SPACE, Key.BACK_SPACE);
		tester.send("Xfoo");
		tester.clickButton();
		tester.assertNoNodeValueParameter();
		tester.assertTextValue("Xfoo");
	}

	// ------------------------------ select ------------------------------ //

	@Test
	public void testWithSelect() {

		var testDiv = openTestNode(() -> new AjaxRequestInputValuesTestDiv());
		var tester = new NodeTester(testDiv, testDiv.select);

		tester.clickButton();
		tester.assertNodeValueParameter(testDiv.option1.getNodeIdString());
		tester.assertSelectedOptions(testDiv.option1);

		click(testDiv.select);
		click(testDiv.option3);
		tester.clickButton();
		tester.assertNodeValueParameter(testDiv.option3.getNodeIdString());
		tester.assertSelectedOptions(testDiv.option3);

		tester.clickButton();
		tester.assertNoNodeValueParameter();
		tester.assertSelectedOptions(testDiv.option3);

		testDiv.select.setSelectedOption(testDiv.option1);
		tester.clickButton();
		tester.assertNoNodeValueParameter();
		tester.assertSelectedOptions(testDiv.option1);

		click(testDiv.select);
		click(testDiv.option2);
		tester.clickButton();
		tester.assertNodeValueParameter(testDiv.option2.getNodeIdString());
		tester.assertSelectedOptions(testDiv.option2);
	}

	// ------------------------------ multi-select ------------------------------ //

	@Test
	public void testWithMultiSelect() {

		var testDiv = openTestNode(() -> new AjaxRequestInputValuesTestDiv(div -> div.select.setMultiple(true)));
		var tester = new NodeTester(testDiv, testDiv.select);

		tester.clickButton();
		tester.assertNoNodeValueParameter();
		tester.assertSelectedOptions();

		click(testDiv.option2);
		click(testDiv.option3, DomModifier.CONTROL);
		tester.clickButton();
		tester.assertNodeValueParameter(testDiv.option2.getNodeIdString() + "," + testDiv.option3.getNodeIdString());
		tester.assertSelectedOptions(testDiv.option2, testDiv.option3);

		tester.clickButton();
		tester.assertNoNodeValueParameter();
		tester.assertSelectedOptions(testDiv.option2, testDiv.option3);

		testDiv.select.setSelectedOptions(List.of(testDiv.option1, testDiv.option2));
		tester.clickButton();
		tester.assertNoNodeValueParameter();
		tester.assertSelectedOptions(testDiv.option1, testDiv.option2);

		click(testDiv.option1, DomModifier.CONTROL);
		click(testDiv.option2, DomModifier.CONTROL);
		tester.clickButton();
		tester.assertNodeValueParameter("");
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

		public void send(AjaxSeleniumLowLevelTestEngineInput.Key...keys) {

			AjaxRequestInputValuesTest.this.send(node, keys);
		}

		public void send(String text) {

			AjaxRequestInputValuesTest.this.send(node, text);
		}

		public void assertTextValue(String expectedValue) {

			assertEquals(expectedValue, ((IDomTextualInput) node).getValueText());
		}

		public void assertSelectedOptions(DomOption...options) {

			var expectedOptions = new HashSet<>(List.of(options));
			var actualOptions = new HashSet<>(((DomSelect<?>) node).getSelectedOptions());

			actualOptions.forEach(option -> assertTrue("unexpected selected option", expectedOptions.contains(option)));
			expectedOptions.forEach(option -> assertTrue("expected selected option", actualOptions.contains(option)));
		}

		public void assertNodeValueParameter(String expectedValue) {

			getRequestMessageAsserter().assertValueParameter(expectedValue, node);
		}

		public void assertNoNodeValueParameter() {

			getRequestMessageAsserter().assertNoValueParameter(node);
		}

		private AjaxRequestMessageAsserter getRequestMessageAsserter() {

			return new AjaxRequestMessageAsserter(testDiv.getLastEvent().getRequestMessage());
		}
	}
}
