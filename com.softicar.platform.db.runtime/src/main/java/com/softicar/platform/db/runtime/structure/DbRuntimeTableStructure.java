package com.softicar.platform.db.runtime.structure;

import com.softicar.platform.db.runtime.enums.IDbEnumTable;
import com.softicar.platform.db.runtime.table.IDbTable;
import com.softicar.platform.db.structure.table.DbTableStructure;
import java.util.Optional;

public class DbRuntimeTableStructure extends DbTableStructure {

	public DbRuntimeTableStructure(IDbTable<?, ?> table) {

		super(table.getFullName());

		table//
			.getAllFields()
			.forEach(field -> addColumnStructure(new DbRuntimeColumnStructure(this, field)));
		table//
			.getAllKeys()
			.forEach(key -> addKeyStructure(new DbRuntimeKeyStructure(this, key)));
		table//
			.getAllFields()
			.stream()
			.map(field -> field.cast().toForeignRowField())
			.filter(Optional::isPresent)
			.map(Optional::get)
			.forEach(field -> addForeignKeyStructure(new DbRuntimeForeignKeyStructure(this, field)));
		if (table instanceof IDbEnumTable) {
			setEnumTableProperties(table);
			new InternalDbEnumTableRowsAdder<>(this, (IDbEnumTable<?, ?>) table).addAll();
		}

		validate();
	}

	private void setEnumTableProperties(IDbTable<?, ?> table) {

		setEnumTable(true);
		setEnumTableComment(table);
	}

	/**
	 * FIXME This hack should not be necessary. We need a concept to avoid it.
	 */
	private void setEnumTableComment(IDbTable<?, ?> table) {

		String comment = getComment();
		if (comment.isEmpty()) {
			setComment("@enum@");
		} else if (!comment.equals("@enum@")) {
			String errorMessage = String
				.format(//
					"Failed to mark '%s' as enum table: Enum tables must not have comments other than '@enum@'. Encountered comment: '%s'",
					table.getFullName(),
					comment);
			throw new IllegalArgumentException(errorMessage);
		}
	}
}
