package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.math.Range;
import com.softicar.platform.common.string.csv.CsvTokenizer;
import com.softicar.platform.emf.EmfI18n;
import java.util.List;

class EmfImportCsvReader {

	private final String csv;

	public EmfImportCsvReader(String csv) {

		this.csv = csv;
	}

	public List<List<String>> parse(int expectedColumnCount) {

		List<List<String>> rows = new CsvTokenizer().tokenize(csv);

		if (rows.isEmpty()) {
			throw new SofticarUserException(EmfI18n.NO_DATA_ROWS_FOUND);
		}

		Range<Integer> columnCount = getColumnCount(rows);
		if (columnCount.getMin() == expectedColumnCount && columnCount.getMax() == expectedColumnCount) {
			return rows;
		} else {
			throw new SofticarUserException(
				EmfI18n.WRONG_NUMBER_OF_COLUMNS//
					.concatSentence(EmfI18n.EXPECTED_ARG1_COLUMNS.toDisplay(expectedColumnCount)));
		}
	}

	private Range<Integer> getColumnCount(List<List<String>> rows) {

		Range<Integer> range = new Range<>();
		rows.forEach(row -> range.add(row.size()));
		return range;
	}
}
