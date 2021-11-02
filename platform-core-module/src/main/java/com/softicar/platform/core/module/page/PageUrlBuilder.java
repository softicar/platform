package com.softicar.platform.core.module.page;

import com.softicar.platform.common.network.url.Url;
import com.softicar.platform.core.module.page.navigation.link.PageNavigationLink;
import com.softicar.platform.core.module.page.service.PageService;
import com.softicar.platform.core.module.web.service.WebServiceUrlBuilder;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import java.util.Objects;

public class PageUrlBuilder<I extends IEmfModuleInstance<I>> {

	private final IEmfPage<?> page;
	private final I moduleInstance;

	public PageUrlBuilder(PageNavigationLink<I> link) {

		this.page = Objects.requireNonNull(link.getPage());
		this.moduleInstance = Objects.requireNonNull(link.getModuleInstance());
	}

	public PageUrlBuilder(Class<? extends IEmfPage<I>> pageClass, I moduleInstance) {

		this.page = EmfSourceCodeReferencePoints.getReferencePoint(pageClass);
		this.moduleInstance = Objects.requireNonNull(moduleInstance);
	}

	public Url build() {

		return new WebServiceUrlBuilder(PageService.class)//
			.addParameter(PageParameterParser.getPageParameter(), page.getAnnotatedUuid().toString())
			.addParameter("moduleInstance", moduleInstance.getItemId().toString())
			.build();
	}
}
