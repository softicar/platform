package com.softicar.platform.dom.elements.button.popup;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.interfaces.IStaticObject;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.DomPopupFrame;
import com.softicar.platform.dom.elements.popup.compositor.DomParentNodeFinder;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngineMethods;
import com.softicar.platform.dom.elements.testing.engine.document.DomDocumentTestExecutionEngine;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.dom.node.IDomNode;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

public class DomPopupButtonTest extends AbstractTest implements IDomTestExecutionEngineMethods {

	private static final IStaticObject POPUP = Mockito.mock(IStaticObject.class);
	private static final IStaticObject OTHER_POPUP = Mockito.mock(IStaticObject.class);
	private static final IStaticObject TEXT_INPUT = Mockito.mock(IStaticObject.class);

	@Rule public IDomTestExecutionEngine engine = new DomDocumentTestExecutionEngine();

	private final Callback callbackBeforeOpen;
	private final Callback callbackAfterOpen;
	private final DomPopupButton button;
	private final DomPopupButton otherButton;

	public DomPopupButtonTest() {

		this.callbackBeforeOpen = new Callback();
		this.callbackAfterOpen = new Callback();
		this.button = new DomPopupButton();
		this.otherButton = new DomPopupButton();
		var testDiv = new DomDiv();
		testDiv.appendChild(button);
		testDiv.appendChild(otherButton);
		setNodeSupplier(() -> testDiv);

		// Ensure proper initialization before trying to open a DomPopup.
		// TODO This should not be necessary.
		findBody();
	}

	@Override
	public IDomTestExecutionEngine getEngine() {

		return engine;
	}

	@Test
	public void testOpen() {

		button.setPopupFactory(TestPopup::new);

		openPopup();

		assertPopupPresent();
	}

	@Test
	public void testOpenWithCallbacks() {

		button.setPopupFactory(TestPopup::new);
		button.setCallbackBeforeOpen(callbackBeforeOpen);
		button.setCallbackAfterOpen(callbackAfterOpen);

		openPopup();

		assertPopupPresent();
		callbackBeforeOpen.assertCalled(1);
		callbackAfterOpen.assertCalled(1);
	}

	@Test
	public void testOpenAndOpen() {

		button.setPopupFactory(TestPopup::new);
		button.setCallbackBeforeOpen(callbackBeforeOpen);
		button.setCallbackAfterOpen(callbackAfterOpen);
		openPopup();
		findTextInput().setInputValue("foo");

		openPopup();

		assertPopupPresent();
		// Ensure that the input value was retained.
		assertEquals("foo", findTextInput().getInputValue());
		callbackBeforeOpen.assertCalled(1);
		callbackAfterOpen.assertCalled(1);
	}

	@Test
	public void testOpenAndOpenWithRetainOpenFalse() {

		button.setPopupFactory(TestPopup::new);
		button.setCallbackBeforeOpen(callbackBeforeOpen);
		button.setCallbackAfterOpen(callbackAfterOpen);
		button.setRetainOpen(false);
		openPopup();
		findTextInput().setInputValue("foo");

		openPopup();

		List<DomNodeTester> popups = assertPopupsPresent(2);

		// Ensure that the input value was retained.
		assertEquals("foo", popups.get(0).findInput(TEXT_INPUT).getInputValue());
		assertEquals("", popups.get(1).findInput(TEXT_INPUT).getInputValue());
		callbackBeforeOpen.assertCalled(2);
		callbackAfterOpen.assertCalled(2);
	}

	@Test
	public void testOpenAndClose() {

		button.setPopupFactory(TestPopup::new);
		button.setCallbackBeforeOpen(callbackBeforeOpen);
		button.setCallbackAfterOpen(callbackAfterOpen);
		openPopup();

		closePopup();

		assertPopupNotPresent();
		callbackBeforeOpen.assertCalled(1);
		callbackAfterOpen.assertCalled(1);
	}

	@Test
	public void testOpenAndCloseAndOpenAgain() {

		button.setPopupFactory(TestPopup::new);
		button.setCallbackBeforeOpen(callbackBeforeOpen);
		button.setCallbackAfterOpen(callbackAfterOpen);
		openPopup();
		closePopup();

		openPopup();

		assertPopupPresent();
		callbackBeforeOpen.assertCalled(2);
		callbackAfterOpen.assertCalled(2);
	}

	@Test
	public void testOpenAndOpenOther() {

		button.setPopupFactory(TestPopup::new);
		otherButton.setPopupFactory(OtherTestPopup::new);
		openPopup();
		openOtherPopup();

		DomNodeTester popup = assertPopupPresent();
		DomNodeTester otherPopup = assertOtherPopupPresent();

		assertEquals(1, getZIndex(popup));
		assertEquals(2, getZIndex(otherPopup));
	}

	@Test
	public void testOpenAndOpenOtherAndOpen() {

		button.setPopupFactory(TestPopup::new);
		otherButton.setPopupFactory(OtherTestPopup::new);
		openPopup();
		openOtherPopup();

		openPopup();

		DomNodeTester popup = assertPopupPresent();
		DomNodeTester otherPopup = assertOtherPopupPresent();

		assertEquals(3, getZIndex(popup));
		assertEquals(2, getZIndex(otherPopup));
	}

	@Test
	public void testOpenWithButtonDefaults() {

		openPopup();

		assertPopupNotPresent();
	}

	private void openPopup() {

		asTester(button).click();
	}

	private void openOtherPopup() {

		asTester(otherButton).click();
	}

	private void closePopup() {

		findButton(DomTestMarker.POPUP_FRAME_CLOSE_BUTTON).click();
	}

	private DomNodeTester assertPopupPresent() {

		return findNodes(POPUP).assertOne();
	}

	private List<DomNodeTester> assertPopupsPresent(int size) {

		return findNodes(POPUP).assertSize(size);
	}

	private DomNodeTester assertOtherPopupPresent() {

		return findNodes(OTHER_POPUP).assertOne();
	}

	private void assertPopupNotPresent() {

		findNodes(POPUP).assertNone();
	}

	private DomNodeTester findTextInput() {

		return findInput(TEXT_INPUT);
	}

	private int getZIndex(DomNodeTester nodeTester) {

		IDomNode node = nodeTester.getNode();
		if (node instanceof DomPopup) {
			node = new DomParentNodeFinder<>(DomPopupFrame.class).findClosestParent(node).get();
		}
		return asTester(node).getZIndex();
	}

	private static class TestPopup extends DomPopup {

		public TestPopup() {

			addMarker(POPUP);
			appendChild(new DomTextInput()).addMarker(TEXT_INPUT);
		}
	}

	private static class OtherTestPopup extends DomPopup {

		public OtherTestPopup() {

			addMarker(OTHER_POPUP);
		}
	}

	private static class Callback implements INullaryVoidFunction {

		private int count;

		public Callback() {

			this.count = 0;
		}

		@Override
		public void apply() {

			this.count++;
		}

		public void assertCalled(int expectedCount) {

			assertEquals(expectedCount, count);
		}
	}
}
