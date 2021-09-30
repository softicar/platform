package com.softicar.platform.db.core.replication.consistency;

import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.db.core.DbStatements;
import com.softicar.platform.db.core.replication.consistency.comparer.MasterSlaveMismatch.ValuePair;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public abstract class AbstractTableRow implements Comparable<AbstractTableRow> {

	private static final Comparator<String> NULL_FRIENDLY_COMPARATOR = Comparator.nullsFirst(String::compareTo);
	private final List<String> columns;
	private final List<String> values;
	private final Map<String, String> valueMap;

	public AbstractTableRow(List<String> columns, List<String> values) {

		this.columns = columns;
		this.values = values;
		this.valueMap = new TreeMap<>();
	}

	public Collection<String> getValues() {

		return Collections.unmodifiableCollection(values);
	}

	public Map<String, String> getValueMap() {

		if (valueMap.isEmpty()) {
			for (int i = 0; i < columns.size(); i++) {
				valueMap.put(columns.get(i), values.get(i));
			}
		}
		return valueMap;
	}

	public Map<String, ValuePair> getDifferingValues(AbstractTableRow other) {

		Map<String, ValuePair> differingValues = new TreeMap<>();
		int i = 0;
		for (String column: columns) {
			String a = values.get(i);
			String b = other.values.get(i);
			if (!Objects.equals(a, b)) {
				differingValues.put(column, new ValuePair(a, b));
			}
			i++;
		}
		return differingValues;
	}

	public String getQuestionMarkList() {

		return DbStatements.createQuestionMarkList(columns.size());
	}

	public String getImplodedColumnNames() {

		return Imploder.implode(getColumns(), ", ", "`", "`");
	}

	public Collection<String> getColumns() {

		return columns;
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof AbstractTableRow) {
			AbstractTableRow other = (AbstractTableRow) object;
			return values.equals(other.values);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return values.hashCode();
	}

	@Override
	public String toString() {

		return Arrays.asList(values).toString();
	}

	@Override
	public int compareTo(AbstractTableRow other) {

		for (int i = 0; i < values.size(); i++) {
			int difference = NULL_FRIENDLY_COMPARATOR.compare(values.get(i), other.values.get(i));
			if (difference != 0) {
				return difference;
			}
		}
		return 0;
	}
}
