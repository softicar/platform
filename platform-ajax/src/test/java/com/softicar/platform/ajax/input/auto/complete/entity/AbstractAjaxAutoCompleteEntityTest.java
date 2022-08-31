package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.ajax.input.auto.complete.AbstractAjaxAutoCompleteTest;
import com.softicar.platform.ajax.input.auto.complete.entity.AbstractAjaxAutoCompleteEntityTest.Asserter.AssertionExecutor;
import com.softicar.platform.ajax.testing.cases.entity.AjaxTestEntity;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomBody;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteDefaultInputEngine;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteIndicatorType;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.style.CssPixel;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.styles.CssDisplay;
import com.softicar.platform.dom.styles.CssFlexDirection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import org.junit.After;

/**
 * Base class of unit tests for {@link DomAutoCompleteInput} with
 * {@link IEntity} values.
 * <p>
 * Each of the test methods must contain an explicit call to
 * {@link Setup#execute()}. Otherwise, {@link AssertionExecutor#assertAll()}
 * fails (see below).
 * <p>
 * Each of the test methods must fully initialize the {@link Asserter} instance
 * and perform an explicit call to {@link AssertionExecutor#assertAll()} (see
 * {@link #executeAssertions()}). This corresponds to an enforced, complete
 * definition of the overall result state after each of the tests.
 * <p>
 * <b>Test method name anatomy:</b>
 *
 * <pre>
 * "test{Valid|Ambiguous|Illegal|Empty}Input[With{Interactions}]"
 *      \__________________________________/ \_________________/
 *             initial input state            user interactions
 * </pre>
 *
 * @author Alexander Schmidt
 */
public abstract class AbstractAjaxAutoCompleteEntityTest extends AbstractAjaxAutoCompleteTest {

	protected static final String AMBIGUOUS_VALUE_NAME_CHUNK = "ba";
	protected static final String ILLEGAL_VALUE_NAME = "xxx";
	protected static final AjaxTestEntity VALUE1 = new AjaxTestEntity(1, "foo"); // name: unique
	protected static final AjaxTestEntity VALUE2 = new AjaxTestEntity(2, "bar"); // name: contains combination of letters ("ba") that also appears in several other names
	protected static final AjaxTestEntity VALUE3 = new AjaxTestEntity(3, "baz"); // name: fully contained in name of other value
	protected static final AjaxTestEntity VALUE4 = new AjaxTestEntity(4, "bazinga");
	protected static final List<AjaxTestEntity> VALUES = Arrays.asList(VALUE2, VALUE3, VALUE4, VALUE1);
	protected static final AjaxTestEntity ILLEGAL_VALUE = new AjaxTestEntity(999, "zzz");

	protected TestInputEngine inputEngine;
	protected DomAutoCompleteInput<AjaxTestEntity> inputNode;
	protected IDomNode focusPredecessorElement;
	protected IDomNode focusSuccessorElement;
	protected IDomTextualInput inputFieldElement;
	protected InputProxy input;
	protected final Setup setup;
	protected final Asserter asserter;
	protected final ChangeCallback changeCallback;
	protected final BodyProxy body;
	protected final PopupProyx popup;
	protected final BackdropProxy backdrop;
	protected final CallbackProxy callback;

	protected AbstractAjaxAutoCompleteEntityTest() {

		this.setup = new Setup();
		this.asserter = new Asserter();
		this.changeCallback = new ChangeCallback();
		this.body = new BodyProxy();
		this.popup = new PopupProyx();
		this.backdrop = new BackdropProxy();
		this.callback = new CallbackProxy();
	}

	protected void assertPopupValues(List<AjaxTestEntity> values) {

		super.assertPopupValues(//
			AjaxTestEntity::getName,
			values);
	}

	@After
	public void executeAssertions() {

		assertTrue("Asserter was not executed.", asserter.isExecuted());
	}

	protected interface TestSetupInstruction extends BiConsumer<DomAutoCompleteInput<AjaxTestEntity>, DomAutoCompleteDefaultInputEngine<AjaxTestEntity>> {

