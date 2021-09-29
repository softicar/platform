package com.softicar.platform.db.runtime.select;

import com.softicar.platform.common.container.map.list.ListTreeMap;
import com.softicar.platform.db.core.table.IDbCoreTable;
import com.softicar.platform.db.runtime.query.IDbQueryColumn;
import com.softicar.platform.db.sql.token.ISqlToken;
import com.softicar.platform.db.sql.token.SqlKeyword;
import com.softicar.platform.db.sql.token.SqlSymbol;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class DbSqlSelect implements IDbSqlSelect {

	private final Set<IDbCoreTable> tables;
	private final ListTreeMap<String, Integer> physicalColumnIndexMap;
	private final DbSqlClause selectClause;
	private final DbSqlClause fromClause;
	private final DbSqlClause whereClause;
	private final DbSqlClause groupByClause;
	private final DbSqlClause havingClause;
	private final DbSqlClause orderByClause;
	private final DbSqlClause limitClause;
	private DbSqlClause activeClause;
	private int physicalColumnIndex;
	private boolean haveOn;
	private boolean straightJoin;

	public DbSqlSelect() {

		this.tables = new HashSet<>();
		this.physicalColumnIndexMap = new ListTreeMap<>();
		this.selectClause = new DbSqlClause();
		this.fromClause = new DbSqlClause();
		this.whereClause = new DbSqlClause();
		this.groupByClause = new DbSqlClause();
		this.havingClause = new DbSqlClause();
		this.orderByClause = new DbSqlClause();
		this.limitClause = new DbSqlClause();
		this.activeClause = null;
		this.physicalColumnIndex = 1;
		this.haveOn = false;
		this.straightJoin = false;
	}

	@Override
	public Set<IDbCoreTable> getTables() {

		return tables;
	}

	@Override
	public boolean isActiveColumn(String columnName) {

		return !getPhysicalColumnIndexList(columnName).isEmpty();
	}

	@Override
	public List<Integer> getPhysicalColumnIndexList(String columnName) {

		return physicalColumnIndexMap.getList(columnName);
	}

	@Override
	public List<ISqlToken> getTokens() {

		List<ISqlToken> tokens = new ArrayList<>();
		tokens.addAll(selectClause.getTokens());
		tokens.addAll(fromClause.getTokens());
		tokens.addAll(whereClause.getTokens());
		tokens.addAll(groupByClause.getTokens());
		tokens.addAll(havingClause.getTokens());
		tokens.addAll(orderByClause.getTokens());
		tokens.addAll(limitClause.getTokens());
		return tokens;
	}

	@Override
	public List<Object> getParameters() {

		List<Object> parameters = new ArrayList<>();
		parameters.addAll(selectClause.getParameters());
		parameters.addAll(fromClause.getParameters());
		parameters.addAll(whereClause.getParameters());
		parameters.addAll(groupByClause.getParameters());
		parameters.addAll(havingClause.getParameters());
		parameters.addAll(orderByClause.getParameters());
		parameters.addAll(limitClause.getParameters());
		return parameters;
	}

	@Override
	public IDbSqlClause getSelectClause() {

		return selectClause;
	}

	@Override
	public IDbSqlClause getFromClause() {

		return fromClause;
	}

	@Override
	public IDbSqlClause getWhereClause() {

		return whereClause;
	}

	@Override
	public IDbSqlClause getGroupByClause() {

		return groupByClause;
	}

	@Override
	public IDbSqlClause getHavingClause() {

		return havingClause;
	}

	@Override
	public IDbSqlClause getOrderByClause() {

		return orderByClause;
	}

	@Override
	public IDbSqlClause getLimitClause() {

		return limitClause;
	}

	public void addSubSelect(DbSqlSelect subSelect) {

		tables.addAll(subSelect.getTables());
		activeClause.addTokens(subSelect.getTokens());
		activeClause.addParameters(subSelect.getParameters());
	}

	public void addToken(ISqlToken token) {

		activeClause.addToken(token);
	}

	public void addParameter(Object parameter) {

		activeClause.addParameter(parameter);
	}

	public void SELECT() {

		if (selectClause.isEmpty()) {
			selectClause.addToken(SqlKeyword.SELECT);
			if (straightJoin) {
				selectClause.addToken(SqlKeyword.STRAIGHT_JOIN);
			}
		} else {
			selectClause.addToken(SqlSymbol.COMMA);
		}

		this.activeClause = selectClause;
	}

	public void SELECT(IDbQueryColumn<?, ?> column) {

		SELECT();
		this.physicalColumnIndexMap.addToList(column.getName(), physicalColumnIndex);
		this.physicalColumnIndex += column.getPhysicalColumnCount();
	}

	public void FROM() {

		if (fromClause.isEmpty()) {
			fromClause.addToken(SqlKeyword.FROM);
		} else {
			throw new IllegalStateException("Tried to add multiple FROM clauses to a single SELECT.");
		}

		this.activeClause = fromClause;
	}

	public void JOIN(SqlKeyword joinType) {

		if (!fromClause.isEmpty()) {
			if (joinType != null) {
				fromClause.addToken(joinType);
				fromClause.addToken(SqlKeyword.JOIN);
			} else {
				fromClause.addToken(SqlKeyword.JOIN);
			}
		} else {
			throw new IllegalStateException("Tried to start JOIN statement without preceding FROM statement.");
		}

		this.activeClause = fromClause;
		this.haveOn = false;
	}

	public void ON() {

		if (activeClause == fromClause) {
			if (!haveOn) {
				fromClause.addToken(SqlKeyword.ON);
			} else {
				fromClause.addToken(SqlKeyword.AND);
			}
		} else {
			throw new IllegalStateException("Tried to add ON condition without preceding JOIN statement.");
		}

		this.haveOn = true;
	}

	public void WHERE() {

		if (whereClause.isEmpty()) {
			whereClause.addToken(SqlKeyword.WHERE);
		} else {
			whereClause.addToken(SqlKeyword.AND);
		}

		this.activeClause = whereClause;
	}

	public void GROUP_BY() {

		if (groupByClause.isEmpty()) {
			groupByClause.addToken(SqlKeyword.GROUP);
			groupByClause.addToken(SqlKeyword.BY);
		} else {
			groupByClause.addToken(SqlSymbol.COMMA);
		}

		this.activeClause = groupByClause;
	}

	public void HAVING() {

		if (havingClause.isEmpty()) {
			havingClause.addToken(SqlKeyword.HAVING);
		} else {
			havingClause.addToken(SqlKeyword.AND);
		}

		this.activeClause = havingClause;
	}

	public void ORDER_BY() {

		if (orderByClause.isEmpty()) {
			orderByClause.addToken(SqlKeyword.ORDER);
			orderByClause.addToken(SqlKeyword.BY);
		} else {
			orderByClause.addToken(SqlSymbol.COMMA);
		}

		this.activeClause = orderByClause;
	}

	public void LIMIT() {

		if (limitClause.isEmpty()) {
			limitClause.addToken(SqlKeyword.LIMIT);
		} else {
			throw new IllegalStateException("Tried to add multiple LIMIT clauses to a single SELECT.");
		}

		this.activeClause = limitClause;
	}

	protected void addTable(IDbCoreTable table) {

		tables.add(table);
	}

	protected void setStraightJoin(boolean straightJoin) {

		this.straightJoin = straightJoin;
	}
}
