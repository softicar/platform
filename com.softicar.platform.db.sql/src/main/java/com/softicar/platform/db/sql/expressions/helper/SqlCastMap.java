package com.softicar.platform.db.sql.expressions.helper;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides information about value type casts in SQL.
 *
 * @author Oliver Richers
 */
public final class SqlCastMap {

	private final Map<Class<?>, String> castMap = new HashMap<>();

	public SqlCastMap() {

		put(BigDecimal.class, "DECIMAL");
		put(Boolean.class, "SIGNED");
		put(Day.class, "DATE");
		put(DayTime.class, "DATETIME");
		put(Double.class, "DECIMAL");
		put(Float.class, "DECIMAL");
		put(Integer.class, "SIGNED");
		put(Long.class, "SIGNED");
		put(String.class, "CHAR");
		put(Time.class, "TIME");
	}

	public String getCast(Class<?> valueType) {

		String cast = castMap.get(valueType);
		if (cast == null) {
			throw new SofticarDeveloperException("No cast defined for value type %s.", valueType);
		}
		return cast;
	}

	private void put(Class<?> valueType, String cast) {

		castMap.put(valueType, cast);
	}
}
