package com.softicar.platform.ajax.request;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.softicar.platform.ajax.exceptions.AjaxHttpBadRequestError;
import com.softicar.platform.common.testing.AbstractTest;
import javax.servlet.ServletRequest;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for {@link AjaxParameterUtils}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class AjaxParameterUtilsTest extends AbstractTest {

	private static final double EPSILON = 0.0001d;
	private ServletRequest request;

	@Before
	public void before() {

		request = mock(ServletRequest.class);
	}

	// -------------------- getInteger -------------------- //

	@Test
	public void getIntegerWithValidValue() {

		when(request.getParameter("foo")).thenReturn("42");

		Integer value = AjaxParameterUtils.getInteger(request, "foo");

		assertNotNull(value);
		assertEquals(42, value.intValue());
	}

	@Test
	public void getIntegerWithMissingValue() {

		Integer value = AjaxParameterUtils.getInteger(request, "foo");

		assertNull(value);
	}

	@Test(expected = AjaxHttpBadRequestError.class)
	public void getIntegerWithInvalidValue() {

		when(request.getParameter("foo")).thenReturn("x");

		AjaxParameterUtils.getInteger(request, "foo");
	}

	// -------------------- getIntOrDefault -------------------- //

	@Test
	public void getIntOrDefaultWithValidValue() {

		when(request.getParameter("foo")).thenReturn("13");

		int value = AjaxParameterUtils.getIntOrDefault(request, "foo", 88);

		assertEquals(13, value);
	}

	@Test
	public void getIntOrDefaultWithMissingValue() {

		int value = AjaxParameterUtils.getIntOrDefault(request, "foo", 88);

		assertEquals(88, value);
	}

	@Test(expected = AjaxHttpBadRequestError.class)
	public void getIntOrDefaultWithInvalidValue() {

		when(request.getParameter("foo")).thenReturn("x");

		AjaxParameterUtils.getIntOrDefault(request, "foo", 88);
	}

	// -------------------- getIntOrThrow -------------------- //

	@Test
	public void getIntOrThrowWithValidValue() {

		when(request.getParameter("foo")).thenReturn("13");

		int value = AjaxParameterUtils.getIntOrThrow(request, "foo");

		assertEquals(13, value);
	}

	@Test(expected = AjaxHttpBadRequestError.class)
	public void getIntOrThrowWithMissingValue() {

		AjaxParameterUtils.getIntOrThrow(request, "foo");
	}

	@Test(expected = AjaxHttpBadRequestError.class)
	public void getIntOrThrowWithInvalidValue() {

		when(request.getParameter("foo")).thenReturn("x");

		AjaxParameterUtils.getIntOrThrow(request, "foo");
	}

	// -------------------- getDouble -------------------- //

	@Test
	public void getDoubleWithValidValue() {

		when(request.getParameter("foo")).thenReturn("42.12");

		Double value = AjaxParameterUtils.getDouble(request, "foo");

		assertNotNull(value);
		assertTrue(Math.abs(value - 42.12) < EPSILON);
	}

	@Test
	public void getDoubleWithMissingValue() {

		Double value = AjaxParameterUtils.getDouble(request, "foo");

		assertNull(value);
	}

	@Test(expected = AjaxHttpBadRequestError.class)
	public void getDoubleWithInvalidValue() {

		when(request.getParameter("foo")).thenReturn("x");

		AjaxParameterUtils.getDouble(request, "foo");
	}

	// -------------------- getDoubleOrDefault -------------------- //

	@Test
	public void getDoubleOrDefaultWithValidValue() {

		when(request.getParameter("foo")).thenReturn("13.12");

		Double value = AjaxParameterUtils.getDoubleOrDefault(request, "foo", 88.88);

		assertTrue(Math.abs(value - 13.12) < EPSILON);
	}

	@Test
	public void getDoubleOrDefaultWithMissingValue() {

		Double value = AjaxParameterUtils.getDoubleOrDefault(request, "foo", 88.88);

		assertTrue(Math.abs(value - 88.88) < EPSILON);
	}

	@Test(expected = AjaxHttpBadRequestError.class)
	public void getDoubleOrDefaultWithInvalidValue() {

		when(request.getParameter("foo")).thenReturn("x");

		AjaxParameterUtils.getDoubleOrDefault(request, "foo", 88.88);
	}

	// -------------------- getDoubleOrThrow -------------------- //

	@Test
	public void getDoubleOrThrowWithValidValue() {

		when(request.getParameter("foo")).thenReturn("13.12");

		Double value = AjaxParameterUtils.getDoubleOrThrow(request, "foo");

		assertTrue(Math.abs(value - 13.12) < EPSILON);
	}

	@Test(expected = AjaxHttpBadRequestError.class)
	public void getDoubleOrThrowWithMissingValue() {

		AjaxParameterUtils.getDoubleOrThrow(request, "foo");
	}

	@Test(expected = AjaxHttpBadRequestError.class)
	public void getDoubleOrThrowWithInvalidValue() {

		when(request.getParameter("foo")).thenReturn("x");

		AjaxParameterUtils.getDoubleOrThrow(request, "foo");
	}
}
