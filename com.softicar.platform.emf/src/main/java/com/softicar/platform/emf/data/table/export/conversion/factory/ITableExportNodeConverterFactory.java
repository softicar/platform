package com.softicar.platform.emf.data.table.export.conversion.factory;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.data.table.export.conversion.ITableExportNodeConverter;

/**
 * Super interface of all factories that create
 * {@link ITableExportNodeConverter} instances.
 * <p>
 * {@link ITableExportNodeConverterFactory}s can be selected on a per-column
 * basis.
 *
 * @param <CT>
 *            content type
 * @author Alexander Schmidt
 */
public interface ITableExportNodeConverterFactory<CT> {

	ITableExportNodeConverter<CT> create();

	IDisplayString getConverterDisplayString();

	IDisplayString getConverterDescription();
}
