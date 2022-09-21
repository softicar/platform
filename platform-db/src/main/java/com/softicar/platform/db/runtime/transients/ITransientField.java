package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.common.core.interfaces.ITestMarker;
import com.softicar.platform.db.runtime.table.row.IDbBasicTableRow;
import java.util.Collection;

/**
 * Generic interface of all transient fields.
 *
 * @param <O>
 *            the type of the field owning object class
 * @param <V>
 *            the type of the field values
 * @author Oliver Richers
 */
public interface ITransientField<O extends IDbBasicTableRow<O>, V> extends IDbTransientField<O, V>, ITestMarker {

	ITransientFieldValueType<V> getValueType();

	void invalidate(O object);

	void invalidateAll(Collection<? extends O> objects);

	void prefetchAll(Collection<? extends O> objects);

	void reload(O object);

	void reloadAll(Collection<? extends O> objects);
}
