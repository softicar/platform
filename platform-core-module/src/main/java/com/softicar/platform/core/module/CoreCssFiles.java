package com.softicar.platform.core.module;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.common.string.charset.Charsets;
import com.softicar.platform.dom.resource.supplier.DomResourceSupplierProxyFactory;

@ResourceSupplierContainer
public interface CoreCssFiles {

	IResourceSupplierFactory FACTORY = new DomResourceSupplierProxyFactory(CoreCssFiles.class, Charsets.UTF8);

	IResourceSupplier STORED_FILE_STYLE = FACTORY.create("stored-file-style.css");
	IResourceSupplier SYSTEM_EVENT_STYLE = FACTORY.create("system-event-style.css");
	IResourceSupplier USER_PASSWORD_QUALITY_STYLE = FACTORY.create("user-password-quality-style.css");
}
