package com.softicar.platform.emf.data.table.column.settings;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import java.util.HashMap;
import java.util.Map;

public class EmfDataTableColumnSettingsMap<R> {

	private final Map<IDataTableColumn<R, ?>, EmfDataTableColumnSettings> map;

	public EmfDataTableColumnSettingsMap() {

		this.map = new HashMap<>();
	}

	public EmfDataTableColumnSettings getSettings(IDataTableColumn<R, ?> column) {

		EmfDataTableColumnSettings settings = map.get(column);
		if (settings == null) {
			map.put(column, settings = new EmfDataTableColumnSettings());
		}
		return settings;
	}
}
