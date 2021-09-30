package com.softicar.platform.db.runtime.transients;

import java.util.HashMap;

class DbTransientValueMap<O> extends HashMap<IDbTransientField<O, ?>, Object> {

	@SuppressWarnings("unchecked")
	public <V> V getValue(IDbTransientField<O, V> field) {

		return (V) get(field);
	}

	public <V> void setValue(IDbTransientField<O, V> field, V value) {

		put(field, value);
	}
}
