package com.softicar.platform.emf.attribute.field.foreign.entity.collection;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.wiki.DomWikiDivBuilder;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.styles.CssDisplay;
import com.softicar.platform.emf.EmfI18n;

public class EmfUnsavedChangedMessageDiv extends DomDiv {

	public EmfUnsavedChangedMessageDiv() {

		new DomWikiDivBuilder(this)//
			.addLine(EmfI18n.THERE_ARE_UNSAVED_CHANGES.enclose("<warning>", "</warning>"))
			.build();
	}

	public EmfUnsavedChangedMessageDiv hide() {

		setStyle(CssDisplay.NONE);
		return this;
	}

	public EmfUnsavedChangedMessageDiv show() {

		unsetStyle(CssStyle.DISPLAY);
		return this;
	}
}
