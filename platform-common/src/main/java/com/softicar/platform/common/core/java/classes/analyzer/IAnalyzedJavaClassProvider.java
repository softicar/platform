package com.softicar.platform.common.core.java.classes.analyzer;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import java.util.Optional;

/**
 * Finds {@link AnalyzedJavaClass} instances by {@link JavaClassName} instances.
 *
 * @author Alexander Schmidt
 */
public interface IAnalyzedJavaClassProvider {

	/**
	 * Returns the {@link AnalyzedJavaClass} for the given
	 * {@link JavaClassName}.
	 *
	 * @param className
	 *            the {@link JavaClassName} to fetch an
	 *            {@link AnalyzedJavaClass} for (never <i>null</i>)
	 * @return the corresponding {@link AnalyzedJavaClass}
	 */
	Optional<AnalyzedJavaClass> getAnalyzedClass(JavaClassName className);

	/**
	 * Returns the {@link AnalyzedJavaClass} for the given
	 * {@link JavaClassName}.
	 * <p>
	 * Returns <i>null</i> if no corresponding {@link AnalyzedJavaClass} can be
	 * found.
	 *
	 * @param className
	 *            the {@link JavaClassName} to fetch an
	 *            {@link AnalyzedJavaClass} for (never <i>null</i>)
	 * @return the corresponding {@link AnalyzedJavaClass} (may be <i>null</i>)
	 */
	default AnalyzedJavaClass getAnalyzedClassOrNull(JavaClassName className) {

		return getAnalyzedClass(className).orElse(null);
	}
}
