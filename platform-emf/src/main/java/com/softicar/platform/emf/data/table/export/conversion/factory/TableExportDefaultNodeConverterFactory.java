package com.softicar.platform.emf.data.table.export.conversion.factory;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.emf.data.table.export.conversion.ITableExportNodeConverter;
import com.softicar.platform.emf.data.table.export.conversion.TableExportTypedNodeConverter;
import com.softicar.platform.emf.data.table.export.node.ITableExportTypedNode;
import com.softicar.platform.emf.data.table.export.node.TableExportTypedNodeValue;

/**
 * Creates an {@link ITableExportNodeConverter} that performs type safe content
 * conversions based upon {@link ITableExportTypedNode}s. Given that a node is
 * not an {@link ITableExportTypedNode}, the converter tries to infer the type
 * of the node's content, e.g. Integer or DayTime.
 *
 * @author Alexander Schmidt
 */
public class TableExportDefaultNodeConverterFactory implements ITableExportNodeConverterFactory<TableExportTypedNodeValue> {

	@Override
	public ITableExportNodeConverter<TableExportTypedNodeValue> create() {

		return new TableExportTypedNodeConverter();
	}

	@Override
	public IDisplayString getConverterDisplayString() {

		return DomI18n.STANDARD;
	}

	@Override
	public IDisplayString getConverterDescription() {

		return DomI18n.CONVERTS_ALL_CONTENTS_OF_THE_COLUMN_TO_THE_FORMAT_THAT_WAS_INTENDED_BY_THE_DEVELOPER_OF_THE_PROGRAM//
			.concat("\n")
			.concat(DomI18n.ALSO_TRIES_TO_AUTOMATICALLY_CONVERT_PURELY_TEXTUAL_VALUES_TO_NUMERICAL_VALUES)
			.concat("\n")
			.concat(DomI18n.IN_CASE_NO_EXPLICIT_FORMAT_WAS_DEFINED_A_CONVERSION_TO_A_PURELY_TEXTUAL_FORMAT_IS_APPLIED);
	}
}
