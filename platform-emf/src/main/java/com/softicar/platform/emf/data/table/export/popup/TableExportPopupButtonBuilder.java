package com.softicar.platform.emf.data.table.export.popup;

import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.emf.data.table.export.conversion.TableExportTypedNodeConverter;
import com.softicar.platform.emf.data.table.export.node.ITableExportTypedNode;
import com.softicar.platform.emf.data.table.export.node.style.ITableExportStyledNode;
import com.softicar.platform.emf.data.table.export.precondition.ITableExportPrecondition;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * The default implementation of an {@link ITableExportPopupButtonBuilder}.
 * Creates {@link TableExportPopupButton} instances.
 * <p>
 * Use {@link #addMainTable(DomTable)} to specify the table to be exported,
 * perform further configuration via other setters and finally call
 * {@link #createExportButton()}.
 * <p>
 * If required, make table cells implement {@link ITableExportStyledNode} to
 * retain styles (font color, font weight, background color, etc.), given that
 * the selected export format and engine implementation support that.
 * <p>
 * If table contents get converted in a way you dislike, make cells implement
 * {@link ITableExportTypedNode} to perform type safe conversions. However, keep
 * in mind that whatever content you export should look like what the user sees
 * on-page. Default content conversions are defined in
 * {@link TableExportTypedNodeConverter}.
 *
 * @author Alexander Schmidt
 */
public class TableExportPopupButtonBuilder extends AbstractTablePopupExportButtonBuilder {

	protected Supplier<String> fileNamePrefixFunction;
	protected boolean appendTimestamp;
	protected boolean enableDeflateCompression;
	protected final List<ITableExportPrecondition> generalTableExportPreconditions;

	public TableExportPopupButtonBuilder() {

		this.fileNamePrefixFunction = null;
		this.appendTimestamp = true;
		this.enableDeflateCompression = false;
		this.generalTableExportPreconditions = new ArrayList<>();
	}

	@Override
	public ITableExportPopupButtonBuilder setFileNamePrefix(String fileNamePrefix) {

		setFileNamePrefix(() -> fileNamePrefix);
		return this;
	}

	@Override
	public ITableExportPopupButtonBuilder setFileNamePrefix(Supplier<String> fileNamePrefixFunction) {

		this.fileNamePrefixFunction = fileNamePrefixFunction;
		return this;
	}

	@Override
	public ITableExportPopupButtonBuilder setAppendTimestamp(boolean appendTimestamp) {

		this.appendTimestamp = appendTimestamp;
		return this;
	}

	@Override
	public ITableExportPopupButtonBuilder setEnableDeflateCompression(boolean enableDeflateCompression) {

		this.enableDeflateCompression = enableDeflateCompression;
		return this;
	}

	@Override
	public ITableExportPopupButtonBuilder addGeneralTableExportPrecondition(ITableExportPrecondition generalTableExportPrecondition) {

		this.generalTableExportPreconditions.add(generalTableExportPrecondition);
		return this;
	}

	@Override
	public ITableExportPopupButtonBuilder clearGeneralTableExportPreconditions() {

		this.generalTableExportPreconditions.clear();
		return this;
	}

	@Override
	public DomButton createExportButton() {

		return new TableExportPopupButton(
			tableSupplierContainer,
			fileNamePrefixFunction,
			label,
			showLabel,
			appendTimestamp,
			enableDeflateCompression,
			generalTableExportPreconditions);
	}
}
