package com.softicar.platform.common.core.java.code.validator;

import com.softicar.platform.common.core.java.code.validation.output.JavaCodeValidationOutputForTesting;
import java.util.function.Function;
import org.junit.Test;

public class JavaClassValidatorTest {

	private final JavaCodeValidationOutputForTesting output;

	public JavaClassValidatorTest() {

		this.output = new JavaCodeValidationOutputForTesting();
	}

	// ------------------------------ abstract ------------------------------ //

	@Test
	public void testIsNotAbstractWithViolation() {

		new JavaClassValidator(output, AbstractTestClass.class).assertIsNotAbstract();

		output.assertViolationCount(1);
	}

	@Test
	public void testIsNotAbstractWithoutViolation() {

		new JavaClassValidator(output, ConcreteTestClass.class).assertIsNotAbstract();

		output.assertViolationCount(0);
	}

	// ------------------------------ interface ------------------------------ //

	@Test
	public void testIsNotInterfaceWithViolation() {

		new JavaClassValidator(output, TestInterface.class).assertIsNotInterface();

		output.assertViolationCount(1);
	}

	@Test
	public void testIsNotInterfaceWithoutViolation() {

		new JavaClassValidator(output, ConcreteTestClass.class).assertIsNotInterface();

		output.assertViolationCount(0);
	}

	// ------------------------------ implements ------------------------------ //

	@Test
	public void testImplementsWithViolation() {

		new JavaClassValidator(output, Object.class).assertImplementsInterface(TestInterface.class);

		output.assertViolationCount(1);
	}

	@Test
	public void testImplementsWithoutViolation() {

		new JavaClassValidator(output, ConcreteTestClass.class).assertImplementsInterface(TestInterface.class);

		output.assertViolationCount(0);
	}

	// ------------------------------ annotation ------------------------------ //

	@Test
	public void testHasAnnotationWithViolation() {

		new JavaClassValidator(output, Object.class).assertHasAnnotation(FunctionalInterface.class);

		output.assertViolationCount(1);
	}

	@Test
	public void testHasAnnotationWithoutViolation() {

		new JavaClassValidator(output, Function.class).assertHasAnnotation(FunctionalInterface.class);

		output.assertViolationCount(0);
	}

	// ------------------------------ parameterless constructor ------------------------------ //

	@Test
	public void testHasParameterlessConstructorWithViolation() {

		new JavaClassValidator(output, TestClassWithoutParameterlessConstructor.class).assertHasParameterlessConstructor();

		output.assertViolationCount(1);
	}

	@Test
	public void testHasParameterlessConstructorWithoutViolation() {

		new JavaClassValidator(output, ConcreteTestClass.class).assertHasParameterlessConstructor();

		output.assertViolationCount(0);
	}

	// ------------------------------ non-static fields ------------------------------ //

	@Test
	public void testHasNoNonStaticFieldsWithViolation() {

		new JavaClassValidator(output, TestClassDerivedFromClassWithNonStaticField.class).assertHasNoNonStaticFieldsDeep();

		output.assertViolationCount(1);
	}

	@Test
	public void testHasNoNonStaticFieldsWithoutViolation() {

		new JavaClassValidator(output, ConcreteTestClass.class).assertHasNoNonStaticFieldsDeep();

		output.assertViolationCount(0);
	}

	// ------------------------------ test class ------------------------------ //

	public static interface TestInterface {

		// nothing to add
	}

	public static abstract class AbstractTestClass {

		// nothing to add
	}

	public static class ConcreteTestClass extends AbstractTestClass implements TestInterface {

		public ConcreteTestClass() {

			// nothing to add
		}
	}

	public static class TestClassWithoutParameterlessConstructor {

		@SuppressWarnings("unused")
		public TestClassWithoutParameterlessConstructor(int dummy) {

			// nothing to add
		}
	}

	public static class TestClassWithNonStaticField {

		@SuppressWarnings("unused") private String field;
	}

	public static class TestClassDerivedFromClassWithNonStaticField extends TestClassWithNonStaticField {

		// nothing to add
	}
}
