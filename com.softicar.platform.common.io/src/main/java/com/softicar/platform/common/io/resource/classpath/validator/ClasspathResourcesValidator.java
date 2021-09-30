package com.softicar.platform.common.io.resource.classpath.validator;

import com.softicar.platform.common.core.constant.container.field.ConstantContainerFieldExtractor;
import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClassCache;
import com.softicar.platform.common.core.java.classes.analyzer.IAnalyzedJavaClassProvider;
import com.softicar.platform.common.core.java.classpath.JavaClasspath;
import com.softicar.platform.common.core.java.classpath.JavaClasspathRootFolder;
import com.softicar.platform.common.core.java.code.validation.JavaCodeValidationEnvironment;
import com.softicar.platform.common.core.java.code.validation.output.JavaCodeViolations;
import com.softicar.platform.common.core.java.code.validator.IJavaCodeValidator;
import com.softicar.platform.common.core.java.code.validator.JavaCodeValidator;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.container.validator.ResourceSupplierContainerAnalyzedJavaClassFetcher;
import com.softicar.platform.common.string.regex.Patterns;
import java.io.File;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@JavaCodeValidator
public class ClasspathResourcesValidator implements IJavaCodeValidator {

	private JavaCodeViolations violations;

	@Override
	public void validate(JavaCodeValidationEnvironment environment) {

		this.violations = new JavaCodeViolations();

		var classPath = environment.getClassPath();
		var cache = new AnalyzedJavaClassCache(classPath);
		var classpathResourceLocations = new ResourceLocationFromClasspathLoader(classPath).load();
		var containerClassResourceLocations = new ResourceLocationFromContainerClassLoader(classPath, cache).load();

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
		private static final Collection<String> FILENAME_BLACKLIST_REGEXES = List
			.of(//
				".*\\.sqml",
				"logback\\.xml");

		private final JavaClasspath classPath;
		private final Collection<Pattern> filenameBlacklistPatterns;

		public ResourceLocationFromClasspathLoader(JavaClasspath classPath) {

			this.classPath = classPath;
			this.filenameBlacklistPatterns = createFilenameBlacklistPatterns();
		}

		public Set<ResourceLocation> load() {

			var result = new TreeSet<ResourceLocation>();
			for (JavaClasspathRootFolder rootFolder: classPath.getRootFolders()) {
				rootFolder//
					.getFiles()
					.stream()
					.filter(this::isResourceFile)
					.map(file -> new ResourceLocation(file, rootFolder))
					.forEach(result::add);
			}
			return result;
		}

		private boolean isResourceFile(File file) {

			return Optional//
				.of(file)
				.filter(this::isNotDirectory)
				.filter(this::isNotClassFile)
				.filter(this::isNotBlacklisted)
				.isPresent();
		}

		private boolean isNotDirectory(File file) {

			return !file.isDirectory();
		}

		private boolean isNotClassFile(File file) {

			return !file.getName().endsWith(".class");
		}

		private boolean isNotBlacklisted(File file) {

			return Patterns.noneMatch(file.getName(), filenameBlacklistPatterns);
		}

		private Collection<Pattern> createFilenameBlacklistPatterns() {

			return FILENAME_BLACKLIST_REGEXES//
				.stream()
				.map(Pattern::compile)
				.collect(Collectors.toList());
		}
	}

	private static class ResourceLocationFromContainerClassLoader {

		private final JavaClasspath classPath;
		private final IAnalyzedJavaClassProvider cache;

		public ResourceLocationFromContainerClassLoader(JavaClasspath classPath, IAnalyzedJavaClassProvider cache) {

			this.classPath = classPath;
			this.cache = cache;
		}

		public Set<ResourceLocation> load() {

			return classPath
				.getRootFolders()//
				.stream()
				.map(new ResourceSupplierContainerAnalyzedJavaClassFetcher(cache)::fetchJavaClasses)
				.flatMap(Collection::stream)
				.map(this::extractFields)
				.flatMap(Collection::stream)
				.map(field -> new ResourceLocation(field.getValue(), field.getContainerClass()))
				.collect(Collectors.toCollection(() -> new TreeSet<>()));
		}

		private Collection<IConstantContainerField<IResourceSupplier>> extractFields(AnalyzedJavaClass javaClass) {

			return new ConstantContainerFieldExtractor<>(loadClassOrThrow(javaClass), IResourceSupplier.class).extractFields();
		}

		private Class<?> loadClassOrThrow(AnalyzedJavaClass javaClass) {

			try {
				return javaClass.loadClass();
			} catch (ClassNotFoundException exception) {
				throw new RuntimeException(exception);
			}
		}
	}

	private static class ResourceLocation implements Comparable<ResourceLocation> {

		private final String relativePath;

		public ResourceLocation(File file, JavaClasspathRootFolder rootFolder) {

			this(rootFolder.getFile().toPath().relativize(file.toPath()).toString());
		}

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
