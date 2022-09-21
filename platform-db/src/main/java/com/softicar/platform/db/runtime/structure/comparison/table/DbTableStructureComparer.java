package com.softicar.platform.db.runtime.structure.comparison.table;

import com.softicar.platform.db.runtime.structure.comparison.diagnostic.DbStructureComparisonDiagnosticContainer;
import com.softicar.platform.db.runtime.structure.comparison.feature.IDbFeatureStructureComparer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Compares the structures of a reference table and a sample table, and
 * generates diagnostics about the results.
 *
 * @author Alexander Schmidt
 */
public class DbTableStructureComparer {

	private final IDbFeatureStructureComparer columnComparer;
	private final IDbFeatureStructureComparer foreignkeyComparer;
	private final IDbFeatureStructureComparer keyComparer;
	private final IDbFeatureStructureComparer enumTableRowComparer;

	public DbTableStructureComparer(IDbTableStructureComparerConfiguration configuration) {

		Objects.requireNonNull(configuration);
		this.columnComparer = Objects.requireNonNull(configuration.getColumnComparer());
		this.foreignkeyComparer = Objects.requireNonNull(configuration.getForeignKeyComparer());
		this.keyComparer = Objects.requireNonNull(configuration.getKeyComparer());
		this.enumTableRowComparer = Objects.requireNonNull(configuration.getEnumTableRowComparer());
	}

	/**
	 * Compares the table structures and returns the generated diagnostics.
	 *
	 * @return a container with the generated diagnostics (never null)
	 */
	public DbStructureComparisonDiagnosticContainer compareAll() {

		DbStructureComparisonDiagnosticContainer container = new DbStructureComparisonDiagnosticContainer();
		getAllComparers()//
			.stream()
			.map(IDbFeatureStructureComparer::compare)
			.forEach(container::addAllFromOther);
		return container;
	}

	/**
	 * Compares only the column structures of the table structures and returns
	 * the generated diagnostics.
	 *
	 * @return a container with the generated diagnostics (never null)
	 */
	public DbStructureComparisonDiagnosticContainer compareColumnsOnly() {

		DbStructureComparisonDiagnosticContainer container = new DbStructureComparisonDiagnosticContainer();
		container.addAllFromOther(columnComparer.compare());
		return container;
	}

	/**
	 * Compares only the foreign key structures of the table structures and
	 * returns the generated diagnostics.
	 *
	 * @return a container with the generated diagnostics (never null)
	 */
	public DbStructureComparisonDiagnosticContainer compareForeignKeysOnly() {

		DbStructureComparisonDiagnosticContainer container = new DbStructureComparisonDiagnosticContainer();
		container.addAllFromOther(foreignkeyComparer.compare());
		return container;
	}

	/**
	 * Compares only the key structures of the table structures and returns the
	 * generated diagnostics.
	 *
	 * @return a container with the generated diagnostics (never null)
	 */
	public DbStructureComparisonDiagnosticContainer compareKeysOnly() {

		DbStructureComparisonDiagnosticContainer container = new DbStructureComparisonDiagnosticContainer();
		container.addAllFromOther(keyComparer.compare());
		return container;
	}

	/**
	 * Compares only the enum table rows of the table structures and returns the
	 * generated diagnostics.
	 *
	 * @return a container with the generated diagnostics (never null)
	 */
	public DbStructureComparisonDiagnosticContainer compareEnumTableRowsOnly() {

		DbStructureComparisonDiagnosticContainer container = new DbStructureComparisonDiagnosticContainer();
		container.addAllFromOther(enumTableRowComparer.compare());
		return container;
	}

	private Collection<IDbFeatureStructureComparer> getAllComparers() {

		Collection<IDbFeatureStructureComparer> processors = new ArrayList<>();
		processors.add(columnComparer);
		processors.add(foreignkeyComparer);
		processors.add(keyComparer);
		processors.add(enumTableRowComparer);
		return processors;
	}

}
