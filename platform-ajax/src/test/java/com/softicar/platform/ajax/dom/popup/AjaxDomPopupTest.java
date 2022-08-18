package com.softicar.platform.ajax.dom.popup;

import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestPoint;
import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestRectangle;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.utils.TestButton;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.DomPopupFrame;
import com.softicar.platform.dom.elements.popup.position.DomPopupPosition;
import com.softicar.platform.dom.engine.DomPopupXAlign;
import com.softicar.platform.dom.engine.DomPopupYAlign;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.input.DomTextInput;
import com.softicar.platform.dom.parent.IDomParentElement;
import com.softicar.platform.dom.style.CssPixel;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssLength;
import org.junit.Assert;
import org.junit.Test;

public class AjaxDomPopupTest extends AbstractAjaxSeleniumLowLevelTest {

	private static final int X = 13;
	private static final int Y = 30;
	private static final ICssLength WIDTH = new CssPixel(100);
	private static final ICssLength HEIGHT = new CssPixel(50);
	private PopupButton button1;
	private PopupButton button2;
	private final Popup popup1;
	private final Popup popup2;

	public AjaxDomPopupTest() {

		openTestNode(TestDiv::new);
		this.popup1 = button1.getPopup();
		this.popup2 = button2.getPopup();
	}

	@Test
	public void test() {

		// precondition: popup is not in tree
		assertFalse(findNode(popup1).isPresent());

		// click to show popup
		click(button1);
		waitForServer();

		// assert popup is shown at desired location
		assertTrue(isDisplayed(popup1));
		AjaxSeleniumTestPoint frameLocation = getLocation(getFrame(popup1));
		Assert.assertEquals(X, frameLocation.getX());
		Assert.assertEquals(Y, frameLocation.getY());

		// click to hide popup
		click(button1);
		waitForServer();
		assertFalse(findNode(popup1).isPresent());
	}

	@Test
	public void testZIndexOnClick() {

		// precondition: pop-ups are not in tree
		assertFalse(findNode(popup1).isPresent());
		assertFalse(findNode(popup2).isPresent());

		// determine y position for pop-ups
		AjaxSeleniumTestRectangle rectangle = getRectangle(button2);
		int y = rectangle.getY() + rectangle.getHeight();

		// click to show pop-ups
		button1.setPosition(10, y + 10);
		Double height = HEIGHT.getValue();
		button2.setPosition(10, y + 10 + height.intValue() + 10);
		click(button1);
		click(button2);
		waitForServer();

		// assert initial z-index
		// (because of the working indicator, the z-index is 2 levels higher)
		assertTrue(getZIndex(popup2) > getZIndex(popup1));

		// assert clicking foreground pop-up does not change z-index
		int zIndex = getZIndex(popup2);
		clickAt(popup2, 0, 0);
		assertEquals(zIndex, getZIndex(popup2));

		// assert clicking background pop-up allocates z-index
		clickAt(popup1, 0, 0);
		assertEquals(getZIndex(popup2) + 1, getZIndex(popup1));
	}

	@Test
	public void testTextInputFocusedAfterOpen() {

		// preparation
		var textInput1 = new DomTextInput();
		var textInput2 = new DomTextInput();
		popup1.appendChild(textInput1);
		popup1.appendChild(textInput2);

		// precondition: popup is not in tree
		assertFalse(findNode(popup1).isPresent());

		// click to show popup
		click(button1);
		waitForServer();

		// assert focus
		assertFocused(textInput1);
	}

	private int getZIndex(DomPopup popup) {

		return Integer.parseInt(getCssAttributeValue(getFrame(popup), "z-index"));
	}

	private DomPopupFrame getFrame(DomPopup popup) {

		IDomParentElement parent = popup.getParent();
		if (parent.hasMarker(DomTestMarker.POPUP_FRAME)) {
			return (DomPopupFrame) popup.getParent();
		} else {
			throw new AssertionError();
		}
	}

	private class TestDiv extends DomDiv {

		public TestDiv() {

			button1 = appendChild(new PopupButton());
			button2 = appendChild(new PopupButton());
		}
	}

	private class PopupButton extends TestButton {

		private final Popup popup;
		private boolean closed;
		private int x;
		private int y;

		public PopupButton() {

			this.popup = new Popup();
			this.popup.configure(it -> it.setPositionStrategy(this::createPosition));
			this.closed = true;
			this.x = X;
			this.y = Y;

			setClickCallback(this::togglePopup);
		}

		public void setPosition(int x, int y) {

			this.x = x;
			this.y = y;
		}

		public Popup getPopup() {

			return popup;
		}

		private DomPopupPosition createPosition(IDomEvent dummy) {

			DevNull.swallow(dummy);
			return new DomPopupPosition(x, y, DomPopupXAlign.LEFT, DomPopupYAlign.TOP);
		}

		private void togglePopup() {

			if (closed) {
				popup.open();
				this.closed = false;
			} else {
				popup.close();
				this.closed = true;
			}
		}
	}

	private class Popup extends DomPopup {

		public Popup() {

			setStyle(CssStyle.WIDTH, WIDTH);
			setStyle(CssStyle.HEIGHT, HEIGHT);
		}
	}
}
