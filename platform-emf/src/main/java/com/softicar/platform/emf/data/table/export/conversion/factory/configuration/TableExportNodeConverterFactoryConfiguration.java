package com.softicar.platform.emf.data.table.export.conversion.factory.configuration;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.emf.data.table.export.conversion.factory.ITableExportNodeConverterFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableExportNodeConverterFactoryConfiguration {

	private final ITableExportNodeConverterFactory headerFactory;
	private final ITableExportNodeConverterFactory defaultFactory;
	private final List<ITableExportNodeConverterFactory> availableFactories = new ArrayList<>();

	/**
	 * @param headerFactory
	 * @param defaultFactory
	 */
	public TableExportNodeConverterFactoryConfiguration(ITableExportNodeConverterFactory headerFactory, ITableExportNodeConverterFactory defaultFactory) {

		validateArgumentConverterFactory(headerFactory);
		validateArgumentConverterFactory(defaultFactory);

		this.headerFactory = headerFactory;
		this.defaultFactory = defaultFactory;

		this.availableFactories.add(defaultFactory);
	}

	@SafeVarargs
	public final TableExportNodeConverterFactoryConfiguration addAvailableFactories(ITableExportNodeConverterFactory...availableFactories) {

		if (availableFactories != null) {
			this.availableFactories.addAll(Arrays.asList(availableFactories));
		}

		return this;
	}

	public ITableExportNodeConverterFactory getHeaderFactory() {

		return headerFactory;
	}

	public ITableExportNodeConverterFactory getDefaultFactory() {

		return defaultFactory;
	}

	public List<ITableExportNodeConverterFactory> getAvailableFactories() {

		return availableFactories;
	}

	private static final void validateArgumentConverterFactory(ITableExportNodeConverterFactory converter) {

		if (converter == null) {
			throw new SofticarDeveloperException("The given %s must not be null.", ITableExportNodeConverterFactory.class.getSimpleName());
		}
	}
}
