package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

public class WebServiceIdFetcher {

	private static final String ID_PARAMETER_NAME = "id";

	public static String getIdParameterName() {

		return ID_PARAMETER_NAME;
	}

	public UUID getServiceIdOrThrow(HttpServletRequest request) {

		String serviceId = getServiceIdOrNullFromParameters(request);
		if (serviceId == null) {
			throw new SofticarDeveloperException("Service ID not specified.");
		} else {
			return UUID.fromString(serviceId);
		}
	}

	private String getServiceIdOrNullFromParameters(HttpServletRequest request) {

		return request.getParameter(ID_PARAMETER_NAME);
	}
}
