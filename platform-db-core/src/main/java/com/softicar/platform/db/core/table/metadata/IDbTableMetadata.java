package com.softicar.platform.db.core.table.metadata;

import java.util.SortedMap;

public interface IDbTableMetadata {

	SortedMap<Integer, String> getTableColumns();

	SortedMap<Integer, String> getPrimaryKeyColumns();
}
