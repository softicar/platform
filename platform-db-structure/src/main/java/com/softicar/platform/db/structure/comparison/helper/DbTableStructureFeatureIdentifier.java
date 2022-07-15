package com.softicar.platform.db.structure.comparison.helper;

import com.softicar.platform.db.structure.column.IDbColumnStructure;
import com.softicar.platform.db.structure.enums.IDbEnumTableRowStructure;
import com.softicar.platform.db.structure.foreign.key.IDbForeignKeyStructure;
import com.softicar.platform.db.structure.key.IDbKeyStructure;
import java.util.Objects;

public class DbTableStructureFeatureIdentifier implements Comparable<DbTableStructureFeatureIdentifier> {

	private final String identifierString;

	public DbTableStructureFeatureIdentifier(IDbColumnStructure structure) {

		this(structure.getNameOrThrow());
	}

	public DbTableStructureFeatureIdentifier(IDbKeyStructure structure) {

		this(structure.getName().orElse(""));
	}

	public DbTableStructureFeatureIdentifier(IDbForeignKeyStructure structure) {

		this(structure.getSyntheticName());
	}

	public DbTableStructureFeatureIdentifier(IDbEnumTableRowStructure structure) {

		this(structure.getId() + "");
	}

	private DbTableStructureFeatureIdentifier(String identifier) {

		this.identifierString = Objects.requireNonNull(identifier);
	}

	public String getIdentifierString() {

		return identifierString;
	}

	@Override
	public String toString() {

		return getIdentifierString();
	}

	@Override
	public int compareTo(DbTableStructureFeatureIdentifier other) {

		return identifierString.compareTo(other.identifierString);
	}
}
