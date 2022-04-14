package com.softicar.platform.ajax.document.registry;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.common.testing.AbstractTest;
import java.util.Optional;
import java.util.UUID;
import org.junit.Test;
import org.mockito.Mockito;

public class AjaxDocumentRegistryTest extends AbstractTest {

	private final AjaxDocumentRegistry registry;

	public AjaxDocumentRegistryTest() {

		this.registry = new AjaxDocumentRegistry();
	}

	@Test
	public void testRegisterAndGetDocument() {

		IAjaxDocument document1 = Mockito.mock(IAjaxDocument.class);
		IAjaxDocument document2 = Mockito.mock(IAjaxDocument.class);

		UUID documentId1 = registry.register(document1);
		UUID documentId2 = registry.register(document2);

		Optional<IAjaxDocument> returnedDocument1 = registry.getDocument(documentId1);
		Optional<IAjaxDocument> returnedDocument2 = registry.getDocument(documentId2);

		assertNotEquals(documentId1, documentId2);
		assertSame(document1, returnedDocument1);
		assertSame(document2, returnedDocument2);
	}

	@Test
	public void testGetDocumentWithoutRegister() {

		Optional<IAjaxDocument> returnedDocument = registry.getDocument(UUID.randomUUID());

		assertFalse(returnedDocument.isPresent());
	}
}
