package com.softicar.platform.common.container.data.table;

/**
 * List all value filer operators.
 *
 * @author Oliver Richers
 */
public enum DataTableValueFilterOperator {

	EQUAL("="),
	NOT_EQUAL("!="),

	GREATER(">"),
	GREATER_EQUAL(">="),

	LESS("<"),
	LESS_EQUAL("<=");

	private final String operatorSymbol;

	private DataTableValueFilterOperator(String operatorSymbol) {

		this.operatorSymbol = operatorSymbol;
	}

	public String getOperatorSymbol() {

		return operatorSymbol;
	}
}
