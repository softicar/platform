package com.softicar.platform.db.runtime.structure.comparison;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.runtime.structure.comparison.table.DbTableStructurePair;
import com.softicar.platform.db.runtime.structure.comparison.table.IDbTableStructurePair;
import com.softicar.platform.db.structure.database.IDbDatabaseStructure;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import com.softicar.platform.db.structure.view.DbAliasView;
import com.softicar.platform.db.structure.view.DbAliasViewParser;
import com.softicar.platform.db.structure.view.DbAliasViewTableStructure;
import com.softicar.platform.db.structure.view.IDbViewStructure;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.apache.commons.collections4.SetUtils;

/**
 * Matches the {@link IDbTableStructure} instances extracted from a pair of
 * {@link IDbDatabaseStructure} instances.
 *
 * @author Alexander Schmidt
 */
class DbDatabaseStructureMatcher {

	private final Map<DbTableName, IDbTableStructure> referenceTableStructures;
	private final Map<DbTableName, IDbTableStructure> sampleTableStructures;

	/**
	 * @param referenceDatabaseStructure
	 *            the reference {@link IDbDatabaseStructure} (never null)
	 * @param sampleDatabaseStructure
	 *            the sample {@link IDbDatabaseStructure} (never null)
	 */
	public DbDatabaseStructureMatcher(IDbDatabaseStructure referenceDatabaseStructure, IDbDatabaseStructure sampleDatabaseStructure) {

		this.referenceTableStructures = new TreeMap<>();
		this.sampleTableStructures = new TreeMap<>();

		referenceDatabaseStructure//
			.getTableStructures()
			.forEach(tableStructure -> referenceTableStructures.put(tableStructure.getTableName(), tableStructure));
		referenceDatabaseStructure//
			.getViewStructures()
			.forEach(this::addAliasViewIfAppropriate);
		sampleDatabaseStructure//
			.getTableStructures()
			.forEach(tableStructure -> sampleTableStructures.put(tableStructure.getTableName(), tableStructure));
	}

	/**
	 * Returns a collection of {@link IDbTableStructure} instances that only
	 * occur in the reference {@link IDbDatabaseStructure}.
	 *
	 * @return a collection of the {@link IDbTableStructure} instances which are
	 *         contained in the reference {@link IDbDatabaseStructure}, but not
	 *         in the sample {@link IDbDatabaseStructure} (never null)
	 */
	public Collection<IDbTableStructure> getReferenceExclusiveTableStructures() {

		return SetUtils//
			.difference(referenceTableStructures.keySet(), sampleTableStructures.keySet())
			.stream()
			.map(referenceTableStructures::get)
			.collect(Collectors.toList());
	}

	/**
	 * Returns a collection of {@link IDbTableStructure} instances that only
	 * occur in the sample {@link IDbDatabaseStructure}.
	 *
	 * @return a collection of those {@link IDbTableStructure} instances which
	 *         are contained in the sample {@link IDbDatabaseStructure}, but not
	 *         in the reference {@link IDbDatabaseStructure} (never null)
	 */
	public Collection<IDbTableStructure> getSampleExclusiveTableStructures() {

		return SetUtils//
			.difference(sampleTableStructures.keySet(), referenceTableStructures.keySet())
			.stream()
			.map(sampleTableStructures::get)
			.collect(Collectors.toList());
	}

	/**
	 * Returns a collection of {@link IDbTableStructure} instances that occur in
	 * both the reference and the sample {@link IDbDatabaseStructure}.
	 *
	 * @return a collection of those {@link IDbTableStructure} instances which
	 *         are contained in both the sample {@link IDbDatabaseStructure},
	 *         and in the reference {@link IDbDatabaseStructure} (never null)
	 */
	public Collection<IDbTableStructurePair> getMutualTableStructures() {

		return SetUtils//
			.intersection(referenceTableStructures.keySet(), sampleTableStructures.keySet())
			.stream()
			.map(it -> new DbTableStructurePair(referenceTableStructures.get(it), sampleTableStructures.get(it)))
			.collect(Collectors.toList());
	}

	private void addAliasViewIfAppropriate(IDbViewStructure viewStructure) {

		try {
			DbAliasView aliasView = new DbAliasViewParser(viewStructure).parse();
			IDbTableStructure tableStructure = referenceTableStructures.get(aliasView.getTableName());
			if (tableStructure != null) {
				referenceTableStructures.put(aliasView.getViewName(), new DbAliasViewTableStructure(aliasView, tableStructure));
			}
		} catch (Exception exception) {
			// failure to parse the view as an alias view
			DevNull.swallow(exception);
		}
	}
}
