package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.ajax.input.auto.complete.AbstractAjaxAutoCompleteTest;
import com.softicar.platform.ajax.input.auto.complete.entity.AbstractAjaxAutoCompleteEntityTest.Asserter.AssertionExecutor;
import com.softicar.platform.ajax.testing.cases.entity.AjaxTestEntity;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.interfaces.IAjaxSeleniumLowLevelTestEngineInput.Key;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.thread.Locker;
import com.softicar.platform.common.core.thread.sleep.Sleep;
import com.softicar.platform.common.testing.Asserts;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomBody;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.input.auto.entity.DomAutoCompleteEntityInMemoryInputEngine;
import com.softicar.platform.dom.elements.input.auto.entity.DomAutoCompleteEntityInput;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.input.auto.DomAutoCompleteInputValidationMode;
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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import org.junit.After;

/**
 * Base class of unit tests for {@link DomAutoCompleteEntityInput}.
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
 * Implementations of this class correspond to various linear phases of user
 * interaction with a {@link DomAutoCompleteEntityInput} element. Focus state
 * and opened/closed state of the auto-complete popup serve as primary criteria
 * of distinction between the individual phases. Hence, the correct phase of the
 * a given interaction with the input element depends on the expected resulting
 * state, in terms of (a) focus, (b) popup visibility and (c) repetition count.
 * <p>
 * The linear phases of user interaction with a
 * {@link DomAutoCompleteEntityInput} element are assumed to be:
 * <p>
 * <b>1 Created:</b> The element was created and displayed, and no user
 * interaction took place, yet. See
 * {@link AjaxAutoCompleteEntityInputCreatedTest}
 * <p>
 * <b>2 Focused:</b> The element gained focus, and no further user interaction
 * took place, yet. See {@link AjaxAutoCompleteEntityInputFocusedTest}
 * <p>
 * <b>2.1 Popup Un-Opened:</b> The focused element received user input but the
 * auto-complete popup is not yet opened. See
 * {@link AjaxAutoCompleteEntityInputFocusedPopupUnopenedTest}
 * <p>
 * <b>2.2 Popup Opened:</b> The focused element received user input and the
 * auto-complete popup is opened. See
 * {@link AjaxAutoCompleteEntityInputFocusedPopupOpenedTest}
 * <p>
 * <b>2.3 Popup Closed:</b> The previously-displayed auto-complete popup
 * receives user input and gets closed.
 * {@link AjaxAutoCompleteEntityInputFocusedPopupClosedTest}
 * <p>
 * <b>2.4 Popup Re-Opened:</b> The focused element received user input and the
 * auto-complete popup is opened once again. See
 * {@link AjaxAutoCompleteEntityInputFocusedPopupReopenedTest}
 * <p>
 * <b>2.5 Popup Re-Closed:</b> The previously-displayed auto-complete popup
 * receives user input and gets closed once again. See
 * {@link AjaxAutoCompleteEntityInputFocusedPopupReclosedTest}
 * <p>
 * <b>3 Unfocused:</b> The element receives user input and loses focus. See
 * {@link AjaxAutoCompleteEntityInputUnfocusedTest}
 * <p>
 * <b>4 Re-Focused:</b> The element receives user input and gains focus once
 * again, after having lost focus before. See
 * {@link AjaxAutoCompleteEntityInputRefocusedTest}
 * <p>
 * <b>4.1 Popup Un-Opened:</b> TODO
 * <p>
 * <b>4.2 Popup Opened:</b> TODO
 * <p>
 * <b>4.3 Popup Closed:</b> TODO
 * <p>
 * <b>4.4 Popup Re-Opened:</b> TODO
 * <p>
 * <b>4.5 Popup Re-Closed:</b> TODO
 * <p>
 * <b>5 Re-Unfocused:</b> TODO
 * <p>
 * To some degree, those phases assume induction: For example, an auto-complete
 * popup can be opened and closed many times. However, it is assumed that, if it
 * works twice in a row, arbitrary repetitions will work as well.
 * <p>
 * <b>Test method name anatomy:</b><br>
 * "test[{FeatureOrFeatureCombination}With][{Interaction}On]{InputElementDescription}"<br>
 * definitions:<br>
 * - {InputElementDescription} := [Active|Passive][Empty|Filled]Input
 *
 * @author Alexander Schmidt
 */
