package com.softicar.platform.ajax.input.auto.complete.entity;

import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import org.junit.Test;

/**
 * Contains unit tests for {@link DomAutoCompleteInput} interaction phase <b>"1
 * Created"</b> (see {@link AbstractAjaxAutoCompleteEntityTest}).
 * <p>
 * FIXME re-process all method names
 *
 * @author Alexander Schmidt
 */
public class AjaxAutoCompleteEntityInputCreatedTest extends AbstractAjaxAutoCompleteEntityTest {

	@Test
	public void testCreationWithEmptyInput() {

		setup//
			.execute();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testCreationWithFilledInput() {

		setup//
			.setSelectedEntity(ENTITY1)
			.execute();

		asserter//
			.expectValues(ENTITY1)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testCreationAndIllegalValueWithFilledInput() {

		setup//
			.setSelectedEntity(UNAVAILABLE_ENTITY)
			.execute();

		asserter//
			.expectClientValue(UNAVAILABLE_ENTITY.toDisplayStringWithId())
			.expectServerValueExceptionMessage()
			.expectIndicatorIllegal()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testCreationAndClearedValueWithFilledInput() {

		setup//
			.setSelectedEntity(ENTITY1)
			.setSelectedEntityNone()
			.execute();

		asserter//
			.expectValuesNone()
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}

	@Test
	public void testCreationAndChangedValueWithFilledInput() {

		setup//
			.setSelectedEntity(ENTITY1)
			.setSelectedEntity(ENTITY2)
			.execute();

		asserter//
			.expectValues(ENTITY2)
			.expectIndicatorNone()
			.expectPopupNotDisplayed()
			.expectNoFocus()
			.expectBackdropNotDisplayed()
			.expectCallbackNone()
			.assertAll();
	}
}
