package com.softicar.platform.db.sql;

import java.util.List;

public interface ISqlStatement {

	String getText();

	List<Object> getParameters();
}
