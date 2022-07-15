package com.softicar.platform.db.structure.comparison.foreign.key;

import com.softicar.platform.db.structure.comparison.feature.AbstractDbFeatureStructureComparer;
import com.softicar.platform.db.structure.comparison.helper.DbTableStructureFeatureIdentifier;
import com.softicar.platform.db.structure.comparison.table.IDbTableStructureComparerConfiguration;
import com.softicar.platform.db.structure.foreign.key.IDbForeignKeyStructure;
import com.softicar.platform.db.structure.table.DbTableStructureFeatureType;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import java.util.Collection;

/**
 * Processes foreign keys to compare structural compatibility, applying a
 * specific strategy.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public abstract class AbstractDbForeignKeyStructureComparer extends AbstractDbFeatureStructureComparer<IDbForeignKeyStructure> {

	public AbstractDbForeignKeyStructureComparer(IDbTableStructureComparerConfiguration configuration) {

		super(configuration, DbTableStructureFeatureType.FOREIGN_KEY);
	}

	@Override
	protected DbTableStructureFeatureIdentifier getIdentifier(IDbForeignKeyStructure structure) {

		return new DbTableStructureFeatureIdentifier(structure);
	}

	@Override
	protected Collection<? extends IDbForeignKeyStructure> getStructureFeatures(IDbTableStructure tableStructure) {

		return tableStructure.getForeignKeys();
	}
}
