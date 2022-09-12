package com.softicar.platform.db.runtime.structure.comparison.column;

import com.softicar.platform.common.core.interfaces.Predicates;
import com.softicar.platform.common.core.utils.equals.Equals;
import com.softicar.platform.db.runtime.structure.comparison.feature.checker.DbExclusiveFeatureChecker;
import com.softicar.platform.db.runtime.structure.comparison.feature.checker.DbMutualFeatureChecker;
import com.softicar.platform.db.runtime.structure.comparison.table.IDbTableStructureComparerConfiguration;
import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.foreign.key.IDbForeignKeyStructure;
import java.util.Optional;

public class DbColumnStructureEqualityComparer extends AbstractDbColumnStructureComparer {

	public DbColumnStructureEqualityComparer(IDbTableStructureComparerConfiguration configuration) {

		super(configuration);
	}

	@Override
	protected void checkMutualFeature(DbMutualFeatureChecker<IDbColumnStructure> checker) {

		checker//
			.compare("Logical Name", IDbColumnStructure::getLogicalName)
			.addErrorIf(Values::notEqual)

			.compare("Field Type", IDbColumnStructure::getFieldType)
			.addErrorIf(Values::notEqual)

			.compare("Enum Values", IDbColumnStructure::getEnumValues)
			.addErrorIf(Values::notEqual)

			.compare("Bits", IDbColumnStructure::getBits)
			.addErrorIf(Values::notEqual)

			.compare("Unsigned Flag", IDbColumnStructure::isUnsigned)
			.addErrorIf(Values::notEqual)

			.compare("Max Length", IDbColumnStructure::getMaxLength)
			.addErrorIf(Values::notEqual)

			.compare("Length Bits", IDbColumnStructure::getLengthBits)
			.addErrorIf(Values::notEqual)

			.compare("Character Set", IDbColumnStructure::getCharacterSet)
			.addErrorIf(Values::notEqual)

			.compare("Collation", IDbColumnStructure::getCollation)
			.addErrorIf(Values::notEqual)

			.compare("Precision", IDbColumnStructure::getPrecision)
			.addErrorIf(Values::notEqual)

			.compare("Scale", IDbColumnStructure::getScale)
			.addErrorIf(Values::notEqual)

			.compare("Nullable Flag", IDbColumnStructure::isNullable)
			.addErrorIf(Values::notEqual)

			.compare("Auto-Increment Flag", IDbColumnStructure::isAutoIncrement)
			.addErrorIf(Values::notEqual)

			.compare("Base Column Flag", IDbColumnStructure::isBaseColumn)
			.addErrorIf(Values::notEqual)

			.compare("Foreign Key Structure", IDbColumnStructure::getForeignKeyStructure)//
			.addErrorIf(ForeignKeyStructures::notEqual)

			.compare("Foreign Key Flag", IDbColumnStructure::isForeignKey)
			.addErrorIf(Values::notEqual)

			.compare("Default Value Type", IDbColumnStructure::getDefaultType)
			.addErrorIf(Values::notEqual)

			.compare("Default Value", this::getNormalizedDefaultValue)
			.addErrorIf(Values::notEqual)

			.compare("Comment", IDbColumnStructure::getComment)
			.addErrorIf(Values::notEqual);
	}

	@Override
	protected void checkReferenceExclusiveFeature(DbExclusiveFeatureChecker<IDbColumnStructure> checker) {

		checker//
			.addErrorIf(Predicates.always(), "Surplus column in %s.", getReferenceName());
	}

	@Override
	protected void checkSampleExclusiveFeature(DbExclusiveFeatureChecker<IDbColumnStructure> checker) {

		checker//
			.addErrorIf(Predicates.always(), "Surplus column in %s.", getSampleName());
	}

	private interface ForeignKeyStructures {

		static boolean notEqual(Optional<IDbForeignKeyStructure> reference, Optional<IDbForeignKeyStructure> sample) {

			return !equal(reference, sample);
		}

		static boolean equal(Optional<IDbForeignKeyStructure> reference, Optional<IDbForeignKeyStructure> sample) {

			if (reference.isPresent() && sample.isPresent()) {
				return Equals//
					// FIXME This hack should have been removed with PLAT-1152.
					// HERE BE DRAGONS:
					// We deliberately ignore (and therefore tolerate) deviations in actual FK names, and instead use synthetic FK names.
					.comparing(IDbForeignKeyStructure::getSyntheticName)
					.comparing(it -> it.getTableStructure().getTableName())
					.comparing(IDbForeignKeyStructure::getColumns)
					.comparing(IDbForeignKeyStructure::getForeignTableName)
					.comparing(IDbForeignKeyStructure::getColumnNameMap)
					.comparing(IDbForeignKeyStructure::getOnDeleteAction)
					.comparing(IDbForeignKeyStructure::getOnUpdateAction)
					.compare(reference.get(), sample.get());
			} else if (!reference.isPresent() && !sample.isPresent()) {
				return true;
			} else {
				return false;
			}
		}
	}
}
