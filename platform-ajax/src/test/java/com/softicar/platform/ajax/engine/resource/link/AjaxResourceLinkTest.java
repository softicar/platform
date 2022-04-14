package com.softicar.platform.ajax.engine.resource.link;

import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.ResourceUrl;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.elements.DomLink.Relationship;
import org.junit.Test;

public class AjaxResourceLinkTest extends AbstractTest {

	@Test
	public void testToHtml() {

		AjaxResourceLink resourceLink = new AjaxResourceLink(new ResourceUrl("abc"), Relationship.STYLESHEET, MimeType.TEXT_CSS);

		assertEquals("<link href='abc' rel='stylesheet' type='text/css'>", resourceLink.toHtml());
	}
}
