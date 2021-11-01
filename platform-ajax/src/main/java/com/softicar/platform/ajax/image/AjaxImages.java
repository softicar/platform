package com.softicar.platform.ajax.image;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.dom.resource.supplier.DomResourceSupplierProxyFactory;

/**
 * Provides icons for the AJAX framework.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
@ResourceSupplierContainer
public interface AjaxImages {

	IResourceSupplierFactory FACTORY = new DomResourceSupplierProxyFactory(AjaxImages.class);

	IResourceSupplier EMBLEM_AUTO_COMPLETE_GENERIC = FACTORY.create("emblem-auto-complete-generic.svg");
	IResourceSupplier EMBLEM_AUTO_COMPLETE_NOT_OKAY = FACTORY.create("emblem-auto-complete-not-okay.svg");
	IResourceSupplier EMBLEM_AUTO_COMPLETE_VALUE_AMBIGUOUS = FACTORY.create("emblem-auto-complete-value-ambiguous.svg");
	IResourceSupplier EMBLEM_AUTO_COMPLETE_VALUE_ILLEGAL = FACTORY.create("emblem-auto-complete-value-illegal.svg");
	IResourceSupplier EMBLEM_AUTO_COMPLETE_VALUE_MISSING = FACTORY.create("emblem-auto-complete-value-missing.svg");
	IResourceSupplier EMBLEM_AUTO_COMPLETE_VALUE_VALID = FACTORY.create("emblem-auto-complete-value-valid.svg");
}
