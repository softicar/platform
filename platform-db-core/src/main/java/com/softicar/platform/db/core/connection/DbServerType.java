package com.softicar.platform.db.core.connection;

import com.softicar.platform.db.core.connection.connector.DbNoneConnector;
import com.softicar.platform.db.core.connection.connector.IDbConnector;
import com.softicar.platform.db.core.connection.quirks.DbDefaultQuirks;
import com.softicar.platform.db.core.connection.quirks.IDbServerQuirks;
import com.softicar.platform.db.core.dbms.h2.DbH2MemoryConnector;
import com.softicar.platform.db.core.dbms.h2.DbH2Quirks;
import com.softicar.platform.db.core.dbms.ibm.as400.DbIbmAs400Connector;
import com.softicar.platform.db.core.dbms.ibm.as400.DbIbmAs400Quirks;
import com.softicar.platform.db.core.dbms.ibm.informix.DbIbmInformixConnector;
import com.softicar.platform.db.core.dbms.mariadb.DbMariaDbConnector;
import com.softicar.platform.db.core.dbms.mariadb.DbMariaDbQuirks;
import com.softicar.platform.db.core.dbms.microsoft.sqlserver.DbMicrosoftSqlServer2000Quirks;
import com.softicar.platform.db.core.dbms.microsoft.sqlserver.DbMicrosoftSqlServerConnector;
import com.softicar.platform.db.core.dbms.microsoft.sqlserver.DbMicrosoftSqlServerLegacyConnector;
import com.softicar.platform.db.core.dbms.mysql.DbMysqlConnector;
import com.softicar.platform.db.core.dbms.mysql.DbMysqlQuirks;
import com.softicar.platform.db.core.dbms.postgresql.DbPostgreSqlConnector;

/**
 * Enumeration of the different types of database servers.
 *
 * @author Oliver Richers
 */
public enum DbServerType implements IDbServerType {

	NONE(new DbNoneConnector(), new DbDefaultQuirks()),
	H2_MEMORY(new DbH2MemoryConnector(), new DbH2Quirks()),
	IBM_AS400(new DbIbmAs400Connector(), new DbIbmAs400Quirks()),
	IBM_INFORMIX(new DbIbmInformixConnector(), new DbDefaultQuirks()),
	MARIA_DB(new DbMariaDbConnector(), new DbMariaDbQuirks()),
	MSSQL_SERVER_2000(new DbMicrosoftSqlServerLegacyConnector(), new DbMicrosoftSqlServer2000Quirks()),
	MSSQL_JDBC4(new DbMicrosoftSqlServerConnector(), new DbMicrosoftSqlServer2000Quirks()),
	MYSQL(new DbMysqlConnector(), new DbMysqlQuirks()),
	POSTGRE_SQL(new DbPostgreSqlConnector(), new DbDefaultQuirks());

	private final IDbConnector connector;
	private IDbServerQuirks quirks;

	private DbServerType(IDbConnector connector, IDbServerQuirks quirks) {

		this.connector = connector;
		this.quirks = quirks;
	}

	@Override
	public IDbConnector getConnector() {

		return connector;
	}

	@Override
	public IDbServerQuirks getQuirks() {

		return quirks;
	}
}
