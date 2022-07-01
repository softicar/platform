package com.softicar.platform.core.module.page;

import com.softicar.platform.ajax.document.IAjaxDocumentParameters;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.core.uuid.Uuids;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.start.page.StartPage;
import com.softicar.platform.emf.page.IEmfPage;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePointMissingException;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

class PageParameterParser {

	private static final Integer SYSTEM_MODULE_INSTANCE_ID = 0;
	private static final String PAGE_PARAMETER = "page";
	private final IAjaxDocumentParameters parameters;

	public PageParameterParser(IAjaxDocumentParameters parameters) {

		this.parameters = Objects.requireNonNull(parameters);
	}

	public static String getPageParameter() {

		return PAGE_PARAMETER;
	}

	public Integer getModuleInstanceId() {

		return Optional//
			.ofNullable(parameters.getParameterOrNull("moduleInstance", Integer::parseInt))
			.orElse(SYSTEM_MODULE_INSTANCE_ID);
	}

	/**
	 * This method tries to parse the "page" parameter from the Url.
	 * <p>
	 * Defaults to {@link StartPage} if no parameter is present.
	 *
	 * @return the {@link IEmfPage} that was determined (never <i>null</i>)
	 */
	public IEmfPage<?> getPage() {

		String pageUuidText = parameters.getParameterOrNull(PAGE_PARAMETER);
		if (pageUuidText != null) {
			Optional<UUID> pageUuid = Uuids.parseUuid(pageUuidText);
			if (pageUuid.isEmpty()) {
				throw new SofticarUserException(CoreI18n.ILLEGAL_UUID_FOR_REQUEST_PARAMETER_ARG1_ARG2.toDisplay(PAGE_PARAMETER, pageUuidText));
			}
			try {
				return EmfSourceCodeReferencePoints.getReferencePointOrThrow(pageUuid.get(), IEmfPage.class);
			} catch (EmfSourceCodeReferencePointMissingException exception) {
				DevNull.swallow(exception);
				return EmfSourceCodeReferencePoints.getReferencePoint(InvalidPageUrlPage.class);
			}
		} else {
			//FIXME This should maybe also manipulate the Url.
			return EmfSourceCodeReferencePoints.getReferencePoint(StartPage.class);
		}
	}
}
