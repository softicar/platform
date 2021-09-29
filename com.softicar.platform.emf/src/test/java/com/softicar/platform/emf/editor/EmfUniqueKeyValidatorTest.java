package com.softicar.platform.emf.editor;

import com.softicar.platform.emf.validation.result.EmfValidationResult;
import org.junit.Test;

public class EmfUniqueKeyValidatorTest extends AbstractEmfUniqueKeyValidationTest {

	private final int existingEntityId;

	public EmfUniqueKeyValidatorTest() {

		this.existingEntityId = insertEntity(EXISTING_NAME, EXISTING_VALUE);
	}

	@Test
	public void testWithConflict() {

		EmfCompositeUniqueKeyTestObject entity = new EmfCompositeUniqueKeyTestObject()//
			.setName(EXISTING_NAME)
			.setValue(EXISTING_VALUE);

		EmfValidationResult validationResult = validateNameAndValueKey(entity);

		assertFalse(validationResult.getDiagnostics().isEmpty());
		assertEquals(1, validationResult.getDiagnostics(EmfCompositeUniqueKeyTestObject.NAME).size());
		assertEquals(1, validationResult.getDiagnostics(EmfCompositeUniqueKeyTestObject.VALUE).size());
	}

	@Test
	public void testWithConflictButNullValue() {

		insertEntity(EXISTING_NAME, null);

		EmfCompositeUniqueKeyTestObject entity = new EmfCompositeUniqueKeyTestObject()//
			.setName(EXISTING_NAME)
			.setValue(null);

		EmfValidationResult validationResult = validateNameAndValueKey(entity);

		assertTrue(validationResult.getDiagnostics().isEmpty());
	}

	@Test
	public void testWithoutConflict() {

		EmfCompositeUniqueKeyTestObject entity = new EmfCompositeUniqueKeyTestObject()//
			.setName(EXISTING_NAME)
			.setValue(OTHER_VALUE);

		EmfValidationResult validationResult = validateNameAndValueKey(entity);

		assertTrue(validationResult.getDiagnostics().isEmpty());
	}

	@Test
	public void testWithConflictToSameEntity() {

		EmfCompositeUniqueKeyTestObject entity = EmfCompositeUniqueKeyTestObject.TABLE.get(existingEntityId);

		EmfValidationResult validationResult = validateNameAndValueKey(entity);

		assertTrue(validationResult.getDiagnostics().isEmpty());
	}

	private int insertEntity(String name, Integer value) {

		return EmfCompositeUniqueKeyTestObject.TABLE//
			.createInsert()
			.set(EmfCompositeUniqueKeyTestObject.NAME, name)
			.set(EmfCompositeUniqueKeyTestObject.VALUE, value)
			.execute();
	}

	private EmfValidationResult validateNameAndValueKey(EmfCompositeUniqueKeyTestObject entity) {

		EmfValidationResult validationResult = new EmfValidationResult();
		new EmfUniqueKeyValidator<>(entity, EmfCompositeUniqueKeyTestObject.UK_NAME_VALUE, validationResult).validate();
		return validationResult;
	}
}
