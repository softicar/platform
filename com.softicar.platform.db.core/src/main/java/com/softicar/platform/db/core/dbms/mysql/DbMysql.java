package com.softicar.platform.db.core.dbms.mysql;

import java.util.Arrays;
import java.util.List;

/**
 * Lists various constants for the MySQL database management system.
 *
 * @author Oliver Richers
 */
public class DbMysql {

	/**
	 * List of all MySQL system schemas.
	 */
	public static final List<String> SYSTEM_SCHEMAS = Arrays
		.asList(//
			"information_schema",
			"mysql",
			"performance_schema",
			"sys");
}
