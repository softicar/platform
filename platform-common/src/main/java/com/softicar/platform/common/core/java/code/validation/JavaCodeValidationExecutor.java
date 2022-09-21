package com.softicar.platform.common.core.java.code.validation;

import com.softicar.platform.common.code.classpath.metadata.ClasspathFilesMetadata;
import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classpath.IJavaClasspathRoot;
import com.softicar.platform.common.core.java.classpath.JavaClasspath;
import com.softicar.platform.common.core.java.classpath.JavaClasspathRootFilter;
import com.softicar.platform.common.core.java.code.validator.IJavaCodeValidator;
import com.softicar.platform.common.core.java.code.validator.JavaCodeValidator;
import com.softicar.platform.common.core.java.stack.trace.JavaStackTraces;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JavaCodeValidationExecutor {

	private final JavaCodeValidationEnvironment environment;
	private final Collection<Throwable> throwables;
	private final Map<String, Long> timings;

	public JavaCodeValidationExecutor(JavaCodeValidationEnvironment environment) {

		this.environment = environment;
		this.throwables = new ArrayList<>();
		this.timings = new HashMap<>();
	}

	public void execute() {

		analyzeClasspath();
		findValidatorClasses().forEach(this::executeValidatorClass);
		logTimings();

		if (!throwables.isEmpty()) {
			throw new JavaCodeValidationException(
				"Code validation failure:\n%s",
				throwables//
					.stream()
					.map(this::getMessage)
					.collect(Collectors.joining("\n")));
		}
	}

	private void analyzeClasspath() {

		environment.logVerbose("Classpath roots that will be validated:");

		try (var timing = new Timing("(Analyze Classpath)")) {
			JavaClasspath classpath = JavaClasspath.getInstance();
			classpath.setPayloadFilter(new JavaClasspathRootFilter().setJarRegex(environment.getJarFilterRegex()));
			classpath.getPayloadRoots().forEach(root -> environment.logVerbose("  * " + root.toString()));
			classpath.getPayloadRoots().forEach(root -> root.getAnalyzedClasses());

			ClasspathFilesMetadata.getInstance();
		}
	}

	private Collection<Class<?>> findValidatorClasses() {

		return JavaClasspath//
			.getInstance()
			.getPayloadRoots()
			.stream()
			.map(IJavaClasspathRoot::getAnalyzedClasses)
			.flatMap(Collection::stream)
			.filter(javaClass -> javaClass.hasAnnotation(JavaCodeValidator.class))
			.map(AnalyzedJavaClass::loadClass)
			.collect(Collectors.toList());
	}

	private void logTimings() {

		if (!timings.isEmpty()) {
			environment.logVerbose("Validation runtimes:");
			timings//
				.entrySet()
				.stream()
				.sorted(Comparator.comparing(entry -> entry.getKey()))
				.forEach(entry -> environment.logVerbose("  * %s: %sms", entry.getKey(), entry.getValue()));
		}
	}

	private void executeValidatorClass(Class<?> validatorClass) {

		environment.logVerbose("Executing %s", validatorClass.getSimpleName());
		environment.setLogPrefix("    ");
		try (var timing = new Timing(validatorClass.getSimpleName())) {
			IJavaCodeValidator validator = (IJavaCodeValidator) validatorClass.getConstructor().newInstance();
			validator.validate(environment);
		} catch (Throwable throwable) {
			throwables.add(throwable);
		} finally {
			environment.setLogPrefix("");
		}
	}

	private String getMessage(Throwable throwable) {

		if (throwable instanceof JavaCodeValidationException) {
			return throwable.getMessage();
		} else {
			return "Unexpected exception:\n" + JavaStackTraces.getStackTraceAsString(throwable);
		}
	}

	private class Timing implements AutoCloseable {

		private final String name;
		private final long start;

		public Timing(String name) {

			this.name = name;
			this.start = System.currentTimeMillis();
		}

		@Override
		public void close() {

			timings.put(name, System.currentTimeMillis() - start);
		}
	}
}
