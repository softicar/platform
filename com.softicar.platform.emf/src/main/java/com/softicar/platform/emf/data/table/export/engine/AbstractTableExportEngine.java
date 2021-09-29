package com.softicar.platform.emf.data.table.export.engine;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.zip.ZipLib;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.select.value.simple.DomSimpleValueSelectBuilder;
import com.softicar.platform.dom.parent.DomParentElement;
import com.softicar.platform.emf.data.table.export.conversion.ITableExportNodeConverter;
import com.softicar.platform.emf.data.table.export.conversion.factory.TableExportNodeConverterFactoryValueSelectBuilder;
import com.softicar.platform.emf.data.table.export.conversion.factory.TableExportNodeConverterFactoryWrapper;
import com.softicar.platform.emf.data.table.export.conversion.factory.configuration.TableExportNodeConverterFactoryConfiguration;
import com.softicar.platform.emf.data.table.export.conversion.factory.selection.TableExportNodeConverterFactorySelectionModel;
import com.softicar.platform.emf.data.table.export.engine.configuration.TableExportColumnConfiguration;
import com.softicar.platform.emf.data.table.export.engine.configuration.TableExportEngineConfiguration;
import com.softicar.platform.emf.data.table.export.engine.configuration.TableExportTableConfiguration;
import com.softicar.platform.emf.data.table.export.engine.factory.ITableExportEngineFactory;
import com.softicar.platform.emf.data.table.export.file.name.ITableExportFileNameCreator;
import com.softicar.platform.emf.data.table.export.file.name.TableExportDefaultFileNameCreator;
import com.softicar.platform.emf.data.table.export.file.name.TableExportFileNameWithOmittableSuffix;
import com.softicar.platform.emf.data.table.export.file.name.TableExportFileTimestampMode;
import com.softicar.platform.emf.data.table.export.model.TableExportColumnModel;
import com.softicar.platform.emf.data.table.export.model.TableExportTableModel;
import com.softicar.platform.emf.data.table.export.node.TableExportTypedNodeValue;
import com.softicar.platform.emf.data.table.export.precondition.TableExportPreconditionResultContainer;
import com.softicar.platform.emf.data.table.export.precondition.TableExportPreconditionResult.Level;
import com.softicar.platform.emf.data.table.export.util.TableExportLib;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Base of all {@link ITableExportEngine} implementations.
 * <p>
 * Note: Assumes the database being configured for a "repeatable reads" style
 * transaction isolation level.
 *
 * @param <CT>
 *            The type to which table cell contents get converted for the
 *            export. Though not mandatory, {@link TableExportTypedNodeValue} is
 *            recommended for any implementation.
 * @author Alexander Schmidt
 */
public abstract class AbstractTableExportEngine<CT> implements ITableExportEngine<CT> {

	protected abstract void exportPreparation(OutputStream targetOutputStream, Collection<TableExportTableConfiguration<CT>> tablesWithColumnModel);

	protected abstract void exportImplementation(OutputStream targetOutputStream, TableExportTableConfiguration<CT> tableConfiguration);

	protected abstract void exportFinalization(OutputStream targetOutputStream);

	// ----

	private final ITableExportEngineFactory<? extends ITableExportEngine<CT>> creatingFactory;
	private final TableExportNodeConverterFactoryConfiguration<CT> nodeConverterFactoryConfiguration;
	private final TableExportNodeConverterFactorySelectionModel<CT> nodeConverterFactorySelectionModel;
	private final ITableExportFileNameCreator fileNameCreator;

	private String fileNamePrefix = null;
	private String fileExtension = null;
	private boolean appendTimestamp = false;
	private boolean enableDeflateCompression = true;
	private Supplier<OutputStream> outputStreamSupplierFunction = null;

	public AbstractTableExportEngine(TableExportEngineConfiguration configuration, ITableExportEngineFactory<? extends ITableExportEngine<CT>> creatingFactory,
			TableExportNodeConverterFactoryConfiguration<CT> nodeConverterFactoryConfiguration) {

		if (configuration == null) {
			throw new SofticarDeveloperException("The given %s must not be null.", TableExportEngineConfiguration.class.getSimpleName());
		}

		this.fileExtension = configuration.getFileNameExtension();
		this.appendTimestamp = configuration.isAppendTimestamp();
		this.enableDeflateCompression = configuration.isCompressed();
		this.creatingFactory = creatingFactory;
		this.nodeConverterFactoryConfiguration = nodeConverterFactoryConfiguration;
		this.nodeConverterFactorySelectionModel = new TableExportNodeConverterFactorySelectionModel<>(nodeConverterFactoryConfiguration);
		this.fileNameCreator = new TableExportDefaultFileNameCreator();
	}

	@Override
	public void export(DomTable table) {

		export(new DomTable[] { table });
	}

	@Override
	public void export(DomTable...tables) {

		List<TableExportTableModel> tableModels = Arrays//
			.asList(tables)
			.stream()
			.map(TableExportTableModel::new)
			.collect(Collectors.toList());

		export(tableModels);
	}

	@Override
	public void export(TableExportTableModel...tableModels) {

		export(Arrays.asList(tableModels));
	}

