package com.softicar.platform.common.core.java.classes.analyzer;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.java.identifier.declaration.JavaIdentifierDeclaration;
import com.softicar.platform.common.core.java.identifier.key.JavaIdentifierKey;
import com.softicar.platform.common.core.java.method.reference.JavaLambdaMethodReferenceFilter;
import com.softicar.platform.common.core.java.method.reference.JavaMethodReference;
import com.softicar.platform.common.testing.AbstractTest;
import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.math.BigDecimal;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;
import org.objectweb.asm.Opcodes;

public class JavaClassAnalyzerTest extends AbstractTest {

	private AnalyzedJavaClass javaClass;

	@Test
	public void testClassImplements() {

		analyze(TestClassForImplements.class);

		assertReferencedClasses(//
			AutoCloseable.class,
			BigDecimal.class,
			Comparable.class,
			List.class);
		assertReferencedMethods(//
			createReference(Object.class, "<init>", "()V"),
			createReference(TestClassForImplements.class, "compareTo", "(Ljava/util/List;)I"));
		assertDeclaredMethods(//
			createDeclaration("<init>", "()V", 0),
			createDeclaration("close", "()V", Opcodes.ACC_PUBLIC),
			createDeclaration("compareTo", "(Ljava/util/List;)I", Opcodes.ACC_PUBLIC));
		assertDeclaredFields();
		assertAnnotations();
	}

	@Test
	public void testClassExtends() {

		analyze(TestClassForExtends.class);

		assertReferencedClasses(//
			AbstractList.class,
			Number.class,
			Set.class);
		assertReferencedMethods(//
			createReference(AbstractList.class, "<init>", "()V"),
			createReference(TestClassForExtends.class, "get", "(I)Ljava/util/Set;"));
		assertDeclaredMethods(//
			createDeclaration("<init>", "()V", 0),
			createDeclaration("get", "(I)Ljava/util/Set;", Opcodes.ACC_PUBLIC),
			createDeclaration("size", "()I", Opcodes.ACC_PUBLIC));
		assertDeclaredFields();
		assertAnnotations();
	}

	@Test
	public void testClassReferences() {

		analyze(TestClassForClassReferences.class);

		assertReferencedClasses(//
			BigDecimal.class,
			Class.class,
			List.class,
			String.class);
		assertReferencedMethods(//
			createReference(Object.class, "<init>", "()V"),
			createReference(TestClassForClassReferences.class, "bar", "(Ljava/lang/Class;)V"));
		assertDeclaredMethods(//
			createDeclaration("<clinit>", "()V", Opcodes.ACC_STATIC),
			createDeclaration("<init>", "()V", 0),
			createDeclaration("bar", "(Ljava/lang/Class;)V", Opcodes.ACC_PRIVATE),
			createDeclaration("foo", "()V", Opcodes.ACC_PRIVATE));
		assertDeclaredFields(//
			createDeclaration("STATIC_FIELD", "Ljava/lang/Class;", Opcodes.ACC_FINAL + Opcodes.ACC_STATIC + Opcodes.ACC_PRIVATE),
			createDeclaration("field", "Ljava/lang/Class;", Opcodes.ACC_FINAL + Opcodes.ACC_PRIVATE));
		assertAnnotations();
	}

	@Test
	public void testClassAnnotations() {

		analyze(TestClassForAnnotations.class);

		assertReferencedClasses(//
			Number.class,
			TestAnnotationWithClassValue.class,
			TestAnnotationWithEnumValue.class,
			TestEnum.class,
			TestAnnotationWithStringValue.class);
		assertReferencedMethods(//
			createReference(Object.class, "<init>", "()V"));
		assertDeclaredMethods(//
			createDeclaration("<init>", "()V", 0));
		assertDeclaredFields();
		assertAnnotations(//
			TestAnnotationWithStringValue.class,
			TestAnnotationWithClassValue.class,
			TestAnnotationWithEnumValue.class);
	}

	@Test
	public void testTypeUseAnnotations() {

		analyze(TestClassForAnnotationsOnTypeUse.class);

		assertReferencedClasses(//
			ArrayList.class,
			Collection.class,
			String.class,
			TestAnnotationForTypeUse.class);
		assertReferencedMethods(//
			createReference(ArrayList.class, "<init>", "()V"),
			createReference(Collection.class, "add", "(Ljava/lang/Object;)Z"),
			createReference(Object.class, "<init>", "()V"));
		assertDeclaredMethods(//
			createDeclaration("<init>", "()V", 0),
			createDeclaration("foo", "()Ljava/util/Collection;", Opcodes.ACC_PUBLIC));
		assertDeclaredFields();
		assertAnnotations();
	}

