package com.softicar.platform.db.runtime.transients;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.object.DbTestObject;
import java.util.function.Function;

public class DbTransientTestField extends AbstractDbTransientField<DbTestObject, String> {

	private final Function<DbTestObject, String> valueLoader;

	public DbTransientTestField(Function<DbTestObject, String> valueLoader) {

		this.valueLoader = valueLoader;
	}

	@Override
	public String getValue(DbTestObject object) {

		String value = readValueFromCache(object);
		if (value == null) {
			writeValueToCache(object, value = valueLoader.apply(object));
		}
		return value;
	}

	@Override
	public IDisplayString getTitle() {

		return IDisplayString.create("Transient Test Field");
	}
}
