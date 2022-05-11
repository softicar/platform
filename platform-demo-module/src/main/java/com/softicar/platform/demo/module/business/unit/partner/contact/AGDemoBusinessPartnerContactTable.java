package com.softicar.platform.demo.module.business.unit.partner.contact;

import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.demo.module.business.unit.partner.AGDemoBusinessPartner;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGDemoBusinessPartnerContactTable extends EmfObjectTable<AGDemoBusinessPartnerContact, AGDemoBusinessPartner> {

	public AGDemoBusinessPartnerContactTable(IDbObjectTableBuilder<AGDemoBusinessPartnerContact> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGDemoBusinessPartnerContact, Integer, AGDemoBusinessPartner> configuration) {

		configuration.setScopeField(AGDemoBusinessPartnerContact.BUSINESS_PARTNER);
		configuration.setBusinessKey(AGDemoBusinessPartnerContact.UK_BUSINESS_PARTNER_EMPLOYEE_ID);
	}
}
