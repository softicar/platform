package com.softicar.platform.emf.attribute.field.foreign.entity;

import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteDefaultInputEngine;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.test.simple.authorization.EmfTestObjectAuthorizedUser;
import com.softicar.platform.emf.test.user.EmfTestUser;
import java.util.List;
import org.junit.Test;

public class EmfForeignEntityAttributeTest extends AbstractEmfTest {

	private final EmfForeignEntityAttribute<EmfTestObjectAuthorizedUser, EmfTestUser> attribute;

	private final EmfTestUser currentUser;
	private final EmfTestUser otherUser;

	public EmfForeignEntityAttributeTest() {

		this.attribute = new EmfForeignEntityAttribute<>(EmfTestObjectAuthorizedUser.USER);
		this.currentUser = (EmfTestUser) CurrentBasicUser.get();
		this.otherUser = EmfTestUser.insert("other", "user");
	}

	@Test
	public void testDefault() {

		assertValues(currentUser, otherUser);
	}

	@Test
	public void testAddFilter() {

		attribute.addFilter(user -> user != currentUser);
		assertValues(otherUser);

		attribute.addFilter(user -> user != otherUser);
		assertValues();
	}

	@Test
	public void testSetValueLoader() {

		attribute.setValueLoader(dummy -> List.of());
		assertValues();

		attribute.setValueLoader(dummy -> List.of(otherUser));
		assertValues(otherUser);

		attribute.setValueLoader(dummy -> List.of(currentUser));
		assertValues(currentUser);

		attribute.setValueLoader(dummy -> List.of(currentUser, otherUser));
		assertValues(currentUser, otherUser);
	}

	@Test
	public void testSetInputEngine() {

		attribute.setInputEngine(() -> new DomAutoCompleteDefaultInputEngine<>());
		assertValues();

		attribute.setInputEngine(() -> new DomAutoCompleteDefaultInputEngine<>(() -> List.of(currentUser, otherUser)));
		assertValues(currentUser, otherUser);
	}

	private void assertValues(EmfTestUser...expectedValues) {

		var actualValues = createInput()//
			.getInputEngine()
			.findMatches("", Integer.MAX_VALUE)
			.getAllValues();
		assertEquals(List.of(expectedValues), actualValues);
	}

	@SuppressWarnings("unchecked")
	private DomAutoCompleteInput<EmfTestUser> createInput() {

		var object = new EmfTestObjectAuthorizedUser();

		return (DomAutoCompleteInput<EmfTestUser>) attribute.createInput(object);
	}
}
