package com.softicar.platform.emf.form;

import com.softicar.platform.emf.EmfMarker;
import com.softicar.platform.emf.action.marker.EmfCommonActionMarker;
import com.softicar.platform.emf.editor.EmfEditAction;
import com.softicar.platform.emf.test.EmfTestSubObject;
import org.junit.Test;

/**
 * Tests the {@link EmfForm} behavior with respect to the frame title.
 *
 * @author Oliver Richers
 */
public class EmfFormTitleTest extends AbstractEmfFormTest {

	@Test
	public void testInitialTitleWithImpermanentEntity() {

		EmfTestSubObject entity = new EmfTestSubObject();
		EmfForm<EmfTestSubObject> form = appendEntityForm(entity);
		form.peekAndRefresh();

		frame.assertTitle(EmfTestSubObject.TABLE.getTitle().toString());
		frame.assertSubTitle("");
	}

	@Test
	public void testInitialTitleWithPermanentEntity() {

		EmfTestSubObject entity = new EmfTestSubObject().setName("Name").setNotNullableValue(420).save();
		EmfForm<EmfTestSubObject> form = appendEntityForm(entity);
		form.peekAndRefresh();

		frame.assertTitle(EmfTestSubObject.TABLE.getTitle().toString());
		frame.assertSubTitle("Name [%s]", entity.getId());
	}

	@Test
	public void testInitialTitleAfterSavingEntity() {

		EmfTestSubObject entity = new EmfTestSubObject().setName("Name").setNotNullableValue(420).save();
		EmfForm<EmfTestSubObject> form = appendEntityForm(entity);
		form.peekAndRefresh();

		clickButton(new EmfCommonActionMarker(EmfEditAction.class));
		enterNameAndClickSave("New Name");

		assertFalse(entity.impermanent());
		frame.assertIsNotClosed();
		frame.assertTitle(EmfTestSubObject.TABLE.getTitle().toString());
		frame.assertSubTitle("New Name [%s]", entity.getId());
	}

	private void enterNameAndClickSave(String name) {

		setInputValue(EmfTestSubObject.NAME, name);
		clickButton(EmfMarker.SAVE);
	}
}
