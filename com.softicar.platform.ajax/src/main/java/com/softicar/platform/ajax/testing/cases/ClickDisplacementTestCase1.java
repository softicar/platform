package com.softicar.platform.ajax.testing.cases;

import com.softicar.platform.common.ui.color.RgbColor;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.dom.style.CssPixel;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.styles.CssCursor;
import com.softicar.platform.dom.styles.CssDisplay;
import com.softicar.platform.dom.styles.CssOverflow;
import com.softicar.platform.dom.styles.CssPosition;

public class ClickDisplacementTestCase1 extends AbstractTestCaseDiv {

	public ClickDisplacementTestCase1() {

		appendChild(new ClickableElement());
	}

	private class ClickableElement extends DomDiv implements IDomClickEventHandler {

		public ClickableElement() {

			setStyle(CssDisplay.INLINE_BLOCK);

			DomDiv div = appendChild(new DomDiv());
			div.appendText("This is a text box with hidden overflow. This is problematic in Firefox.");
			div.setStyle(CssStyle.MAX_WIDTH, new CssPixel(100));
			div.setStyle(CssOverflow.HIDDEN);

			setBackgroundColor(new RgbColor(0xFFC0C0));

			setStyle(CssCursor.POINTER);
			setStyle(CssPosition.RELATIVE);
		}

		@Override
		public void handleClick(IDomEvent event) {

			log("clicked");
		}
	}
}
