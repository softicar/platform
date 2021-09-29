package com.softicar.platform.emf.data.table.export.conversion.factory;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.emf.data.table.export.conversion.ITableExportNodeConverter;
import com.softicar.platform.emf.data.table.export.conversion.TableExportTypedNodeConverter;
import com.softicar.platform.emf.data.table.export.node.ITableExportTypedNode;
import com.softicar.platform.emf.data.table.export.node.TableExportTypedNodeValue;

/**
 * Creates an {@link ITableExportNodeConverter} that converts all node contents
 * to Strings (even for {@link ITableExportTypedNode}s).
 *
 * @author Alexander Schmidt
 */
public class TableExportTextOnlyNodeConverterFactory implements ITableExportNodeConverterFactory<TableExportTypedNodeValue> {

	private final String cellContentSeparator;

	public TableExportTextOnlyNodeConverterFactory() {

		this(null);
	}

	public TableExportTextOnlyNodeConverterFactory(String cellContentSeparator) {

		this.cellContentSeparator = cellContentSeparator;
	}

	@Override
	public ITableExportNodeConverter<TableExportTypedNodeValue> create() {

		TableExportTypedNodeConverter converter = new TableExportTypedNodeConverter().setEnableAutomaticTypeConversion(false).setForceStringValues(true);

		if (this.cellContentSeparator != null) {
			converter.setCellContentSeparator(this.cellContentSeparator);
		}

		return converter;
	}

	@Override
	public IDisplayString getConverterDisplayString() {

		return DomI18n.ONLY_TEXT;
	}

	@Override
	public IDisplayString getConverterDescription() {

		return DomI18n.CONVERTS_ALL_CONTENTS_OF_THE_COLUMN_TO_A_PURELY_TEXTUAL_FORMAT_EXACTLY_REPRESENTING_THE_DISPLAYED_TABLE//
			.concat(" ")
			.concat(DomI18n.THIS_CAN_CAUSE_PROBLEMS_WHEN_TRYING_TO_SUM_UP_TEXTUAL_NUMERICAL_VALUES_IN_AN_EXCEL_FILE);
	}
}
