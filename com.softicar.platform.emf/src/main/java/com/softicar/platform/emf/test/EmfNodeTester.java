package com.softicar.platform.emf.test;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.dom.elements.testing.engine.IDomTestEngine;
import com.softicar.platform.dom.elements.testing.node.tester.DomNodeTester;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.IDomEventHandler;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfNodeTester extends DomNodeTester {

	public EmfNodeTester(IDomTestEngine engine, IDomNode node) {

		super(engine, node);
	}

	public EmfNodeTester(IDomTestEngine engine, DomNodeTester tester) {

		super(engine, tester.getNode());
	}

	public <R extends IEmfTableRow<R, ?>, V> void enterEmfInputValue(IEmfAttribute<R, V> attribute, V value) {

		enterEmfInputValue(attribute.getOriginalAttribute().getField().get(), value);
	}

	public <R extends IEmfTableRow<R, ?>, V> void enterEmfInputValue(IDbField<R, V> field, V value) {

		IEmfInput<V> input = getEmfInput(field);
		input.setValue(value);

		// send change event
		new EmfNodeTester(getEngine(), input)//
			.findNodes()
			.withInstanceOf(IDomEventHandler.class)
			.stream()
			.findAny()
			.ifPresent(node -> new EmfNodeTester(getEngine(), node).sendEvent(DomEventType.CHANGE));
	}

	public <R extends IEmfTableRow<R, ?>, V> V getEmfInputValue(IDbField<R, V> field) {

		return getEmfInput(field).getValueOrThrow();
	}

	public <R extends IEmfTableRow<R, ?>, V> IEmfInput<V> getEmfInput(IDbField<R, V> field) {

		IEmfInput<?> input = findNode(field)//
			.findNodes(IEmfInput.class)
			.assertSome()
			.get(0)
			.assertType(IEmfInput.class);
		return CastUtils.cast(input);
	}
}
