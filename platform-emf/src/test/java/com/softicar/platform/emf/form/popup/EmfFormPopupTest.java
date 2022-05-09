package com.softicar.platform.emf.form.popup;

import com.softicar.platform.common.date.Day;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.popup.configuration.DomPopupConfiguration;
import com.softicar.platform.dom.elements.popup.manager.DomPopupManager;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import org.junit.Test;

/**
 * TODO implement more test cases
 */
public class EmfFormPopupTest extends AbstractEmfTest {

	public EmfFormPopupTest() {

		setNodeSupplier(DomDiv::new);
	}

	@Test
	public void testDisplaysAttributeNames() {

		showPopup(insertEntity("foobar", Day.today()));

		findBody()//
			.findNode(DomPopup.class)
			.assertContainsText("Id");

		findBody()//
			.findNode(DomPopup.class)
			.assertContainsText("Name");

		findBody()//
			.findNode(DomPopup.class)
			.assertContainsText("Day");
	}

	@Test
	public void testDisplaysAttributeValues() {

		EmfTestObject entity = insertEntity("foobar", Day.today());
		showPopup(entity);

		findBody()//
			.findNode(DomPopup.class)
			.assertContainsText(entity.getId() + "");

		findBody()//
			.findNode(DomPopup.class)
			.assertContainsText("foobar");

		findBody()//
			.findNode(DomPopup.class)
			.assertContainsText("Today");
	}

	private EmfTestObject insertEntity(String name, Day day) {

		EmfTestObject entity = new EmfTestObject();
		entity.setName(name);
		entity.setDay(day);
		entity.save();
		return entity;
	}

	private void showPopup(EmfTestObject entity) {

		// Ensure initialization of the element under test.
		// FIXME This hack should not be necessary.
		findBody();

		DomPopupManager//
			.getInstance()
			.getPopup(entity, EmfFormPopup.class, it -> new EmfFormPopup<>(it))
			.configure(DomPopupConfiguration::setPositionStrategyByViewportCenter)
			.open();
	}
}
