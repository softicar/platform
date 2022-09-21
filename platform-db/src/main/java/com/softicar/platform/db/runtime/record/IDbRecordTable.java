package com.softicar.platform.db.runtime.record;

import com.softicar.platform.db.runtime.table.IDbTable;
import java.util.Collection;
import java.util.Map;

/**
 * Represents a simple database table where every row is an {@link IDbRecord}.
 * <p>
 * Every {@link IDbRecordTable} has a single primary key, uniquely identifying
 * each row in the table.
 *
 * @author Oliver Richers
 */
public interface IDbRecordTable<R extends IDbRecord<R, P>, P> extends IDbTable<R, P> {

	R getOrCreate(P key);

	Map<P, R> getOrCreateAsMap(Collection<P> keys);
}
