package com.softicar.platform.db.runtime.utils;

import com.softicar.platform.common.core.item.IBasicItem;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.logic.IDbObject;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;

public interface IDbObjectList<R extends IBasicItem> extends List<R> {

	default <F extends IDbObject<F>> IDbObjectList<F> prefetch(IDbForeignField<R, F> field) {

		return field.prefetch(this);
	}

	default <F extends IDbObject<F>, C extends Collection<F>> C prefetch(IDbForeignField<R, F> field, Supplier<C> listFactory) {

		return field.prefetch(this, listFactory);
	}

	default Map<Integer, R> toMap() {

		TreeMap<Integer, R> map = new TreeMap<>();
		for (R object: this) {
			Integer id = object.getId();
			if (id != null) {
				map.put(id, object);
			} else {
				throw new IllegalStateException("Tried to put a non persistent object into a map.");
			}
		}
		return map;
	}
}
