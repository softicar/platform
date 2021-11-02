package com.softicar.platform.core.module.component.external.type.library;

import com.softicar.platform.common.core.java.code.validator.IJavaCodeValidator;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.string.regex.Patterns;
import com.softicar.platform.core.module.component.external.IExternalComponent;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;

public class ExternalLibrariesFromClasspathFetcherTest extends Assert {

	private final ExternalLibrariesFromClasspathFetcher fetcher;

	public ExternalLibrariesFromClasspathFetcherTest() {

		this.fetcher = new ExternalLibrariesFromClasspathFetcher();
	}

	@Test
	public void testFetchAllReturnsTypicalLibraries() {

		var libraries = fetcher.fetchAll();

		libraries.stream().map(it -> it.getName()).forEach(Log::finfo);

		// ensure the presence of some arbitrary external libraries
		new Asserter(libraries)//
			.assertContains("asm")
			.assertContains("commons-io")
			.assertContains("junit");
	}

	/**
	 * TODO This should be turned into an {@link IJavaCodeValidator} later-on,
	 * in order to provide extend the scope of this check beyond platform
	 * dependencies.
	 */
	@Test
	public void testFetchAllReturnsLicensesForAllLibraries() {

		var libraries = fetcher.fetchAll();
		var blacklist = new LibraryLicenseCheckBlacklist();

		for (IExternalComponent library: libraries) {
			if (!blacklist.isBlacklisted(library)) {
				assertNotNull(//
					"Failed to determine license for '%s'.".formatted(library.getName()),
					library.getLicense());
			}
		}
	}

	private class Asserter {

		private final Set<String> names;

		public Asserter(List<IExternalComponent> libraries) {

			Objects.requireNonNull(libraries);
			this.names = libraries//
				.stream()
				.map(IExternalComponent::getName)
				.collect(Collectors.toSet());
		}

		public Asserter assertContains(String expectedName) {

			assertTrue(//
				"Failed to find library '%s'.".formatted(expectedName),
				names.stream().anyMatch(name -> name.equals(expectedName)));
			return this;
		}
	}

	/**
	 * Blacklists artifacts that are only present in the class path during build
	 * time.
	 * <p>
	 * The licenses of those artifacts are irrelevant for this unit test.
	 * <p>
	 * TODO Instead of bluntly blacklisting, we need a proper way to handle the
	 * licenses of build-time dependencies.
	 */
	private static class LibraryLicenseCheckBlacklist {

		private boolean isBlacklisted(IExternalComponent library) {

			return Patterns
				.anyMatch(//
					library.getName(),
					BuildTimeArtifactPattern.GRADLE.get(),
					BuildTimeArtifactPattern.JAVAX_INJECT.get(),
					BuildTimeArtifactPattern.JUL_TO_SLF4J.get(),
					BuildTimeArtifactPattern.NATIVE_PLATFORM.get(),
					BuildTimeArtifactPattern.PLATFORM.get()
				//
				);
		}

		private static enum BuildTimeArtifactPattern {

			GRADLE("gradle-.*"),
			JAVAX_INJECT("javax\\.inject"),
			JUL_TO_SLF4J("jul-to-slf4j"),
			NATIVE_PLATFORM("native-platform"),
			PLATFORM("platform-.*"),
			//
			;

			private final Pattern pattern;

			private BuildTimeArtifactPattern(String pattern) {

				this.pattern = Pattern.compile(pattern);
			}

			public Pattern get() {

				return pattern;
			}
		}
	}
}
