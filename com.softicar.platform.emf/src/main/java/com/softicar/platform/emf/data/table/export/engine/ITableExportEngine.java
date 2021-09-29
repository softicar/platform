package com.softicar.platform.emf.data.table.export.engine;

import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.select.value.simple.DomSimpleValueSelectBuilder;
import com.softicar.platform.dom.parent.DomParentElement;
import com.softicar.platform.emf.data.table.export.conversion.factory.TableExportNodeConverterFactoryWrapper;
import com.softicar.platform.emf.data.table.export.conversion.factory.configuration.TableExportNodeConverterFactoryConfiguration;
import com.softicar.platform.emf.data.table.export.conversion.factory.selection.TableExportNodeConverterFactorySelectionModel;
import com.softicar.platform.emf.data.table.export.engine.factory.ITableExportEngineFactory;
import com.softicar.platform.emf.data.table.export.model.TableExportTableModel;
import com.softicar.platform.emf.data.table.export.node.TableExportTypedNodeValue;
import java.io.OutputStream;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * Common interface of all table export engines.
 *
 * @param <CT>
 *            The type to which table cell contents get converted for the
 *            export. Though not mandatory, {@link TableExportTypedNodeValue} is
 *            recommended for any implementation.
 * @author Alexander Schmidt
 */
public interface ITableExportEngine<CT> {

	void export(DomTable table);

	void export(DomTable...tables);

	void export(TableExportTableModel...tableModels);

	void export(Collection<TableExportTableModel> tableModels);

	ITableExportEngine<CT> setFileNamePrefix(String fileNamePrefix);

	ITableExportEngine<CT> setAppendTimestamp(boolean appendTimestamp);

	ITableExportEngine<CT> setEnableDeflateCompression(boolean enableDeflateCompression);

	ITableExportEngine<CT> setOutputStreamCreationFunction(Supplier<OutputStream> outputStreamSupplierFunction);

	ITableExportEngineFactory<? extends ITableExportEngine<CT>> getCreatingFactory();

	TableExportNodeConverterFactoryConfiguration<CT> getNodeConverterFactoryConfiguration();

	TableExportNodeConverterFactorySelectionModel<CT> getNodeConverterFactorySelectionModel();

	DomSimpleValueSelectBuilder<TableExportNodeConverterFactoryWrapper<CT>> createConverterFactoryValueSelectBuiler(int targetColumn,
			DomParentElement converterFactoryHelpElementParent);
}
