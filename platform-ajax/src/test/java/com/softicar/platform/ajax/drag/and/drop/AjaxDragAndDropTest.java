package com.softicar.platform.ajax.drag.and.drop;

import com.softicar.platform.ajax.engine.AjaxDomEngine;
import com.softicar.platform.ajax.testing.selenium.engine.common.geometry.AjaxSeleniumTestPoint;
import com.softicar.platform.ajax.testing.selenium.engine.level.low.AbstractAjaxSeleniumLowLevelTest;
import com.softicar.platform.dom.elements.DomColorEnum;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.style.CssPixel;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.styles.CssPosition;
import org.junit.Test;

/**
 * This class tests the drag-and-drop feature of the {@link AjaxDomEngine}.
 *
 * @author Oliver Richers
 */
public class AjaxDragAndDropTest extends AbstractAjaxSeleniumLowLevelTest {

	@Test
	public void testDragging() {

		TestDiv testDiv = openTestNode(TestDiv::new);
		DomDiv targetDiv = testDiv.getDragTargetDiv();
		DomDiv anchorDiv = testDiv.getDragAnchorDiv();

		AjaxSeleniumTestPoint initialPosition = getRectangle(targetDiv).getLocation();
		final int xOffset = 13;
		final int yOffset = 17;

		dragAndDrop(anchorDiv, xOffset, yOffset);

		AjaxSeleniumTestPoint finalPosition = getRectangle(targetDiv).getLocation();

		assertEquals(initialPosition.getX() + xOffset, finalPosition.getX());
		assertEquals(initialPosition.getY() + yOffset, finalPosition.getY());
	}

	private class TestDiv extends DomDiv {

		private final DomDiv dragTargetDiv;
		private final DomDiv dragAnchorDiv;

		public TestDiv() {

			setStyle(CssPosition.RELATIVE);

			this.dragTargetDiv = appendChild(new Div(200, 100));
			this.dragTargetDiv.setBackgroundColor(DomColorEnum.RED);
			this.dragTargetDiv.setStyle(CssPosition.ABSOLUTE);
			this.dragAnchorDiv = dragTargetDiv.appendChild(new Div(50, 50));
			this.dragAnchorDiv.setBackgroundColor(DomColorEnum.YELLOW);

			getDomEngine().makeDraggable(dragTargetDiv, dragAnchorDiv);
		}

		public DomDiv getDragTargetDiv() {

			return dragTargetDiv;
		}

		public DomDiv getDragAnchorDiv() {

			return dragAnchorDiv;
		}

		private class Div extends DomDiv {

			public Div(int width, int height) {

				setStyle(CssStyle.WIDTH, new CssPixel(width));
				setStyle(CssStyle.HEIGHT, new CssPixel(height));
			}
		}
	}
}
