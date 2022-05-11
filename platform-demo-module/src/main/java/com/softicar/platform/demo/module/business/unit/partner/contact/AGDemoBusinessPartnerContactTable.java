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

//	@Override
//	public void customizeAttributeProperties(IEmfAttributeList<AGDemoBusinessPartnerContact> attributes) {
//
//		attributes//
//			.editAttribute(AGDemoBusinessPartnerContact.FIRST_NAME)
//			.setPredicateMandatory(EmfPredicates.always());
//		attributes//
//			.editAttribute(AGDemoBusinessPartnerContact.LAST_NAME)
//			.setPredicateMandatory(EmfPredicates.always());
//	}

//	@Override
//	public void customizeLoggers(EmfChangeLoggerSet<AGBusinessPartnerContact> loggerSet) {
//
//		loggerSet//
//			.addPlainChangeLogger(AGBusinessPartnerContactLog.BUSINESS_PARTNER_CONTACT, AGBusinessPartnerContactLog.TRANSACTION)
//			.addMapping(AGBusinessPartnerContact.ACTIVE, AGBusinessPartnerContactLog.ACTIVE)
//			.addMapping(AGBusinessPartnerContact.TITLE, AGBusinessPartnerContactLog.TITLE)
//			.addMapping(AGBusinessPartnerContact.FIRST_NAME, AGBusinessPartnerContactLog.FIRST_NAME)
//			.addMapping(AGBusinessPartnerContact.LAST_NAME, AGBusinessPartnerContactLog.LAST_NAME)
//			.addMapping(AGBusinessPartnerContact.EMAIL, AGBusinessPartnerContactLog.EMAIL)
//			.addMapping(AGBusinessPartnerContact.DEPARTMENT, AGBusinessPartnerContactLog.DEPARTMENT)
//			.addMapping(AGBusinessPartnerContact.POSITION, AGBusinessPartnerContactLog.POSITION)
//			.addMapping(AGBusinessPartnerContact.PHONE, AGBusinessPartnerContactLog.PHONE)
//			.addMapping(AGBusinessPartnerContact.MOBILE, AGBusinessPartnerContactLog.MOBILE)
//			.addMapping(AGBusinessPartnerContact.FAX, AGBusinessPartnerContactLog.FAX);
//	}

//	@Override
//	public void customizeAuthorizer(EmfAuthorizer<AGBusinessPartnerContact, AGBusinessPartner> authorizer) {
//
//		authorizer//
//			.setCreationRole(
//				BusinessUnitRoles.ADMINISTRATOR
//					.of(IEmfTableRowMapper.nonOptional(BusinessUnitI18n.BUSINESS_UNIT_MODULE_INSTANCE, it -> it.getModuleInstance())))
//			.setViewRole(
//				BusinessUnitRoles.VIEWER
//					.of(IEmfTableRowMapper.nonOptional(BusinessUnitI18n.BUSINESS_UNIT_MODULE_INSTANCE, it -> it.getBusinessPartner().getModuleInstance())))
//			.setEditRole(
//				BusinessUnitRoles.ADMINISTRATOR
//					.of(IEmfTableRowMapper.nonOptional(BusinessUnitI18n.BUSINESS_UNIT_MODULE_INSTANCE, it -> it.getBusinessPartner().getModuleInstance())));
//	}
}
