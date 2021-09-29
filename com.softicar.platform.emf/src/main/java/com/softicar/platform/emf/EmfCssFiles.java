package com.softicar.platform.emf;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.dom.resource.supplier.DomResourceSupplierProxyFactory;

@ResourceSupplierContainer
public interface EmfCssFiles {

	IResourceSupplierFactory FACTORY = new DomResourceSupplierProxyFactory(EmfCssFiles.class, Charsets.UTF8);

	IResourceSupplier EMF_ATTRIBUTE_DISPLAY_STYLE = FACTORY.create("emf-attribute-display-style.css");
	IResourceSupplier EMF_COLUMN_HANDLER_STYLE = FACTORY.create("emf-column-handler-style.css");
	IResourceSupplier EMF_DATA_TABLE_CONFIGURATION_STYLE = FACTORY.create("emf-data-table-configuration-style.css");
	IResourceSupplier EMF_DATA_TABLE_STYLE = FACTORY.create("emf-data-table-style.css");
	IResourceSupplier EMF_DISPLAY_STYLE = FACTORY.create("emf-display-style.css");
	IResourceSupplier EMF_EXTERNAL_PAGE_STYLE = FACTORY.create("emf-external-page-style.css");
	IResourceSupplier EMF_FORM_SECTION_STYLE = FACTORY.create("emf-form-section-style.css");
	IResourceSupplier EMF_FORM_STYLE = FACTORY.create("emf-form-style.css");
	IResourceSupplier EMF_INPUT_STYLE = FACTORY.create("emf-input-style.css");
	IResourceSupplier EMF_LOG_FEED_STYLE = FACTORY.create("emf-log-feed-style.css");
	IResourceSupplier EMF_MANAGEMENT_STYLE = FACTORY.create("emf-management-style.css");
	IResourceSupplier EMF_PREFILTER_ROW_STYLE = FACTORY.create("emf-prefilter-row-style.css");
}
