package com.softicar.platform.ajax.document.registry;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.common.date.DayTime;
import org.junit.Test;

/**
 * Test cases for {@link AjaxOldDocumentsRemover}.
 *
 * @author Oliver Richers
 */
public class AjaxOldDocumentsRemoverTest extends AbstractAjaxDocumentRemoverTest {

	private final IAjaxDocument document1;
	private final IAjaxDocument document2;
	private final IAjaxDocument document3;

	public AjaxOldDocumentsRemoverTest() {

		this.document1 = createDocument();
		this.document2 = createDocument();
		this.document3 = createDocument();
	}

	@Test
	public void doesNothingIfNoDocumentsExist() {

		new AjaxOldDocumentsRemover(documents, 2).remove();

		assertEquals(0, documents.size());
	}

	@Test
	public void doesNotRemoveDocumentsBelowLimit() {

		addDocument(document1);
		addDocument(document2);

		new AjaxOldDocumentsRemover(documents, 2).remove();

		assertEquals(2, documents.size());
		assertDocumentDoesExist(document1);
		assertDocumentDoesExist(document2);
	}

	@Test
	public void removesOldestDocument() {

		document1.getLogs().setLastAccess(DayTime.now().minusSeconds(1));
		document2.getLogs().setLastAccess(DayTime.now().minusSeconds(3));
		document3.getLogs().setLastAccess(DayTime.now().minusSeconds(2));
		addDocument(document1);
		addDocument(document2);
		addDocument(document3);

		new AjaxOldDocumentsRemover(documents, 2).remove();

		assertEquals(2, documents.size());
		assertDocumentDoesExist(document1);
		assertDocumentDoesNotExist(document2);
		assertDocumentDoesExist(document3);
	}
}
