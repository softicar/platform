package com.softicar.platform.core.module.localization;

import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.object.table.EmfObjectTable;

public class AGLocalizationTable extends EmfObjectTable<AGLocalization, SystemModuleInstance> {

	public AGLocalizationTable(IDbObjectTableBuilder<AGLocalization> builder) {

		super(builder);
	}
}
