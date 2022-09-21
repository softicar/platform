package com.softicar.platform.db.structure.table;

import com.softicar.platform.common.core.exceptions.SofticarException;
import java.util.Optional;

/**
 * Common interface of all structural features of a database table.
 *
 * @author Alexander Schmidt
 */
public interface IDbTableStructureFeature {

	/**
	 * Returns the {@link IDbTableStructure} this column belongs to.
	 *
	 * @return the related table structure (never null)
	 */
	IDbTableStructure getTableStructure();

	/**
	 * Returns the optional name of this feature.
	 *
	 * @return the feature name (never null)
	 */
	Optional<String> getName();

	/**
	 * Returns the name of this feature or throws an exception.
	 *
	 * @return the feature name (never null)
	 */
	default String getNameOrThrow() {

		return getName().orElseThrow(() -> new SofticarException("Missing name of structual feature on table: %s", getTableStructure().getTableName()));
	}

	/**
	 * Validates the consistency of this table feature.
	 */
	void validate();
}
