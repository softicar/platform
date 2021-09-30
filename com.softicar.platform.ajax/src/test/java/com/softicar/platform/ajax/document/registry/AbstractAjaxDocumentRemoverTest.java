package com.softicar.platform.ajax.document.registry;

import com.softicar.platform.ajax.customization.IAjaxStrategy;
import com.softicar.platform.ajax.document.AjaxDocument;
import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.framework.AjaxFramework;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.common.testing.AbstractTest;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import javax.servlet.http.HttpSession;
import org.mockito.Mockito;

class AbstractAjaxDocumentRemoverTest extends AbstractTest {

	protected final Map<UUID, IAjaxDocument> documents;
	private final IAjaxRequest request;

	public AbstractAjaxDocumentRemoverTest() {

		this.documents = new TreeMap<>();
		this.request = Mockito.mock(IAjaxRequest.class);

		AjaxFramework ajaxFramework = new AjaxFramework(Mockito.mock(IAjaxStrategy.class));
		Mockito.when(request.getAjaxFramework()).thenReturn(ajaxFramework);

		HttpSession session = Mockito.mock(HttpSession.class);
		Mockito.when(request.getHttpSession()).thenReturn(session);

		Mockito.when(session.getAttribute(AjaxDocumentRegistry.class.getCanonicalName())).thenReturn(new AjaxDocumentRegistry());
	}

	protected AjaxDocument createDocument() {

		return new AjaxDocument(request);
	}

	protected void addDocument(IAjaxDocument document) {

		documents.put(document.getInstanceUuid(), document);
	}

	protected void assertDocumentDoesExist(IAjaxDocument document) {

		assertTrue(documents.containsKey(document.getInstanceUuid()));
		assertSame(document, documents.get(document.getInstanceUuid()));
	}

	protected void assertDocumentDoesNotExist(IAjaxDocument document) {

		assertFalse(documents.containsKey(document.getInstanceUuid()));
		assertNull(documents.get(document.getInstanceUuid()));
	}
}
