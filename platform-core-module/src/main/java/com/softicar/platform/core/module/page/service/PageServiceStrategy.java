package com.softicar.platform.core.module.page.service;

import com.softicar.platform.ajax.customization.AbstractAjaxStrategy;
import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.export.AjaxInMemoryExportBuffer;
import com.softicar.platform.ajax.export.IAjaxExportBuffer;
import com.softicar.platform.ajax.request.CurrentAjaxRequest;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.CorePermissions;
import com.softicar.platform.core.module.ajax.logging.AjaxLogging;
import com.softicar.platform.core.module.ajax.page.EmfPageConnectionProfiler;
import com.softicar.platform.core.module.ajax.session.SofticarAjaxSession;
import com.softicar.platform.core.module.user.configuration.table.UserSpecificTableConfigurationPersistenceApi;
import com.softicar.platform.core.module.user.preferences.UserSpecificDomPreferences;
import com.softicar.platform.dom.resource.set.CurrentDomResourceSet;
import com.softicar.platform.dom.resource.set.DomResourceSet;
import com.softicar.platform.dom.user.CurrentDomPreferences;
import com.softicar.platform.emf.persistence.CurrentEmfPersistenceApi;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class PageServiceStrategy extends AbstractAjaxStrategy {

	private static final int MAXIMUM_EXPORT_BUFFER_SIZE = 4 * 1024 * 1024;
	private final Optional<DomResourceSet> defaultTheme;

	public PageServiceStrategy() {

		this.defaultTheme = new PageServiceDefaultThemeLoader().load();
	}

	@Override
	public void buildDocument(IAjaxDocument document) {

		defaultTheme.ifPresent(CurrentDomResourceSet::set);
		CurrentDomPreferences.set(new UserSpecificDomPreferences());
		CurrentEmfPersistenceApi.set(new UserSpecificTableConfigurationPersistenceApi());

		new PageServiceDocumentBuilder(document).build();
	}

	@Override
	public void beforeDocumentRequestHandling(IAjaxDocument document, IAjaxRequest request) {

		CurrentAjaxRequest.set(document, request);

		EmfPageConnectionProfiler.installIfRequested(document.getParameters());
	}

	@Override
	public IAjaxExportBuffer createExportBuffer() {

		return new AjaxInMemoryExportBuffer(MAXIMUM_EXPORT_BUFFER_SIZE, PageServiceExportBuffer::new);
	}

	@Override
	public Optional<IResource> getFaviconResource() {

		return Optional.of(CoreImages.PAGE_ICON.getResource());
	}

	@Override
	public boolean isAdministrative(HttpSession session) {

		return SofticarAjaxSession//
			.getInstance(session)
			.map(ajaxSession -> CorePermissions.ADMINISTRATION.test(AGCoreModuleInstance.getInstance(), ajaxSession.getUser()))
			.orElse(false);
	}

	@Override
	public void logException(Throwable throwable, HttpServletRequest request) {

		throwable.printStackTrace();
		AjaxLogging.logException(throwable, request);
	}
}
