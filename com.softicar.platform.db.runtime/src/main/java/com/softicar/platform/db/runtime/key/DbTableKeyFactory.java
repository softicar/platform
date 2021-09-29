package com.softicar.platform.db.runtime.key;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.container.tuple.Tuple3;
import com.softicar.platform.common.container.tuple.Tuple4;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.logic.IDbObject;
import com.softicar.platform.db.runtime.table.row.IDbTableRow;

/**
 * A factory for instances of {@link IDbTableKey}.
 *
 * @author Oliver Richers
 */
public class DbTableKeyFactory {

	public static final <R extends IDbObject<R>> IDbTableKey<R, Integer> createIdKey(IDbField<R, Integer> field) {

		return new DbTableIdKey<>(field);
	}

	public static final <R extends IDbTableRow<R, P>, P> IDbTableKey<R, P> createKey(IDbField<R, P> field) {

		return new DbTableKey1<>(field);
	}

	public static final <R extends IDbTableRow<R, Tuple2<T0, T1>>, T0, T1> IDbTableKey<R, Tuple2<T0, T1>> createKey(IDbField<R, T0> field0,
			IDbField<R, T1> field1) {

		return new DbTableKey2<>(field0, field1);
	}

	public static final <R extends IDbTableRow<R, Tuple3<T0, T1, T2>>, T0, T1, T2> IDbTableKey<R, Tuple3<T0, T1, T2>> createKey(IDbField<R, T0> field0,
			IDbField<R, T1> field1, IDbField<R, T2> field2) {

		return new DbTableKey3<>(field0, field1, field2);
	}

	public static final <R extends IDbTableRow<R, Tuple4<T0, T1, T2, T3>>, T0, T1, T2, T3>//
	IDbTableKey<R, Tuple4<T0, T1, T2, T3>> createKey(IDbField<R, T0> field0, IDbField<R, T1> field1, IDbField<R, T2> field2, IDbField<R, T3> field3) {

		return new DbTableKey4<>(field0, field1, field2, field3);
	}
}
