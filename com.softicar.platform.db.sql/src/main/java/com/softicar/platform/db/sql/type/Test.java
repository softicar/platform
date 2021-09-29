package com.softicar.platform.db.sql.type;

import com.softicar.platform.common.code.java.JavaClass;
import com.softicar.platform.common.core.logging.Log;

public class Test {

	public static void main(String[] args) {

		JavaClass javaClass = SqlFieldType.BOOLEAN.getJavaClass();
		Log.finfo(javaClass.getImports() + "");
	}
}