		// convenience interface
	}

	protected class Setup {

		private boolean executed;
		private final List<TestSetupInstruction> instructions;

		public Setup() {

			this.executed = false;
			this.instructions = new ArrayList<>();
			addListenToChange();
		}

		public Setup setSelectedValueNone() {

			return setSelectedValue(null);
		}

		public Setup setSelectedValue(AjaxTestEntity value) {

			return add((input, engine) -> input.setValue(value));
		}

		public Setup setAvailableValues(AjaxTestEntity...values) {

			return setAvailableValues(Arrays.asList(values));
		}

		public Setup setAvailableValues(Collection<AjaxTestEntity> values) {

			return add((input, engine) -> engine.setLoader(() -> values));
		}

		public Setup add(TestSetupInstruction instruction) {

			this.instructions.add(instruction);
			return this;
		}

		public void execute() {

			Container container = openTestNode(() -> {
				// ensure that screenshots include the auto-complete pop-up
				DomBody body = CurrentDomDocument.get().getBody();
				body.setStyle(CssStyle.WIDTH, new CssPixel(300));
				body.setStyle(CssStyle.HEIGHT, new CssPixel(300));

				// create engine
				inputEngine = new TestInputEngine();
				inputEngine.setLoader(() -> VALUES);

				// create input
				var input = new DomAutoCompleteInput<>(inputEngine);
				this.instructions.forEach(it -> it.accept(input, inputEngine));
				return new Container(input);
			});
			focusPredecessorElement = container.getFocusPredecessorNode();
			focusSuccessorElement = container.getFocusSuccessorNode();
			inputNode = container.getInputNode();
			inputFieldElement = inputNode.getInputField();
			input = new InputProxy();
			this.executed = true;
		}

		public void assertExecuted() {

			assertTrue("Setup was not executed", executed);
		}

		private void addListenToChange() {

			add((input, engine) -> input.addChangeCallback(changeCallback));
		}
	}

	protected class TestInputEngine extends DomAutoCompleteDefaultInputEngine<AjaxTestEntity> {

		@Override
		public Collection<AjaxTestEntity> findMatches(String pattern, int limit) {

			return super.findMatches(pattern, limit);
		}
	}

	protected class Asserter {

		private boolean executed;
		private AjaxTestEntity expectedSelectedValue;
		private IDisplayString expectedSelectedValueExceptionMessage;
		private String expectedInputText;
		private DomAutoCompleteIndicatorType expectedIndicator;
		private boolean expectedPopupDisplayed;
		private List<AjaxTestEntity> expectedPopupValues;
		private Integer expectedSelectedValueNumber;
		private FocusTarget expectedFocusTarget;
		private AjaxTestEntity expectedCallbackValue;
		private int expectedCallbackCount;

		public Asserter() {

			this.executed = false;
		}

		public boolean isExecuted() {

			return executed;
		}

		public IndicatorExpectationSetter expectValues(AjaxTestEntity value) {

			String inputText = Optional.ofNullable(value).map(AjaxTestEntity::toDisplay).map(IDisplayString::toString).orElse("");
			setExpectedSelectedValue(value);
			setExpectedInputText(inputText);
			return new IndicatorExpectationSetter();
		}

		public IndicatorExpectationSetter expectValuesNone() {

			return expectValues(null);
		}

		public SelectedValueExpectationSetter expectInputText(AjaxTestEntity value) {

			return expectInputText(value.toDisplay().toString());
		}

		public SelectedValueExpectationSetter expectInputText(String inputText) {

			setExpectedInputText(inputText);
			return new SelectedValueExpectationSetter();
		}

		public SelectedValueExpectationSetter expectInputTextNone() {

			return expectInputText((String) null);
		}

		private void setExpectedSelectedValue(AjaxTestEntity value) {

			this.expectedSelectedValue = value;
		}

		private void setExpectedSelectedValueExceptionMessage(IDisplayString exceptionMessage) {

			this.expectedSelectedValueExceptionMessage = exceptionMessage;
		}

