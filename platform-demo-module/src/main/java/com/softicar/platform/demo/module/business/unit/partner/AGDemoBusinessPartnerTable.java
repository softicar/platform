package com.softicar.platform.demo.module.business.unit.partner;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.demo.module.business.unit.AGDemoBusinessUnitModuleInstance;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGDemoBusinessPartnerTable extends EmfObjectTable<AGDemoBusinessPartner, AGDemoBusinessUnitModuleInstance> {

	public AGDemoBusinessPartnerTable(IDbObjectTableBuilder<AGDemoBusinessPartner> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGDemoBusinessPartner, Integer, AGDemoBusinessUnitModuleInstance> configuration) {

		configuration.setScopeField(AGDemoBusinessPartner.MODULE_INSTANCE);
		configuration.setBusinessKey(AGDemoBusinessPartner.UK_MODULE_INSTANCE_VAT_ID);
	}
}
