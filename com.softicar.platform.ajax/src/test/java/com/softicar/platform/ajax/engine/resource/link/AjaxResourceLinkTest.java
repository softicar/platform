package com.softicar.platform.ajax.engine.resource.link;

import static org.junit.Assert.assertEquals;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.ResourceUrl;
import com.softicar.platform.dom.elements.DomLink.Relationship;
import org.junit.Test;

public class AjaxResourceLinkTest {

	@Test
	public void testToHtml() {

		AjaxResourceLink resourceLink = new AjaxResourceLink(new ResourceUrl("abc"), Relationship.STYLESHEET, MimeType.TEXT_CSS);

		assertEquals("<link href='abc' rel='stylesheet' type='text/css'>", resourceLink.toHtml());
	}
}
