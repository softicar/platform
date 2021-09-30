package com.softicar.platform.core.module.web.service.test;

import com.softicar.platform.core.module.web.service.environment.IWebServiceEnvironment;
import com.softicar.platform.core.module.web.service.environment.WebServiceDefaultEnvironment;
import com.softicar.platform.db.core.database.DbCurrentDatabase;
import com.softicar.platform.db.core.database.IDbDatabase;

/**
 * An {@link IWebServiceEnvironment} that ensures that all service calls use the
 * given {@link IDbDatabase} instance.
 *
 * @author Oliver Richers
 */
public class WebServiceTestEnvironment extends WebServiceDefaultEnvironment {

	private final IDbDatabase database;

	public WebServiceTestEnvironment(IDbDatabase database) {

		this.database = database;
	}

	@Override
	public void setupEnvironment() {

		super.setupEnvironment();

		DbCurrentDatabase.set(database);
	}
}
