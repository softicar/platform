package com.softicar.platform.dom.elements.tables;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.elements.DomTBody;
import com.softicar.platform.dom.elements.DomTHead;
import com.softicar.platform.dom.elements.DomTable;

public class DomDataTable extends DomTable {

	private final DomTHead head;
	private final DomTBody body;

	public DomDataTable() {

		setCssClass(DomCssClasses.DOM_DATA_TABLE);

		appendChild(head = new DomTHead());
		appendChild(body = new DomTBody());
	}

	public DomTHead getHead() {

		return head;
	}

	public DomTBody getBody() {

		return body;
	}
}
