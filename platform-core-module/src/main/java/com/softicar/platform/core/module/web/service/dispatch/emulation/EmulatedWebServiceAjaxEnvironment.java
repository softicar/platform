package com.softicar.platform.core.module.web.service.dispatch.emulation;

import com.softicar.platform.core.module.web.service.environment.WebServiceDefaultEnvironment;
import com.softicar.platform.db.core.database.DbCurrentDatabase;
import com.softicar.platform.db.core.database.IDbDatabase;

/**
 * Ensures that the given {@link IDbDatabase} object is retained as the default
 * database.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class EmulatedWebServiceAjaxEnvironment extends WebServiceDefaultEnvironment {

	protected final IDbDatabase database;

	public EmulatedWebServiceAjaxEnvironment(IDbDatabase database) {

		this.database = database;
	}

	@Override
	public void setupEnvironment() {

		super.setupEnvironment();

		DbCurrentDatabase.set(database);
	}
}
