package com.softicar.platform.dom.style;

import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.dom.document.CurrentDomDocument;

/**
 * Default implementation of {@link ICssClass}.
 *
 * @author Oliver Richers
 */
public class CssClass implements ICssClass {

	private final String name;
	private final IResourceSupplier resourceSupplier;

	public CssClass(String name) {

		this.name = name;
		this.resourceSupplier = null;
	}

	public CssClass(String name, IResourceSupplier resourceSupplier) {

		this.name = name;
		this.resourceSupplier = resourceSupplier;
	}

	@Override
	public String getName() {

		return name;
	}

	@Override
	public void beforeUse() {

		if (resourceSupplier != null) {
			CurrentDomDocument.get().getEngine().registerCssResourceLink(resourceSupplier.getResource());
		}
	}

	@Override
	public String toString() {

		return name;
	}
}
