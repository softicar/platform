package com.softicar.platform.common.io.resource.key;

import com.softicar.platform.common.core.utils.equals.Equals;
import com.softicar.platform.common.io.resource.IResource;
import java.util.Comparator;
import java.util.Objects;

/**
 * Standard implementation of an {@link IResourceKey} which abstractly
 * identifies an {@link IResource}.
 *
 * @author Oliver Richers
 */
public class ResourceKey implements IResourceKey {

	private final String packageName;
	private final ResourceKeyBasename basename;
	private final ResourceKeySuperMimeType superMimeType;

	public ResourceKey(Class<?> anchorClass, String filename) {

		this(anchorClass.getPackageName(), filename);
	}

	public ResourceKey(String packageName, String filename) {

		this.packageName = packageName.toLowerCase();
		this.basename = ResourceKeyBasename.fromFilename(filename);
		this.superMimeType = ResourceKeySuperMimeType.fromFilename(filename);
	}

	@Override
	public int hashCode() {

		return Objects.hash(packageName, basename, superMimeType);
	}

	@Override
	public boolean equals(Object other) {

		return Equals//
			.comparing(IResourceKey::getPackageName)
			.comparing(IResourceKey::getBasename)
			.comparing(IResourceKey::getSuperMimeType)
			.compareToObject(this, other);
	}

	@Override
	public int compareTo(IResourceKey other) {

		return Comparator//
			.comparing(IResourceKey::getPackageName)
			.thenComparing(IResourceKey::getBasename)
			.thenComparing(IResourceKey::getSuperMimeType)
			.compare(this, other);
	}

	@Override
	public String toString() {

		return String.format("%s.%s.%s", packageName, basename, superMimeType);
	}

	@Override
	public String getPackageName() {

		return packageName;
	}

	@Override
	public ResourceKeyBasename getBasename() {

		return basename;
	}

	@Override
	public ResourceKeySuperMimeType getSuperMimeType() {

		return superMimeType;
	}
}
