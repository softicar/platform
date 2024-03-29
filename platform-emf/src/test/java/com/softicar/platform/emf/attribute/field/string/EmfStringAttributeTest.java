package com.softicar.platform.emf.attribute.field.string;

import com.softicar.platform.common.string.Padding;
import com.softicar.platform.common.testing.AbstractTest;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.test.EmfTestSubObject;
import com.softicar.platform.emf.validation.result.EmfValidationResult;
import java.util.Optional;
import org.junit.Test;
import org.mockito.Mockito;

public class EmfStringAttributeTest extends AbstractTest {

	private static final String EMPTY_STRING = "";
	private static final String ABC = "abc";
	private static final String ABC_WITH_WHITESPACE = " \nabc \t";
	private static final String MULTI_LINE = " abc\n\n\nabc\t";
	private static final String MULTI_LINE_WITH_EMPTY_LINES = " \n abc\n\n\nabc\t\n\t";
	private static final int MAXIMUM_LENGTH = 15;
	private static final int LENGTHBITS = 8;
	private final EmfTestSubObject subObject;
	private final IDbStringField<EmfTestSubObject> field;
	private final EmfStringAttribute<EmfTestSubObject> attribute;
	private final EmfValidationResult result;

	public EmfStringAttributeTest() {

		this.subObject = new EmfTestSubObject();
		this.field = Mockito.mock(IDbStringField.class);
		Mockito.when(field.getName()).thenReturn(ABC);
		Mockito.when(field.getComment()).thenReturn(Optional.empty());
		Mockito.when(field.getMaximumLength()).thenReturn(MAXIMUM_LENGTH);
		Mockito.when(field.getLengthBits()).thenReturn(LENGTHBITS);
		this.attribute = new EmfStringAttribute<>(field);
		this.result = new EmfValidationResult();
	}

	// ------------------------------ auto-trimming single-line ------------------------------ //

	@Test
	public void testSetValueWithAutoTrimming() {

		attribute.setValue(subObject, ABC_WITH_WHITESPACE);

		Mockito.verify(field).setValue(subObject, ABC);
	}

	@Test
	public void testSetValueWithoutAutoTrimming() {

		attribute.setAutoTrimming(false);

		attribute.setValue(subObject, ABC_WITH_WHITESPACE);

		Mockito.verify(field).setValue(subObject, ABC_WITH_WHITESPACE);
	}

	@Test
	public void testSetValueWithAutoTrimmingAndNullValue() {

		attribute.setValue(subObject, null);

		Mockito.verify(field).setValue(subObject, null);
	}

	@Test
	public void testGetValueWithAutoTrimming() {

		Mockito.when(field.getValue(subObject)).thenReturn(ABC_WITH_WHITESPACE);

		String value = attribute.getValue(subObject);

		assertEquals(ABC, value);
	}

	@Test
	public void testGetValueWithoutAutoTrimming() {

		attribute.setAutoTrimming(false);
		Mockito.when(field.getValue(subObject)).thenReturn(ABC_WITH_WHITESPACE);

		String value = attribute.getValue(subObject);

		assertEquals(ABC_WITH_WHITESPACE, value);
	}

	@Test
	public void testGetValueWithAutoTrimmingAndNullValue() {

		Mockito.when(field.getValue(subObject)).thenReturn(null);

		String value = attribute.getValue(subObject);

		assertNull(value);
	}

	// ------------------------------ auto-trimming multi-line ------------------------------ //

	@Test
	public void testSetValueWithMultiLineAndAutoTrimming() {

		attribute.setMultiline(true);
		attribute.setValue(subObject, MULTI_LINE_WITH_EMPTY_LINES);

		Mockito.verify(field).setValue(subObject, MULTI_LINE);
	}

	@Test
	public void testSetValueWithMultiLineAndWithoutAutoTrimming() {

		attribute.setMultiline(true);
		attribute.setAutoTrimming(false);

		attribute.setValue(subObject, MULTI_LINE_WITH_EMPTY_LINES);

		Mockito.verify(field).setValue(subObject, MULTI_LINE_WITH_EMPTY_LINES);
	}

	@Test
	public void testSetValueWithMultiLineAndAutoTrimmingAndNullValue() {

		attribute.setMultiline(true);
		attribute.setValue(subObject, null);

		Mockito.verify(field).setValue(subObject, null);
	}

