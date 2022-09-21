package com.softicar.platform.db.runtime.query;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.runtime.select.IDbSqlSelect;
import com.softicar.platform.db.sql.type.ISqlValueType;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Default implementation of {@link IDbQueryColumn}.
 *
 * @author Oliver Richers
 */
public class DbQueryColumn<R extends IDbQueryRow<R>, T> extends AbstractDbQueryColumn<R, T> {

	private final ISqlValueType<T> valueType;
	private final Optional<Comparator<T>> valueComparator;

	public DbQueryColumn(Function<R, T> valueGetter, String name, ISqlValueType<T> valueType, IDisplayString title) {

		super(valueGetter, name, title);

		this.valueType = valueType;
		this.valueComparator = Optional.of(valueType::compare);
	}

	public DbQueryColumn(Function<R, T> valueGetter, String name, ISqlValueType<T> valueType) {

		this(valueGetter, name, valueType, null);
	}

	@Override
	public Class<T> getValueClass() {

		return valueType.getValueClass();
	}

	@Override
	public Optional<Comparator<T>> getValueComparator() {

		return valueComparator;
	}

	@Override
	public int compareValues(T leftValue, T rightValue) {

		return valueType.compare(leftValue, rightValue);
	}

	@Override
	public T loadValue(IDbSqlSelect select, DbResultSet resultSet) {

		List<Integer> indexList = select.getPhysicalColumnIndexList(getName());
		if (indexList.isEmpty()) {
			return null;
		} else if (indexList.size() == 1) {
			return valueType.getValue(resultSet, indexList.get(0));
		} else {
			throw new IllegalStateException("Found multiple physical column indexes for simple SELECT column.");
		}
	}

	@Override
	public int getPhysicalColumnCount() {

		return valueType.getColumnCount();
	}

	@Override
	public void prefetchData(Collection<T> values) {

		// nothing to do
	}
}
