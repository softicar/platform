package com.softicar.platform.core.module.locale;

import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.object.table.EmfObjectTable;

public class AGLocalizationPresetTable extends EmfObjectTable<AGLocalizationPreset, SystemModuleInstance> {

	public AGLocalizationPresetTable(IDbObjectTableBuilder<AGLocalizationPreset> builder) {

		super(builder);
	}
}
