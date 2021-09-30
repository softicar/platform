package com.softicar.platform.db.sql.expressions.string;

import com.softicar.platform.db.sql.Sql;
import com.softicar.platform.db.sql.expressions.base.ISqlAggregatableExpression;
import com.softicar.platform.db.sql.operations.SqlOperations;
import com.softicar.platform.db.sql.type.SqlValueTypes;

public interface ISqlStringExpression<SELF, BOOL> extends ISqlAggregatableExpression<SELF, BOOL, String> {

	default SELF trim() {

		return wrap(SqlOperations.TRIM.create(this));
	}

	default SELF rtrim() {

		return wrap(SqlOperations.RTRIM.create(this));
	}

	default SELF ltrim() {

		return wrap(SqlOperations.LTRIM.create(this));
	}

	default SELF upper() {

		return wrap(SqlOperations.UPPER.create(this));
	}

	default SELF lower() {

		return wrap(SqlOperations.LOWER.create(this));
	}

	default SELF substring(int index) {

		return wrap(SqlOperations.SUBSTRING2.create(this, Sql.literal(index)));
	}

	default SELF substring(int index, int length) {

		return wrap(SqlOperations.SUBSTRING3.create(this, Sql.literal(index), Sql.literal(length)));
	}

	default SELF concat(String value) {

		return concat(Sql.literal(value));
	}

	default SELF concat(SqlStringExpression0 other) {

		return wrap(SqlOperations.CONCAT.create(this, other));
	}

	// -------------------- isLike and isNotLike -------------------- //

	default BOOL isLike(String value) {

		return isLike(Sql.literal(value));
	}

	default BOOL isNotLike(String value) {

		return isNotLike(Sql.literal(value));
	}

	default BOOL isLike(SqlStringExpression0 value) {

		return wrapBool(SqlOperations.IS_LIKE.create(SqlValueTypes.BOOLEAN, this, value));
	}

	default BOOL isNotLike(SqlStringExpression0 value) {

		return wrapBool(SqlOperations.IS_NOT_LIKE.create(SqlValueTypes.BOOLEAN, this, value));
	}

	// -------------------- isRegexp and isNotRegexp -------------------- //

	default BOOL isRegexp(String regexp) {

		return isRegexp(Sql.literal(regexp));
	}

	default BOOL isNotRegexp(String regexp) {

		return isNotRegexp(Sql.literal(regexp));
	}

	default BOOL isRegexp(SqlStringExpression0 regexp) {

		return wrapBool(SqlOperations.IS_REGEXP.create(SqlValueTypes.BOOLEAN, this, regexp));
	}

	default BOOL isNotRegexp(SqlStringExpression0 regexp) {

		return wrapBool(SqlOperations.IS_NOT_REGEXP.create(SqlValueTypes.BOOLEAN, this, regexp));
	}

	// -------------------- old methods for backwards-compatibility -------------------- //

	default BOOL like(String value) {

		return isLike(value);
	}

	default BOOL notLike(String value) {

		return isNotLike(value);
	}
}
