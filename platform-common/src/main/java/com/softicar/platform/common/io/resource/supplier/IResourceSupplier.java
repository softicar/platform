package com.softicar.platform.common.io.resource.supplier;

import com.softicar.platform.common.core.constant.container.field.ConstantContainerFieldExtractor;
import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.key.IResourceKey;
import java.io.InputStream;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Supplies an {@link IResource} and a corresponding {@link IResourceKey}.
 *
 * @author Alexander Schmidt
 */
public interface IResourceSupplier {

	/**
	 * Returns the provided {@link IResource}.
	 *
	 * @return the {@link IResource} (never <i>null</i>)
	 */
	IResource getResource();

	/**
	 * Returns the {@link IResourceKey} of this {@link IResourceSupplier}.
	 *
	 * @return the {@link IResourceKey} (never <i>null</i>)
	 */
	IResourceKey getResourceKey();

	/**
	 * Returns the resource as an input stream.
	 * <p>
	 * When the resource is requested, the complete data returned by this input
	 * stream is sent to the requester.
	 *
	 * @return input stream with the content of the resource (may be
	 *         <i>null</i>)
	 */
	default InputStream getResourceAsStream() {

		return getResource().getResourceAsStream();
	}

	/**
	 * Returns all {@link IResourceSupplier} that are in a given interface.
	 *
	 * @param resourceInterface
	 *            the interface to load all {@link IResourceSupplier} from
	 * @return a collection of {@link IResourceSupplier} (never <i>null</i>)
	 */
	static Collection<IResourceSupplier> getResourceSuppliers(Class<?> resourceInterface) {

		return new ConstantContainerFieldExtractor<>(resourceInterface, IResourceSupplier.class)//
			.extractFields()
			.stream()
			.map(IConstantContainerField::getValue)
			.collect(Collectors.toList());
	}
}