	@Test
	public void testGetValueWithMultiLineAndAutoTrimming() {

		attribute.setMultiline(true);
		Mockito.when(field.getValue(subObject)).thenReturn(MULTI_LINE_WITH_EMPTY_LINES);

		String value = attribute.getValue(subObject);

		assertEquals(MULTI_LINE, value);
	}

	@Test
	public void testGetValueWithMultiLineAndWithoutAutoTrimming() {

		attribute.setMultiline(true);
		attribute.setAutoTrimming(false);
		Mockito.when(field.getValue(subObject)).thenReturn(MULTI_LINE_WITH_EMPTY_LINES);

		String value = attribute.getValue(subObject);

		assertEquals(MULTI_LINE_WITH_EMPTY_LINES, value);
	}

	@Test
	public void testGetValueWithMultiLineAndAutoTrimmingAndNullValue() {

		attribute.setMultiline(true);
		Mockito.when(field.getValue(subObject)).thenReturn(null);

		String value = attribute.getValue(subObject);

		assertNull(value);
	}

	// ------------------------------ mandatory validation ------------------------------ //

	@Test
	public void testMandatoryValidationWithNull() {

		attribute.setPredicateMandatory(EmfPredicates.always());
		Mockito.when(field.getValue(Mockito.any())).thenReturn(null);

		attribute.validate(subObject, result);

		assertTrue(result.hasErrors());
	}

	@Test
	public void testMandatoryValidationWithEmptyString() {

		attribute.setPredicateMandatory(EmfPredicates.always());
		Mockito.when(field.getValue(Mockito.any())).thenReturn("");

		attribute.validate(subObject, result);

		assertTrue(result.hasErrors());
	}

	@Test
	public void testMandatoryValidationWithWhitespace() {

		attribute.setPredicateMandatory(EmfPredicates.always());
		Mockito.when(field.getValue(Mockito.any())).thenReturn(" \t \n");

		attribute.validate(subObject, result);

		assertTrue(result.hasErrors());
	}

	@Test
	public void testMandatoryValidationWithWhitespaceAndWithoutAutoTrimming() {

		attribute.setAutoTrimming(false);
		attribute.setPredicateMandatory(EmfPredicates.always());
		Mockito.when(field.getValue(Mockito.any())).thenReturn(" \t \n");

		attribute.validate(subObject, result);

		assertFalse(result.hasErrors());
		assertTrue(result.getDiagnostics().isEmpty());
	}

	@Test
	public void testMandatoryValidationWithProperValue() {

		attribute.setPredicateMandatory(EmfPredicates.always());
		Mockito.when(field.getValue(Mockito.any())).thenReturn(ABC);

		attribute.validate(subObject, result);

		assertFalse(result.hasErrors());
		assertTrue(result.getDiagnostics().isEmpty());
	}

	// ------------------------------ length validation ------------------------------ //

	@Test
	public void testLengthValidationWithTooLongValue() {

		attribute.setMaximumLength(8);
		attribute.setLengthBits(0);
		Mockito.when(field.getValue(Mockito.any())).thenReturn("123456789");

		attribute.validate(subObject, result);

		assertTrue(result.hasErrors());
	}

	@Test
	public void testLengthValidationWithProperValue() {

		attribute.setMaximumLength(9);
		attribute.setLengthBits(0);
		Mockito.when(field.getValue(Mockito.any())).thenReturn("123456789");

		attribute.validate(subObject, result);

		assertFalse(result.hasErrors());
		assertTrue(result.getDiagnostics().isEmpty());
	}

	@Test
	public void testLengthValidationFromDbFieldWithTooLongValue() {

		attribute.setLengthBits(0);
		Mockito.when(field.getValue(Mockito.any())).thenReturn("123456789101112131415");

		attribute.validate(subObject, result);

		assertTrue(result.hasErrors());
	}

	@Test
	public void testLengthValidationFromDbFieldWithProperValue() {

		attribute.setLengthBits(0);
		Mockito.when(field.getValue(Mockito.any())).thenReturn("12345678910");

		attribute.validate(subObject, result);

		assertFalse(result.hasErrors());
		assertTrue(result.getDiagnostics().isEmpty());
	}

