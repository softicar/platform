package com.softicar.platform.db.structure.comparison.diagnostic;

import com.softicar.platform.db.core.table.DbTableName;
import com.softicar.platform.db.structure.table.DbTableStructureFeatureType;
import com.softicar.platform.db.structure.table.IDbTableStructure;
import java.util.Objects;
import java.util.Optional;

public class DbStructureComparisonDiagnosticData {

	private final DbTableStructureFeatureType featureType;
	private DbStructureComparisonDiagnosticLevel level;
	private DbTableName tableName;
	private IDbTableStructure referenceStructure;
	private IDbTableStructure sampleStructure;
	private String text;
	private Object[] args;

	public DbStructureComparisonDiagnosticData(DbTableStructureFeatureType featureType) {

		this.featureType = featureType;
		this.level = null;
		this.tableName = null;
		this.referenceStructure = null;
		this.sampleStructure = null;
		this.text = null;
		this.args = null;
	}

	public DbTableStructureFeatureType getFeatureType() {

		return featureType;
	}

	public DbStructureComparisonDiagnosticLevel getLevel() {

		return level;
	}

	public DbTableName getTableName() {

		return tableName;
	}

	public Optional<IDbTableStructure> getReferenceStructure() {

		return Optional.ofNullable(referenceStructure);
	}

	public Optional<IDbTableStructure> getSampleStructure() {

		return Optional.ofNullable(sampleStructure);
	}

	public String getFormattedText() {

		return String.format(text, args);
	}

	public void setLevel(DbStructureComparisonDiagnosticLevel level) {

		this.level = Objects.requireNonNull(level);
	}

	public void setTableName(DbTableName tableName) {

		this.tableName = Objects.requireNonNull(tableName);
	}

	public void setReferenceStructure(IDbTableStructure referenceStructure) {

		this.referenceStructure = referenceStructure;
	}

	public void setSampleStructure(IDbTableStructure sampleStructure) {

		this.sampleStructure = sampleStructure;
	}

	public void setText(String text, Object...args) {

		this.text = text;
		this.args = args;
	}
}
