package com.softicar.platform.dom.document;

import com.softicar.platform.dom.engine.DomNullEngine;
import com.softicar.platform.dom.engine.IDomEngine;

/**
 * Main implementation of {@link IDomDocument}.
 *
 * @author Oliver Richers
 */
public class DomDocument extends AbstractDomDocument {

	private final IDomEngine engine;

	public DomDocument() {

		this(DomNullEngine.get());
	}

	public DomDocument(IDomEngine engine) {

		this.engine = engine;
	}

	@Override
	public IDomEngine getEngine() {

		return engine;
	}
}
