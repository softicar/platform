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
public interface StoredFileContentStores {

	/**
	 * Determines all content stores that are currently accessible.
	 * <p>
	 * If there is a primary content store that is <i>accessible</i> (according
	 * to {@link IStoredFileContentStore#isAccessible()}), that content store
	 * will be the first element in the returned {@link Collection}.
	 * <p>
	 * If no content store can be determined, an empty {@link Collection} is
	 * returned.
	 *
	 * @return all currently accessible content stores
	 * @see IStoredFileContentStore#isAccessible()
	 */
	public static Collection<IStoredFileContentStore> getAccessibleContentStores() {

		return getAllContentStores()//
			.stream()
			.filter(store -> store.isAccessible())
			.collect(Collectors.toList());
	}

	/**
	 * Returns the primary content store if it is defined and accessible.
	 *
	 * @return the accessible primary content store
	 */
	public static Optional<IStoredFileContentStore> getPrimaryContentStore() {

		return AGStoredFileRepository//
			.getPrimaryIfActive()
			.flatMap(new StoredFileContentStoreFactory()::create)
			.filter(store -> store.isAccessible());
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
