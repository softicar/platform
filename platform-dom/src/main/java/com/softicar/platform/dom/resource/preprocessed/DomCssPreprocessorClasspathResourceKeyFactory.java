package com.softicar.platform.dom.resource.preprocessed;

import com.softicar.platform.common.io.resource.key.IResourceKey;
import com.softicar.platform.common.io.resource.key.ResourceKey;
import java.util.Objects;

/**
 * Creates an {@link IResourceKey} instance from a given absolute path that
 * refers to a resource in the classpath.
 *
 * @author Alexander Schmidt
 */
class DomCssPreprocessorClasspathResourceKeyFactory {

	/**
	 * Creates an {@link IResourceKey} from the given absolute path.
	 *
	 * @param absolutePath
	 *            an absolute path that refers to a resource in the classpath
	 *            (never <i>null</i>)
	 * @return the created {@link IResourceKey} (never <i>null</i>)
	 * @throws DomCssPreprocessorException
	 *             if the given path is not absolute
	 */
	public IResourceKey create(String absolutePath) {

		validateAbsolutePath(absolutePath);
		String filename = absolutePath.replaceFirst(".*/", "");
		String packageName = absolutePath.substring(1, absolutePath.lastIndexOf("/")).replaceAll("/", ".");
		return new ResourceKey(packageName, filename);
	}

	private void validateAbsolutePath(String absolutePath) {

		Objects.requireNonNull(absolutePath);
		if (!absolutePath.startsWith("/")) {
			throw new DomCssPreprocessorException("'%s' is not an absolute path.".formatted(absolutePath));
		}
	}
}
