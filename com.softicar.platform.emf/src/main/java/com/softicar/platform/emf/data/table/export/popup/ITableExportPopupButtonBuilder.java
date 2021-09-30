package com.softicar.platform.emf.data.table.export.popup;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.tables.pageable.DomPageableTable;
import com.softicar.platform.emf.data.table.export.precondition.ITableExportPrecondition;
import java.util.List;
import java.util.function.Supplier;

/**
 * Super interface of all builders creating table export buttons.
 * <p>
 * Particularly used in {@link DomPageableTable} which also allows for replacing
 * its default {@link ITableExportPopupButtonBuilder} by a custom one. This way,
 * custom export buttons can be injected into table navigations.
 *
 * @author Alexander Schmidt
 */
public interface ITableExportPopupButtonBuilder {

	/**
	 * Adds the main table to the list of tables to be exported. The main table
	 * is the one to which the column selection refers.
	 *
	 * @param table
	 *            The main table to be exported.
	 */
	ITableExportPopupButtonBuilder addMainTable(DomTable table);

	/**
	 * Adds the main table to the list of tables to be exported. The main table
	 * is the one to which the column selection refers.
	 *
	 * @param tableSupplier
	 *            A Supplier for the main table to be exported. That Supplier is
	 *            evaluated when the export execution button is clicked.
	 */
	ITableExportPopupButtonBuilder addMainTable(Supplier<DomTable> tableSupplier);

	/**
	 * Adds a support table to the list of tables to be exported. For support
	 * tables, no column selection is displayed.
	 *
	 * @param table
	 *            A support table to be exported.
	 */
	ITableExportPopupButtonBuilder addSupportTable(DomTable table);

	/**
	 * Adds a support table to the list of tables to be exported. For support
	 * tables, no column selection is displayed.
	 *
	 * @param tableSupplier
	 *            A Supplier for a support table to be exported. That Supplier
	 *            is evaluated when the export execution button is clicked.
	 */
	ITableExportPopupButtonBuilder addSupportTable(Supplier<DomTable> tableSupplier);

	ITableExportPopupButtonBuilder addSupportTables(List<DomTable> tablesSupplier);

	/**
	 * @param label
	 *            Specifies a label for the generated button.
	 */
	ITableExportPopupButtonBuilder setLabel(IDisplayString label);

	/**
	 * @param showLabel
	 *            Specifies whether the generated button should carry a label.
	 */
	ITableExportPopupButtonBuilder setShowLabel(boolean showLabel);

	/**
	 * @param fileNamePrefix
	 *            Specifies the file name prefix (i.e. the part of the file name
	 *            before the extension and before and auto-appended part like a
	 *            timestamp) for the file generated upon table export.
	 */
	ITableExportPopupButtonBuilder setFileNamePrefix(String fileNamePrefix);

	/**
	 * @param fileNamePrefixFunction
	 *            Specifies a Supplier function returning a file name prefix for
	 *            the file to be exported. That function is evaluated when the
	 *            export execution button is clicked.
	 */
	ITableExportPopupButtonBuilder setFileNamePrefix(Supplier<String> fileNamePrefixFunction);

	/**
	 * @param appendTimestamp
	 *            Specifies whether a timestamp should be appended to the file
	 *            name prefix (i.e. right before the file name extension).
	 */
	ITableExportPopupButtonBuilder setAppendTimestamp(boolean appendTimestamp);

	/**
	 * @param enableDeflateCompression
	 *            Specifies whether the exported files should be compressed
	 *            using Deflate (".zip").
	 */
	ITableExportPopupButtonBuilder setEnableDeflateCompression(boolean enableDeflateCompression);

	/**
	 * @param generalTableExportPrecondition
	 *            Saves an {@link ITableExportPrecondition} to be evaluated when
	 *            the generated button is clicked.
	 */
	ITableExportPopupButtonBuilder addGeneralTableExportPrecondition(ITableExportPrecondition generalTableExportPrecondition);

	/**
	 * Clears previously set {@link ITableExportPrecondition}s.
	 */
	ITableExportPopupButtonBuilder clearGeneralTableExportPreconditions();

	/**
	 * @return A newly created table export button, according to the previously
	 *         set parameters.
	 */
	DomButton build();
}