		private void setExpectedInputText(String inputText) {

			this.expectedInputText = Optional.ofNullable(inputText).orElse("");
		}

		private void setExpectedIndicator(DomAutoCompleteIndicatorType indicatorType) {

			this.expectedIndicator = indicatorType;
		}

		private void setExpectedPopupDisplayed(boolean displayed) {

			this.expectedPopupDisplayed = displayed;
		}

		private void setExpectedPopupValues(List<AjaxTestEntity> values) {

			this.expectedPopupValues = values;
		}

		private void setExpectedSelectedEntityNumber(Integer valueNumber) {

			this.expectedSelectedValueNumber = valueNumber;
		}

		private void setExpectedFocusTarget(FocusTarget focusTarget) {

			this.expectedFocusTarget = focusTarget;
		}

		private void setExpectedCallbackValue(AjaxTestEntity value) {

			this.expectedCallbackValue = value;
		}

		private void setExpectedCallbackCount(int count) {

			this.expectedCallbackCount = count;
		}

		public class InputTextExpectationSetter {

			public IndicatorExpectationSetter expectInputText(String inputText) {

				setExpectedInputText(inputText);
				return new IndicatorExpectationSetter();
			}

			public IndicatorExpectationSetter expectInputTextNone() {

				return expectInputText((String) null);
			}
		}

		public class SelectedValueExpectationSetter {

			public IndicatorExpectationSetter expectSelectedValueExceptionMessage() {

				setExpectedSelectedValueExceptionMessage(DomI18n.PLEASE_SELECT_A_VALID_ENTRY);
				return new IndicatorExpectationSetter();
			}

			public IndicatorExpectationSetter expectSelectedValue(AjaxTestEntity value) {

				setExpectedSelectedValue(value);
				return new IndicatorExpectationSetter();
			}

			public IndicatorExpectationSetter expectSelectedValueNone() {

				return expectSelectedValue(null);
			}
		}

		public class IndicatorExpectationSetter {

			public PopupDisplayExpectationSetter expectIndicatorAmbiguous() {

				return expectIndicator(DomAutoCompleteIndicatorType.AMBIGUOUS);
			}

			public PopupDisplayExpectationSetter expectIndicatorIllegal() {

				return expectIndicator(DomAutoCompleteIndicatorType.ILLEGAL);
			}

			public PopupDisplayExpectationSetter expectIndicatorNone() {

				return expectIndicator(null);
			}

			public PopupDisplayExpectationSetter expectIndicator(DomAutoCompleteIndicatorType indicatorType) {

				setExpectedIndicator(indicatorType);
				return new PopupDisplayExpectationSetter();
			}
		}

		public class PopupDisplayExpectationSetter {

			public PopupValuesExpectationSetter expectPopupDisplayed() {

				expectPopup(true);
				return new PopupValuesExpectationSetter();
			}

			public FocusExpectationSetter expectPopupNotDisplayed() {

				expectPopup(false);
				setExpectedPopupValues(Collections.emptyList());
				setExpectedSelectedEntityNumber(null);
				return new FocusExpectationSetter();
			}

			private void expectPopup(boolean displayed) {

				setExpectedPopupDisplayed(displayed);
			}
		}

		public class PopupValuesExpectationSetter {

			public PopupSelectedValueExpectationSetter expectPopupValues(AjaxTestEntity...values) {

				return expectPopupValues(Arrays.asList(values));
			}

			public PopupSelectedValueExpectationSetter expectPopupValues(List<AjaxTestEntity> values) {

				setExpectedPopupValues(values);
				return new PopupSelectedValueExpectationSetter();
			}

			public FocusExpectationSetter expectPopupValuesNone() {

				setExpectedPopupValues(Collections.emptyList());
				setExpectedSelectedEntityNumber(null);
				return new FocusExpectationSetter();
			}
		}

		public class PopupSelectedValueExpectationSetter {

			public FocusExpectationSetter expectPopupSelectedValueFirst() {

				return expectPopupSelectedValue(1);
			}