	@Test
	public void testFields() {

		analyze(TestClassForFields.class);

		assertReferencedClasses(//
			BigDecimal.class,
			Collection.class,
			List.class,
			Number.class,
			Set.class,
			String.class,
			TestAnnotationWithClassValue.class,
			TestAnnotationWithEnumValue.class,
			TestEnum.class);
		assertReferencedMethods(//
			createReference(Object.class, "<init>", "()V"));
		assertDeclaredMethods(//
			createDeclaration("<init>", "()V", 0));
		assertDeclaredFields(//
			createDeclaration("fieldA", "Ljava/util/Set;", Opcodes.ACC_PRIVATE),
			createDeclaration("fieldB", "Ljava/util/Collection;", Opcodes.ACC_PRIVATE));
		assertAnnotations();
	}

	@Test
	public void testInnerClass() {

		analyze(TestClassForInnerClasses.class);

		assertReferencedClasses(//
			TestClassForInnerClasses.A.class,
			TestClassForInnerClasses.B.class);
		assertReferencedMethods(//
			createReference(Object.class, "<init>", "()V"),
			createReference(TestClassForInnerClasses.B.class, "<init>", String.format("(%s)V", getTypeSignature(TestClassForInnerClasses.class))));
		assertDeclaredMethods(//
			createDeclaration("<init>", "()V", 0));
		assertDeclaredFields(//
			createDeclaration(//
				"a",
				"Lcom/softicar/platform/common/core/java/classes/analyzer/TestClassForInnerClasses$A;",
				Opcodes.ACC_FINAL + Opcodes.ACC_PRIVATE),
			createDeclaration("b", "Ljava/lang/Object;", Opcodes.ACC_FINAL + Opcodes.ACC_PRIVATE));
		assertAnnotations();
	}

	@Test
	public void testMethods() {

		analyze(TestClassForMethods.class);

		assertReferencedClasses(//
			BigDecimal.class,
			Byte.class,
			Collections.class,
			Double.class,
			Float.class,
			Integer.class,
			List.class,
			Long.class,
			Map.class,
			Number.class,
			RuntimeException.class,
			Set.class,
			Stream.class,
			String.class,
			TestClassForInnerClasses.A.class,
			TreeSet.class);
		assertReferencedMethods(//
			createReference(Integer.class, "valueOf", "(I)Ljava/lang/Integer;"),
			createReference(List.class, "stream", "()Ljava/util/stream/Stream;"),
			createReference(Long.class, "valueOf", "(J)Ljava/lang/Long;"),
			createReference(Object.class, "<init>", "()V"),
			createReference(Stream.class, "count", "()J"),
			createReference(TreeSet.class, "<init>", "()V"));
		assertDeclaredMethods(//
			createDeclaration("<init>", "()V", 0),
			createDeclaration("foo", "(Ljava/util/List;)Ljava/lang/Double;", Opcodes.ACC_PUBLIC));
		assertDeclaredFields();
		assertAnnotations();
	}

	@Test
	public void testMethodWithAnnotations() {

		analyze(TestClassForMethodWithAnnotations.class);

		assertReferencedClasses(//
			Double.class,
			TestAnnotation.class,
			TestAnnotationWithClassValue.class);
		assertReferencedMethods(//
			createReference(Object.class, "<init>", "()V"));
		assertDeclaredMethods(//
			createDeclaration("<init>", "()V", 0),
			createDeclaration("foo", "(Ljava/lang/Object;)V", Opcodes.ACC_PUBLIC));
		assertDeclaredFields();
		assertAnnotations();
	}

	@Test
	public void testMethodWithDynamicCast() {

		analyze(TestClassForMethodWithDynamicCast.class);

		assertReferencedClasses(//
			Double.class,
			Number.class);
		assertReferencedMethods(//
			createReference(Object.class, "<init>", "()V"),
			createReference(TestClassForMethodWithDynamicCast.class, "getSome", "()Ljava/lang/Object;"));
		assertDeclaredMethods(//
			createDeclaration("<init>", "()V", 0),
			createDeclaration("foo", "()V", Opcodes.ACC_PUBLIC),
			createDeclaration("getSome", "()Ljava/lang/Object;", Opcodes.ACC_PRIVATE));
		assertDeclaredFields();
		assertAnnotations();
	}

