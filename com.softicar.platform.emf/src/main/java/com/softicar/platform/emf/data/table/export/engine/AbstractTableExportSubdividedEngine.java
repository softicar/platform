package com.softicar.platform.emf.data.table.export.engine;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.emf.data.table.export.conversion.factory.configuration.TableExportNodeConverterFactoryConfiguration;
import com.softicar.platform.emf.data.table.export.engine.configuration.TableExportColumnConfiguration;
import com.softicar.platform.emf.data.table.export.engine.configuration.TableExportEngineConfiguration;
import com.softicar.platform.emf.data.table.export.engine.configuration.TableExportTableConfiguration;
import com.softicar.platform.emf.data.table.export.engine.factory.ITableExportEngineFactory;
import com.softicar.platform.emf.data.table.export.node.TableExportTypedNodeValue;
import com.softicar.platform.emf.data.table.export.util.TableExportLib;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

/**
 * Base of all {@link ITableExportEngine} implementations which are subdivided
 * into the following steps:
 * <p>
 * 1. Preparation<br>
 * 2. Header creation<br>
 * 3. Body creation<br>
 * 4. Finalization
 *
 * @param <CT>
 *            The type to which table cell contents get converted for the
 *            export. Though not mandatory, {@link TableExportTypedNodeValue} is
 *            recommended for any implementation.
 * @author Alexander Schmidt
 */
public abstract class AbstractTableExportSubdividedEngine<CT> extends AbstractTableExportEngine<CT> {

	protected abstract void prepare(OutputStream targetOutputStream, Collection<TableExportTableConfiguration<CT>> tableConfigurations);

	protected abstract void prepareTable(TableExportTableConfiguration<CT> tableConfiguration);

	protected abstract void appendHeader(DomTable table, TableExportColumnConfiguration<CT> columnConfiguration);

	protected abstract void appendBody(DomTable table, TableExportColumnConfiguration<CT> columnConfiguration);

	protected abstract void finishTable();

	protected abstract void finish(OutputStream targetOutputStream) throws IOException;

	// ----

	protected AbstractTableExportSubdividedEngine(TableExportEngineConfiguration configuration,
			ITableExportEngineFactory<? extends ITableExportEngine<CT>> creatingFactory,
			TableExportNodeConverterFactoryConfiguration<CT> nodeConverterFactoryConfiguration) {

		super(configuration, creatingFactory, nodeConverterFactoryConfiguration);
	}

	@Override
	protected void exportPreparation(OutputStream targetOutputStream, Collection<TableExportTableConfiguration<CT>> tablesWithColumnModel) {

		try {
			TableExportLib.Timing.begin("100 Prepare");
			prepare(targetOutputStream, tablesWithColumnModel);
			TableExportLib.Timing.end("100 Prepare");
		} catch (Exception exception) {
			throw new SofticarUserException(exception, DomI18n.FAILED_TO_PREPARE_THE_FILE_FOR_EXPORT);
		}
	}

	@Override
	protected void exportImplementation(OutputStream targetOutputStream, TableExportTableConfiguration<CT> tableConfiguration) {

		DomTable table = tableConfiguration.getTable();
		TableExportColumnConfiguration<CT> columnConfiguration = tableConfiguration.getColumnConfiguration();

		prepareTable(tableConfiguration);

		try {
			TableExportLib.Timing.begin("200 Header");
			appendHeader(table, columnConfiguration);
			TableExportLib.Timing.end("200 Header");
		} catch (Exception exception) {
			throw new SofticarUserException(exception, DomI18n.FAILED_TO_GENERATE_TABLE_HEADER);
		}

		try {
			TableExportLib.Timing.begin("300 Body");
			appendBody(table, columnConfiguration);
			TableExportLib.Timing.end("300 Body");
		} catch (Exception exception) {
			throw new SofticarUserException(exception, DomI18n.FAILED_TO_GENERATE_TABLE_BODY);
		}

		finishTable();
	}

	@Override
	protected void exportFinalization(OutputStream targetOutputStream) {

		try {
			TableExportLib.Timing.begin("400 Finish");
			finish(targetOutputStream);
			TableExportLib.Timing.end("400 Finish");
		} catch (Exception exception) {
			throw new SofticarUserException(exception, DomI18n.FAILED_TO_FINISH_THE_FILE_FOR_EXPORT);
		}
	}
}
