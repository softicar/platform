package com.softicar.platform.core.module.component.external;

import com.softicar.platform.core.module.component.external.key.IExternalComponentKey;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Common implementation of {@link IExternalComponent}.
 *
 * @author Alexander Schmidt
 */
public class ExternalComponent implements IExternalComponent {

	private final IExternalComponentKey key;
	private String description;
	private String version;
	private String license;

	/**
	 * Constructs a new {@link ExternalComponent}.
	 *
	 * @param key
	 *            the component key (never <i>null</i>)
	 */
	public ExternalComponent(IExternalComponentKey key) {

		this.key = Objects.requireNonNull(key);
		this.description = null;
		this.version = null;
		this.license = null;
	}

	@Override
	public IExternalComponentKey getKey() {

		return key;
	}

	@Override
	public String getDescription() {

		return description;
	}

	/**
	 * Sets the first supplied non-null value as a description.
	 * <p>
	 * Any given {@link Supplier} is only invoked if all previous
	 * {@link Supplier}s yielded <i>null</i>.
	 *
	 * @param suppliers
	 *            the value suppliers
	 */
	@SafeVarargs
	public final void setDescription(Supplier<String>...suppliers) {

		setSupplied(this::setDescription, suppliers);
	}

	@Override
	public String getVersion() {

		return version;
	}

	/**
	 * Sets the first supplied non-null value as a version.
	 * <p>
	 * Any given {@link Supplier} is only invoked if all previous
	 * {@link Supplier}s yielded <i>null</i>.
	 *
	 * @param suppliers
	 *            the value suppliers
	 */
	@SafeVarargs
	public final void setVersion(Supplier<String>...suppliers) {

		setSupplied(this::setVersion, suppliers);
	}

	@Override
	public String getLicense() {

		return license;
	}

	/**
	 * Sets the first supplied non-null value as a license.
	 * <p>
	 * Any given {@link Supplier} is only invoked if all previous
	 * {@link Supplier}s yielded <i>null</i>.
	 *
	 * @param suppliers
	 *            the value suppliers
	 */
	@SafeVarargs
	public final void setLicense(Supplier<String>...suppliers) {

		setSupplied(this::setLicense, suppliers);
	}

	private void setDescription(String description) {

		this.description = description;
	}

	private void setVersion(String version) {

		this.version = version;
	}

	private void setLicense(String license) {

		this.license = license;
	}

	@SafeVarargs
	private void setSupplied(Consumer<String> setter, Supplier<String>...suppliers) {

		Arrays//
			.asList(suppliers)
			.stream()
			.map(Supplier::get)
			.filter(Objects::nonNull)
			.findFirst()
			.ifPresent(setter);
	}
}
