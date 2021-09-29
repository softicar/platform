package com.softicar.platform.db.sql;

import com.softicar.platform.common.code.java.JavaCodePrinter;
import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.java.packages.name.JavaPackageName;

public class AbstractSqlJavaCodePrinter extends JavaCodePrinter {

	protected void writeOutToSourceFolder(String simpleClassName) {

		JavaPackageName packageName = new JavaPackageName(getClass());
		writeOutToSourceFolder(new JavaClassName(packageName, simpleClassName));
	}
}