	@Override
	public void export(Collection<TableExportTableModel> tableModels) {

		//
		// check preconditions
		//

		TableExportLib.Timing.begin("000 Preconditions");
		TableExportPreconditionResultContainer errorMessages = getCreatingFactory().checkPreconditions(tableModels);
		if (errorMessages == null) {
			errorMessages = new TableExportPreconditionResultContainer();
		}
		TableExportLib.Timing.end("000 Preconditions");

		if (!errorMessages.getAllByLevel(Level.ERROR).isEmpty()) {
			throw new SofticarUserException(DomI18n.EXPORT_PRECONDITIONS_WERE_NOT_MET);
		}

		//
		// gather table configurations
		//

		List<TableExportTableConfiguration<CT>> tableConfigurations = new ArrayList<>();

		for (TableExportTableModel tableModel: tableModels) {
			Map<Integer, TableExportColumnModel> selectedColumnModels = tableModel.getSelectedColumnModels();

			ITableExportNodeConverter<CT> headerConverter = getNodeConverterFactoryConfiguration().getHeaderFactory().create();
			Map<Integer, ITableExportNodeConverter<CT>> nodeConvertersByColumn =
					getNodeConverterFactorySelectionModel().fetchNodeConvertersByColumn(selectedColumnModels.keySet());
			TableExportColumnConfiguration<CT> columnConfiguration =
					new TableExportColumnConfiguration<>(selectedColumnModels, nodeConvertersByColumn, headerConverter);
			tableConfigurations.add(new TableExportTableConfiguration<>(tableModel, columnConfiguration));
		}

		//
		// prepare exports for all tables
		//

		TableExportFileNameWithOmittableSuffix outputFileName;
		byte[] bytes;

		try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

			exportPreparation(buffer, tableConfigurations);

			//
			// execute per-table export implementations
			//

			for (TableExportTableConfiguration<CT> tableConfiguration: tableConfigurations) {
				// Run any implementation's queries in a transaction to avoid rows being dropped or duplicated
				// when paging an SQL resultset based DomPageableTable, in case the underlying database table gets
				// altered during the export process. Requires a "repeatable reads" style transaction isolation level.
				try (AutoCloseable transaction = tableConfiguration.startTransaction()) {
					exportImplementation(buffer, tableConfiguration);
				} catch (Exception exception) {
					throw new RuntimeException(exception);
				}
			}

			exportFinalization(buffer);

			TableExportLib.Timing.log();

			outputFileName = this.fileNameCreator
				.createFileName(
					this.fileNamePrefix,
					this.fileExtension,
					this.appendTimestamp? TableExportFileTimestampMode.CURRENT_TIME : TableExportFileTimestampMode.NONE,
					enableDeflateCompression);

			if (this.enableDeflateCompression) {
				bytes = ZipLib.zipSingleFile(buffer, outputFileName.getFileName(true), false);
			} else {
				bytes = buffer.toByteArray();
			}
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		String fileName = outputFileName.getFileName(false);

		if (this.outputStreamSupplierFunction == null) {
			this.outputStreamSupplierFunction = () -> CurrentDomDocument//
				.get()
				.getEngine()
				.createExport()
				.setFilename(fileName)
				.setMimeType(MimeType.APPLICATION_OCTET_STREAM)
				.openOutputStream();
		}

		try (OutputStream targetOutputStream = this.outputStreamSupplierFunction.get()) {
			targetOutputStream.write(bytes);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public ITableExportEngine<CT> setFileNamePrefix(String fileNamepPrefix) {

		this.fileNamePrefix = fileNamepPrefix.trim();
		return this;
	}

	@Override
	public ITableExportEngine<CT> setAppendTimestamp(boolean appendTimestamp) {

		this.appendTimestamp = appendTimestamp;
		return this;
	}

	@Override
	public ITableExportEngine<CT> setEnableDeflateCompression(boolean enableDeflateCompression) {

		this.enableDeflateCompression = enableDeflateCompression;
		return this;
	}

	@Override
	public ITableExportEngine<CT> setOutputStreamCreationFunction(Supplier<OutputStream> outputStreamSupplierFunction) {

		this.outputStreamSupplierFunction = outputStreamSupplierFunction;
		return this;
	}

	@Override
	public ITableExportEngineFactory<? extends ITableExportEngine<CT>> getCreatingFactory() {

		return creatingFactory;
	}

	@Override
	public TableExportNodeConverterFactoryConfiguration<CT> getNodeConverterFactoryConfiguration() {

		return nodeConverterFactoryConfiguration;
	}

	@Override
	public TableExportNodeConverterFactorySelectionModel<CT> getNodeConverterFactorySelectionModel() {

		return this.nodeConverterFactorySelectionModel;
	}

	@Override
	public DomSimpleValueSelectBuilder<TableExportNodeConverterFactoryWrapper<CT>> createConverterFactoryValueSelectBuiler(int targetColumn,
			DomParentElement converterFactoryHelpElementContainer) {

		return new TableExportNodeConverterFactoryValueSelectBuilder<>(
			this.nodeConverterFactoryConfiguration,
			this.nodeConverterFactorySelectionModel,
			targetColumn,
			converterFactoryHelpElementContainer);
	}
}
