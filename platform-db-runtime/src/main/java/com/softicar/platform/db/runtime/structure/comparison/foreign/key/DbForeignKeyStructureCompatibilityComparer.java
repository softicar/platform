package com.softicar.platform.db.runtime.structure.comparison.foreign.key;

import com.softicar.platform.common.core.interfaces.Predicates;
import com.softicar.platform.db.runtime.structure.comparison.feature.checker.DbExclusiveFeatureChecker;
import com.softicar.platform.db.runtime.structure.comparison.feature.checker.DbMutualFeatureChecker;
import com.softicar.platform.db.runtime.structure.comparison.table.IDbTableStructureComparerConfiguration;
import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.foreign.key.IDbForeignKeyStructure;
import java.util.List;
import java.util.Optional;

public class DbForeignKeyStructureCompatibilityComparer extends AbstractDbForeignKeyStructureComparer {

	public DbForeignKeyStructureCompatibilityComparer(IDbTableStructureComparerConfiguration configuration) {

		super(configuration);
	}

	@Override
	protected void checkMutualFeature(DbMutualFeatureChecker<IDbForeignKeyStructure> checker) {

		checker//
			.compare("Foreign Table Name", IDbForeignKeyStructure::getForeignTableName)
			.addErrorIf(Values::notEqual)

			.compare("Column Name Map", IDbForeignKeyStructure::getColumnNameMap)
			.addWarningIf(Values::notEqual)

			.compare("On-Delete Action", IDbForeignKeyStructure::getOnDeleteAction)
			.addWarningIf(Values::notEqual)

			.compare("On-Update Action", IDbForeignKeyStructure::getOnUpdateAction)
			.addWarningIf(Values::notEqual);
	}

	@Override
	protected void checkReferenceExclusiveFeature(DbExclusiveFeatureChecker<IDbForeignKeyStructure> checker) {

		Optional<IDbColumnStructure> sourceColumn = getSourceColumn(checker.getFeature());

		if (isColumnIgnored(sourceColumn)) {
			checker//
				.addInfoIf(Predicates.always(), "Surplus FK on ignored column '%s' in %s.", sourceColumn.get().getName(), getReferenceName());
		} else {
			checker//
				.addWarningIf(Predicates.always(), "Surplus FK in %s.", getReferenceName());
		}
	}

	@Override
	protected void checkSampleExclusiveFeature(DbExclusiveFeatureChecker<IDbForeignKeyStructure> checker) {

		checker//
			.addWarningIf(Predicates.always(), "Surplus FK in %s.", getSampleName());
	}

	private Optional<IDbColumnStructure> getSourceColumn(IDbForeignKeyStructure structure) {

		List<String> sourceColumns = structure.getColumns();
		if (sourceColumns.size() == 1) {
			return structure.getTableStructure().getColumnByPhysicalName(sourceColumns.get(0));
		} else {
			return Optional.empty();
		}
	}

	private boolean isColumnIgnored(Optional<IDbColumnStructure> structure) {

		return structure.map(IDbColumnStructure::isIgnored).orElse(false);
	}
}
