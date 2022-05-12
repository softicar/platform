package com.softicar.platform.ajax.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import com.softicar.platform.dom.node.IDomNode;

public class AjaxRequestAsserter {

	private final IAjaxRequest request;

	public AjaxRequestAsserter(IAjaxRequest request) {

		this.request = request;
	}

	public void assertNoValueParameter(IDomNode node) {

		assertNull(getValueParameter(node));
	}

	public void assertValueParameter(String expectedValue, IDomNode node) {

		assertEquals(expectedValue, getValueParameter(node));
	}

	private String getValueParameter(IDomNode node) {

		return request.getParameter("V" + node.getNodeId());
	}
}
