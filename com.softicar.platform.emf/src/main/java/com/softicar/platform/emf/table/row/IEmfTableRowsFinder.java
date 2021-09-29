package com.softicar.platform.emf.table.row;

import com.softicar.platform.db.sql.selects.SqlSelect1_1;

@FunctionalInterface
public interface IEmfTableRowsFinder<E> {

	SqlSelect1_1<E, E> createSelectByDisplayName(String displayNamePattern);
}
