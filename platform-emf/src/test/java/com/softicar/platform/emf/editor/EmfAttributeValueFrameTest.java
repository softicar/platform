package com.softicar.platform.emf.editor;

import com.softicar.platform.common.core.annotations.TestingOnly;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.emf.attribute.field.string.EmfStringAttribute;
import com.softicar.platform.emf.attribute.field.string.EmfStringInput;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

public class EmfAttributeValueFrameTest extends AbstractTest implements IDomTestExecutionEngineMethods {

	@Rule public IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();

	private static final String AAA = "AAA";
	private static final String BBB = "BBB";
	private static final String CCC = "CCC";
	private final TestObject object;
	private final IDbField<TestObject, String> field;
	private final EmfStringAttribute<TestObject> attribute;
	private String value;
	private int changeCallbacks;
	private DomNodeTester valueFrameTester;
	private EmfAttributeValueFrame<TestObject, String> valueFrame;

	public EmfAttributeValueFrameTest() {

		this.object = new TestObject();
		this.field = Mockito.mock(IDbField.class);
		this.attribute = new EmfStringAttribute<>(field);
		this.value = AAA;
		this.changeCallbacks = 0;

		attribute.setInputFactory(TestInput::new);

		Mockito.when(field.getValue(object)).thenAnswer(mock -> value);
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	// ------------------------------ test initial value mode ------------------------------ //

	@Test
	public void testHidden() {

		startWithMode(EmfAttributeValueMode.HIDDEN);

		assertHidden();
	}

	@Test
	public void testDisplay() {

		startWithMode(EmfAttributeValueMode.DISPLAY);

		assertDisplay(AAA);
	}

	@Test
	public void testMandatoryInput() {

		startWithMode(EmfAttributeValueMode.MANDATORY_INPUT);

		assertMandatoryInput(AAA, 0);
	}

	@Test
	public void testOptionalInput() {

		startWithMode(EmfAttributeValueMode.OPTIONAL_INPUT);

		assertOptionalInput(AAA, 0);
	}

	// ------------------------------ test refresh from HIDDEN ------------------------------ //

	@Test
	public void testRefreshFromHiddenToHidden() {

		startWithModeChangeValueAndRefresh(EmfAttributeValueMode.HIDDEN, EmfAttributeValueMode.HIDDEN);

		assertHidden();
	}

	@Test
	public void testRefreshFromHiddenToDisplay() {

		startWithModeChangeValueAndRefresh(EmfAttributeValueMode.HIDDEN, EmfAttributeValueMode.DISPLAY);

		assertDisplay(BBB);
	}

	@Test
	public void testRefreshFromHiddenToMandatoryInput() {

		startWithModeChangeValueAndRefresh(EmfAttributeValueMode.HIDDEN, EmfAttributeValueMode.MANDATORY_INPUT);

		assertMandatoryInput(BBB, 0);
	}

	@Test
	public void testRefreshFromHiddenToOptionalInput() {

		startWithModeChangeValueAndRefresh(EmfAttributeValueMode.HIDDEN, EmfAttributeValueMode.OPTIONAL_INPUT);

		assertOptionalInput(BBB, 0);
	}

	// ------------------------------ test refresh from DISPLAY ------------------------------ //

	@Test
	public void testRefreshFromDisplayToHidden() {

		startWithModeChangeValueAndRefresh(EmfAttributeValueMode.DISPLAY, EmfAttributeValueMode.HIDDEN);

		assertHidden();
	}

	@Test
	public void testRefreshFromDisplayToDisplay() {

		startWithModeChangeValueAndRefresh(EmfAttributeValueMode.DISPLAY, EmfAttributeValueMode.DISPLAY);

		assertDisplay(BBB);
	}

	@Test
	public void testRefreshFromDisplayToMandatoryInput() {

		startWithModeChangeValueAndRefresh(EmfAttributeValueMode.DISPLAY, EmfAttributeValueMode.MANDATORY_INPUT);

		assertMandatoryInput(BBB, 0);
	}

	@Test
	public void testRefreshFromDisplayToOptionalInput() {

		startWithModeChangeValueAndRefresh(EmfAttributeValueMode.DISPLAY, EmfAttributeValueMode.OPTIONAL_INPUT);

		assertOptionalInput(BBB, 0);
	}

	// ------------------------------ test refresh from MANDATORY INPUT ------------------------------ //

	@Test
	public void testRefreshFromMandatoryInputToHidden() {

		startWithModeChangeValueAndRefresh(EmfAttributeValueMode.MANDATORY_INPUT, EmfAttributeValueMode.HIDDEN);

		assertHidden();
	}

	@Test
	public void testRefreshFromMandatoryInputToDisplay() {

		startWithModeChangeValueAndRefresh(EmfAttributeValueMode.MANDATORY_INPUT, EmfAttributeValueMode.DISPLAY);

		assertDisplay(BBB);
	}

	@Test
	public void testRefreshFromMandatoryInputToMandatoryInput() {

		startWithModeChangeValueAndRefresh(EmfAttributeValueMode.MANDATORY_INPUT, EmfAttributeValueMode.MANDATORY_INPUT);

		assertMandatoryInput(AAA, 1);
	}

	@Test
	public void testRefreshFromMandatoryInputToOptionalInput() {

		startWithModeChangeValueAndRefresh(EmfAttributeValueMode.MANDATORY_INPUT, EmfAttributeValueMode.OPTIONAL_INPUT);

		assertOptionalInput(AAA, 1);
	}

	// ------------------------------ test refresh from OPTIONAL INPUT ------------------------------ //

	@Test
	public void testRefreshFromOptionalInputToHidden() {

		startWithModeChangeValueAndRefresh(EmfAttributeValueMode.OPTIONAL_INPUT, EmfAttributeValueMode.HIDDEN);

		assertHidden();
	}

	@Test
	public void testRefreshFromOptionalInputToDisplay() {

		startWithModeChangeValueAndRefresh(EmfAttributeValueMode.OPTIONAL_INPUT, EmfAttributeValueMode.DISPLAY);

		assertDisplay(BBB);
	}

	@Test
	public void testRefreshFromOptionalInputToMandatoryInput() {

		startWithModeChangeValueAndRefresh(EmfAttributeValueMode.OPTIONAL_INPUT, EmfAttributeValueMode.MANDATORY_INPUT);

		assertMandatoryInput(AAA, 1);
	}

	@Test
	public void testRefreshFromOptionalInputToOptionalInput() {

		startWithModeChangeValueAndRefresh(EmfAttributeValueMode.OPTIONAL_INPUT, EmfAttributeValueMode.OPTIONAL_INPUT);

		assertOptionalInput(AAA, 1);
	}

	// ------------------------------ special tests ------------------------------ //

	@Test
	public void testInputValueIsRetained() {

		// start with input mode and set input value
		startWithMode(EmfAttributeValueMode.MANDATORY_INPUT);
		valueFrameTester.findNode(EmfStringInput.class).setInputValue(CCC);

		// change attribute value and refresh to display mode
		value = BBB;
		valueFrame.refresh(EmfAttributeValueMode.DISPLAY);
		assertDisplay(BBB);

		// change back to input mode and assert input value is retained
		valueFrame.refresh(EmfAttributeValueMode.OPTIONAL_INPUT);
		assertOptionalInput(CCC, 1);
	}

	@Test
	public void testChangeCallback() {

		startWithMode(EmfAttributeValueMode.MANDATORY_INPUT);

		DomNodeTester input = valueFrameTester.findNode(EmfStringInput.class);

		input.setInputValue(AAA);
		assertEquals(1, changeCallbacks);

		input.setInputValue(BBB);
		assertEquals(2, changeCallbacks);
	}

	// ------------------------------ assert ------------------------------ //

	private void assertHidden() {

		valueFrameTester.assertContainsNoText();
	}

	private void assertDisplay(String expectedText) {

		valueFrameTester.assertContainsText(expectedText);
	}

	private void assertMandatoryInput(String expectedValue, int expectedRefreshCalls) {

		assertInput(expectedValue, true, expectedRefreshCalls);
	}

	private void assertOptionalInput(String expectedValue, int expectedRefreshCalls) {

		assertInput(expectedValue, false, expectedRefreshCalls);
	}

	private void assertInput(String expectedValue, boolean expectedMandatory, int expectedRefreshCalls) {

		DomNodeTester input = valueFrameTester.findNode(TestInput.class);

		input.assertInputValue(expectedValue);
		input.assertType(TestInput.class).assertMandatory(expectedMandatory);
		input.assertType(TestInput.class).assertRefreshCalls(expectedRefreshCalls);
	}

	// ------------------------------ utility ------------------------------ //

	private void startWithMode(EmfAttributeValueMode valueMode) {

		setNodeSupplier(() -> new EmfAttributeValueFrame<>(this::onChange, attribute, object, valueMode));

		this.valueFrameTester = findNode(EmfAttributeValueFrame.class);
		this.valueFrame = valueFrameTester.assertType(EmfAttributeValueFrame.class);
	}

	private void onChange() {

		changeCallbacks++;
	}

	private void startWithModeChangeValueAndRefresh(EmfAttributeValueMode source, EmfAttributeValueMode target) {

		startWithMode(source);

		value = BBB;
		valueFrame.refresh(target);
	}

	// ------------------------------ test object ------------------------------ //

	@TestingOnly
	private static class TestObject extends AbstractDbObject<TestObject> implements IEmfObject<TestObject> {

		@Override
		public IDisplayString toDisplayWithoutId() {

			throw new UnsupportedOperationException();
		}

		@Override
		public EmfObjectTable<TestObject, TestObject> table() {

			throw new UnsupportedOperationException();
		}
	}

	private static class TestInput extends EmfStringInput {

		private boolean mandatory = false;
		private int refreshCalls = 0;

		@Override
		public void setMandatory(boolean mandatory) {

			this.mandatory = mandatory;
		}

		@Override
		public void refreshInputConstraints() {

			refreshCalls++;
		}

		public void assertMandatory(boolean expectedMandatory) {

			assertEquals(expectedMandatory, mandatory);
		}

		public void assertRefreshCalls(int expectedCalls) {

			assertEquals(expectedCalls, refreshCalls);
		}
	}
}
