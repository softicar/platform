package com.softicar.platform.ajax.framework;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import com.softicar.platform.ajax.customization.IAjaxStrategy;
import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.ajax.simple.SimpleHttpSession;
import com.softicar.platform.ajax.simple.SimpleServletRequest;
import com.softicar.platform.ajax.simple.SimpleServletResponse;
import com.softicar.platform.common.testing.AbstractTest;
import javax.servlet.ServletContext;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

public class AjaxFrameworkTest extends AbstractTest {

	private final SimpleServletResponse response;
	private final SimpleServletRequest request;
	private final IAjaxStrategy ajaxStrategy;
	private final AjaxFramework ajaxFramework;

	public AjaxFrameworkTest() {

		this.request = new SimpleServletRequest();
		this.request.setMethod("GET");
		this.request.setSession(new SimpleHttpSession(""));
		this.response = new SimpleServletResponse();
		this.ajaxStrategy = Mockito.mock(IAjaxStrategy.class);
		this.ajaxFramework = new AjaxFramework(ajaxStrategy);
	}

	@Test
	public void testServiceWithoutInitialize() {

		ajaxFramework.service(request, response);
		assertEquals(500, response.getStatus());
	}

	@Test
	public void testServiceWithDocumentCreation() {

		ajaxFramework.initialize(Mockito.mock(ServletContext.class));
		ajaxFramework.service(request, response);

		InOrder inOrder = inOrder(ajaxStrategy);
		inOrder.verify(ajaxStrategy).beforeDocumentRequestHandling(any(IAjaxDocument.class), any(IAjaxRequest.class));
		inOrder.verify(ajaxStrategy).buildDocument(any(IAjaxDocument.class));
	}
}