public abstract class AbstractAjaxAutoCompleteEntityTest extends AbstractAjaxAutoCompleteTest {

	protected static final String AMBIGUOUS_ITEM_NAME_CHUNK = "ba";
	protected static final String INVALID_ITEM_NAME = "xxx";
	protected static final AjaxTestEntity ENTITY1 = new AjaxTestEntity(1, "foo"); // name: unique
	protected static final AjaxTestEntity ENTITY2 = new AjaxTestEntity(2, "bar"); // name: contains combination of letters ("ba") that also appears in several other names
	protected static final AjaxTestEntity ENTITY3 = new AjaxTestEntity(3, "baz"); // name: fully contained in name of other item
	protected static final AjaxTestEntity ENTITY4 = new AjaxTestEntity(4, "bazinga");
	protected static final List<AjaxTestEntity> ENTITIES = Arrays.asList(ENTITY1, ENTITY2, ENTITY3, ENTITY4);
	protected static final AjaxTestEntity UNAVAILABLE_ENTITY = new AjaxTestEntity(999, "zzz");
	protected static final long DURATION_100 = 100;
	protected static final long DURATION_250 = 250;
	protected static final long DURATION_500 = 500;
	protected static final long DURATION_1000 = 1000;
	protected static final long DURATION_2000 = 2000;

	protected TestInputEngine inputEngine;
	protected DomAutoCompleteEntityInput<AjaxTestEntity> inputNode;
	protected DomButton eventTriggerButton;
	protected IDomNode focusPredecessorElement;
	protected IDomTextualInput inputFieldElement;
	protected InputProxy input;
	protected EventTriggerProxy eventTrigger;
	protected final Setup setup;
	protected final Asserter asserter;
	protected final ChangeCallback changeCallback;
	protected final BodyProxy body;
	protected final PopupProyx popup;
	protected final OverlayProxy overlay;
	protected final CallbackProxy callback;

	protected AbstractAjaxAutoCompleteEntityTest() {

		this.setup = new Setup();
		this.asserter = new Asserter();
		this.changeCallback = new ChangeCallback();
		this.body = new BodyProxy();
		this.popup = new PopupProyx();
		this.overlay = new OverlayProxy();
		this.callback = new CallbackProxy();
	}

	protected void assertPopupEntities(List<AjaxTestEntity> items) {

		super.assertPopupItems(//
			AjaxTestEntity::getName,
			items);
	}

	@After
	public void executeAssertions() {

		assertTrue("Asserter was not executed.", asserter.isExecuted());
	}

	protected class Setup {

		private boolean executed;
		private final List<ISetupInstruction> instructions;

		public Setup() {

			this.executed = false;
			this.instructions = new ArrayList<>();
		}

		public Setup setListenToChange() {

			return add((input, engine) -> input.addChangeCallback(changeCallback));
		}

		public Setup setSelectedEntityNone() {

			return setSelectedEntity(null);
		}

		public Setup setSelectedEntity(AjaxTestEntity selectedEntity) {

			return add((input, engine) -> input.setValue(selectedEntity));
		}

		public Setup setStringValue(String stringValue) {

			return add((input, engine) -> input.getInputField().setValue(stringValue));
		}

		public Setup setMandatory() {

			return setMandatory(true);
		}

		public Setup setMandatory(boolean mandatory) {

			return add((input, engine) -> input.getConfiguration().setMandatory(mandatory));
		}

		public Setup setMode(DomAutoCompleteInputValidationMode mode) {

			return add((input, engine) -> input.getConfiguration().setValidationMode(mode));
		}

		public Setup markValueAsInvalid() {

			return add((input, engine) -> input.getDomEngine().setAutoCompleteInputInvalid(input));
		}

		public Setup setEntities(AjaxTestEntity...items) {

			return setEntities(Arrays.asList(items));
		}

		public Setup setEntities(Collection<AjaxTestEntity> items) {

			return add((input, engine) -> engine.setLoader(() -> items));
		}

