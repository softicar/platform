package com.softicar.platform.ajax.document.registry;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.common.date.DayTime;
import org.junit.Test;

/**
 * Test cases for {@link AjaxIdleDocumentsRemover}.
 *
 * @author Oliver Richers
 */
public class AjaxIdleDocumentsRemoverTest extends AbstractAjaxDocumentRemoverTest {

	private final IAjaxDocument document1;
	private final IAjaxDocument document2;

	public AjaxIdleDocumentsRemoverTest() {

		this.document1 = createDocument();
		this.document2 = createDocument();
	}

	@Test
	public void doesNothingIfNoDocumentsExist() {

		new AjaxIdleDocumentsRemover(documents, 100).remove();

		assertTrue(documents.isEmpty());
	}

	@Test
	public void doesNotRemoveActiveDocument() {

		document1.getLogs().setLastAccess(DayTime.now().minusSeconds(3));
		addDocument(document1);

		new AjaxIdleDocumentsRemover(documents, 100).remove();

		assertDocumentDoesExist(document1);
	}

	@Test
	public void removesIdleDocument() {

		document1.getLogs().setLastAccess(DayTime.now().minusSeconds(101));
		addDocument(document1);

		new AjaxIdleDocumentsRemover(documents, 100).remove();

		assertDocumentDoesNotExist(document1);
	}

	@Test
	public void removesOnlyIdleDocument() {

		document1.getLogs().setLastAccess(DayTime.now().minusSeconds(100));
		document2.getLogs().setLastAccess(DayTime.now().minusSeconds(101));
		addDocument(document1);
		addDocument(document2);

		new AjaxIdleDocumentsRemover(documents, 100).remove();

		assertDocumentDoesExist(document1);
		assertDocumentDoesNotExist(document2);
	}
}
