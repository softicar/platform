package com.softicar.platform.dom.elements.testing.node.tester;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.dom.elements.input.auto.DomAutoCompleteInput;
import com.softicar.platform.dom.elements.testing.engine.IDomTestExecutionEngine;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEventHandler;

public class DomAutoCompleteTester<T> extends AbstractDomNodeTester<DomAutoCompleteInput<?>> {

	private final DomAutoCompleteInput<?> select;

	public DomAutoCompleteTester(IDomTestExecutionEngine engine, DomAutoCompleteInput<?> select) {

		super(engine, select);
		this.select = select;
	}

	public DomAutoCompleteTester<T> selectValue(T value) {

		select.setValue(CastUtils.cast(value));

		if (node instanceof IDomEventHandler) {
			sendEvent(DomEventType.CHANGE);
		}

		return this;
	}
}
