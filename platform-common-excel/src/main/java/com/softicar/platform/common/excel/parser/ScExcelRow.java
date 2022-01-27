package com.softicar.platform.common.excel.parser;

import com.softicar.platform.common.string.Imploder;
import java.util.TreeMap;

/**
 * Represents a row in an Excel sheet.
 * <p>
 * The keys are zero-based column indexes. Values are {@link ScExcelCell} base
 * cell contents.
 * <p>
 * May contain <i>null</i> values.
 *
 * @author Alexander Schmidt
 */
public class ScExcelRow extends TreeMap<Integer, ScExcelCell> {

	private static final long serialVersionUID = 1L;

	public ScExcelRow() {

		// nothing to do
	}

	@Override
	public String toString() {

		return Imploder.implode(entrySet(), (entry) -> (entry.getKey() + ": " + entry.getValue()), "\t");
	}
}
