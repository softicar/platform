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

	private final Day day;

	public EmfFormPopupTest() {

		setNodeSupplier(DomDiv::new);
		this.day = Day.fromYMD(2022, 12, 31);
	}

	@Test
	public void testDisplaysAttributeNames() {

		EmfTestObject entity = insertEntity("foobar", day);
		entity.addAuthorizedUser(user);
		openPopup(entity);

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

		EmfTestObject entity = insertEntity("foobar", day);
		entity.addAuthorizedUser(user);
		openPopup(entity);

		findBody()//
			.findNode(DomPopup.class)
			.assertContainsText(entity.getId() + "");

		findBody()//
			.findNode(DomPopup.class)
			.assertContainsText("foobar");

		findBody()//
			.findNode(DomPopup.class)
			.assertContainsText("2022-12-31");
	}

	private EmfTestObject insertEntity(String name, Day day) {

		EmfTestObject entity = new EmfTestObject();
		entity.setName(name);
		entity.setDay(day);
		entity.save();
		return entity;
	}

	private void openPopup(EmfTestObject entity) {

		DomPopupManager//
			.getInstance()
			.getPopup(entity, EmfFormPopup.class, it -> new EmfFormPopup<>(it))
			.configure(DomPopupConfiguration::setPlacementStrategyByViewportCenter)
			.open();
	}
}
