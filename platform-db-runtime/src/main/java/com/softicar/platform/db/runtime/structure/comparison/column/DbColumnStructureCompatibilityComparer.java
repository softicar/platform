package com.softicar.platform.db.runtime.structure.comparison.column;

import com.softicar.platform.common.core.interfaces.Predicates;
import com.softicar.platform.db.runtime.structure.comparison.feature.checker.DbExclusiveFeatureChecker;
import com.softicar.platform.db.runtime.structure.comparison.feature.checker.DbMutualFeatureChecker;
import com.softicar.platform.db.runtime.structure.comparison.table.IDbTableStructureComparerConfiguration;
import com.softicar.platform.db.structure.column.DbColumnDefaultType;
import com.softicar.platform.db.structure.column.IDbColumnStructure;

public class DbColumnStructureCompatibilityComparer extends AbstractDbColumnStructureComparer {

	public DbColumnStructureCompatibilityComparer(IDbTableStructureComparerConfiguration configuration) {

		super(configuration);
	}

	@Override
	protected void checkMutualFeature(DbMutualFeatureChecker<IDbColumnStructure> checker) {

		checker//
			.compare("Logical Name", IDbColumnStructure::getLogicalName)
			.addInfoIf(Values::notEqual)

			.compare("Field Type", IDbColumnStructure::getFieldType)
			.addErrorIf(FieldTypes::erroneousDifference)
			.addWarningIf(FieldTypes::unsafeDifference)

			.compare("Enum Values", IDbColumnStructure::getEnumValues)
			.addErrorIf(EnumValues::sampleHasMore)
			.addWarningIf(EnumValues::referenceHasMore)

			.compare("Bits", IDbColumnStructure::getBits)
			.addErrorIf(Integers::referenceSmaller)
			.addInfoIf(Integers::referenceGreater)

			.compare("Unsigned Flag", IDbColumnStructure::isUnsigned)
			.addErrorIf(Values::notEqual)

			.compare("Max Length", IDbColumnStructure::getMaxLength)
			.addErrorIf(Integers::referenceSmaller)
			.addInfoIf(Integers::referenceGreater)

			.compare("Length Bits", IDbColumnStructure::getLengthBits)
			.addErrorIf(Integers::referenceSmaller)
			.addInfoIf(Integers::referenceGreater)

			.compare("Character Set", IDbColumnStructure::getCharacterSet)
			.addWarningIf(Values::notEqual)

			.compare("Collation", IDbColumnStructure::getCollation)
			.addWarningIf(Values::notEqual)

			.compare("Precision", IDbColumnStructure::getPrecision)
			.addErrorIf(Values::notEqual)

			.compare("Scale", IDbColumnStructure::getScale)
			.addErrorIf(Values::notEqual)

			.compare("Nullable Flag", IDbColumnStructure::isNullable)
			.addErrorIf(Booleans::onlySample)
			.addWarningIf(Booleans::onlyReference)

			.compare("Auto-Increment Flag", IDbColumnStructure::isAutoIncrement)
			.addErrorIf(Values::notEqual)

			.compare("Base Column Flag", IDbColumnStructure::isBaseColumn)
			.addErrorIf(Values::notEqual)

			.compare("Foreign Key Flag", IDbColumnStructure::isForeignKey)
			.addWarningIf(Values::notEqual)

			.compare("Default Value Type", IDbColumnStructure::getDefaultType)
			.addWarningIf(Values::notEqual)

			.compare("Default Value", this::getNormalizedDefaultValue)
			.addWarningIf(Values::notEqual)

			.compare("Comment", IDbColumnStructure::getComment)
			.addInfoIf(Values::notEqual);
	}

	@Override
	protected void checkReferenceExclusiveFeature(DbExclusiveFeatureChecker<IDbColumnStructure> checker) {

		checker//
			.addErrorIf(this::isNotNegligible, "Surplus column in %s is neither nullable, nor has a default value.", getReferenceName())
			.addInfoIf(this::isNegligible, "Surplus column in %s.", getReferenceName());
	}

	@Override
	protected void checkSampleExclusiveFeature(DbExclusiveFeatureChecker<IDbColumnStructure> checker) {

		checker//
			.addErrorIf(Predicates.always(), "Surplus column in %s.", getSampleName());
	}

	private boolean isNotNegligible(IDbColumnStructure structure) {

		return !isNegligible(structure);
	}

	private boolean isNegligible(IDbColumnStructure structure) {

		return structure.isNullable() || !structure.getDefaultType().equals(DbColumnDefaultType.NONE);
	}
}