			public FocusExpectationSetter expectPopupSelectedValueLast() {

				return expectPopupSelectedValue(expectedPopupValues.size());
			}

			public FocusExpectationSetter expectPopupSelectedValue(int number) {

				setExpectedSelectedEntityNumber(number);
				return new FocusExpectationSetter();
			}

			public FocusExpectationSetter expectPopupSelectedValueNone() {

				setExpectedSelectedEntityNumber(null);
				return new FocusExpectationSetter();
			}
		}

		public class FocusExpectationSetter {

			public CallbackExpectationSetter expectFocusOnBody() {

				return expectFocusTarget(FocusTarget.BODY);
			}

			public CallbackExpectationSetter expectFocusOnInput() {

				return expectFocusTarget(FocusTarget.INPUT);
			}

			public CallbackExpectationSetter expectFocusOnPredecessor() {

				return expectFocusTarget(FocusTarget.PREDECESSOR);
			}

			public CallbackExpectationSetter expectFocusOnSuccessor() {

				return expectFocusTarget(FocusTarget.SUCCESSOR);
			}

			public CallbackExpectationSetter expectFocusTarget(FocusTarget focustarget) {

				setExpectedFocusTarget(focustarget);
				return new CallbackExpectationSetter();
			}
		}

		public class CallbackExpectationSetter {

			public AssertionExecutor expectCallbackNone() {

				setExpectedCallbackValue(null);
				setExpectedCallbackCount(0);
				return new AssertionExecutor();
			}

			public CallbackCountExpectationSetter expectCallbackValueNone() {

				return expectCallbackValue(null);
			}

			public CallbackCountExpectationSetter expectCallbackValue(AjaxTestEntity value) {

				setExpectedCallbackValue(value);
				return new CallbackCountExpectationSetter();
			}

			public CallbackValueExpectationSetter expectCallbackCountOne() {

				return expectCallbackCount(1);
			}

			public CallbackValueExpectationSetter expectCallbackCount(int count) {

				setExpectedCallbackCount(count);
				return new CallbackValueExpectationSetter();
			}
		}

		public class CallbackValueExpectationSetter {

			public AssertionExecutor expectCallbackValueNone() {

				return expectCallbackValue(null);
			}

			public AssertionExecutor expectCallbackValue(AjaxTestEntity value) {

				setExpectedCallbackValue(value);
				return new AssertionExecutor();
			}
		}

		public class CallbackCountExpectationSetter {

			public AssertionExecutor expectCallbackCountOne() {

				return expectCallbackCount(1);
			}

			public AssertionExecutor expectCallbackCount(int count) {

				setExpectedCallbackCount(count);
				return new AssertionExecutor();
			}
		}

		public class AssertionExecutor {

			public void assertAll() {

				executed = true;
				setup.assertExecuted();
				input.assertValues(expectedSelectedValueExceptionMessage, expectedSelectedValue, expectedInputText);
				indicator.assertIndicates(expectedIndicator);
				popup.assertDisplayed(expectedPopupDisplayed);
				input.assertFocusTarget(expectedFocusTarget);
				if (expectedPopupDisplayed) {
					popup.assertValues(expectedPopupValues);
					popup.assertSelectedValue(expectedSelectedValueNumber);
				}
				callback.assertCalled(expectedCallbackValue, expectedCallbackCount);
			}
		}
	}

	protected class InputProxy {

		public InputProxy sendString(String string) {

			send(inputFieldElement, string);
			return this;
		}

		public InputProxy pressBackspace() {

			send(inputFieldElement, Key.BACK_SPACE);
			return this;
		}

		public InputProxy pressBackspace(int count) {

			for (int i = 0; i < count; i++) {
				pressBackspace();
			}
			return this;
		}

		public InputProxy pressEnter() {

			send(inputFieldElement, Key.ENTER);
			return this;
		}

		public InputProxy pressEscape() {

			send(inputFieldElement, Key.ESCAPE);
			return this;
		}