		public Setup add(ISetupInstruction instruction) {

			this.instructions.add(instruction);
			return this;
		}

		public void execute() {

			Container container = openTestNode(() -> {
				// ensure that screenshots include the auto-complete pop-up
				DomBody body = CurrentDomDocument.get().getBody();
				body.setStyle(CssStyle.WIDTH, new CssPixel(300));
				body.setStyle(CssStyle.HEIGHT, new CssPixel(300));

				// create engine and input
				inputEngine = new TestInputEngine();
				DomAutoCompleteEntityInput<AjaxTestEntity> input = new DomAutoCompleteEntityInput<>(inputEngine);
				inputEngine.setLoader(() -> ENTITIES);
				this.instructions.forEach(it -> it.accept(input, inputEngine));
				return new Container(input);
			});
			focusPredecessorElement = container.getFocusPredecessorNode();
			inputNode = container.getInputNode();
			eventTriggerButton = container.getEventTriggerNode();
			inputFieldElement = inputNode.getInputField();
			input = new InputProxy();
			eventTrigger = new EventTriggerProxy();
			this.executed = true;
		}

		public void assertExecuted() {

			assertTrue("Setup was not executed", executed);
		}
	}

	protected class TestInputEngine extends DomAutoCompleteEntityInMemoryInputEngine<AjaxTestEntity> {

		private final Lock lock;

		public TestInputEngine() {

			this.lock = new ReentrantLock();
		}

		public Locker createLocker() {

			return new Locker(lock);
		}

		@Override
		public Collection<AjaxTestEntity> findMatchingItems(String pattern, int fetchOffset, int fetchSize) {

			try (Locker locker = createLocker()) {
				return super.findMatchingItems(pattern, fetchOffset, fetchSize);
			}
		}

		@Override
		public Optional<AjaxTestEntity> findPerfectMatch(String pattern) {

			try (Locker locker = createLocker()) {
				return super.findPerfectMatch(pattern);
			}
		}
	}

	protected interface ISetupInstruction
			extends BiConsumer<DomAutoCompleteEntityInput<AjaxTestEntity>, DomAutoCompleteEntityInMemoryInputEngine<AjaxTestEntity>> {

		// convenience interface
	}

	protected class Asserter {

		private boolean executed;
		private AjaxTestEntity expectedServerValue;
		private IDisplayString expectedServerValueExceptionMessage;
		private String expectedClientValue;
		private Indicator expectedIndicator;
		private boolean expectedIndicatorPresence;
		private boolean expectedPopupDisplayed;
		private List<AjaxTestEntity> expectedPopupItems;
		private Integer expectedSelectedItemNumber;
		private boolean expectedFocusState;
		private boolean expectedOverlayDisplayed;
		private AjaxTestEntity expectedCallbackValue;
		private int expectedCallbackCount;

		public Asserter() {

			this.executed = false;
		}

		public boolean isExecuted() {

			return executed;
		}

		public IndicatorExpectationSetter expectValues(AjaxTestEntity serverValue, String clientValue) {

			setExpectedServerValue(serverValue);
			setExpectedClientValue(clientValue);
			return new IndicatorExpectationSetter();
		}

		public IndicatorExpectationSetter expectValues(AjaxTestEntity serverAndClientValue) {

			String clientValue = Optional.ofNullable(serverAndClientValue).map(it -> it.toDisplay().toString()).orElse("");
			return expectValues(serverAndClientValue, clientValue);
		}

		public IndicatorExpectationSetter expectValuesNone() {

			return expectValues(null);
		}

		public ServerValueExpectationSetter expectClientValue(AjaxTestEntity clientValue) {

			return expectClientValue(clientValue.toDisplay().toString());
		}

		public ServerValueExpectationSetter expectClientValue(String clientValue) {

			setExpectedClientValue(clientValue);
			return new ServerValueExpectationSetter();
		}

		public ServerValueExpectationSetter expectClientValueNone() {

			return expectClientValue((String) null);
		}

		private void setExpectedServerValue(AjaxTestEntity serverValue) {

			this.expectedServerValue = serverValue;
		}

