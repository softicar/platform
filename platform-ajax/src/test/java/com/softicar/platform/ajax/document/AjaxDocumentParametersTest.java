package com.softicar.platform.ajax.document;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.testing.AbstractTest;
import java.util.Arrays;
import java.util.TreeMap;
import org.junit.Test;

public class AjaxDocumentParametersTest extends AbstractTest {

	private static final String STRING_DEFAULT = "defaultValue";
	private static final String STRING_VALUE = "stringValue";
	private static final Integer INTEGER_VALUE = 123;
	private static final Integer INTEGER_DEFAULT = 1337;
	private final TreeMap<String, String[]> parameterMap;
	private final IAjaxDocumentParameters parameters;

	public AjaxDocumentParametersTest() {

		this.parameterMap = new TreeMap<>();
		this.parameterMap.put("undefined", null);
		this.parameterMap.put("emptyList", new String[] {});
		this.parameterMap.put("stringParameter", new String[] { STRING_VALUE });
		this.parameterMap.put("numberParameter", new String[] { "123" });
		this.parameters = new AjaxDocumentParameters(this.parameterMap);
	}

	// ------------------  getParameterOrNull ------------------ //

	@Test
	public void getParameterOrNull() {

		assertNull(parameters.getParameterOrNull("undefined"));
		assertNull(parameters.getParameterOrNull("emptyList"));
		assertNotNull(parameters.getParameterOrNull("stringParameter"));
		assertEquals(STRING_VALUE, parameters.getParameterOrNull("stringParameter"));

		assertNull(parameters.getParameterOrNull("undefined", Integer::valueOf));
		assertNull(parameters.getParameterOrNull("emptyList", Integer::valueOf));
		assertNotNull(parameters.getParameterOrNull("numberParameter", Integer::valueOf));
		assertEquals(INTEGER_VALUE, parameters.getParameterOrNull("numberParameter", Integer::valueOf));
	}

	// ------------------  getParameterOrDefault ------------------ //

	@Test
	public void getParameterOrDefault() {

		assertEquals(STRING_DEFAULT, parameters.getParameterOrDefault("undefined", STRING_DEFAULT));
		assertEquals(STRING_DEFAULT, parameters.getParameterOrDefault("emptyList", STRING_DEFAULT));
		assertEquals(STRING_VALUE, parameters.getParameterOrDefault("stringParameter", STRING_DEFAULT));

		assertEquals(INTEGER_DEFAULT, parameters.getParameterOrDefault("undefined", INTEGER_DEFAULT, Integer::valueOf));
		assertEquals(INTEGER_DEFAULT, parameters.getParameterOrDefault("emptyList", INTEGER_DEFAULT, Integer::valueOf));
		assertEquals(INTEGER_VALUE, parameters.getParameterOrDefault("numberParameter", INTEGER_DEFAULT, Integer::valueOf));
	}

	// ------------------  getParameterOrThrow ------------------ //

	@Test(expected = SofticarUserException.class)
	public void getParameterOrThrowWithUndefinedParameter() {

		parameters.getParameterOrThrow("undefined");
	}

	@Test(expected = SofticarUserException.class)
	public void getParameterOrThrowWithEmptyListParameter() {

		parameters.getParameterOrThrow("emptyList");
	}

	@Test
	public void getParameterOrThrow() {

		assertEquals(STRING_VALUE, parameters.getParameterOrThrow("stringParameter"));
		assertEquals(INTEGER_VALUE, parameters.getParameterOrThrow("numberParameter", Integer::valueOf));
	}

	// ------------------  getParameterValueList ------------------ //

	@Test
	public void getParameterValueList() {

		assertTrue(parameters.getParameterValueList("undefined").isEmpty());
		assertTrue(parameters.getParameterValueList("emptyList").isEmpty());
		assertEquals(Arrays.asList(STRING_VALUE), parameters.getParameterValueList("stringParameter"));
		assertEquals(Arrays.asList(INTEGER_VALUE), parameters.getParameterValueList("numberParameter", Integer::valueOf));
	}
}
