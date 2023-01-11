package com.softicar.platform.core.module.configuration;

import com.softicar.platform.db.runtime.table.row.IDbTableRow;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractStandardConfiguration {

	public abstract void createAndSaveAll();

	protected <R extends IDbTableRow<R, ?>> void saveAll(Stream<R> rows) {

		saveAll(rows.collect(Collectors.toList()));
	}

	protected <R extends IDbTableRow<R, ?>> void saveAll(Collection<R> rows) {

		var iterator = rows.iterator();
		if (iterator.hasNext()) {
			var table = iterator.next().table();
			table.saveAll(rows);
		}
	}
}