		public InputProxy pressTab() {

			send(inputFieldElement, Key.TAB);
			return this;
		}

		public InputProxy clickInputField() {

			click(inputFieldElement);
			return this;
		}

		public InputProxy focusByTab() {

			send(focusPredecessorElement, Key.TAB);
			waitForServer();
			assertFocused(inputFieldElement);
			return this;
		}

		public InputProxy pressArrowDown() {

			send(inputFieldElement, Key.DOWN);
			return this;
		}

		public InputProxy pressArrowUp() {

			send(inputFieldElement, Key.UP);
			return this;
		}

		public InputProxy waitForServer() {

			AbstractAjaxAutoCompleteEntityTest.super.waitForServer();
			return this;
		}

		public void assertValues(IDisplayString expectedSelectedValueExceptionMessage, AjaxTestEntity expectedSelectedValue, String expectedInputText) {

			if (expectedSelectedValueExceptionMessage != null) {
				assertExceptionMessage(expectedSelectedValueExceptionMessage, inputNode::getValueOrNull);
				waitForServer();
			} else {
				AjaxTestEntity actualSelectedValue = inputNode.getValueOrNull();
				assertEquals(//
					"Unexpected selected value.",
					expectedSelectedValue,
					actualSelectedValue);
			}

			assertEquals(//
				"Unexpected input text.",
				expectedInputText,
				getAttributeValue(inputFieldElement, "value"));
		}

		public void assertFocusTarget(FocusTarget focusTarget) {

			String focusedNodeClass = getFocusedNode().map(it -> it.getClass().getCanonicalName()).orElse("unknown");

			switch (focusTarget) {
			case BODY:
				assertTrue(//
					"Unexpected focus state: Expected the body element to be focused but encountered a focused element of type '%s'."
						.formatted(focusedNodeClass),
					isFocused(inputFieldElement.getDomDocument().getBody()));
				break;
			case INPUT:
				assertTrue(//
					"Unexpected focus state: Expected the input element to be focused but encountered a focused element of type '%s'."
						.formatted(focusedNodeClass),
					isFocused(inputFieldElement));
				break;
			case PREDECESSOR:
				assertTrue(//
					"Unexpected focus state: Expected the predecessor element to be focused but encountered a focused element of type '%s'."
						.formatted(focusedNodeClass),
					isFocused(focusPredecessorElement));
				break;
			case SUCCESSOR:
				assertTrue(//
					"Unexpected focus state: Expected the successor element to be focused but encountered a focused element of type '%s'."
						.formatted(focusedNodeClass),
					isFocused(focusSuccessorElement));
				break;
			}
		}
	}

	protected class BodyProxy {

		public BodyProxy click() {

			clickBodyNode();
			return this;
		}

		public BodyProxy waitForServer() {

			AbstractAjaxAutoCompleteEntityTest.super.waitForServer();
			return this;
		}
	}

	protected class PopupProyx {

		public PopupProyx clickValueNumber(int number) {

			assertTrue(//
				"The given value number must not be lower than 1.",
				number >= 1);

			List<String> elementNames = getAutoCompletePopupValueNames();
			assertTrue(//
				String.format("Tried to click on value %s but the list only contained %s values.", number, elementNames.size()),
				number <= elementNames.size());

			clickAutoCompletePopupValue(number - 1);

			return this;
		}

		public PopupProyx waitForServer() {

			AbstractAjaxAutoCompleteEntityTest.super.waitForServer();
			return this;
		}

		public void assertDisplayed(boolean displayed) {

			assertEquals(//
				"Unexpected display state of auto-complete popup.",
				displayed,
				isAutoCompletePopupDisplayed());
			assertEquals(//
				"Unexpected display state of backdrop.",
				displayed,
				isAutoCompleteBackdropDisplayed());
		}

		public void assertValues(List<AjaxTestEntity> values) {

			if (values.isEmpty()) {
				assertTrue(//
					"Unexpected popup value count: Expected placeholder value but did not find it.",
					isAutoCompleteValuePlaceholderElementDisplayed());
			} else {
				assertPopupValues(values);
			}
		}