		private void setExpectedServerValueExceptionMessage(IDisplayString exceptionMessage) {

			this.expectedServerValueExceptionMessage = exceptionMessage;
		}

		private void setExpectedClientValue(String clientValue) {

			this.expectedClientValue = Optional.ofNullable(clientValue).orElse("");
		}

		private void setExpectedIndicator(Indicator indicator) {

			this.expectedIndicator = indicator;
		}

		private void setExpectedIndicatorPresence(boolean displayed) {

			this.expectedIndicatorPresence = displayed;
		}

		private void setExpectedPopupDisplayed(boolean displayed) {

			this.expectedPopupDisplayed = displayed;
		}

		private void setExpectedPopupEntities(List<AjaxTestEntity> items) {

			this.expectedPopupItems = items;
		}

		private void setExpectedSelectedEntityNumber(Integer itemNumber) {

			this.expectedSelectedItemNumber = itemNumber;
		}

		private void setExpectedFocusState(boolean focusState) {

			this.expectedFocusState = focusState;
		}

		private void setExpectedOverlayDisplayed(boolean displayed) {

			this.expectedOverlayDisplayed = displayed;
		}

		private void setExpectedCallbackValue(AjaxTestEntity value) {

			this.expectedCallbackValue = value;
		}

		private void setExpectedCallbackCount(int count) {

			this.expectedCallbackCount = count;
		}

		public class ClientValueExpectationSetter {

			public IndicatorExpectationSetter expectClientValue(AjaxTestEntity value) {

				return expectClientValue(value.toDisplay().toString());
			}

			public IndicatorExpectationSetter expectClientValue(String clientValue) {

				setExpectedClientValue(clientValue);
				return new IndicatorExpectationSetter();
			}

			public IndicatorExpectationSetter expectClientValueNone() {

				return expectClientValue((String) null);
			}
		}

		public class ServerValueExpectationSetter {

			public IndicatorExpectationSetter expectServerValueExceptionMessage() {

				setExpectedServerValueExceptionMessage(DomI18n.PLEASE_SELECT_A_VALID_ENTRY);
				return new IndicatorExpectationSetter();
			}

			public IndicatorExpectationSetter expectServerValue(AjaxTestEntity serverValue) {

				setExpectedServerValue(serverValue);
				return new IndicatorExpectationSetter();
			}

			public IndicatorExpectationSetter expectServerValueNone() {

				return expectServerValue(null);
			}
		}

		public class IndicatorExpectationSetter {

			public PopupDisplayExpectationSetter expectIndicatorCommitting() {

				return expectIndicatorCommitting(true);
			}

			public PopupDisplayExpectationSetter expectIndicatorCommitting(boolean displayed) {

				return expectIndicator(Indicator.COMMITTING, displayed);
			}

			public PopupDisplayExpectationSetter expectIndicatorLoading() {

				return expectIndicatorLoading(true);
			}

			public PopupDisplayExpectationSetter expectIndicatorLoading(boolean displayed) {

				return expectIndicator(Indicator.LOADING, displayed);
			}

			public PopupDisplayExpectationSetter expectIndicatorNotOkay() {

				return expectIndicatorNotOkay(true);
			}

			public PopupDisplayExpectationSetter expectIndicatorNotOkay(boolean displayed) {

				return expectIndicator(Indicator.NOT_OKAY, displayed);
			}

			public PopupDisplayExpectationSetter expectIndicatorValueAmbiguous() {

				return expectIndicatorValueAmbiguous(true);
			}

			public PopupDisplayExpectationSetter expectIndicatorValueAmbiguous(boolean displayed) {

				return expectIndicator(Indicator.VALUE_AMBIGUOUS, displayed);
			}

			public PopupDisplayExpectationSetter expectIndicatorValueIllegal() {

				return expectIndicatorValueIllegal(true);
			}

			public PopupDisplayExpectationSetter expectIndicatorValueIllegal(boolean displayed) {

				return expectIndicator(Indicator.VALUE_ILLEGAL, displayed);
			}

