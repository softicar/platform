package com.softicar.platform.db.structure.table;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class DbTableStructureValidator {

	private final IDbTableStructure tableStructure;

	public DbTableStructureValidator(IDbTableStructure tableStructure) {

		this.tableStructure = tableStructure;
	}

	public void validate() {

		validateFeatures(tableStructure.getColumns());
		validateFeatures(tableStructure.getKeys());
		validateFeatures(tableStructure.getForeignKeys());

		assertUniqueNames(tableStructure.getColumns());
		assertUniqueNames(tableStructure.getKeys());
		assertUniqueNames(tableStructure.getForeignKeys());

		if (tableStructure.getColumns().isEmpty()) {
			throw new IllegalStateException("Table structure has no columns.");
		}
	}

	private void validateFeatures(Collection<? extends IDbTableStructureFeature> features) {

		features.forEach(IDbTableStructureFeature::validate);
	}

	private void assertUniqueNames(Collection<? extends IDbTableStructureFeature> features) {

		UniqueNameAsserter asserter = new UniqueNameAsserter();
		features//
			.stream()
			.map(IDbTableStructureFeature::getName)
			.filter(Optional::isPresent)
			.map(Optional::get)
			.forEach(asserter::assertUniqueName);
	}

	private static class UniqueNameAsserter {

		private final Set<String> usedNames;

		public UniqueNameAsserter() {

			this.usedNames = new TreeSet<>();
		}

		public void assertUniqueName(String name) {

			boolean added = usedNames.add(name);
			if (!added) {
				throw new IllegalStateException(String.format("Duplicate table feature name '%s'.", name));
			}
		}
	}
}
