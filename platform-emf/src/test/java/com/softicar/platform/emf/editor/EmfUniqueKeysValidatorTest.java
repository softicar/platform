package com.softicar.platform.emf.editor;

import com.softicar.platform.emf.validation.result.EmfValidationResult;
import org.junit.Test;

public class EmfUniqueKeysValidatorTest extends AbstractEmfUniqueKeyValidationTest {

	public EmfUniqueKeysValidatorTest() {

		insertEntity(EXISTING_NAME, EXISTING_VALUE);
	}

	@Test
	public void testWithNameConflict() {

		EmfMultiUniqueKeyTestObject entity = new EmfMultiUniqueKeyTestObject()//
			.setName(EXISTING_NAME)
			.setValue(OTHER_VALUE);

		EmfValidationResult validationResult = validateKeys(entity);

		assertFalse(validationResult.getDiagnostics().isEmpty());
		assertEquals(1, validationResult.getDiagnostics(EmfMultiUniqueKeyTestObject.NAME).size());
		assertEquals(0, validationResult.getDiagnostics(EmfMultiUniqueKeyTestObject.VALUE).size());
	}

	@Test
	public void testWithNameAndValueConflicts() {

		EmfMultiUniqueKeyTestObject entity = new EmfMultiUniqueKeyTestObject()//
			.setName(EXISTING_NAME)
			.setValue(EXISTING_VALUE);

		EmfValidationResult validationResult = validateKeys(entity);

		assertFalse(validationResult.getDiagnostics().isEmpty());
		assertEquals(1, validationResult.getDiagnostics(EmfMultiUniqueKeyTestObject.NAME).size());
		assertEquals(1, validationResult.getDiagnostics(EmfMultiUniqueKeyTestObject.VALUE).size());
	}

	@Test
	public void testWithNameConflictAndNullValue() {

		EmfMultiUniqueKeyTestObject entity = new EmfMultiUniqueKeyTestObject()//
			.setName(EXISTING_NAME)
			.setValue(null);

		EmfValidationResult validationResult = validateKeys(entity);

		assertFalse(validationResult.getDiagnostics().isEmpty());
		assertEquals(1, validationResult.getDiagnostics(EmfMultiUniqueKeyTestObject.NAME).size());
		assertEquals(0, validationResult.getDiagnostics(EmfMultiUniqueKeyTestObject.VALUE).size());
	}

	@Test
	public void testWithNullNameAndNullValue() {

		EmfMultiUniqueKeyTestObject entity = new EmfMultiUniqueKeyTestObject()//
			.setName(null)
			.setValue(null);

		EmfValidationResult validationResult = validateKeys(entity);

		assertTrue(validationResult.getDiagnostics().isEmpty());
	}

	@Test
	public void testWithoutConflicts() {

		EmfMultiUniqueKeyTestObject entity = new EmfMultiUniqueKeyTestObject()//
			.setName(OTHER_NAME)
			.setValue(OTHER_VALUE);

		EmfValidationResult validationResult = validateKeys(entity);

		assertTrue(validationResult.getDiagnostics().isEmpty());
	}

	private void insertEntity(String name, Integer value) {

		EmfMultiUniqueKeyTestObject.TABLE//
			.createInsert()
			.set(EmfMultiUniqueKeyTestObject.NAME, name)
			.set(EmfMultiUniqueKeyTestObject.VALUE, value)
			.execute();
	}

	private EmfValidationResult validateKeys(EmfMultiUniqueKeyTestObject entity) {

		EmfValidationResult validationResult = new EmfValidationResult();
		new EmfUniqueKeysValidator<>(entity, validationResult).validate();
		return validationResult;
	}
}
