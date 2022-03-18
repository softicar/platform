package com.softicar.platform.dom.elements.testing.node.tester;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEventHandler;
import com.softicar.platform.dom.input.auto.IDomAutoCompleteInput;

public class DomAutoCompleteTester<T> extends AbstractDomNodeTester<IDomAutoCompleteInput<?>> {

	private final IDomAutoCompleteInput<?> select;

	public DomAutoCompleteTester(IDomTestEngine engine, IDomAutoCompleteInput<?> select) {

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
