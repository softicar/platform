package com.softicar.platform.core.module.page.navigation;

import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplierFactory;
import com.softicar.platform.dom.resource.supplier.DomResourceSupplierProxyFactory;

@ResourceSupplierContainer
public interface PageNavigationResources {

	IResourceSupplierFactory FACTORY = new DomResourceSupplierProxyFactory(PageNavigationResources.class);

	IResourceSupplier PAGE_NAVIGATION_OPEN_IN_NEW_TAB_ICON = FACTORY.create("page-navigation-open-in-new-tab-icon.svg");
	IResourceSupplier PAGE_NAVIGATION_STYLE = FACTORY.create("page-navigation-style.css");
	IResourceSupplier PAGE_NAVIGATION_TOGGLE_ICON = FACTORY.create("page-navigation-toggle-icon.svg");
}
