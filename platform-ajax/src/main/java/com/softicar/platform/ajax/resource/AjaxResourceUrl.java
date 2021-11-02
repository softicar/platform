package com.softicar.platform.ajax.resource;

import com.softicar.platform.common.io.resource.ResourceUrl;
import com.softicar.platform.common.io.resource.hash.ResourceHash;
import javax.servlet.http.HttpServletRequest;

public class AjaxResourceUrl extends ResourceUrl {

	private static final String ID_PARAMETER_NAME = "resourceId";
	private static final String HASH_PARAMETER_NAME = "resourceHash";

	public AjaxResourceUrl(int resourceId) {

		super(String.format("?%s=%d", ID_PARAMETER_NAME, resourceId));
	}

	public AjaxResourceUrl(ResourceHash resourceHash) {

		super(String.format("?%s=%s", HASH_PARAMETER_NAME, resourceHash));
	}

	public static String getIdParameterName() {

		return ID_PARAMETER_NAME;
	}

	public static String getHashParameterName() {

		return HASH_PARAMETER_NAME;
	}

	public static boolean haveResourceParameter(HttpServletRequest request) {

		return request.getParameter(ID_PARAMETER_NAME) != null || request.getParameter(HASH_PARAMETER_NAME) != null;
	}
}
