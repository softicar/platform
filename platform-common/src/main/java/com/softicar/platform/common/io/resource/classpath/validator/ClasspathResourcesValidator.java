package com.softicar.platform.common.io.resource.classpath.validator;

import com.softicar.platform.common.core.constant.container.field.ConstantContainerFieldExtractor;
import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.java.classpath.IJavaClasspathRoot;
import com.softicar.platform.common.core.java.classpath.JavaClasspath;
import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationEnvironment;
import com.softicar.platform.common.core.java.code.validation.output.JavaCodeViolations;
import com.softicar.platform.common.core.java.code.validator.IJavaCodeValidator;
import com.softicar.platform.common.core.java.code.validator.JavaCodeValidator;
import com.softicar.platform.common.io.classpath.file.IClasspathFile;
import com.softicar.platform.common.io.resource.container.ResourceSupplierContainers;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.string.regex.Patterns;
import java.io.File;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Validates that all resources have a corresponding {@link IResourceSupplier}
 * and vice-versa.
 *
 * @author Oliver Richers
 */
@JavaCodeValidator
public class ClasspathResourcesValidator implements IJavaCodeValidator {

	private JavaCodeViolations violations;

	@Override
	public void validate(JavaCodeValidationEnvironment environment) {

		this.violations = new JavaCodeViolations();

		var classpathResourceLocations = new ResourceLocationFromClasspathLoader().load();
		var containerClassResourceLocations = new ResourceLocationFromContainerClassLoader(environment).load();

		classpathResourceLocations//
			.stream()
			.filter(location -> !containerClassResourceLocations.contains(location))
			.forEach(location -> violations.formatViolation("Failed to find container class for resource file: %s", location.getRelativePath()));

		containerClassResourceLocations//
			.stream()
			.filter(location -> !classpathResourceLocations.contains(location))
			.forEach(location -> violations.formatViolation("Failed to find resource file for container class entry: %s", location.getRelativePath()));

		violations.throwExceptionIfNotEmpty();
	}

	private static class ResourceLocationFromClasspathLoader {

		// TODO This blacklist should be configurable... somehow.
		private static final Collection<String> FILE_PATH_BLACKLIST_REGEXES = List
			.of(//
				"META-INF/MANIFEST\\.MF",
				".*/[^/]*\\.sqml",
				"logback\\.xml");

		private final Collection<Pattern> filePathBlacklistPatterns;

		public ResourceLocationFromClasspathLoader() {

			this.filePathBlacklistPatterns = createFilePathBlacklistPatterns();
		}

		public Set<ResourceLocation> load() {

			var result = new TreeSet<ResourceLocation>();
			for (IJavaClasspathRoot root: JavaClasspath.getInstance().getPayloadRoots()) {
				root//
					.getResourceFiles()
					.stream()
					.map(IClasspathFile::getRelativePath)
					.filter(this::isNotBlacklisted)
					.map(ResourceLocation::new)
					.forEach(result::add);
			}
			return result;
		}

		private boolean isNotBlacklisted(String resourcePath) {

			return Patterns.noneMatch(resourcePath, filePathBlacklistPatterns);
		}

		private Collection<Pattern> createFilePathBlacklistPatterns() {

			return FILE_PATH_BLACKLIST_REGEXES//
				.stream()
				.map(Pattern::compile)
				.collect(Collectors.toList());
		}
	}

	private static class ResourceLocationFromContainerClassLoader {

		private final JavaCodeValidationEnvironment environment;

		public ResourceLocationFromContainerClassLoader(JavaCodeValidationEnvironment environment) {

			this.environment = environment;
		}

		public Set<ResourceLocation> load() {

			return ResourceSupplierContainers//
				.findAll()
				.stream()
				.map(this::extractFields)
				.flatMap(Collection::stream)
				.map(field -> new ResourceLocation(field.getValue(), field.getContainerClass()))
				.collect(Collectors.toCollection(() -> new TreeSet<>()));
		}

		private Collection<IConstantContainerField<IResourceSupplier>> extractFields(Class<?> javaClass) {

			environment.logVerbose("Resource container: %s", javaClass.getCanonicalName());

			return new ConstantContainerFieldExtractor<>(javaClass, IResourceSupplier.class).extractFields();
		}
	}

	private static class ResourceLocation implements Comparable<ResourceLocation> {

		private final String relativePath;

		private ResourceLocation(IResourceSupplier supplier, Class<?> containerClass) {

			this(createRelativePathString(supplier, containerClass));
		}

		private ResourceLocation(String relativePath) {

			this.relativePath = Objects.requireNonNull(relativePath);
		}

		@Override
		public int compareTo(ResourceLocation other) {

			return Comparator//
				.comparing(ResourceLocation::getRelativePath)
				.compare(this, other);
		}

		@Override
		public String toString() {

			return getRelativePath();
		}

		public String getRelativePath() {

			return relativePath;
		}

		private static String createRelativePathString(IResourceSupplier supplier, Class<?> containerClass) {

			return new StringBuilder()//
				.append(containerClass.getPackageName().replace(".", File.separator))
				.append(File.separator)
				.append(supplier.getResource().getFilename().orElseThrow())
				.toString();
		}
	}
}
