package com.softicar.platform.ajax.request;

import com.softicar.platform.ajax.document.action.AjaxDocumentActionType;
import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

/**
 * Test cases for {@link AjaxDocumentActionType}.
 *
 * @author Oliver Richers
 */
public class AjaxRequestTypeTest extends AbstractTest {

	@Test
	public void testMapping() {

		for (AjaxDocumentActionType type: AjaxDocumentActionType.values()) {
			assertSame(type, AjaxDocumentActionType.getById(type.getId()));
		}
	}
}
