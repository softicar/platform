package com.softicar.platform.ajax.testing.cases;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.Consumers;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.dom.elements.popup.DomPopup;
import java.util.function.Consumer;

public class PopupTestCase extends AbstractTestCaseDiv {

	private Consumer<DomDiv> popupSetup = Consumers.noOperation();

	public PopupTestCase() {

		appendChild(new DomActionBar(new PopupButton()));
	}

	public void setPopupSetup(Consumer<DomDiv> popupSetup) {

		this.popupSetup = popupSetup;
	}

	private class PopupButton extends DomPopupButton {

		public PopupButton() {

			setIcon(DomImages.EMBLEM_AUTO_COMPLETE_VALUE_VALID.getResource());
			setLabel(IDisplayString.create("Click to Open Popup"));
			setPopupFactory(Popup::new);
		}
	}

	private class Popup extends DomPopup {

		public Popup() {

			setCaption(IDisplayString.create("Popup"));
			popupSetup.accept(this);
		}
	}
}
