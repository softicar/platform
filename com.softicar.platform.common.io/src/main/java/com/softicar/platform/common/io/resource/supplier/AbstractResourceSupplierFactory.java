package com.softicar.platform.common.io.resource.supplier;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.static_.IStaticResource;
import com.softicar.platform.common.io.resource.static_.StaticResource;
import java.nio.charset.Charset;
import java.util.Objects;

public abstract class AbstractResourceSupplierFactory implements IResourceSupplierFactory {

	private final Class<?> anchorClass;
	private final Charset charset;

	public AbstractResourceSupplierFactory(Class<?> anchorClass) {

		this(anchorClass, null);
	}

	public AbstractResourceSupplierFactory(Class<?> anchorClass, Charset charset) {

		this.anchorClass = Objects.requireNonNull(anchorClass);
		this.charset = charset;
	}

	protected abstract IResourceSupplier createSupplier(IStaticResource staticResource);

	/**
	 * Creates a new {@link IResourceSupplier} instance based on the given
	 * filename, anchor {@link Class} and optional {@link Charset}.
	 *
	 * @param filename
	 *            the file name of the {@link IResource} (never <i>null</i>)
	 * @return the {@link IResourceSupplier} (never <i>null</i>)
	 */
	@Override
	public IResourceSupplier create(String filename) {

		return createSupplier(new StaticResource(anchorClass, filename, charset));
	}
}
