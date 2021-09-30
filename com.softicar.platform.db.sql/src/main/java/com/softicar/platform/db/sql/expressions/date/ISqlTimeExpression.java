package com.softicar.platform.db.sql.expressions.date;

import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.common.date.Time;
import com.softicar.platform.db.sql.expressions.base.ISqlAggregatableExpression;

/**
 * Interface of all SQL expressions for type {@link DayTime}.
 *
 * @author Oliver Richers
 */
public interface ISqlTimeExpression<SELF, BOOL> extends ISqlAggregatableExpression<SELF, BOOL, Time> {

	// nothing to add yet
}
