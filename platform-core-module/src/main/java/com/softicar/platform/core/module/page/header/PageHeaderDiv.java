package com.softicar.platform.core.module.page.header;

import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.page.header.notification.PageHeaderMaintenanceNotificationArea;
import com.softicar.platform.core.module.page.navigation.PageNavigationController;
import com.softicar.platform.core.module.page.navigation.entry.PageNavigationEntry;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.text.DomTextNode;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.page.IEmfPage;

public class PageHeaderDiv<I extends IEmfModuleInstance<I>> extends DomDiv {

	private final IEmfPage<I> page;
	private final I moduleInstance;
	private final IEmfModule<I> module;

	public PageHeaderDiv(PageNavigationEntry<I> linkEntry, INullaryVoidFunction navigationToggleFunction, PageNavigationController controller) {

		this.page = linkEntry.getPage();
		this.moduleInstance = linkEntry.getModuleInstance();
		this.module = CastUtils.cast(moduleInstance.getModuleOrThrow());
		setCssClass(CoreCssClasses.PAGE_HEADER_DIV);
		appendChild(new PageHeaderNavigationToggleButton(navigationToggleFunction));
		appendChild(new PagePathDiv());
		appendChild(new PageHeaderMaintenanceNotificationArea());
		appendChild(new PageHeaderUserPopoverButton(controller));
	}

	private class PagePathDiv extends DomDiv {

		public PagePathDiv() {

			setCssClass(CoreCssClasses.PAGE_HEADER_PATH_DIV);

			EmfPagePath pagePath = page.getPagePath(module.getDefaultPagePath(moduleInstance));
			for (String segment: pagePath.getSegments()) {
				appendChild(DomTextNode.create(segment));
				appendChild(new Separator());
			}
			appendChild(new PageName(page.getTitle(moduleInstance).toString()));
		}
	}

	private class Separator extends DomDiv {

		public Separator() {

			setCssClass(CoreCssClasses.PAGE_HEADER_SEPARATOR);
			appendText(">>");
		}
	}

	private class PageName extends DomDiv {

		public PageName(String name) {

			setCssClass(CoreCssClasses.PAGE_HEADER_PAGE_NAME);
			appendText(name);
		}
	}
}
