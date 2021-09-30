package com.softicar.platform.ajax.document;

import com.softicar.platform.ajax.request.IAjaxRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AjaxDocumentParameters implements IAjaxDocumentParameters {

	private final Map<String, String[]> parameterMap;

	public AjaxDocumentParameters(IAjaxRequest request) {

		this(request.getParameterMap());
	}

	public AjaxDocumentParameters(Map<String, String[]> parameterMap) {

		this.parameterMap = parameterMap;
	}

	@Override
	public List<String> getParameterValueList(String parameterName) {

		String[] values = parameterMap.getOrDefault(parameterName, null);
		return values != null? Arrays.asList(values) : Collections.emptyList();
	}
}
