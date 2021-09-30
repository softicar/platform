package com.softicar.platform.db.core.dbms.mysql.process;

import com.softicar.platform.common.container.matrix.AsciiTable;
import com.softicar.platform.db.core.DbResultSet;
import com.softicar.platform.db.core.dbms.mysql.DbMysqlStatements;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySqlProcessList {

	private final List<MySqlProcess> processes;

	public MySqlProcessList() {

		this.processes = new ArrayList<>();

		try (DbResultSet resultSet = DbMysqlStatements.showProcessList()) {
			while (resultSet.next()) {
				processes.add(new MySqlProcess(resultSet));
			}
		}
	}

	public List<MySqlProcess> getProcesses() {

		return processes;
	}

	@Override
	public String toString() {

		AsciiTable table = new AsciiTable("Id", "User", "Host", "Database", "Command", "Time", "State", "Info");
		for (MySqlProcess process: processes) {
			table.add(process.getId());
			table.add(process.getUser());
			table.add(process.getHost());
			table.add(process.getDatabase());
			table.add(process.getCommand());
			table.add(process.getTime());
			table.add(process.getState());
			table.add(Optional.ofNullable(process.getInfo()).map(info -> info.replace('\n', ' ')).orElse(""));
		}
		return table.toString();
	}
}
