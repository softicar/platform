package com.softicar.platform.core.module.web.service.test;

import com.softicar.platform.core.module.test.SofticarTestDatabase;
import com.softicar.platform.core.module.web.service.WebServiceBrokerService;
import com.softicar.platform.db.core.database.DbDatabaseScope;
import com.softicar.platform.db.core.database.IDbDatabase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WebServiceTestService extends WebServiceBrokerService {

	protected final IDbDatabase database;

	public WebServiceTestService() {

		this(new SofticarTestDatabase());
	}

	public WebServiceTestService(IDbDatabase database) {

		this.database = database;
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {

		try (var scope = new DbDatabaseScope(database)) {
			super.service(request, response);
		}
	}
}
