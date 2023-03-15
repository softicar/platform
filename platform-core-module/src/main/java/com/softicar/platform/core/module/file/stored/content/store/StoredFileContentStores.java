package com.softicar.platform.core.module.file.stored.content.store;

import com.softicar.platform.core.module.file.stored.repository.AGStoredFileRepository;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Provides utility methods for {@link IStoredFileContentStore} instances.
 *
 * @author Alexander Schmidt
 */
public class StoredFileContentStores {

	private StoredFileContentStores() {

		// do not instantiate
	}

	/**
	 * Determines all content stores that are currently available.
	 * <p>
	 * If there is a primary content store that is <i>available</i> (according
	 * to {@link IStoredFileContentStore#isAvailable()}), that content store
	 * will be the first element in the returned {@link Collection}.
	 * <p>
	 * If no content store can be determined, an empty {@link Collection} is
	 * returned.
	 *
	 * @return all currently available content stores
	 * @see IStoredFileContentStore#isAvailable()
	 */
	public static Collection<IStoredFileContentStore> getAvailableContentStores() {

		return getAllContentStores()//
			.stream()
			.filter(store -> store.isAvailable())
			.collect(Collectors.toList());
	}

	/**
	 * Determines the currently preferred and available content store.
	 * <p>
	 * If there is a primary content store that is <i>available</i> (according
	 * to {@link IStoredFileContentStore#isAvailable()}), that content store
	 * will be returned. Otherwise, an available non-primary content store may
	 * be returned.
	 * <p>
	 * If no content store can be determined, {@link Optional#empty()} is
	 * returned.
	 *
	 * @return the currently preferred and available content store
	 * @see IStoredFileContentStore#isAvailable()
	 */
	public static Optional<IStoredFileContentStore> getPreferredAvailableContentStore() {

		return getAvailableContentStores()//
			.stream()
			.findFirst();
	}

	/**
	 * Returns the primary content store if it is defined and available.
	 *
	 * @return the available primary content store
	 */
	public static Optional<IStoredFileContentStore> getPrimaryContentStore() {

		return AGStoredFileRepository//
			.getPrimaryIfActive()
			.flatMap(new StoredFileContentStoreFactory()::create)
			.filter(store -> store.isAvailable());
	}

	private static List<IStoredFileContentStore> getAllContentStores() {

		return AGStoredFileRepository//
			.getAllActiveWithPrimaryFirst()
			.stream()
			.map(new StoredFileContentStoreFactory()::create)
			.flatMap(Optional::stream)
			.collect(Collectors.toList());
	}
}
