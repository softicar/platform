package com.softicar.platform.db.runtime.table.dependency;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.finder.DbTableFinder;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class DbTableDependencyGraph {

	private static final Comparator<IDbTable<?, ?>> COMPARATOR = Comparator.comparing(IDbTable::getFullName);
	private final Set<IDbTable<?, ?>> tables;
	private final Map<IDbTable<?, ?>, Set<IDbTable<?, ?>>> directDependersMap;
	private final Map<IDbTable<?, ?>, Set<IDbTable<?, ?>>> allDependersMap;

	public DbTableDependencyGraph() {

		this(new DbTableFinder().findAllTables());
	}

	public DbTableDependencyGraph(Collection<IDbTable<?, ?>> tables) {

		this.tables = new TreeSet<>(COMPARATOR);
		this.directDependersMap = new HashMap<>();
		this.allDependersMap = new HashMap<>();

		tables.forEach(this::registerTable);
	}

	public Set<IDbTable<?, ?>> getAllTables() {

		return tables;
	}

	public Set<IDbTable<?, ?>> getDirectDependers(IDbTable<?, ?> table) {

		return directDependersMap.getOrDefault(table, Collections.emptySet());
	}

	public Set<IDbTable<?, ?>> getAllDependers(IDbTable<?, ?> table) {

		var allDependers = allDependersMap.get(table);
		if (allDependers == null) {
			allDependers = new TreeSet<>(COMPARATOR);
			allDependersMap.put(table, allDependers);
			for (IDbTable<?, ?> depender: getDirectDependers(table)) {
				allDependers.add(depender);
				allDependers.addAll(getAllDependers(depender));
			}
		}
		return allDependers;
	}

	private void registerTable(IDbTable<?, ?> table) {

		tables.add(table);

		for (IDbField<?, ?> field: table.getAllFields()) {
			CastUtils.tryCast(field, IDbForeignRowField.class).ifPresent(this::registerForeignRowField);
		}
	}

	private void registerForeignRowField(IDbForeignRowField<?, ?, ?> field) {

		directDependersMap.computeIfAbsent(field.getTargetTable(), dummy -> new TreeSet<>(COMPARATOR)).add(field.getTable());
	}
}