	// ------------------------------ bitlength validation ------------------------------ //

	@Test
	public void testTinyTextValidationWithTooLongValue() {

		assertLengthBitsValidationWithTooLongValue(8, Padding.generate('a', 256));
	}

	@Test
	public void testTinyTextValidationWithTooLongValueAndDoubleByteChar() {

		assertLengthBitsValidationWithTooLongValue(8, Padding.generate('ä', 128));
	}

	@Test
	public void testTinyTextValidationWithProperValue() {

		assertLengthBitValidationWithProperValue(8, Padding.generate('a', 255));
	}

	@Test
	public void testTinyTextValidationWithProperValueAndDoubleByteChar() {

		assertLengthBitValidationWithProperValue(8, Padding.generate('ä', 127));
	}

	@Test
	public void testTextValidationWithTooLongValue() {

		assertLengthBitsValidationWithTooLongValue(16, Padding.generate('a', 65535));
	}

	@Test
	public void testTextValidationWithTooLongValueAndDoubleByteChar() {

		assertLengthBitsValidationWithTooLongValue(16, Padding.generate('ä', 32768));
	}

	@Test
	public void testTextValidationWithProperValue() {

		assertLengthBitValidationWithProperValue(16, Padding.generate('a', 65534));
	}

	@Test
	public void testTextValidationWithProperValueAndDoubleByteChar() {

		assertLengthBitValidationWithProperValue(16, Padding.generate('ä', 32767));
	}

	@Test
	public void testMediumTextValidationWithTooLongValue() {

		assertLengthBitsValidationWithTooLongValue(24, Padding.generate('a', 16777214));
	}

	@Test
	public void testMediumTextValidationWithTooLongValueAndDoubleByteChar() {

		assertLengthBitsValidationWithTooLongValue(24, Padding.generate('ä', 8388607));
	}

	@Test
	public void testMediumTextValidationWithProperValue() {

		assertLengthBitValidationWithProperValue(24, Padding.generate('a', 16777213));
	}

	@Test
	public void testMediumTextValidationWithProperValueAndDoubleByteChar() {

		assertLengthBitValidationWithProperValue(24, Padding.generate('ä', 8388606));
	}

	@Test
	public void testLengthBitsValidationFromDbFieldWithTooLongValue() {

		attribute.setMaximumLength(0);
		Mockito.when(field.getValue(Mockito.any())).thenReturn(Padding.generate('a', 256));

		attribute.validate(subObject, result);

		assertTrue(result.hasErrors());
	}

	@Test
	public void testLengthBitsValidationFromDbFieldWithProperValue() {

		attribute.setMaximumLength(0);
		Mockito.when(field.getValue(Mockito.any())).thenReturn(Padding.generate('a', 255));

		attribute.validate(subObject, result);

		assertFalse(result.hasErrors());
		assertTrue(result.getDiagnostics().isEmpty());
	}

	// ------------------------------ empty string validation ------------------------------ //

	@Test
	public void testEmptyStringWithNullableField() {

		Mockito.when(field.isNullable()).thenReturn(true);

		attribute.setValue(subObject, EMPTY_STRING);

		Mockito.verify(field).setValue(subObject, null);
	}

	@Test
	public void testEmptyStringWithNonNullableField() {

		Mockito.when(field.isNullable()).thenReturn(false);

		attribute.setValue(subObject, EMPTY_STRING);

		Mockito.verify(field).setValue(subObject, EMPTY_STRING);
	}

	// ------------------------------ convenience methods ------------------------------ //
	private void assertLengthBitsValidationWithTooLongValue(int lengthBits, String value) {

		attribute.setMaximumLength(0);
		attribute.setLengthBits(lengthBits);
		Mockito.when(field.getValue(Mockito.any())).thenReturn(value);

		attribute.validate(subObject, result);

		assertTrue(result.hasErrors());
	}

	private void assertLengthBitValidationWithProperValue(int lengthBits, String value) {

		attribute.setMaximumLength(0);
		attribute.setLengthBits(lengthBits);
		Mockito.when(field.getValue(Mockito.any())).thenReturn(value);

		attribute.validate(subObject, result);

		assertFalse(result.hasErrors());
		assertTrue(result.getDiagnostics().isEmpty());
	}

}
