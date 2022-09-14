package com.softicar.platform.ajax.engine.resource.link;

import com.softicar.platform.ajax.document.AjaxDocument;
import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.framework.AjaxFramework;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.ajax.simple.SimpleHttpSession;
import com.softicar.platform.ajax.testing.AjaxTestingStrategy;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.IResourceUrl;
import com.softicar.platform.common.io.resource.ResourceUrl;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.DomHead;
import com.softicar.platform.dom.elements.DomLink;
import com.softicar.platform.dom.elements.DomLink.Relationship;
import javax.servlet.http.HttpServletRequest;
import org.junit.Test;
import org.mockito.Mockito;

public class AjaxResourceLinkRegistryTest extends AbstractTest {

	private final IAjaxDocument document;
	private final AjaxResourceLinkRegistry registry;
	private final IResourceUrl resourceUrl1;
	private final IResourceUrl resourceUrl2;
	private final DomHead head;

	public AjaxResourceLinkRegistryTest() {

		IAjaxRequest request = Mockito.mock(IAjaxRequest.class);
		Mockito.when(request.getHttpRequest()).thenReturn(Mockito.mock(HttpServletRequest.class));
		Mockito.when(request.getHttpSession()).thenReturn(new SimpleHttpSession(""));
		Mockito.when(request.getAjaxFramework()).thenReturn(new AjaxFramework(new AjaxTestingStrategy()));

		this.document = new AjaxDocument(request);
		this.registry = new AjaxResourceLinkRegistry(document);
		this.head = document.getHead();

		CurrentDomDocument.set(document);
		this.resourceUrl1 = new ResourceUrl("foo");
		this.resourceUrl2 = new ResourceUrl("bar");
	}

	@Test
	public void testRegisterLink() {

		int childCount = head.getChildCount();

		registry.registerLink(resourceUrl1, Relationship.STYLESHEET, MimeType.TEXT_CSS);
		registry.registerLink(resourceUrl2, Relationship.STYLESHEET, MimeType.TEXT_CSS);

		assertEquals(childCount + 2, head.getChildCount());
		DomLink link1 = (DomLink) head.getChild(childCount);
		DomLink link2 = (DomLink) head.getChild(childCount + 1);

		assertEquals(resourceUrl1.toString(), link1.getAttribute("href").getValue());
		assertEquals(Relationship.STYLESHEET.toString(), link1.getAttribute("rel").getValue());
		assertEquals(MimeType.TEXT_CSS.toString(), link1.getAttribute("type").getValue());

		assertEquals(resourceUrl2.toString(), link2.getAttribute("href").getValue());
		assertEquals(Relationship.STYLESHEET.toString(), link2.getAttribute("rel").getValue());
		assertEquals(MimeType.TEXT_CSS.toString(), link2.getAttribute("type").getValue());
	}

	@Test
	public void testRegisterLinkWithRedundantCalls() {

		int childCount = head.getChildCount();

		registry.registerLink(resourceUrl1, Relationship.STYLESHEET, MimeType.TEXT_CSS);
		registry.registerLink(resourceUrl1, Relationship.ICON, MimeType.IMAGE_PNG);
		registry.registerLink(resourceUrl1, Relationship.PRELOAD, MimeType.TEXT_CSS);

		assertEquals(childCount + 1, head.getChildCount());
		DomLink link = (DomLink) head.getChild(childCount);

		assertEquals(resourceUrl1.toString(), link.getAttribute("href").getValue());
		assertEquals(Relationship.STYLESHEET.toString(), link.getAttribute("rel").getValue());
		assertEquals(MimeType.TEXT_CSS.toString(), link.getAttribute("type").getValue());
	}

	@Test
	public void testSetDomLinkCreationEnabled() {

		int childCount = head.getChildCount();

		registry.setDomLinkCreationEnabled(false);
		registry.registerLink(resourceUrl1, Relationship.STYLESHEET, MimeType.TEXT_CSS);
		assertEquals(childCount, head.getChildCount());

		registry.setDomLinkCreationEnabled(true);
		registry.registerLink(resourceUrl1, Relationship.STYLESHEET, MimeType.TEXT_CSS);
		assertEquals(childCount, head.getChildCount());

		AjaxResourceLink resourceLink = assertOne(registry.getResourceLinks());
		assertEquals("<link href='foo' rel='stylesheet' type='text/css'>", resourceLink.toHtml());
	}
}
