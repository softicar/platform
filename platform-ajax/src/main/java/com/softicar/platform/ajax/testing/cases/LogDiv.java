package com.softicar.platform.ajax.testing.cases;

import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.style.CssColorEnum;

public class LogDiv extends DomDiv {

	public LogDiv() {

		setColor(CssColorEnum.GRAY);
	}

	public void log(String text, Object...arguments) {

		appendText(text, arguments);
		appendNewChild(DomElementTag.BR);
	}
}