		public void assertSelectedValue(Integer number) {

			if (number != null) {
				popup.assertSelectedValueNumber(number);
			} else {
				popup.assertSelectedValueNone();
			}
		}

		private void assertSelectedValueNone() {

			assertFalse(//
				"Unexpectedly encountered a selected value.",
				getAutoCompletePopupSelectedValueIndex().isPresent());
		}

		private void assertSelectedValueNumber(int number) {

			assertTrue(//
				"The given value number must not be lower than 1.",
				number >= 1);

			Optional<Integer> selectedValueIndex = getAutoCompletePopupSelectedValueIndex();
			assertTrue(//
				"Failed to identify the selected value.",
				selectedValueIndex.isPresent());

			List<String> availableValueElementNames = getAutoCompletePopupValueNames();
			assertTrue(//
				String.format("Expected value number %s to be selected, but only %s value/s was/were available.", number, availableValueElementNames.size()),
				availableValueElementNames.size() >= number);

			int selectedIndex = selectedValueIndex.get();
			assertTrue(//
				"Failed to find the selected value in the list of available values.",
				selectedIndex >= 0);

			assertEquals(//
				"Unexpected position of selection.",
				number,
				selectedIndex + 1);
		}
	}

	protected class BackdropProxy {

		public BackdropProxy click() {

			assertTrue(//
				"Failed to find backdrop.",
				isAutoCompleteBackdropDisplayed());
			clickAutoCompleteBackdrop();
			return this;
		}

		public BackdropProxy waitForServer() {

			AbstractAjaxAutoCompleteEntityTest.super.waitForServer();
			return this;
		}

		public void assertDisplayed(boolean displayed) {

			assertEquals(//
				"Unexpected display state of backdrop.",
				displayed,
				isAutoCompleteBackdropDisplayed());
		}
	}

	protected class CallbackProxy {

		public void assertCalled(AjaxTestEntity value, int count) {

			assertEquals(//
				"Unexpected total number of callbacks.",
				count,
				changeCallback.getCount());

			assertEquals(//
				"Unexpected value for most recent callback.",
				value,
				changeCallback.getLastValue());
		}
	}

	protected class ChangeCallback implements INullaryVoidFunction {

		private AjaxTestEntity lastValue;
		private int count;

		public ChangeCallback() {

			this.lastValue = null;
			this.count = 0;
		}

		@Override
		public void apply() {

			this.lastValue = inputNode.getValueNoThrow().orElse(null);
			this.count++;
		}

		public AjaxTestEntity getLastValue() {

			return lastValue;
		}

		public int getCount() {

			return count;
		}
	}

	private static class Container extends DomDiv {

		private final DomAutoCompleteInput<AjaxTestEntity> inputNode;
		private final IDomNode focusPredecessorNode;
		private final IDomNode focusSuccessorNode;

		public Container(DomAutoCompleteInput<AjaxTestEntity> inputNode) {

			setStyle(CssDisplay.FLEX);
			setStyle(CssFlexDirection.COLUMN);

			this.inputNode = inputNode;
			this.focusPredecessorNode = createTabTarget("TAB target before the input");
			this.focusSuccessorNode = createTabTarget("TAB target after the input");

			appendChild(focusPredecessorNode);
			appendChild(inputNode);
			appendChild(focusSuccessorNode);
		}

		public IDomNode getFocusPredecessorNode() {

			return focusPredecessorNode;
		}

		public IDomNode getFocusSuccessorNode() {

			return focusSuccessorNode;
		}

		public DomAutoCompleteInput<AjaxTestEntity> getInputNode() {

			return inputNode;
		}

		/**
		 * @return an element that can serve as a TAB target
		 */
		private IDomNode createTabTarget(String description) {

			return new DomTextInput(description);
		}
	}

	public enum FocusTarget {

		BODY,
		INPUT,
		PREDECESSOR,
		SUCCESSOR;
	}
}
