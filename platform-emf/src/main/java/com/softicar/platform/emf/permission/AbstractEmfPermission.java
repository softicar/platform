package com.softicar.platform.emf.permission;

import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.string.formatting.StackTraceFormatting;

public abstract class AbstractEmfPermission<E> implements IEmfPermission<E> {

	protected void logEvaluationException(Exception exception) {

		Log.fwarning("Caught an exception while determining permission ownership:\n%s", StackTraceFormatting.getStackTraceAsString(exception));
	}
}
