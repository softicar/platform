package com.softicar.platform.integration.unit.test;

import com.softicar.platform.common.testing.AbstractTest;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;

/**
 * Ensures that all unit test classes extend {@link AbstractTest}.
 * <p>
 * A class is considered a unit test if it contains at least one
 * {@link Test}-annotated method.
 *
 * @author Alexander Schmidt
 */
public class UnitTestCodeValidationTest extends AbstractTest {

	private static final Class<?> REQUIRED_BASE_CLASS = AbstractTest.class;

	@Test
	public void test() {

		try (ScanResult scanResult = new ClassGraph().ignoreClassVisibility().enableAnnotationInfo().enableMethodInfo().scan()) {
			List<String> badClasses = scanResult//
				.getClassesWithMethodAnnotation(Test.class.getCanonicalName())
				.stream()
				.filter(this::isMissingBaseClass)
				.map(ClassInfo::getName)
				.sorted()
				.collect(Collectors.toList());

			if (!badClasses.isEmpty()) {
				fail(
					"The following %s test classes do not extend '%s':\n%s"
						.formatted(//
							badClasses.size(),
							REQUIRED_BASE_CLASS.getCanonicalName(),
							badClasses.stream().collect(Collectors.joining("\n"))));
			}
		}
	}

	private boolean isMissingBaseClass(ClassInfo classInfo) {

		return !classInfo.extendsSuperclass(REQUIRED_BASE_CLASS.getCanonicalName());
	}
}
