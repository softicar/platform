package com.softicar.platform.db.runtime.structure.comparison.diagnostic;

import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.runtime.structure.comparison.helper.DbTableStructureFeatureIdentifier;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import java.util.Optional;

public class DbStructureComparisonDiagnostic implements IDbStructureComparisonDiagnostic {

	private final DbStructureComparisonDiagnosticData diagnosticData;
	private DbTableStructureFeatureIdentifier identifier;

	public DbStructureComparisonDiagnostic(DbStructureComparisonDiagnosticData diagnosticData) {

		this.diagnosticData = diagnosticData;
		this.identifier = null;
	}

	@Override
	public DbStructureComparisonDiagnosticLevel getLevel() {

		return diagnosticData.getLevel();
	}

	@Override
	public DbTableName getTableName() {

		return diagnosticData.getTableName();
	}

	@Override
	public Optional<IDbTableStructure> getReferenceStructure() {

		return diagnosticData.getReferenceStructure();
	}

	@Override
	public Optional<IDbTableStructure> getSampleStructure() {

		return diagnosticData.getSampleStructure();
	}

	@Override
	public String getDiagnosticText() {

		var tableNameText = getTableName().getQuoted();
		var featureTypeText = diagnosticData.getFeatureType().getTitle();
		var identifierText = identifier != null? " `" + identifier + "`" : "";
		var diagnosticText = diagnosticData.getFormattedText();
		return "Table %s, %s%s: %s".formatted(tableNameText, featureTypeText, identifierText, diagnosticText);
	}

	@Override
	public String toString() {

		return getDiagnosticText();
	}

	public void setIdentifier(DbTableStructureFeatureIdentifier identifier) {

		this.identifier = identifier;
	}
}
