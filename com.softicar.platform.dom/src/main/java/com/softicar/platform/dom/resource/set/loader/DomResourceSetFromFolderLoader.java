package com.softicar.platform.dom.resource.set.loader;

import com.softicar.platform.common.io.resource.file.FileResource;
import com.softicar.platform.common.io.resource.key.ResourceKey;
import com.softicar.platform.dom.resource.set.DomResourceSet;
import java.io.File;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Loads a {@link DomResourceSet} from a folder.
 *
 * @author Oliver Richers
 */
public class DomResourceSetFromFolderLoader {

	private final File rootFolder;
	private DomResourceSet resourceSet;

	/**
	 * Constructs this {@link DomResourceSetFromFolderLoader}.
	 *
	 * @param rootFolder
	 *            the root folder of the {@link DomResourceSet} (never <i>null</i>)
	 */
	public DomResourceSetFromFolderLoader(File rootFolder) {

		this.rootFolder = Objects.requireNonNull(rootFolder);
	}

	/**
	 * Returns the {@link DomResourceSet} according to the files found in the given
	 * root folder.
	 *
	 * @return the {@link DomResourceSet} (never <i>null</i>)
	 */
	public DomResourceSet load() {

		this.resourceSet = new DomResourceSet();

		Stream.of(rootFolder.listFiles()).filter(File::isDirectory).forEach(this::addPackageFolder);

		return resourceSet;
	}

	private void addPackageFolder(File packageFolder) {

		Stream.of(packageFolder.listFiles()).forEach(this::addResourceFile);
	}

	private void addResourceFile(File resourceFile) {

		resourceSet.put(getResourceKey(resourceFile), new FileResource(resourceFile));
	}

	private ResourceKey getResourceKey(File resourceFile) {

		String packageName = resourceFile.getParentFile().getName();
		return new ResourceKey(packageName, resourceFile.getName());
	}
}
