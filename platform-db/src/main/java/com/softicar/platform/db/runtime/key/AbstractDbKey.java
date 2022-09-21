package com.softicar.platform.db.runtime.key;

import com.softicar.platform.db.structure.key.DbKeyType;

public abstract class AbstractDbKey<R> implements IDbKey<R> {

	private final DbKeyType type;
	private final String name;

	public AbstractDbKey(DbKeyType type, String name) {

		this.type = type;
		this.name = name;
	}

	@Override
	public DbKeyType getType() {

		return type;
	}

	@Override
	public boolean isPrimaryKey() {

		return type == DbKeyType.PRIMARY;
	}

	@Override
	public boolean isUniqueKey() {

		return type == DbKeyType.PRIMARY || type == DbKeyType.UNIQUE;
	}

	@Override
	public String getName() {

		return name;
	}
}
