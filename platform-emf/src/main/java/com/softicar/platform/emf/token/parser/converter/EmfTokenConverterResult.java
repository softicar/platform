package com.softicar.platform.emf.token.parser.converter;

import com.softicar.platform.common.core.i18n.IDisplayString;

public class EmfTokenConverterResult<V> {

	private final boolean okay;
	private V value;
	private Exception failureCause;
	private IDisplayString failureReason;

	private EmfTokenConverterResult(boolean okay) {

		this.okay = okay;
		this.value = null;
		this.failureCause = null;
		this.failureReason = null;
	}

	public static <V> EmfTokenConverterResult<V> failed(Exception failureCause, IDisplayString failureReason) {

		return EmfTokenConverterResult.<V> failed(failureReason).setFailureCause(failureCause);
	}

	public static <V> EmfTokenConverterResult<V> failed(IDisplayString failureReason) {

		return new EmfTokenConverterResult<V>(false).setFailureReason(failureReason);
	}

	public static <V> EmfTokenConverterResult<V> okay(V value) {

		return new EmfTokenConverterResult<V>(true).setValue(value);
	}

	public boolean isOkay() {

		return okay;
	}

	public V getValue() {

		return value;
	}

	public Exception getFailureCause() {

		return failureCause;
	}

	public IDisplayString getFailureReason() {

		return failureReason;
	}

	private EmfTokenConverterResult<V> setValue(V value) {

		this.value = value;
		return this;
	}

	private EmfTokenConverterResult<V> setFailureCause(Exception failureCause) {

		this.failureCause = failureCause;
		return this;
	}

	private EmfTokenConverterResult<V> setFailureReason(IDisplayString failureReason) {

		this.failureReason = failureReason;
		return this;
	}
}
