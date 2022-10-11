package com.softicar.platform.core.module.page;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.network.url.Url;
import com.softicar.platform.core.module.page.navigation.entry.PageNavigationEntry;
import com.softicar.platform.core.module.page.service.PageServiceFactory;
import com.softicar.platform.core.module.web.service.WebServiceUrlBuilder;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.page.IEmfPage;
import java.util.Objects;

public class PageUrlBuilder<I extends IEmfModuleInstance<I>> {

	private final IEmfPage<?> page;
	private final I moduleInstance;

	public PageUrlBuilder(PageNavigationEntry<I> linkEntry) {

		this.page = Objects.requireNonNull(linkEntry.getPage());
		this.moduleInstance = Objects.requireNonNull(linkEntry.getModuleInstance());
	}

	public PageUrlBuilder(Class<? extends IEmfPage<I>> pageClass, I moduleInstance) {

		this.page = SourceCodeReferencePoints.getReferencePoint(pageClass);
		this.moduleInstance = Objects.requireNonNull(moduleInstance);
	}

	public Url build() {

		return new WebServiceUrlBuilder(PageServiceFactory.class)//
			.addParameter(PageParameterParser.getPageParameter(), page.getAnnotatedUuid().toString())
			.addParameter("moduleInstance", moduleInstance.getItemId().toString())
			.build();
	}
}
