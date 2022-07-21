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

		StringBuilder builder = new StringBuilder();
		builder.append("Table ");
		builder.append("**''" + getTableName().getCanonicalName() + "''**");
		builder.append(" / ");
		builder.append(diagnosticData.getFeatureType().getTitle());
		Optional
			.ofNullable(identifier)//
			.map(identifier -> " **''" + identifier + "''**")
			.ifPresent(builder::append);
		builder.append(": ");
		builder.append(diagnosticData.getFormattedText());
		return builder.toString();
	}

	@Override
	public String toString() {

		return getDiagnosticText();
	}

	public void setIdentifier(DbTableStructureFeatureIdentifier identifier) {

		this.identifier = identifier;
	}
}
