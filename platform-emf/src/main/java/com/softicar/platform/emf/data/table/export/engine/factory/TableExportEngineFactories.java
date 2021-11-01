package com.softicar.platform.emf.data.table.export.engine.factory;

import com.softicar.platform.emf.data.table.export.popup.TableExportPopupButton;
import java.util.List;

/**
 * Provides convenience methods related to {@link ITableExportEngineFactory}
 * implementations.
 *
 * @author Alexander Schmidt
 */
public interface TableExportEngineFactories {

	/**
	 * Provides all {@link ITableExportEngineFactory} implementations (i.e.
	 * export formats) that are available for selection in the pop-up created by
	 * {@link TableExportPopupButton}.
	 *
	 * @return all {@link ITableExportEngineFactory} implementations (never
	 *         <i>null</i>)
	 */
	public static List<ITableExportEngineFactory<?>> getAllFactories() {

		return List
			.of(//
				TableExportEngineFactoryEnum.CSV.get(),
				TableExportEngineFactoryEnum.CSV_EXCEL_2003.get(),
				TableExportEngineFactoryEnum.EXCEL_POI_XLS.get(),
				TableExportEngineFactoryEnum.EXCEL_POI_XLSX.get(),
				TableExportEngineFactoryEnum.HTML.get());
	}

	/**
	 * Returns the {@link ITableExportEngineFactory} implementation (i.e. export
	 * format) that shall be pre-selected in the pop-up created by
	 * {@link TableExportPopupButton}.
	 *
	 * @return the default {@link ITableExportEngineFactory} implementation
	 *         (never <i>null</i>)
	 */
	public static ITableExportEngineFactory<?> getDefaultFactory() {

		return TableExportEngineFactoryEnum.EXCEL_POI_XLSX.get();
	}
}
