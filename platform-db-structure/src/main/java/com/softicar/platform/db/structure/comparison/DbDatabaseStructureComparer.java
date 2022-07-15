package com.softicar.platform.db.structure.comparison;

import com.softicar.platform.db.structure.comparison.diagnostic.DbStructureComparisonDiagnostic;
import com.softicar.platform.db.structure.comparison.diagnostic.DbStructureComparisonDiagnosticBuilder;
import com.softicar.platform.db.structure.comparison.diagnostic.DbStructureComparisonDiagnosticContainer;
import com.softicar.platform.db.structure.comparison.diagnostic.IDbStructureComparisonDiagnostic;
import com.softicar.platform.db.structure.comparison.strategy.IDbStructureComparisonStrategy;
import com.softicar.platform.db.structure.comparison.table.DbTableStructureComparer;
import com.softicar.platform.db.structure.comparison.table.DbTableStructureComparerConfiguration;
import com.softicar.platform.db.structure.comparison.table.IDbTableStructureComparerConfiguration;
import com.softicar.platform.db.structure.comparison.table.IDbTableStructurePair;
import com.softicar.platform.db.structure.database.IDbDatabaseStructure;
import com.softicar.platform.db.structure.table.DbTableStructureFeatureType;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Compares two {@link IDbDatabaseStructure} instances using a
 * {@link IDbStructureComparisonStrategy}, and generates a
 * {@link DbStructureComparisonDiagnosticContainer} which contains the result of
 * the analysis.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class DbDatabaseStructureComparer {

	private final IDbStructureComparisonStrategy strategy;

	/**
	 * @param strategy
	 *            the strategy to compare table structures (never null)
	 */
	public DbDatabaseStructureComparer(IDbStructureComparisonStrategy strategy) {

		this.strategy = Objects.requireNonNull(strategy);
	}

	/**
	 * Loads and analyzes the database structures.
	 *
	 * @param referenceDatabaseStructure
	 *            the reference database structure (never null)
	 * @param sampleDatabaseStructure
	 *            the sample database structure (never null)
	 * @return the resulting diagnostics, in a
	 *         {@link DbStructureComparisonDiagnosticContainer} (never null)
	 */
	public DbStructureComparisonDiagnosticContainer compareAll(IDbDatabaseStructure referenceDatabaseStructure, IDbDatabaseStructure sampleDatabaseStructure) {

		DbStructureComparisonDiagnosticContainer container = new DbStructureComparisonDiagnosticContainer();

		DbDatabaseStructureMatcher matcher = new DbDatabaseStructureMatcher(referenceDatabaseStructure, sampleDatabaseStructure);
		container.addAll(createReferenceExclusiveDiagnostics(matcher));
		container.addAll(createSampleExclusiveDiagnostics(matcher));
		container.addAllFromOthers(createMutualDiagnostics(matcher));

		return container;
	}

	private List<IDbStructureComparisonDiagnostic> createReferenceExclusiveDiagnostics(DbDatabaseStructureMatcher matcher) {

		return matcher//
			.getReferenceExclusiveTableStructures()
			.stream()
			.map(this::createReferenceExclusiveDiagnostic)
			.collect(Collectors.toList());
	}

	private List<IDbStructureComparisonDiagnostic> createSampleExclusiveDiagnostics(DbDatabaseStructureMatcher matcher) {

		return matcher//
			.getSampleExclusiveTableStructures()
			.stream()
			.map(this::createSampleExclusiveDiagnostic)
			.collect(Collectors.toList());
	}

	private List<DbStructureComparisonDiagnosticContainer> createMutualDiagnostics(DbDatabaseStructureMatcher matcher) {

		return matcher//
			.getMutualTableStructures()
			.stream()
			.map(this::createMutualDiagnostics)
			.collect(Collectors.toList());
	}

	private DbStructureComparisonDiagnostic createReferenceExclusiveDiagnostic(IDbTableStructure structure) {

		// TODO shouldn't this be part of the strategy?
		return new DbStructureComparisonDiagnosticBuilder(DbTableStructureFeatureType.TABLE)//
			.setLevelWarning()
			.setTableStructures(structure, null)
			.build("Exists only in %s.", strategy.getReferenceName());
	}

	private DbStructureComparisonDiagnostic createSampleExclusiveDiagnostic(IDbTableStructure structure) {

		// TODO shouldn't this be part of the strategy?
		return new DbStructureComparisonDiagnosticBuilder(DbTableStructureFeatureType.TABLE)//
			.setLevelError()
			.setTableStructures(null, structure)
			.build("Exists only in %s.", strategy.getSampleName());
	}

	private DbStructureComparisonDiagnosticContainer createMutualDiagnostics(IDbTableStructurePair tableStructurePair) {

		IDbTableStructureComparerConfiguration configuration = new DbTableStructureComparerConfiguration(strategy, tableStructurePair);
		DbTableStructureComparer comparer = new DbTableStructureComparer(configuration);
		return comparer.compareAll();
	}
}
