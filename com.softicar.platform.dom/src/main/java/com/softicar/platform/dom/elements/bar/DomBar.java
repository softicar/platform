package com.softicar.platform.dom.elements.bar;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.node.IDomNode;
import java.util.stream.Stream;

public class DomBar extends DomDiv {

	public DomBar() {

		this(new IDomNode[0]);
	}

	public DomBar(IDomNode...children) {

		addCssClass(DomElementsCssClasses.DOM_BAR);
		Stream.of(children).forEach(this::appendChild);
	}
}