			public PopupDisplayExpectationSetter expectIndicatorValueMissing() {

				return expectIndicatorValueMissing(true);
			}

			public PopupDisplayExpectationSetter expectIndicatorValueMissing(boolean displayed) {

				return expectIndicator(Indicator.VALUE_MISSING, displayed);
			}

			public PopupDisplayExpectationSetter expectIndicatorValueValid() {

				return expectIndicatorValueValid(true);
			}

			public PopupDisplayExpectationSetter expectIndicatorValueValid(boolean displayed) {

				return expectIndicator(Indicator.VALUE_VALID, displayed);
			}

			public PopupDisplayExpectationSetter expectIndicator(Indicator indicator) {

				return expectIndicator(indicator, true);
			}

			public PopupDisplayExpectationSetter expectIndicator(Indicator indicator, boolean displayed) {

				setExpectedIndicator(indicator);
				setExpectedIndicatorPresence(displayed);
				return new PopupDisplayExpectationSetter();
			}
		}

		public class PopupDisplayExpectationSetter {

			public PopupItemsExpectationSetter expectPopupDisplayed() {

				expectPopup(true);
				return new PopupItemsExpectationSetter();
			}

			public FocusedStateExpectationSetter expectPopupNotDisplayed() {

				expectPopup(false);
				setExpectedPopupEntities(Collections.emptyList());
				setExpectedSelectedEntityNumber(null);
				return new FocusedStateExpectationSetter();
			}

			private void expectPopup(boolean displayed) {

				setExpectedPopupDisplayed(displayed);
			}
		}

		public class PopupItemsExpectationSetter {

			public PopupSelectedItemExpectationSetter expectPopupEntities(AjaxTestEntity...items) {

				return expectPopupEntities(Arrays.asList(items));
			}

			public PopupSelectedItemExpectationSetter expectPopupEntities(List<AjaxTestEntity> items) {

				setExpectedPopupEntities(items);
				return new PopupSelectedItemExpectationSetter();
			}

			public FocusedStateExpectationSetter expectPopupEntitiesNone() {

				setExpectedPopupEntities(Collections.emptyList());
				setExpectedSelectedEntityNumber(null);
				return new FocusedStateExpectationSetter();
			}
		}

		public class PopupSelectedItemExpectationSetter {

			public FocusedStateExpectationSetter expectPopupSelectedItemFirst() {

				return expectPopupSelectedItem(1);
			}

			public FocusedStateExpectationSetter expectPopupSelectedItemLast() {

				return expectPopupSelectedItem(expectedPopupItems.size());
			}

			public FocusedStateExpectationSetter expectPopupSelectedItem(int number) {

				setExpectedSelectedEntityNumber(number);
				return new FocusedStateExpectationSetter();
			}

			public FocusedStateExpectationSetter expectPopupSelectedItemNone() {

				setExpectedSelectedEntityNumber(null);
				return new FocusedStateExpectationSetter();
			}
		}

		public class FocusedStateExpectationSetter {

			public OverlayExpectationSetter expectNoFocus() {

				return expectFocusState(false);
			}

			public OverlayExpectationSetter expectFocus() {

				return expectFocusState(true);
			}

			public OverlayExpectationSetter expectFocusState(boolean focusState) {

				setExpectedFocusState(focusState);
				return new OverlayExpectationSetter();
			}
		}

		public class OverlayExpectationSetter {

			public CallbackExpectationSetter expectOverlayDisplayed() {

				return expectOverlayDisplayed(true);
			}

			public CallbackExpectationSetter expectOverlayNotDisplayed() {

				return expectOverlayDisplayed(false);
			}

