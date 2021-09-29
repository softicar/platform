package com.softicar.platform.emf.data.table.export.popup;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.tables.pageable.DomPageableTable;
import com.softicar.platform.dom.elements.tables.pageable.DomPageableTableMarker;
import com.softicar.platform.emf.data.table.export.engine.factory.ITableExportEngineFactory;
import com.softicar.platform.emf.data.table.export.engine.factory.TableExportEngineFactoryEnum;
import com.softicar.platform.emf.data.table.export.model.TableExportTableModelListFactory;
import com.softicar.platform.emf.data.table.export.precondition.ITableExportPrecondition;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * Generic export button for {@link DomTable}.
 * <p>
 * Allows for exporting arbitrary {@link DomTable}s (including
 * {@link DomPageableTable}s to various formats. When clicked, a pop-up offering
 * export format and options is opened.
 * <p>
 * Each format internally corresponds to {@link ITableExportEngineFactory}. The
 * formats/factories offered for selection are defined in
 * {@link TableExportEngineFactoryEnum}.
 * <p>
 * Create instances with {@link TableExportPopupButtonBuilder}.
 *
 * @author Alexander Schmidt
 */
public class TableExportPopupButton extends DomButton {

	private final TableExportTableModelListFactory tableSupplierContainer;
	private final Supplier<String> fileNamePrefixFunction;
	private final boolean appendTimestamp;
	private final boolean enableDeflateCompression;
	private final Collection<ITableExportPrecondition> generalTableExportPreconditions;

	public TableExportPopupButton(TableExportTableModelListFactory tableSupplierContainer, //
			Supplier<String> fileNamePrefixFunction, //
			IDisplayString label, //
			boolean showLabel, //
			boolean appendTimestamp, //
			boolean enableDeflateCompression,//
			Collection<ITableExportPrecondition> generalTableExportPreconditions) {

		this.tableSupplierContainer = tableSupplierContainer;
		this.fileNamePrefixFunction = fileNamePrefixFunction;
		this.appendTimestamp = appendTimestamp;
		this.enableDeflateCompression = enableDeflateCompression;
		this.generalTableExportPreconditions = generalTableExportPreconditions;

		setIcon(DomElementsImages.TABLE_EXPORT.getResource());
		setMarker(DomPageableTableMarker.NAVIGATION_EXPORT_BUTTON);

		if (showLabel) {
			setLabel(label);
		} else {
			setTitle(label);
		}

		setClickCallback(this::handleClick);
	}

	private void handleClick() {

		for (ITableExportPrecondition precondition: this.generalTableExportPreconditions) {
			precondition.passOrThrow();
		}

		new TableExportPopup(//
			tableSupplierContainer,
			fileNamePrefixFunction,
			appendTimestamp,
			enableDeflateCompression).show();
	}
}
