package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class EmfTestBusinessPartnerTable extends EmfObjectTable<EmfTestBusinessPartner, EmfTestBusinessUnitModuleInstance> {

	public EmfTestBusinessPartnerTable(IDbObjectTableBuilder<EmfTestBusinessPartner> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<EmfTestBusinessPartner, Integer, EmfTestBusinessUnitModuleInstance> configuration) {

		configuration.setScopeField(EmfTestBusinessPartner.BUSINESS_UNIT_MODULE_INSTANCE);
		configuration.setBusinessKey(EmfTestBusinessPartner.UK_BUSINESS_UNIT_MODULE_INSTANCE_NAME);
	}
}
