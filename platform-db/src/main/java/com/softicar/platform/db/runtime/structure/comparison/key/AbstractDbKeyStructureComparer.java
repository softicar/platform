package com.softicar.platform.db.runtime.structure.comparison.key;

import com.softicar.platform.db.runtime.structure.comparison.feature.AbstractDbFeatureStructureComparer;
import com.softicar.platform.db.runtime.structure.comparison.helper.DbTableStructureFeatureIdentifier;
import com.softicar.platform.db.runtime.structure.comparison.table.IDbTableStructureComparerConfiguration;
import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.key.DbKeyType;
import com.softicar.platform.db.structure.key.IDbKeyStructure;
import com.softicar.platform.db.structure.table.DbTableStructureFeatureType;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Processes keys to compare structural compatibility, applying a specific
 * strategy.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public abstract class AbstractDbKeyStructureComparer extends AbstractDbFeatureStructureComparer<IDbKeyStructure> {

	public AbstractDbKeyStructureComparer(IDbTableStructureComparerConfiguration configuration) {

		super(configuration, DbTableStructureFeatureType.KEY);
	}

	@Override
	protected DbTableStructureFeatureIdentifier getIdentifier(IDbKeyStructure structure) {

		return new DbTableStructureFeatureIdentifier(structure);
	}

	@Override
	protected Collection<? extends IDbKeyStructure> getStructureFeatures(IDbTableStructure tableStructure) {

		return tableStructure.getKeys();
	}

	protected boolean isIndexKey(IDbKeyStructure structure) {

		return structure.getType() == DbKeyType.INDEX;
	}

	protected boolean isUniqueKey(IDbKeyStructure structure) {

		return structure.getType() == DbKeyType.UNIQUE;
	}

	protected boolean isPrimaryKey(IDbKeyStructure structure) {

		return structure.getType() == DbKeyType.PRIMARY;
	}

	protected List<String> getColumnNames(IDbKeyStructure structure) {

		return structure//
			.getColumns()
			.stream()
			.map(IDbColumnStructure::getNameOrThrow)
			.collect(Collectors.toList());
	}
}
