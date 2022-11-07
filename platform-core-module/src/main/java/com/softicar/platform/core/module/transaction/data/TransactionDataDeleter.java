package com.softicar.platform.core.module.transaction.data;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.module.IModule;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.runtime.table.dependency.DbTableDependencyGraph;
import com.softicar.platform.db.sql.statement.ISqlDelete;
import com.softicar.platform.emf.module.registry.CurrentEmfModuleRegistry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TransactionDataDeleter {

	public void execute() {

		getTableList().forEach(this::deleteFromTable);
	}

	public List<ISqlDelete<?>> getStatementList() {

		return getTableList()//
			.stream()
			.map(table -> table.createDelete())
			.collect(Collectors.toList());
	}

	private void deleteFromTable(IDbTable<?, ?> table) {

		try {
			table.createDelete().execute();
		} catch (Exception exception) {
			throw new SofticarUserException(exception, CoreI18n.FAILED_TO_DELETE_DATA_FROM_TABLE_ARG1.toDisplay(table.getFullName()));
		}
	}

	private List<IDbTable<?, ?>> getTableList() {

		return new Sorter(new DbTableDependencyGraph(getTransactionDataTables())).sortByDependeny();
	}

	private HashSet<IDbTable<?, ?>> getTransactionDataTables() {

		var transactionDataTables = new HashSet<IDbTable<?, ?>>();
		for (IModule<?> module: getStandardModules()) {
			transactionDataTables.addAll(module.getTransactionDataTables());
		}
		return transactionDataTables;
	}

	private Collection<IModule<?>> getStandardModules() {

		return CurrentEmfModuleRegistry//
			.get()
			.getAllModules()
			.stream()
			.filter(IModule.class::isInstance)
			.map(module -> (IModule<?>) module)
			.collect(Collectors.toList());
	}

	private static class Sorter {

		private final DbTableDependencyGraph graph;
		private Set<IDbTable<?, ?>> done;
		private List<IDbTable<?, ?>> list;

		public Sorter(DbTableDependencyGraph graph) {

			this.graph = graph;
		}

		public List<IDbTable<?, ?>> sortByDependeny() {

			this.done = new HashSet<>();
			this.list = new ArrayList<>();
			graph.getAllTables().forEach(this::add);
			return list;
		}

		private void add(IDbTable<?, ?> table) {

			if (done.add(table)) {
				graph.getAllDependers(table).forEach(this::add);
				list.add(table);
			}
		}
	}
}
