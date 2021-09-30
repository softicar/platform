package com.softicar.platform.db.structure.view;

import com.softicar.platform.db.core.table.DbTableName;

/**
 * Default implementation of {@link IDbViewStructure}.
 *
 * @author Oliver Richers
 */
public class DbViewStructure implements IDbViewStructure {

	private final DbTableName viewName;
	private final String viewQuery;

	public DbViewStructure(DbTableName viewName, String viewQuery) {

		this.viewName = viewName;
		this.viewQuery = viewQuery;
	}

	@Override
	public DbTableName getViewName() {

		return viewName;
	}

	@Override
	public String getViewQuery() {

		return viewQuery;
	}

}
