package com.softicar.platform.emf.data.table.export.conversion.factory;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.emf.data.table.export.conversion.ITableExportNodeConverter;
import com.softicar.platform.emf.data.table.export.conversion.TableExportTypedNodeConverter;
import com.softicar.platform.emf.data.table.export.node.ITableExportTypedNode;

/**
 * Creates an {@link ITableExportNodeConverter} that performs type safe content
 * conversions based upon {@link ITableExportTypedNode}s. That converter will
 * not try to infer types (like Integers, DayTimes, ...) form String contents.
 * Instead, it will simply convert the contents of any node that is not an
 * {@link ITableExportTypedNode} to String.
 *
 * @author Alexander Schmidt
 */
public class TableExportStrictNodeConverterFactory implements ITableExportNodeConverterFactory {

	@Override
	public ITableExportNodeConverter create() {

		return new TableExportTypedNodeConverter().setEnableAutomaticTypeConversion(false);
	}

	@Override
	public IDisplayString getConverterDisplayString() {

		return DomI18n.STRICT;
	}

	@Override
	public IDisplayString getConverterDescription() {

		return DomI18n.CONVERTS_ALL_CONTENTS_OF_THE_COLUMN_TO_THE_FORMAT_THAT_WAS_INTENDED_BY_THE_DEVELOPER_OF_THE_PROGRAM//
			.concat(" ")
			.concat(DomI18n.IN_CASE_NO_EXPLICIT_FORMAT_WAS_DEFINED_A_CONVERSION_TO_A_PURELY_TEXTUAL_FORMAT_IS_APPLIED);
	}
}
