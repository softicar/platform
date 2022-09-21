package com.softicar.platform.db.runtime.structure.comparison.enumeration;

import com.softicar.platform.db.runtime.structure.comparison.feature.AbstractDbFeatureStructureComparer;
import com.softicar.platform.db.runtime.structure.comparison.helper.DbTableStructureFeatureIdentifier;
import com.softicar.platform.db.runtime.structure.comparison.table.IDbTableStructureComparerConfiguration;
import com.softicar.platform.db.structure.enums.IDbEnumTableRowStructure;
import com.softicar.platform.db.structure.table.DbTableStructureFeatureType;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import java.util.Collection;

public abstract class AbstractDbEnumTableRowStructureComparer extends AbstractDbFeatureStructureComparer<IDbEnumTableRowStructure> {

	public AbstractDbEnumTableRowStructureComparer(IDbTableStructureComparerConfiguration configuration) {

		super(configuration, DbTableStructureFeatureType.ENUM_TABLE_ROW);
	}

	@Override
	protected DbTableStructureFeatureIdentifier getIdentifier(IDbEnumTableRowStructure structure) {

		return new DbTableStructureFeatureIdentifier(structure);
	}

	@Override
	protected Collection<? extends IDbEnumTableRowStructure> getStructureFeatures(IDbTableStructure tableStructure) {

		return tableStructure.getEnumTableRows();
	}
}
