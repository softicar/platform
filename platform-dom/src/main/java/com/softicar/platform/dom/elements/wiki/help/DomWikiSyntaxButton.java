package com.softicar.platform.dom.elements.wiki.help;

import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.elements.button.popup.DomPopupContentButton;
import com.softicar.platform.dom.elements.wiki.DomWikiI18n;

public class DomWikiSyntaxButton extends DomPopupContentButton {

	public DomWikiSyntaxButton() {

		setIcon(DomImages.INFO.getResource());
		setLabel(DomWikiI18n.WIKI_SYNTAX);

		setCaption(DomWikiI18n.SOFTICAR_WIKI_SYNTAX);
		setContentFactory(DomWikiSyntaxDiv::new);
	}
}
