package com.softicar.platform.db.structure.mysql;

import com.softicar.platform.common.container.tuple.Tuple2;
import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.db.core.DbMultiResultSetIterable;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.connection.DbConnections;
import com.softicar.platform.db.core.connection.DbServerType;
import com.softicar.platform.db.core.statement.DbStatement;
import com.softicar.platform.db.core.table.DbTableName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Loads the <i>CREATE TABLE</i> statement for a given set of database tables.
 *
 * @author Oliver Richers
 */
public class DbMysqlCreateTableStatementsLoader {

	private static final int MAX_QUERIES = 1000;
	private final List<DbTableName> tables;

	public DbMysqlCreateTableStatementsLoader(DbTableName...tables) {

		this.tables = Arrays.asList(tables);
	}

	public DbMysqlCreateTableStatementsLoader(Collection<DbTableName> tables) {

		this.tables = new ArrayList<>(tables);
	}

	public Collection<String> loadAll() {

		return loadAllAsMap().values();
	}

	public Map<DbTableName, String> loadAllAsMap() {

		Map<DbTableName, String> map = new TreeMap<>();

		int j = 0;
		int n = tables.size() / MAX_QUERIES;
		for (int i = 0; i < n; ++i) {
			map.putAll(load(j, MAX_QUERIES));
			j += MAX_QUERIES;
		}

		int rest = tables.size() % MAX_QUERIES;
		if (rest > 0) {
			map.putAll(load(j, rest));
		}

		return map;
	}

	private Map<DbTableName, String> load(int index, int count) {

		assertCompatibleServerType();

		List<Tuple2<DbTableName, String>> queryTuples = createQueryTuples(index, count);
		Map<DbTableName, String> map = new TreeMap<>();

		List<String> queries = queryTuples.stream().map(it -> it.get1()).collect(Collectors.toList());
		DbStatement statement = new DbStatement(Imploder.implode(queries, ";"));
		try (DbMultiResultSetIterable resultSets = statement.executeMultiQuery()) {
			int counter = 0;
			for (DbResultSet resultSet: resultSets) {
				if (resultSet.next()) {
					DbTableName tableName = queryTuples.get(counter).get0();
					assertCorresponding(tableName, resultSet.getString(1));
					map.put(tableName, getWithoutAutoIncrement(resultSet.getString(2)));
				}
				++counter;
			}
		}
		return map;
	}

	private void assertCorresponding(DbTableName expectedTableName, String actualTableName) {

		if (!actualTableName.equalsIgnoreCase(expectedTableName.getSimpleName())) {
			throw new SofticarDeveloperException("Table name `%s` does not correspond to %s.", actualTableName, expectedTableName);
		}
	}

	private void assertCompatibleServerType() {

		if (!DbConnections.isServerType(DbServerType.MYSQL) && !DbConnections.isServerType(DbServerType.MARIA_DB)) {
			throw new SofticarDeveloperException("SHOW CREATE TABLE can only be used on a MySQL or MariaDB database server.");
		}
	}

	private List<Tuple2<DbTableName, String>> createQueryTuples(int index, int count) {

		return tables//
			.subList(index, index + count)
			.stream()
			.map(table -> new Tuple2<>(table, createQuery(table)))
			.collect(Collectors.toList());
	}

	private String createQuery(DbTableName table) {

		return String.format("SHOW CREATE TABLE `%s`.`%s`", table.getDatabaseName(), table.getSimpleName());
	}

	private String getWithoutAutoIncrement(String original) {

		return original.replaceFirst("AUTO_INCREMENT=[0-9]+ ", "");
	}
}
