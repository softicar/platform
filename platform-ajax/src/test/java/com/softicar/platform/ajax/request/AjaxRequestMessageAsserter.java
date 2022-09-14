package com.softicar.platform.ajax.request;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import com.softicar.platform.dom.node.IDomNode;

public class AjaxRequestMessageAsserter {

	private final AjaxRequestMessage message;

	public AjaxRequestMessageAsserter(AjaxRequestMessage message) {

		this.message = message;
	}

	public void assertNoValueParameter(IDomNode node) {

		assertNull(getValueParameter(node));
	}

	public void assertValueParameter(String expectedValue, IDomNode node) {

		assertEquals(expectedValue, getValueParameter(node));
	}

	private String getValueParameter(IDomNode node) {

		return message.getNodeValues().get(node.getNodeIdString());
	}
}
