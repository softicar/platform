package com.softicar.platform.dom.elements.message;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.elements.message.style.DomMessageType;
import com.softicar.platform.dom.node.IDomNode;
import java.util.List;
import java.util.Objects;

public class DomMessageDiv extends DomDiv {

	public DomMessageDiv(DomMessageType type) {

		Objects.requireNonNull(type);
		addCssClass(DomCssClasses.DOM_MESSAGE_DIV);
		addCssClass(type.getStyleClass());
		appendChild(new DomImage(type.getIcon()));
	}

	public DomMessageDiv(DomMessageType type, IDisplayString message) {

		this(type, createMessageNode(message));
	}

	public DomMessageDiv(DomMessageType type, IDomNode messageNode) {

		this(type);
		Objects.requireNonNull(messageNode);
		appendChild(messageNode);
	}

	private static IDomNode createMessageNode(IDisplayString message) {

		DomDiv messageDiv = new DomDiv();
		for (String line: List.of(message.toString().split("\n"))) {
			messageDiv.appendText(line);
			messageDiv.appendNewChild(DomElementTag.BR);
		}
		return messageDiv;
	}
}
