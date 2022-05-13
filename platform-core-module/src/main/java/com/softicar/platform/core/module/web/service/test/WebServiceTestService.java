package com.softicar.platform.core.module.web.service.test;

import com.softicar.platform.core.module.test.SofticarTestDatabase;
import com.softicar.platform.core.module.web.service.WebServiceBrokerService;
import com.softicar.platform.db.core.database.DbDatabaseScope;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebServiceTestService extends WebServiceBrokerService {

	protected final SofticarTestDatabase database;

	public WebServiceTestService() {

		this(new SofticarTestDatabase());
	}

	public WebServiceTestService(SofticarTestDatabase database) {

		this.database = database;
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {

		try (var scope = new DbDatabaseScope(database)) {
			super.service(request, response);
		}
	}
}
