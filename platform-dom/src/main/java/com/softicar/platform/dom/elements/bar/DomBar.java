package com.softicar.platform.dom.elements.bar;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.node.IDomNode;
import java.util.stream.Stream;

public class DomBar extends DomDiv {

	public DomBar(IDomNode...children) {

		addCssClass(DomCssClasses.DOM_BAR);
		Stream.of(children).forEach(this::appendChild);
	}
}
