package com.softicar.platform.emf.form.section.exception;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.wiki.DomWikiDivBuilder;
import com.softicar.platform.emf.EmfI18n;

public class EmfFormExceptionDiv extends DomDiv {

	public EmfFormExceptionDiv(Exception exception, INullaryVoidFunction okayCallback) {

		appendChild(
			new DomWikiDivBuilder()//
				.addText(IDisplayString.create(exception.getMessage()).enclose("<error>", "</error>"))
				.build());

		appendNewChild(DomElementTag.HR);

		appendChild(new DomActionBar())//
			.appendChild(
				new DomButton()//
					.setLabel(EmfI18n.OK)
					.setIcon(DomElementsImages.DIALOG_OKAY.getResource())
					.setClickCallback(okayCallback));
	}
}
