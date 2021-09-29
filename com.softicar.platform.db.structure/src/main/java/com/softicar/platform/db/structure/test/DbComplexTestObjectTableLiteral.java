package com.softicar.platform.db.structure.test;

import com.softicar.platform.db.core.table.DbTableName;

/**
 * Provides a literal representation of the database table of a complex test
 * object.
 */
public class DbComplexTestObjectTableLiteral {

	private boolean isH2Database = false;

	public DbComplexTestObjectTableLiteral setIsH2Databse(boolean isH2Database) {

		this.isH2Database = isH2Database;
		return this;
	}

	public String getCreateTableStatement() {

		return new StringBuilder()
			.append("CREATE TABLE `database`.`table` (")
			.append("`id` INT NOT NULL AUTO_INCREMENT" + getAutoIncrement() + ",")
			.append("`fk` INT,")
			.append("`long` BIGINT,")
			.append("`string` VARCHAR(255) DEFAULT 'abc' COMMENT 'someComment',")
			.append("`stringWithCharacterSet` VARCHAR(128) " + getCharacterSet() + "NOT NULL,")
			.append("`enum` ENUM('BIG','MEDIUM','SMALL','TINY') " + getCharacterSet() + "NOT NULL,")
			.append("`unsignedInteger` INT UNSIGNED DEFAULT NULL,")
			.append("`dayTime` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,")
			.append("PRIMARY KEY (`id`),")
			.append("UNIQUE KEY `database$table$uniqueStringLong` (`string`, `long`),")
			.append(isH2Database? "" : "KEY `database$table$indexLong` (`long`),")
			.append("CONSTRAINT `fk_constraint` FOREIGN KEY (`fk`) REFERENCES `database`.`table` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE);")
			.toString();
	}

	private String getAutoIncrement() {

		return isH2Database? "(252)" : "";
	}

	private String getCharacterSet() {

		return isH2Database? "" : "CHARACTER SET someCharacterSet ";
	}

	public DbTableName getTableName() {

		return new DbTableName("database", "table");
	}
}
