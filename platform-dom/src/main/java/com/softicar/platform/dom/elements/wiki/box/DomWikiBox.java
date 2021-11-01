package com.softicar.platform.dom.elements.wiki.box;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.ui.wiki.element.tag.WikiBoxType;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;

public class DomWikiBox extends DomMessageDiv {

	private final DomDiv contentDiv;

	public DomWikiBox(WikiBoxType type) {

		super(getMessageType(type));
		this.contentDiv = appendChild(new DomDiv());
	}

	public DomDiv getContentDiv() {

		return contentDiv;
	}

	private static DomMessageType getMessageType(WikiBoxType type) {

		switch (type) {
		case ERROR:
			return DomMessageType.ERROR;
		case INFO:
			return DomMessageType.INFO;
		case WARNING:
			return DomMessageType.WARNING;
		}
		throw new SofticarUnknownEnumConstantException(type);
	}
}
