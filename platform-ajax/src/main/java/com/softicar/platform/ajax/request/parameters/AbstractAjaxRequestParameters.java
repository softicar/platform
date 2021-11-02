package com.softicar.platform.ajax.request.parameters;

import com.softicar.platform.common.container.map.MapFactory;
import java.util.Map;

public abstract class AbstractAjaxRequestParameters implements IAjaxRequestParameters {

	private Map<String, String[]> map = MapFactory.createTreeMap();

	protected void setMap(Map<String, String[]> map) {

		this.map = map;
	}

	@Override
	public Map<String, String[]> getMap() {

		return map;
	}

	@Override
	public String[] get(String key) {

		return getMap().get(key);
	}
}
