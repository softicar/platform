package com.softicar.platform.db.core.dbms.mysql.process;

import com.softicar.platform.db.core.DbResultSet;

public class MySqlProcess {

	private final Integer id;
	private final String user;
	private final String host;
	private final String database;
	private final String command;
	private final Integer time;
	private final String state;
	private final String info;

	public MySqlProcess(DbResultSet resultSet) {

		this.id = resultSet.getInteger("Id");
		this.user = resultSet.getString("User");
		this.host = resultSet.getString("Host");
		this.database = resultSet.getString("Db");
		this.command = resultSet.getString("Command");
		this.time = resultSet.getInteger("Time");
		this.state = resultSet.getString("State");
		this.info = resultSet.getString("Info");
	}

	public Integer getId() {

		return id;
	}

	public String getUser() {

		return user;
	}

	public String getHost() {

		return host;
	}

	public String getDatabase() {

		return database;
	}

	public String getCommand() {

		return command;
	}

	public Integer getTime() {

		return time;
	}

	public String getState() {

		return state;
	}

	public String getInfo() {

		return info;
	}
}
