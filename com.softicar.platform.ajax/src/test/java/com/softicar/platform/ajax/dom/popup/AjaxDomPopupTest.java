package com.softicar.platform.ajax.dom.popup;

import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestPoint;
import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestRectangle;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.ajax.utils.TestButton;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.engine.DomPopupXAlign;
import com.softicar.platform.dom.engine.DomPopupYAlign;
import com.softicar.platform.dom.node.IDomNode;
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
	private final PopupDiv popup1;
	private final PopupDiv popup2;

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
		AjaxSeleniumTestPoint location = getLocation(popup1);
		Assert.assertEquals(X, location.getX());
		Assert.assertEquals(Y, location.getY());

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
		click(popup2);
		assertEquals(zIndex, getZIndex(popup2));

		// assert clicking background pop-up allocates z-index
		click(popup1);
		assertEquals(getZIndex(popup2) + 1, getZIndex(popup1));
	}

	private int getZIndex(IDomNode node) {

		return Integer.parseInt(getCssAttributeValue(node, "z-index"));
	}

	private class TestDiv extends DomDiv {

		public TestDiv() {

			button1 = appendChild(new PopupButton());
			button2 = appendChild(new PopupButton());
		}
	}

	private class PopupButton extends TestButton {

		private final PopupDiv popupDiv;
		private boolean hidden;
		private int x;
		private int y;

		public PopupButton() {

			this.popupDiv = new PopupDiv();
			this.hidden = true;
			this.x = X;
			this.y = Y;

			setClickCallback(this::openPopup);
		}

		public void setPosition(int x, int y) {

			this.x = x;
			this.y = y;
		}

		public PopupDiv getPopup() {

			return popupDiv;
		}

		private void openPopup() {

			if (hidden) {
				getDomDocument().getBody().appendChild(popupDiv);
				getDomEngine().showPopup(popupDiv, x, y, DomPopupXAlign.LEFT, DomPopupYAlign.TOP);
				this.hidden = false;
			} else {
				getDomEngine().hidePopup(popupDiv);
				getDomDocument().getBody().removeChild(popupDiv);
				this.hidden = true;
			}
		}
	}

	private class PopupDiv extends DomDiv {

		public PopupDiv() {

			setStyle(CssStyle.WIDTH, WIDTH);
			setStyle(CssStyle.HEIGHT, HEIGHT);
		}
	}
}
