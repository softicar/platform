package com.softicar.platform.emf.data.table.export.engine;

import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.select.value.simple.DomSimpleValueSelectBuilder;
import com.softicar.platform.dom.parent.DomParentElement;
import com.softicar.platform.emf.data.table.export.conversion.factory.TableExportNodeConverterFactoryWrapper;
import com.softicar.platform.emf.data.table.export.conversion.factory.configuration.TableExportNodeConverterFactoryConfiguration;
import com.softicar.platform.emf.data.table.export.conversion.factory.selection.TableExportNodeConverterFactorySelectionModel;
import com.softicar.platform.emf.data.table.export.engine.factory.ITableExportEngineFactory;
import com.softicar.platform.emf.data.table.export.model.TableExportTableModel;
import java.io.OutputStream;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * Common interface of all table export engines.
 *
 * @author Alexander Schmidt
 */
public interface ITableExportEngine {

	void export(DomTable table);

	void export(DomTable...tables);

	void export(TableExportTableModel...tableModels);

	void export(Collection<TableExportTableModel> tableModels);

	ITableExportEngine setFileNamePrefix(String fileNamePrefix);

	ITableExportEngine setAppendTimestamp(boolean appendTimestamp);

	ITableExportEngine setEnableDeflateCompression(boolean enableDeflateCompression);

	ITableExportEngine setOutputStreamCreationFunction(Supplier<OutputStream> outputStreamSupplierFunction);

	ITableExportEngineFactory<? extends ITableExportEngine> getCreatingFactory();

	TableExportNodeConverterFactoryConfiguration getNodeConverterFactoryConfiguration();

	TableExportNodeConverterFactorySelectionModel getNodeConverterFactorySelectionModel();

	DomSimpleValueSelectBuilder<TableExportNodeConverterFactoryWrapper> createConverterFactoryValueSelectBuiler(int targetColumn,
			DomParentElement converterFactoryHelpElementParent);
}
