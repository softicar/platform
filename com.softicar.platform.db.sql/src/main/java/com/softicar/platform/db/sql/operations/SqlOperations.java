package com.softicar.platform.db.sql.operations;

import com.softicar.platform.db.core.connection.quirks.IDbServerQuirks;
import com.softicar.platform.db.sql.expressions.builder.SqlBinaryFunctionBuilder;
import com.softicar.platform.db.sql.expressions.builder.SqlBinaryOperatorBuilder;
import com.softicar.platform.db.sql.expressions.builder.SqlCastBuilder;
import com.softicar.platform.db.sql.expressions.builder.SqlNoOperationBuilder;
import com.softicar.platform.db.sql.expressions.builder.SqlNullaryFunctionBuilder;
import com.softicar.platform.db.sql.expressions.builder.SqlParameterizedCastBuilder;
import com.softicar.platform.db.sql.expressions.builder.SqlPrefixOperatorBuilder;
import com.softicar.platform.db.sql.expressions.builder.SqlSuffixOperatorBuilder;
import com.softicar.platform.db.sql.expressions.builder.SqlTrinaryFunctionBuilder;
import com.softicar.platform.db.sql.expressions.builder.SqlUnaryFunctionBuilder;

/**
 * List of all allowed operations.
 *
 * @author Oliver Richers
 */
public interface SqlOperations {

	SqlNoOperationBuilder NOP = new SqlNoOperationBuilder();

	SqlNullaryFunctionBuilder NOW = new SqlNullaryFunctionBuilder("NOW()");
	SqlNullaryFunctionBuilder COUNT_ALL = new SqlNullaryFunctionBuilder("COUNT(*)");

	SqlCastBuilder CAST_CHAR = new SqlCastBuilder("CHAR");
	SqlCastBuilder CAST_DATE = new SqlCastBuilder("DATE");
	SqlCastBuilder CAST_DATETIME = new SqlCastBuilder("DATETIME");
	SqlCastBuilder CAST_DECIMAL = new SqlCastBuilder("DECIMAL");
	SqlCastBuilder CAST_SIGNED = new SqlCastBuilder(IDbServerQuirks::getSignedIntegerType);

	SqlParameterizedCastBuilder CAST_DECIMAL_TO = new SqlParameterizedCastBuilder("DECIMAL");

	SqlPrefixOperatorBuilder NOT = new SqlPrefixOperatorBuilder("NOT ");
	SqlPrefixOperatorBuilder NEGATE = new SqlPrefixOperatorBuilder("-");

	SqlSuffixOperatorBuilder IS_NULL = new SqlSuffixOperatorBuilder(" IS NULL");
	SqlSuffixOperatorBuilder IS_NOT_NULL = new SqlSuffixOperatorBuilder(" IS NOT NULL");

	SqlBinaryOperatorBuilder AND = new SqlBinaryOperatorBuilder(" AND ");
	SqlBinaryOperatorBuilder OR = new SqlBinaryOperatorBuilder(" OR ");

	SqlBinaryOperatorBuilder BIT_AND = new SqlBinaryOperatorBuilder(" & ");
	SqlBinaryOperatorBuilder BIT_INVERSE = new SqlBinaryOperatorBuilder(" ~ ");
	SqlBinaryOperatorBuilder BIT_OR = new SqlBinaryOperatorBuilder(" | ");
	SqlBinaryOperatorBuilder BIT_XOR = new SqlBinaryOperatorBuilder(" ^ ");

	SqlBinaryOperatorBuilder IS_EQUAL = new SqlBinaryOperatorBuilder(" = ");
	SqlBinaryOperatorBuilder IS_LESS = new SqlBinaryOperatorBuilder(" < ");
	SqlBinaryOperatorBuilder IS_GREATER = new SqlBinaryOperatorBuilder(" > ");
	SqlBinaryOperatorBuilder IS_NOT_EQUAL = new SqlBinaryOperatorBuilder(" != ");
	SqlBinaryOperatorBuilder IS_LESS_EQUAL = new SqlBinaryOperatorBuilder(" <= ");
	SqlBinaryOperatorBuilder IS_GREATER_EQUAL = new SqlBinaryOperatorBuilder(" >= ");

	SqlBinaryOperatorBuilder IS_LIKE = new SqlBinaryOperatorBuilder(" LIKE ");
	SqlBinaryOperatorBuilder IS_NOT_LIKE = new SqlBinaryOperatorBuilder(" NOT LIKE ");
	SqlBinaryOperatorBuilder IS_REGEXP = new SqlBinaryOperatorBuilder(" REGEXP ");
	SqlBinaryOperatorBuilder IS_NOT_REGEXP = new SqlBinaryOperatorBuilder(" NOT REGEXP ");

	SqlBinaryOperatorBuilder PLUS = new SqlBinaryOperatorBuilder(" + ");
	SqlBinaryOperatorBuilder MINUS = new SqlBinaryOperatorBuilder(" - ");
	SqlBinaryOperatorBuilder TIMES = new SqlBinaryOperatorBuilder(" * ");
	SqlBinaryOperatorBuilder MODULO = new SqlBinaryOperatorBuilder(" % ");
	SqlBinaryOperatorBuilder DECIMAL_DIVIDED = new SqlBinaryOperatorBuilder(" / ");
	SqlBinaryOperatorBuilder INTEGRAL_DIVIDED = new SqlBinaryOperatorBuilder(" DIV ");

	SqlUnaryFunctionBuilder COUNT = new SqlUnaryFunctionBuilder("COUNT");
	SqlUnaryFunctionBuilder SUM = new SqlUnaryFunctionBuilder("SUM");
	SqlUnaryFunctionBuilder MAX = new SqlUnaryFunctionBuilder("MAX");
	SqlUnaryFunctionBuilder MIN = new SqlUnaryFunctionBuilder("MIN");

	SqlUnaryFunctionBuilder ABS = new SqlUnaryFunctionBuilder("ABS");
	SqlUnaryFunctionBuilder CEIL = new SqlUnaryFunctionBuilder("CEIL");
	SqlUnaryFunctionBuilder FLOOR = new SqlUnaryFunctionBuilder("FLOOR");
	SqlUnaryFunctionBuilder ROUND = new SqlUnaryFunctionBuilder("ROUND");

	SqlUnaryFunctionBuilder TRIM = new SqlUnaryFunctionBuilder("TRIM");
	SqlUnaryFunctionBuilder LTRIM = new SqlUnaryFunctionBuilder("LTRIM");
	SqlUnaryFunctionBuilder RTRIM = new SqlUnaryFunctionBuilder("RTRIM");
	SqlUnaryFunctionBuilder UPPER = new SqlUnaryFunctionBuilder("UPPER");
	SqlUnaryFunctionBuilder LOWER = new SqlUnaryFunctionBuilder("LOWER");
	SqlUnaryFunctionBuilder LENGTH = new SqlUnaryFunctionBuilder("LENGTH");

	SqlBinaryFunctionBuilder CONCAT = new SqlBinaryFunctionBuilder("CONCAT");
	SqlBinaryFunctionBuilder ROUND_TO = new SqlBinaryFunctionBuilder("ROUND");
	SqlBinaryFunctionBuilder SUBSTRING2 = new SqlBinaryFunctionBuilder("SUBSTRING");

	SqlTrinaryFunctionBuilder SUBSTRING3 = new SqlTrinaryFunctionBuilder("SUBSTRING");
}
