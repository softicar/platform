package com.softicar.platform.db.sql.operations;

import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuildable;
import com.softicar.platform.db.sql.statement.builder.clause.ISqlClauseBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SqlImploder implements ISqlClauseBuildable {

	private final List<ISqlClauseBuildable> buildables;
	private final String delimiter;
	private final String left;
	private final String right;

	public SqlImploder(String delimiter) {

		this(delimiter, "", "");
	}

	public SqlImploder(String delimiter, String left, String right) {

		this.buildables = new ArrayList<>();
		this.delimiter = delimiter;
		this.left = left;
		this.right = right;
	}

	public SqlImploder add(ISqlClauseBuildable buildable) {

		this.buildables.add(buildable);
		return this;
	}

	public SqlImploder addAll(Collection<? extends ISqlClauseBuildable> buildables) {

		this.buildables.addAll(buildables);
		return this;
	}

	@Override
	public void build(ISqlClauseBuilder builder) {

		builder.addText(left);
		boolean first = true;
		for (ISqlClauseBuildable buildable: buildables) {
			if (!first) {
				builder.addText(delimiter);
			}
			buildable.build(builder);
			first = false;
		}
		builder.addText(right);
	}
}