			public CallbackExpectationSetter expectOverlayDisplayed(boolean displayed) {

				setExpectedOverlayDisplayed(displayed);
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
				input.assertValues(expectedServerValueExceptionMessage, expectedServerValue, expectedClientValue);
				indicator.assertIndicates(expectedIndicator, expectedIndicatorPresence);
				popup.assertDisplayed(expectedPopupDisplayed);
				input.assertFocusState(expectedFocusState);
				if (expectedPopupDisplayed) {
					popup.assertEntities(expectedPopupItems);
					popup.assertSelectedEntity(expectedSelectedItemNumber);
				}
				overlay.assertDisplayed(expectedOverlayDisplayed);
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

		public InputProxy pressEsc() {

			send(inputFieldElement, Key.ESCAPE);
			return this;
		}

		public InputProxy pressTab() {

			send(inputFieldElement, Key.TAB);
			return this;
		}

		public InputProxy focusWithClick() {

			click(inputFieldElement);
			return this;
		}

		public InputProxy focusWithTab() {

			// TODO assert absence of overlay and popup here
			send(focusPredecessorElement, Key.TAB);
			// TODO assert focus in auto-complete input here
			return this;
		}

		public InputProxy pressDownArrow() {

			send(inputFieldElement, Key.DOWN);
			return this;
		}

		public InputProxy pressDownArrowAndWaitForPopup() {

			pressDownArrow();
			waitForPopup();
			return this;
		}

		public InputProxy pressUpArrow() {

			send(inputFieldElement, Key.UP);
			return this;
		}

		public InputProxy pressUpArrowAndWaitForPopup() {

			pressUpArrow();
			waitForPopup();
			return this;
		}

		public InputProxy waitForLoadingFinished() {

			waitForIndicatorToHide(Indicator.LOADING);
			return this;
		}

		public InputProxy waitForPopupAndLoadingFinished() {

			waitForIndicatorToHide(Indicator.LOADING);
			waitForPopup();
			return this;
		}

		public InputProxy waitForPopup() {

			AbstractAjaxAutoCompleteEntityTest.super.waitForAutoCompletePopup();
			return this;
		}

		public InputProxy waitForNoPopup() {

			// race assumption: this is the maximum amount of time after which the popup would appear
			sleep500();
			return this;
		}

		public InputProxy sleep500() {

			Sleep.sleep(DURATION_500);
			return this;
		}

		public InputProxy waitForServer() {

			AbstractAjaxAutoCompleteEntityTest.super.waitForServer();
			return this;
		}

		public void assertValues(IDisplayString expectedServerValueExceptionMessage, AjaxTestEntity expectedServerValue, String expectedClientValue) {

			if (expectedServerValueExceptionMessage != null) {
				Asserts.assertException(inputNode.getSelection()::getValueOrNull, expectedServerValueExceptionMessage);
				waitForServer();
			} else {
				AjaxTestEntity actualServerValue = inputNode.getSelection().getValueOrNull();
				assertEquals(//
					"Unexpected server-side value.",
					expectedServerValue,
					actualServerValue);
			}

			assertEquals(//
				"Unexpected client-side value.",
				expectedClientValue,
				getAttributeValue(inputFieldElement, "value"));
		}

		public void assertFocusState(boolean focusState) {

			if (focusState) {
				assertTrue(//
					"Unexpected focus state: The auto-complete input element unexpectedly lost focus.",
					isFocused(inputFieldElement));
			} else {
				assertFalse(//
					"Unexpected focus state: The auto-complete input element unexpectedly gained focus.",
					isFocused(inputFieldElement));
			}
		}
	}

	protected class EventTriggerProxy {

		public EventTriggerProxy trigger() {

			click(eventTriggerButton);
			return this;
		}

		public EventTriggerProxy waitForServer() {

			AbstractAjaxAutoCompleteEntityTest.this.waitForServer();
			return this;
		}
	}

	protected class BodyProxy {

		public void click() {

			clickBodyNode();
		}
	}

	protected class PopupProyx {

		public void clickEntityNumber(int number) {

			assertTrue(//
				"The given entity number must not be lower than 1.",
				number >= 1);

			List<String> elementNames = getAutoCompletePopupItemNames();
			assertTrue(//
				String.format("Tried to click on entity %s but the list only contained %s entities.", number, elementNames.size()),
				number <= elementNames.size());

			clickAutoCompletePopupItem(number - 1);
		}

		public void assertDisplayed(boolean displayed) {

			assertEquals(//
				"Unexpected display state of auto-complete popup.",
				displayed,
				isAutoCompletePopupDisplayed());
		}

