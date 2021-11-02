package com.softicar.platform.db.runtime.table.logic;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Comparator;
import java.util.List;

/**
 * A table row comparator, comparing the primary key of the rows.
 *
 * @author Oliver Richers
 */
public class DbTableRowComparator<R extends IDbTableRow<R, P>, P> implements Comparator<R> {

	private final IDbTable<R, P> table;
	private final boolean generatedPk;

	public DbTableRowComparator(IDbTable<R, P> table) {

		this.table = table;
		this.generatedPk = table.getPrimaryKey().isGenerated();
	}

	@Override
	public int compare(R left, R right) {

		if (generatedPk) {
			return comparePrimaryKeysOrData(left, right);
		} else {
			return comparePrimaryKeys(left, right);
		}
	}

	private int comparePrimaryKeysOrData(R left, R right) {

		if (left != null && right != null) {
			P leftPk = left.pk();
			P rightPk = right.pk();
			if (leftPk != null && rightPk != null) {
				return table.getPrimaryKey().compare(leftPk, rightPk);
			} else if (leftPk == null && rightPk == null) {
				return compareFields(table.getDataFields(), left, right);
			} else {
				return compareNullsFirst(leftPk, rightPk);
			}
		} else {
			return compareNullsFirst(left, right);
		}
	}

	private int comparePrimaryKeys(R left, R right) {

		if (left != null && right != null) {
			return compareFields(left.table().getPrimaryKey().getFields(), left, right);
		} else {
			return compareNullsFirst(left, right);
		}
	}

	private int compareFields(List<? extends IDbField<R, ?>> fields, R left, R right) {

		assert left != null && right != null;

		for (IDbField<R, ?> field: fields) {
			int cmp = compareValuesOf(field, left, right);
			if (cmp != 0) {
				return cmp;
			}
		}

		return 0;
	}

	private <V> int compareValuesOf(IDbField<R, V> field, R left, R right) {

		return field.getValueType().compare(field.getValueDirectly(left), field.getValueDirectly(right));
	}

	private static int compareNullsFirst(Object left, Object right) {

		assert left == null || right == null;

		if (left == null) {
			return right == null? 0 : -1;
		} else {
			return right == null? 1 : 0;
		}
	}
}
