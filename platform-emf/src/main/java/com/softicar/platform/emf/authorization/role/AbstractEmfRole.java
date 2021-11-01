package com.softicar.platform.emf.authorization.role;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;

public abstract class AbstractEmfRole<E> implements IEmfRole<E> {

	protected void logEvaluationException(Exception exception) {

		Log.fwarning("Caught an exception while determining role membership:\n%s", StackTraceFormatting.getStackTraceAsString(exception));
	}
}
