package com.softicar.platform.common.io.resource.supplier;

import com.softicar.platform.common.core.constant.container.field.ConstantContainerFieldExtractor;
import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.key.IResourceKey;
import com.softicar.platform.common.string.charset.Charsets;
import java.io.IOException;
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
	 * Returns the content of the {@link IResource} as UTF-8 encoded text.
	 * <p>
	 * May only be called on textual resources.
	 *
	 * @return the content as UTF-8 encoded text (never <i>null</i>)
	 */
	default String getTextUtf8() {

		try (var inputStream = getResource().getResourceAsStream()) {
			return StreamUtils.toString(inputStream, Charsets.UTF8);
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
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