	@Test
	public void testMethodWithInvokeDynamicInstructions() {

		analyze(TestClassForMethodWithInvokeDynamicInstruction.class);

		assertReferencedClasses(//
			ArrayList.class,
			CallSite.class,
			Function.class,
			Integer.class,
			LambdaMetafactory.class,
			List.class,
			MethodHandle.class,
			MethodHandles.Lookup.class,
			MethodType.class,
			Optional.class,
			String.class,
			Supplier.class);
		assertReferencedMethods(//
			createReference(ArrayList.class, "<init>", "(I)V"),
			createReference(Object.class, "<init>", "()V"),
			createReference(Optional.class, "ofNullable", "(Ljava/lang/Object;)Ljava/util/Optional;"),
			createReference(Optional.class, "orElseGet", "(Ljava/util/function/Supplier;)Ljava/lang/Object;"),
			createReference(Integer.class, "valueOf", "(I)Ljava/lang/Integer;"));
		assertDeclaredMethods(//
			createDeclaration("<init>", "()V", 0),
			createDeclaration("foo", "()V", Opcodes.ACC_PUBLIC));
		assertDeclaredFields();
		assertAnnotations();
	}

	@Test
	public void testMethodWithLambda() {

		analyze(TestClassForMethodWithLambda.class);

		assertReferencedClasses(//
			CallSite.class,
			Integer.class,
			LambdaMetafactory.class,
			MethodHandle.class,
			MethodHandles.Lookup.class,
			MethodType.class,
			Number.class,
			String.class,
			Supplier.class);
		assertReferencedMethods(//
			createReference(Integer.class, "valueOf", "(I)Ljava/lang/Integer;"),
			createReference(Object.class, "<init>", "()V"));
		assertDeclaredMethods(//
			createDeclaration("<init>", "()V", 0),
			createDeclaration("foo", "()V", Opcodes.ACC_PUBLIC));
		assertDeclaredFields();
		assertAnnotations();
	}

	// ------------------------------ private utility ------------------------------ //

	private static String getTypeSignature(Class<?> clazz) {

		return 'L' + clazz.getCanonicalName().replace('.', '/') + ';';
	}

	private JavaMethodReference createReference(Class<?> owner, String methodName, String methodDescriptor) {

		return new JavaMethodReference(owner, methodName, methodDescriptor);
	}

	private JavaIdentifierDeclaration createDeclaration(String name, String descriptor, int access) {

		return new JavaIdentifierDeclaration(javaClass, new JavaIdentifierKey(name, descriptor), access);
	}

	/**
	 * Analyzes the dependencies of the given class.
	 *
	 * @param classToAnalyze
	 *            the class to analyze (never null)
	 */
	private void analyze(Class<?> classToAnalyze) {

		this.javaClass = new JavaClassAnalyzer(classToAnalyze).analyze();
	}

	/**
	 * Asserts that the previously analyzed class has the given class
	 * references.
	 *
	 * @param expectedReferencedClasses
	 *            the expected classes (never null)
	 */
	private void assertReferencedClasses(Class<?>...expectedReferencedClasses) {

		Set<JavaClassName> expectedClassNames = getJavaClassNames(expectedReferencedClasses);

		// add Object.class and analyzed class as default expectation
		expectedClassNames.add(javaClass.getClassName());
		expectedClassNames.add(new JavaClassName(Object.class));

		Set<JavaClassName> missing = getDifference(expectedClassNames, javaClass.getReferencedClasses());
		Set<JavaClassName> unexpected = getDifference(javaClass.getReferencedClasses(), expectedClassNames);
		String message = new StringBuilder()//
			.append("Dependency mismatch.\n")
			.append(String.format("Missing dependencies: %s\n", missing))
			.append(String.format("Unexpected dependencies: %s", unexpected))
			.toString();
		if (!expectedClassNames.equals(javaClass.getReferencedClasses())) {
			throw new AssertionError(message);
		}
	}

