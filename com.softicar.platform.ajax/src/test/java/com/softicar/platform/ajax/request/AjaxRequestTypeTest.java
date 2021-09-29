package com.softicar.platform.ajax.request;

import static org.junit.Assert.assertSame;
import com.softicar.platform.ajax.document.action.AjaxDocumentActionType;
import org.junit.Test;

/**
 * Test cases for {@link AjaxDocumentActionType}.
 *
 * @author Oliver Richers
 */
public class AjaxRequestTypeTest {

	@Test
	public void testMapping() {

		for (AjaxDocumentActionType type: AjaxDocumentActionType.values()) {
			assertSame(type, AjaxDocumentActionType.getById(type.getId()));
		}
	}
}
