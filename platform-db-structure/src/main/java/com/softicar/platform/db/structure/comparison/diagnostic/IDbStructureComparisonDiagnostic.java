package com.softicar.platform.db.structure.comparison.diagnostic;

import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import java.util.Optional;

public interface IDbStructureComparisonDiagnostic {

	DbStructureComparisonDiagnosticLevel getLevel();

	DbTableName getTableName();

	Optional<IDbTableStructure> getReferenceStructure();

	Optional<IDbTableStructure> getSampleStructure();

	String getDiagnosticText();
}
