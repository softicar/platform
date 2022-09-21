package com.softicar.platform.db.structure.foreign.key;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Represents a mapping of one column onto another column by a foreign key.
 *
 * @author Oliver Richers
 */
public class DbForeignKeyColumnPair {

	private final String sourceColumn;
	private final String targetColumn;

	public DbForeignKeyColumnPair(String sourceColumn, String targetColumn) {

		this.sourceColumn = sourceColumn;
		this.targetColumn = targetColumn;
	}

	/**
	 * Returns the source column.
	 * <p>
	 * The source columns points to the target column.
	 *
	 * @return the source column (never null)
	 */
	public String getSourceColumn() {

		return sourceColumn;
	}

	/**
	 * Returns the target column.
	 * <p>
	 * The target columns is referenced by the source column.
	 *
	 * @return the target column (never null)
	 */
	public String getTargetColumn() {

		return targetColumn;
	}

	@Override
	public String toString() {

		return String.format("[%s, %s]", sourceColumn, targetColumn);
	}

	public static Collector<DbForeignKeyColumnPair, ?, Map<String, String>> getToMapCollector() {

		return Collectors.toMap(DbForeignKeyColumnPair::getSourceColumn, DbForeignKeyColumnPair::getTargetColumn);
	}
}
