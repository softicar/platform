package com.softicar.platform.emf.validation;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.emf.test.simple.EmfTestObject;
import com.softicar.platform.emf.validation.result.EmfValidationResult;
import java.util.function.Consumer;
import org.junit.Test;

public class AbstractEmfValidatorTest extends AbstractDbTest {

	private final EmfTestObject object;
	private final EmfValidationResult result;

	public AbstractEmfValidatorTest() {

		this.object = new EmfTestObject();
		this.result = new EmfValidationResult();
	}

	// ------------------------------ assertNull ------------------------------ //

	@Test
	public void testAssertNullWithNull() {

		object.setName(null);

		createAndRunValidator(it -> it.assertNull(EmfTestObject.NAME));

		assertEquals("", result.toString());
	}

	@Test
	public void testAssertNullWithNonNull() {

		object.setName("foo");

		createAndRunValidator(it -> it.assertNull(EmfTestObject.NAME));

		assertEquals("ERROR: The attribute 'Name' must not be defined but was 'foo'.", result.toString());
	}

	// ------------------------------ assertNull ------------------------------ //

	@Test
	public void testAssertNotNullWithNull() {

		object.setName(null);

		createAndRunValidator(it -> it.assertNotNull(EmfTestObject.NAME));

		assertEquals("ERROR: The attribute 'Name' is mandatory.", result.toString());
	}

	@Test
	public void testAssertNotNullWithNonNull() {

		object.setName("foo");

		createAndRunValidator(it -> it.assertNotNull(EmfTestObject.NAME));

		assertEquals("", result.toString());
	}

	// ------------------------------ assertEmpty ------------------------------ //

	@Test
	public void testAssertEmptyWithNull() {

		object.setName(null);

		createAndRunValidator(it -> it.assertEmpty(EmfTestObject.NAME));

		assertEquals("", result.toString());
	}

	@Test
	public void testAssertEmptyWithEmptyString() {

		object.setName("");

		createAndRunValidator(it -> it.assertEmpty(EmfTestObject.NAME));

		assertEquals("", result.toString());
	}

	@Test
	public void testAssertEmptyWithOnlyWhitespace() {

		object.setName(" \t\n");

		createAndRunValidator(it -> it.assertEmpty(EmfTestObject.NAME));

		assertEquals("ERROR: The attribute 'Name' must be empty.", result.toString());
	}

	@Test
	public void testAssertEmptyWithNonEmptyString() {

		object.setName("foo");

		createAndRunValidator(it -> it.assertEmpty(EmfTestObject.NAME));

		assertEquals("ERROR: The attribute 'Name' must be empty.", result.toString());
	}

	// ------------------------------ assertNotBlank ------------------------------ //

	@Test
	public void testAssertNotBlankWithNull() {

		object.setName(null);

		createAndRunValidator(it -> it.assertNotBlank(EmfTestObject.NAME));

		assertEquals("ERROR: The attribute 'Name' may not be empty.", result.toString());
	}

	@Test
	public void testAssertNotBlankWithEmptyString() {

		object.setName("");

		createAndRunValidator(it -> it.assertNotBlank(EmfTestObject.NAME));

		assertEquals("ERROR: The attribute 'Name' may not be empty.", result.toString());
	}

	@Test
	public void testAssertNotBlankWithOnlyWhitespace() {

		object.setName(" \t\n");

		createAndRunValidator(it -> it.assertNotBlank(EmfTestObject.NAME));

		assertEquals("ERROR: The attribute 'Name' may not be empty.", result.toString());
	}

	@Test
	public void testAssertNotBlankWithNonEmptyString() {

		object.setName("foo");

		createAndRunValidator(it -> it.assertNotBlank(EmfTestObject.NAME));

		assertEquals("", result.toString());
	}

	// ------------------------------ assertCondition ------------------------------ //

	@Test
	public void testAssertConditionWithSuccess() {

		createAndRunValidator(it -> it.assertCondition(() -> true, () -> IDisplayString.create("Validation failed!"), EmfTestObject.NAME));

		assertEquals("", result.toString());
	}

	@Test
	public void testAssertConditionWithFailure() {

		createAndRunValidator(it -> it.assertCondition(() -> false, () -> IDisplayString.create("Validation failed!"), EmfTestObject.NAME));

		assertEquals("ERROR: Validation failed!", result.toString());
	}

	// ------------------------------ auxiliary ------------------------------ //

	private void createAndRunValidator(Consumer<TestValiator> action) {

		new TestValiator(action).validate(object, result);
	}

	private static class TestValiator extends AbstractEmfValidator<EmfTestObject> {

		private final Consumer<TestValiator> action;

		public TestValiator(Consumer<TestValiator> action) {

			this.action = action;
		}

		@Override
		protected void validate() {

			action.accept(this);
		}
	}
}
