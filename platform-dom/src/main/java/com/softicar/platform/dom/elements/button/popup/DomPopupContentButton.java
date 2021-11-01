package com.softicar.platform.dom.elements.button.popup;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.popup.DomPopup;
import java.util.function.Supplier;

public class DomPopupContentButton extends DomPopupButton {

	private IDisplayString caption = IDisplayString.EMPTY;
	private Supplier<IDomElement> contentFactory = DomDiv::new;

	public DomPopupContentButton() {

		setPopupFactory(Popup::new);
	}

	public DomPopupContentButton setCaption(IDisplayString caption) {

		this.caption = caption;
		return this;
	}

	public DomPopupContentButton setContentFactory(Supplier<IDomElement> contentFactory) {

		this.contentFactory = contentFactory;
		return this;
	}

	public class Popup extends DomPopup {

		public Popup() {

			setCaption(caption);
			appendChild(contentFactory.get());
		}
	}
}
