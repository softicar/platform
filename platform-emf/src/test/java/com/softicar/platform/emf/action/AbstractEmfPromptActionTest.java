package com.softicar.platform.emf.action;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfMarker;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import com.softicar.platform.emf.test.EmfFormTestBody;
import com.softicar.platform.emf.test.EmfTableRowCacheMapTestMethods;
import com.softicar.platform.emf.test.EmfTestSubObject;
import org.junit.Test;

/**
 * TODO implement more test cases
 */
public class AbstractEmfPromptActionTest extends AbstractEmfTest {

	private final String promptActionName;
	private final EmfTestSubObject subObject;

	public AbstractEmfPromptActionTest() {

		this.promptActionName = "some prompt action";
		this.subObject = insertEntity("foo");

		showFormBodyWithPromptAction(subObject);
	}

	@Test
	public void testShowInteractiveRefresh() {

		EmfTableRowCacheMapTestMethods.useOtherCacheMapAndModify(subObject, it -> it.setName("bar"));
		clickPromptActionButton();
		clickSaveButton();

		findBody()//
			.findNode(EmfMarker.INTERACTIVE_REFRESH_BUTTON);
	}

	@Test
	public void testShowNoInteractiveRefresh() {

		clickPromptActionButton();
		clickSaveButton();

		findBody()//
			.findNodes(EmfMarker.INTERACTIVE_REFRESH_BUTTON)
			.assertNone();
	}

	private EmfTestSubObject insertEntity(String name) {

		EmfTestSubObject entity = new EmfTestSubObject();
		entity.setName(name);
		entity.setNotNullableValue(420);
		entity.save();
		return entity;
	}

	private void showFormBodyWithPromptAction(EmfTestSubObject entity) {

		setNodeSupplier(() -> new EmfFormTestBody<>(entity).showAction(new TestPromptAction<>()));
	}

	private void clickPromptActionButton() {

		findBody()//
			.clickNode(IDisplayString.create(promptActionName));
	}

	private void clickSaveButton() {

		findBody()//
			.clickNode(EmfI18n.SAVE);
	}

	private class TestPromptAction<R extends IEmfTableRow<R, ?>> extends AbstractEmfPromptAction<R> {

		@Override
		protected void buildPrompt(R tableRow, IEmfPromptActionInput<R> input) {

			// nothing to do
		}

		@Override
		public IEmfPredicate<R> getPrecondition() {

			return EmfPredicates.always();
		}

		@Override
		public IEmfPermission<R> getRequiredPermission() {

			return EmfPermissions.anybody();
		}

		@Override
		public IResource getIcon() {

			return DomElementsImages.DIALOG_OKAY.getResource();
		}

		@Override
		public IDisplayString getTitle() {

			return IDisplayString.create(promptActionName);
		}
	}
}
