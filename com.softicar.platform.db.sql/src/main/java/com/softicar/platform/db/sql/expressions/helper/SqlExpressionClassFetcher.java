package com.softicar.platform.db.sql.expressions.helper;

import com.softicar.platform.common.code.java.JavaClass;
import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.java.packages.name.JavaPackageName;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.db.sql.expressions.ISqlExpression;
import com.softicar.platform.db.sql.expressions.array.ISqlByteArrayExpression;
import com.softicar.platform.db.sql.expressions.bool.ISqlBooleanExpressionX;
import com.softicar.platform.db.sql.expressions.date.ISqlDayExpression;
import com.softicar.platform.db.sql.expressions.date.ISqlDayTimeExpression;
import com.softicar.platform.db.sql.expressions.date.ISqlTimeExpression;
import com.softicar.platform.db.sql.expressions.numeric.ISqlBigDecimalExpression;
import com.softicar.platform.db.sql.expressions.numeric.ISqlDoubleExpression;
import com.softicar.platform.db.sql.expressions.numeric.ISqlFloatExpression;
import com.softicar.platform.db.sql.expressions.numeric.ISqlIntegerExpression;
import com.softicar.platform.db.sql.expressions.numeric.ISqlLongExpression;
import com.softicar.platform.db.sql.expressions.string.ISqlStringExpression;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class SqlExpressionClassFetcher {

	private final static ExpressionClassMap classMap = new ExpressionClassMap();
	private final Class<?> referenceClass;

	private static class ExpressionClassMap {

		private final Map<Class<?>, Class<?>> map = new HashMap<>();

		public ExpressionClassMap() {

			map.put(ISqlExpression.class, ISqlExpression.class);

			map.put(BigDecimal.class, ISqlBigDecimalExpression.class);
			map.put(Boolean.class, ISqlBooleanExpressionX.class);
			map.put(byte[].class, ISqlByteArrayExpression.class);
			map.put(Day.class, ISqlDayExpression.class);
			map.put(DayTime.class, ISqlDayTimeExpression.class);
			map.put(Double.class, ISqlDoubleExpression.class);
			map.put(Float.class, ISqlFloatExpression.class);
			map.put(Integer.class, ISqlIntegerExpression.class);
			map.put(Long.class, ISqlLongExpression.class);
			map.put(String.class, ISqlStringExpression.class);
			map.put(Time.class, ISqlTimeExpression.class);
		}

		public Class<?> getReferenceClass(Class<?> valueClass) {

			Class<?> referenceClass = map.get(valueClass);
			if (referenceClass == null) {
				throw new SofticarDeveloperException("No expression classes found for value class %s.", valueClass.getSimpleName());
			}
			return referenceClass;
		}
	}

	public SqlExpressionClassFetcher(Class<?> valueClass) {

		this.referenceClass = classMap.getReferenceClass(valueClass);
	}

	public JavaClass getBaseInterface() {

		return new JavaClass(referenceClass);
	}

	public JavaClass getInterface(int index) {

		return get(index, false);
	}

	public JavaClass getImplementingClass(int index) {

		return get(index, true);
	}

	private JavaClass get(int index, boolean concrete) {

		JavaPackageName packageName = new JavaPackageName(referenceClass);
		String simpleName = referenceClass.getSimpleName();
		if (simpleName.startsWith("I") && concrete) {
			simpleName = simpleName.substring(1);
		}
		if (simpleName.endsWith("X")) {
			simpleName = simpleName.substring(0, simpleName.length() - 1);
		}
		JavaClassName className = new JavaClassName(packageName, simpleName + index);
		return new JavaClass(className);
	}
}
