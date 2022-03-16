package com.softicar.platform.common.io.resource.supplier;

/**
 * Implementations of this interface create {@link IResourceSupplier} instances
 * from file names.
 *
 * @author Alexander Schmidt
 */
@FunctionalInterface
public interface IResourceSupplierFactory {

	/**
	 * Creates an {@link IResourceSupplier} that supplies the resource with the
	 * given file name.
	 *
	 * @param filename
	 *            the name of the resource file (never <i>null</i>)
	 * @return the {@link IResourceSupplier} (never <i>null</i>)
	 */
	IResourceSupplier create(String filename);
}