		public void assertEntities(List<AjaxTestEntity> entities) {

			if (entities.isEmpty()) {
				assertTrue(//
					"Unexpected popup entity count: Expected placeholder entity but did not find it.",
					isAutoCompleteItemPlaceholderElementDisplayed());
			} else {
				assertPopupEntities(entities);
			}
		}

		public void assertSelectedEntity(Integer number) {

			if (number != null) {
				popup.assertSelectedEntityNumber(number);
			} else {
				popup.assertSelectedEntityNone();
			}
		}

		private void assertSelectedEntityNone() {

			assertFalse(//
				"Unexpectedly encountered a selected entity.",
				getAutoCompletePopupSelectedItemIndex().isPresent());
		}

		private void assertSelectedEntityNumber(int number) {

			assertTrue(//
				"The given entity number must not be lower than 1.",
				number >= 1);

			Optional<Integer> selectedItemIndex = getAutoCompletePopupSelectedItemIndex();
			assertTrue(//
				"Failed to identify the selected entity.",
				selectedItemIndex.isPresent());

			List<String> availableItemElementNames = getAutoCompletePopupItemNames();
			assertTrue(//
				String.format("Expected entity number %s to be selected, but only %s entity/ies was/were available.", number, availableItemElementNames.size()),
				availableItemElementNames.size() >= number);

			int selectedIndex = selectedItemIndex.get();
			assertTrue(//
				"Failed to find the selected entity in the list of available entities.",
				selectedIndex >= 0);

			assertEquals(//
				"Unexpected position of selection.",
				number,
				selectedIndex + 1);
		}
	}

	protected class OverlayProxy {

		public OverlayProxy click() {

			assertTrue(//
				"Failed to find modal overlay.",
				isAutoCompleteModalDivDisplayed());
			clickAutoCompleteModalDiv();
			return this;
		}

		public OverlayProxy waitForServer() {

			AbstractAjaxAutoCompleteEntityTest.super.waitForServer();
			return this;
		}

		public void assertDisplayed(boolean displayed) {

			assertEquals(//
				"Unexpected display state of modal overlay.",
				displayed,
				isAutoCompleteModalDivDisplayed());
		}
	}

	protected class CallbackProxy {

		public void assertCalled(AjaxTestEntity value, int count) {

			assertEquals(//
				"Unexpected value for most recent callback.",
				value,
				changeCallback.getLastValue());

			assertEquals(//
				"Unexpected total number of callbacks.",
				count,
				changeCallback.getCount());
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

		private final IDomNode focusPredecessorNode;
		private final DomAutoCompleteEntityInput<AjaxTestEntity> inputNode;
		private final EventTrigger trigger;

		public Container(DomAutoCompleteEntityInput<AjaxTestEntity> inputNode) {

			setStyle(CssDisplay.FLEX);
			setStyle(CssFlexDirection.COLUMN);

			// An element that can hold the focus, such that pressing TAB would move the focus to the tested input element.
			this.focusPredecessorNode = appendChild(createTabTarget("TAB target before the input"));

			this.inputNode = appendChild(inputNode);

			// When TAB is pressed in the tested input element, and if this this TAB press leads to the
			// tested input element losing focus, this dummy element catches the focus. Without this dummy,
			// element the TAB focus would leave the document body and jump to a native browser UI component.
			appendChild(createTabTarget("TAB target after the input"));

			this.trigger = appendChild(new EventTrigger());
		}

		public IDomNode getFocusPredecessorNode() {

			return focusPredecessorNode;
		}

		public DomAutoCompleteEntityInput<AjaxTestEntity> getInputNode() {

			return inputNode;
		}

		public DomButton getEventTriggerNode() {

			return trigger;
		}

		/**
		 * @return an element that can serve as a TAB target
		 */
		private IDomNode createTabTarget(String description) {

			return new DomTextInput(description);
		}

		/**
		 * A DOM node that can trigger an event when clicked.
		 */
		private class EventTrigger extends DomButton {

			public EventTrigger() {

				setLabel(IDisplayString.create("event trigger"));
				setClickCallback(this::handleClick);
			}

			private void handleClick() {

				// does nothing, by design
			}
		}
	}
}
