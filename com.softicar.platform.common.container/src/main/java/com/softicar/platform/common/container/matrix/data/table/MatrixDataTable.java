package com.softicar.platform.common.container.matrix.data.table;

import com.softicar.platform.common.container.data.table.IDataTable;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.common.container.matrix.IMatrix;
import com.softicar.platform.common.core.i18n.IDisplayString;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Adapter to convert an {@link IMatrix} into an {@link IDataTable}.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class MatrixDataTable<R, C, V> extends AbstractInMemoryDataTable<MatrixDataTableRow<R, C, V>> {

	private final List<MatrixDataTableRow<R, C, V>> rows;

	public MatrixDataTable(IMatrix<R, C, V> matrix, Class<R> rowClass, Class<V> valueClass, IDisplayString rowTitle,
			Function<C, IDisplayString> columnNameFunction) {

		newColumn(rowClass)//
			.setGetter(row -> row.getRow())
			.setTitle(rowTitle)
			.addColumn();
		for (C col: matrix.getColumns()) {
			newColumn(valueClass)//
				.setGetter(row -> row.getValue(col))
				.setTitle(columnNameFunction.apply(col))
				.addColumn();
		}

		this.rows = new ArrayList<>();
		for (R row: matrix.getRows()) {
			this.rows.add(new MatrixDataTableRow<>(row, matrix.getRowMap(row)));
		}
	}

	@Override
	protected Iterable<MatrixDataTableRow<R, C, V>> getTableRows() {

		return rows;
	}
}
