package com.softicar.platform.core.module.page;

import com.softicar.platform.ajax.document.AjaxDocument;
import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.request.AjaxRequest;
import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.network.url.Url;
import com.softicar.platform.core.module.ajax.page.EmfPageConnectionProfiler;
import com.softicar.platform.core.module.page.navigation.entry.PageNavigationEntry;
import com.softicar.platform.core.module.page.service.PageServiceFactory;
import com.softicar.platform.core.module.web.service.WebServiceUrlBuilder;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.page.IEmfPage;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class PageUrlBuilder<I extends IEmfModuleInstance<I>> {

	private final IEmfPage<?> page;
	private final I moduleInstance;
	private final Map<String, String> debuggingParameters;

	public PageUrlBuilder(PageNavigationEntry<I> linkEntry) {

		this.page = Objects.requireNonNull(linkEntry.getPage());
		this.moduleInstance = Objects.requireNonNull(linkEntry.getModuleInstance());
		this.debuggingParameters = extractDebuggingParameters();
	}

	public PageUrlBuilder(Class<? extends IEmfPage<I>> pageClass, I moduleInstance) {

		this.page = SourceCodeReferencePoints.getReferencePoint(pageClass);
		this.moduleInstance = Objects.requireNonNull(moduleInstance);
		this.debuggingParameters = Collections.emptyMap();
	}

	public Url build() {

		return new WebServiceUrlBuilder(PageServiceFactory.class)//
			.addParameter(PageParameterParser.getPageParameter(), page.getAnnotatedUuid().toString())
			.addParameter("moduleInstance", moduleInstance.getItemId().toString())
			.addParameters(debuggingParameters)
			.build();
	}

	private Map<String, String> extractDebuggingParameters() {

		var map = new TreeMap<String, String>();
		copyParameter(AjaxRequest.DEBUG_PARAMETER, map);
		copyParameter(AjaxRequest.VERBOSE_PARAMETER, map);
		copyParameter(EmfPageConnectionProfiler.PROFILER_PARAMETER, map);
		copyParameter(EmfPageConnectionProfiler.STACKTRACE_PARAMETER, map);
		return map;
	}

	private void copyParameter(String name, Map<String, String> map) {

		AjaxDocument//
			.getCurrentDocument()
			.map(IAjaxDocument::getParameters)
			.map(parameters -> parameters.getParameterOrNull(name))
			.ifPresent(value -> map.put(name, value));
	}
}