	private void assertReferencedMethods(JavaMethodReference...methodReferences) {

		Set<JavaMethodReference> expectedMethodReferences = new TreeSet<>(Arrays.asList(methodReferences));
		Set<JavaMethodReference> actualMethodReferences = javaClass.getReferencedMethods(new JavaLambdaMethodReferenceFilter());

		Set<JavaMethodReference> missing = getDifference(expectedMethodReferences, actualMethodReferences);
		Set<JavaMethodReference> unexpected = getDifference(actualMethodReferences, expectedMethodReferences);
		String message = new StringBuilder()//
			.append("Dependency mismatch.\n")
			.append(String.format("Missing dependencies: %s\n", missing))
			.append(String.format("Unexpected dependencies: %s", unexpected))
			.toString();
		if (!actualMethodReferences.equals(expectedMethodReferences)) {
			throw new AssertionError(message);
		}
	}

	private void assertDeclaredMethods(JavaIdentifierDeclaration...methodDeclarations) {

		Set<JavaIdentifierDeclaration> expectedMethodDeclarations = new TreeSet<>(Arrays.asList(methodDeclarations));
		Set<JavaIdentifierDeclaration> actualMethodDeclarations = stripSynthetics(javaClass.getDeclaredMethods());

		Set<JavaIdentifierDeclaration> missing = getDifference(expectedMethodDeclarations, actualMethodDeclarations);
		Set<JavaIdentifierDeclaration> unexpected = getDifference(actualMethodDeclarations, expectedMethodDeclarations);

		String message = new StringBuilder()//
			.append("Declared methods mismatch.\n")
			.append(String.format("Missing methods: %s\n", missing))
			.append(String.format("Unexpected methods: %s", unexpected))
			.toString();
		if (!actualMethodDeclarations.equals(expectedMethodDeclarations)) {
			throw new AssertionError(message);
		}
	}

	private void assertDeclaredFields(JavaIdentifierDeclaration...fieldDeclarations) {

		Set<JavaIdentifierDeclaration> expectedFieldDeclarations = new TreeSet<>(Arrays.asList(fieldDeclarations));
		Set<JavaIdentifierDeclaration> actualFieldDeclarations = stripSynthetics(javaClass.getDeclaredFields());

		Set<JavaIdentifierDeclaration> missing = getDifference(expectedFieldDeclarations, actualFieldDeclarations);
		Set<JavaIdentifierDeclaration> unexpected = getDifference(actualFieldDeclarations, expectedFieldDeclarations);

		String message = new StringBuilder()//
			.append("Declared fields mismatch.\n")
			.append(String.format("Missing fields: %s\n", missing))
			.append(String.format("Unexpected fields: %s", unexpected))
			.toString();
		if (!actualFieldDeclarations.equals(expectedFieldDeclarations)) {
			throw new AssertionError(message);
		}
	}

	private void assertAnnotations(Class<?>...expectedAnnotationClasses) {

		Set<JavaClassName> expectedClasses = getJavaClassNames(expectedAnnotationClasses);
		Set<JavaClassName> actualClasses = javaClass//
			.getAnnotations()
			.stream()
			.map(AnalyzedJavaClassAnnotation::getClassName)
			.collect(Collectors.toCollection(TreeSet::new));

		Set<JavaClassName> missing = getDifference(expectedClasses, actualClasses);
		Set<JavaClassName> unexpected = getDifference(actualClasses, expectedClasses);
		String message = new StringBuilder()//
			.append("Annotation mismatch.\n")
			.append(String.format("Missing annotations: %s\n", missing))
			.append(String.format("Unexpected annotations: %s", unexpected))
			.toString();
		if (!actualClasses.equals(expectedClasses)) {
			throw new AssertionError(message);
		}
	}

	private static <T> Set<T> getDifference(Set<T> left, Set<T> right) {

		Set<T> difference = new TreeSet<>(left);
		difference.removeAll(right);
		return difference;
	}

	private Set<JavaClassName> getJavaClassNames(Class<?>[] classes) {

		return Arrays//
			.asList(classes)
			.stream()
			.map(JavaClassName::new)
			.collect(Collectors.toCollection(TreeSet::new));
	}

	private Set<JavaIdentifierDeclaration> stripSynthetics(Collection<JavaIdentifierDeclaration> declarations) {

		return declarations//
			.stream()
			.filter(declaration -> !declaration.isSynthetic())
			.collect(Collectors.toCollection(TreeSet::new));
	}
}
