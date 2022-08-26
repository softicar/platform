package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.input.DomTextInput;
import org.junit.Rule;
import org.junit.Test;

public class DomAutoCompletePopupTest extends AbstractDomAutoCompleteDefaultInputEngineTest implements IDomTestExecutionEngineMethods {

	@Rule public IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();
	private final DomAutoCompleteDefaultInputEngine<TestValue> inputEngine;

	public DomAutoCompletePopupTest() {

		this.inputEngine = new DomAutoCompleteDefaultInputEngine<>();

		inputEngine.setLoader(() -> values);

		addTestValue(DomI18n.ONE, 1);
		addTestValue(DomI18n.TWO, 2);
		setNodeSupplier(() -> new DomAutoCompleteInput<>(inputEngine));
	}

	@Test
	public void testPopupDisplayedOnClick() {

		findNode(DomTextInput.class).click();
		findNode(DomAutoCompletePopup.class).assertDisplayed();
	}

	@Test
	public void testPopupDisplayedValues() {

		findNode(DomTextInput.class).click();
		DomNodeTester popup = findNode(DomAutoCompletePopup.class);
		popup.assertDisplayed();
		popup.assertContainsText(DomI18n.ONE);
		popup.assertContainsText(DomI18n.TWO);
	}

	@Test
	public void testPopupHiddenOnBlur() {

		findNode(DomTextInput.class).click();
		findNode(DomAutoCompletePopup.class).assertDisplayed();

		findNode(DomTextInput.class).sendEvent(DomEventType.BLUR);
		findNodes(DomAutoCompletePopup.class).assertNone();
	}

	@Test
	public void testPopupHiddenOnValueSelect() {

		findNode(DomTextInput.class).click();
		DomNodeTester popup = findNode(DomAutoCompletePopup.class);
		popup.assertDisplayed();

		popup.findNodes(DomAutoCompletePopupValueDisplay.class).withText(DomI18n.ONE).assertOne().click();
		findNodes(DomAutoCompletePopup.class).assertNone();
		findNode(DomTextInput.class).assertInputValue(DomI18n.ONE.toString());
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}
}
