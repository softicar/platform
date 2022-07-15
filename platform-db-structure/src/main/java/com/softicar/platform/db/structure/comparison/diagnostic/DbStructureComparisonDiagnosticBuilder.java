package com.softicar.platform.db.structure.comparison.diagnostic;

import com.softicar.platform.db.structure.comparison.helper.DbTableStructures;
import com.softicar.platform.db.structure.table.DbTableStructureFeatureType;
import com.softicar.platform.db.structure.table.IDbTableStructure;

public class DbStructureComparisonDiagnosticBuilder {

	private final DbStructureComparisonDiagnosticData diagnosticData;

	public DbStructureComparisonDiagnosticBuilder(DbTableStructureFeatureType featureType) {

		this.diagnosticData = new DbStructureComparisonDiagnosticData(featureType);
	}

	public TableStructureSetter setLevelError() {

		return setLevel(DbStructureComparisonDiagnosticLevel.ERROR);
	}

	public TableStructureSetter setLevelWarning() {

		return setLevel(DbStructureComparisonDiagnosticLevel.WARNING);
	}

	public TableStructureSetter setLevelInfo() {

		return setLevel(DbStructureComparisonDiagnosticLevel.INFO);
	}

	public TableStructureSetter setLevel(DbStructureComparisonDiagnosticLevel level) {

		diagnosticData.setLevel(level);
		return new TableStructureSetter();
	}

	public class TableStructureSetter {

		private TableStructureSetter() {

			// avoid external instantiation
		}

		public Builder setTableStructures(IDbTableStructure referenceStructure, IDbTableStructure sampleStructure) {

			diagnosticData.setReferenceStructure(referenceStructure);
			diagnosticData.setSampleStructure(sampleStructure);
			diagnosticData.setTableName(DbTableStructures.getUniqueTableNameOrThrow(referenceStructure, sampleStructure));
			return new Builder();
		}
	}

	public class Builder {

		private Builder() {

			// avoid external instantiation
		}

		public DbStructureComparisonDiagnostic build(String text) {

			return build(text, new Object[0]);
		}

		public DbStructureComparisonDiagnostic build(String text, Object...args) {

			diagnosticData.setText(text, args);
			return new DbStructureComparisonDiagnostic(diagnosticData);
		}
	}
}
