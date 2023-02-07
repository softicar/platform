package com.softicar.platform.ajax.engine;

import com.softicar.platform.ajax.customization.AbstractAjaxStrategy;
import com.softicar.platform.ajax.document.AjaxDocument;
import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.framework.AjaxFramework;
import com.softicar.platform.ajax.request.AjaxRequest;
import com.softicar.platform.ajax.simple.SimpleHttpSession;
import com.softicar.platform.ajax.simple.SimpleServletRequest;
import com.softicar.platform.ajax.simple.SimpleServletResponse;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.IResourceUrl;
import com.softicar.platform.common.io.resource.hash.ResourceHash;
import com.softicar.platform.common.testing.AbstractTest;
import java.util.Optional;
import org.junit.Test;
import org.mockito.Mockito;

public class AjaxDomEngineGetResourceUrlTest extends AbstractTest {

	private final AjaxDomEngine engine;

	public AjaxDomEngineGetResourceUrlTest() {

		AjaxFramework framework = new AjaxFramework(new TestStrategy());
		SimpleServletRequest servletRequest = new SimpleServletRequest().setSession(new SimpleHttpSession(""));
		SimpleServletResponse servletResponse = new SimpleServletResponse();
		AjaxRequest request = new AjaxRequest(framework, servletRequest, servletResponse);
		AjaxDocument document = new AjaxDocument(request);
		this.engine = new AjaxDomEngine(document);
	}

	@Test
	public void testGetResourceUrlWithHash() {

		IResource resource = Mockito.mock(IResource.class);
		Mockito.when(resource.getContentHash()).thenReturn(Optional.of(new ResourceHash("abc")));

		IResourceUrl resourceUrl = engine.getResourceUrl(resource);

		assertEquals("?resourceHash=abc", resourceUrl.toString());
	}

	@Test
	public void testGetResourceUrlWithoutHash() {

		IResource resource = Mockito.mock(IResource.class);
		Mockito.when(resource.getContentHash()).thenReturn(Optional.empty());

		IResourceUrl resourceUrl = engine.getResourceUrl(resource);

		assertEquals("?resourceId=1", resourceUrl.toString());
	}

	private static class TestStrategy extends AbstractAjaxStrategy {

		@Override
		public void buildDocument(IAjaxDocument document) {

			// nothing to do
		}
	}
}
