package com.softicar.platform.common.excel.parser;

import com.softicar.platform.common.string.Imploder;
import java.util.TreeMap;

/**
 * A simple List of {@link ScExcelCell}s. May contain null entries.
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
