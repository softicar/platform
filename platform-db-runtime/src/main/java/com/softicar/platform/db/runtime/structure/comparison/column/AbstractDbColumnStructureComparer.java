package com.softicar.platform.db.runtime.structure.comparison.column;

import com.softicar.platform.db.runtime.structure.comparison.feature.AbstractDbFeatureStructureComparer;
import com.softicar.platform.db.runtime.structure.comparison.helper.DbTableStructureFeatureIdentifier;
import com.softicar.platform.db.runtime.structure.comparison.table.IDbTableStructureComparerConfiguration;
import com.softicar.platform.db.sql.type.SqlFieldType;
import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.table.DbTableStructureFeatureType;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Processes columns to compare structural compatibility, applying a specific
 * strategy.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public abstract class AbstractDbColumnStructureComparer extends AbstractDbFeatureStructureComparer<IDbColumnStructure> {

	public AbstractDbColumnStructureComparer(IDbTableStructureComparerConfiguration configuration) {

		super(configuration, DbTableStructureFeatureType.COLUMN);
	}

	@Override
	protected DbTableStructureFeatureIdentifier getIdentifier(IDbColumnStructure structure) {

		return new DbTableStructureFeatureIdentifier(structure);
	}

	@Override
	protected Collection<? extends IDbColumnStructure> getStructureFeatures(IDbTableStructure tableStructure) {

		return tableStructure.getColumns();
	}

	protected String getNormalizedDefaultValue(IDbColumnStructure columnStructure) {

		String defaultValue = columnStructure.getDefaultValue();
		if (defaultValue != null) {
			if (columnStructure.getFieldType() == SqlFieldType.DOUBLE) {
				return Double.valueOf(defaultValue).toString();
			} else if (columnStructure.getFieldType() == SqlFieldType.FLOAT) {
				return Float.valueOf(defaultValue).toString();
			} else if (columnStructure.getFieldType() == SqlFieldType.BIG_DECIMAL) {
				return new BigDecimal(defaultValue).toPlainString();
			} else {
				return defaultValue;
			}
		} else {
			return null;
		}
	}

	protected interface EnumValues {

		static boolean referenceHasMore(List<String> reference, List<String> sample) {

			return !Objects.equals(reference, sample) && reference.containsAll(sample);
		}

		static boolean sampleHasMore(List<String> reference, List<String> sample) {

			return !Objects.equals(reference, sample) && sample.containsAll(reference);
		}

		static boolean contradictive(List<String> reference, List<String> sample) {

			return referenceHasMore(reference, sample) && sampleHasMore(reference, sample);
		}
	}

	protected interface FieldTypes {

		static boolean erroneousDifference(SqlFieldType reference, SqlFieldType sample) {

			return reference != sample && !unsafeDifference(reference, sample);
		}

		static boolean unsafeDifference(SqlFieldType reference, SqlFieldType sample) {

			if (reference != sample) {
				Collection<SqlFieldType> integerNumbers = Arrays.asList(SqlFieldType.INTEGER, SqlFieldType.LONG);
				Collection<SqlFieldType> fractionalNumbers = Arrays.asList(SqlFieldType.FLOAT, SqlFieldType.DOUBLE);
				if (integerNumbers.contains(reference) && integerNumbers.contains(sample)) {
					return true;
				} else if (fractionalNumbers.contains(reference) && fractionalNumbers.contains(sample)) {
					return true;
				}
			}
			return false;
		}
	}
}
