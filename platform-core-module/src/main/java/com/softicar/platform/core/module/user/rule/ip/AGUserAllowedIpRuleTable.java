package com.softicar.platform.core.module.user.rule.ip;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGUserAllowedIpRuleTable extends EmfObjectTable<AGUserAllowedIpRule, AGCoreModuleInstance> {

	public AGUserAllowedIpRuleTable(IDbObjectTableBuilder<AGUserAllowedIpRule> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGUserAllowedIpRule, Integer, AGCoreModuleInstance> configuration) {

		configuration.setIcon(CoreImages.NETWORK);
		configuration.addValidator(UserAllowedIpRuleValidator::new);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGUserAllowedIpRule> attributes) {

		attributes//
			.editStringAttribute(AGUserAllowedIpRule.ADDRESSES)
			.setMultiline(true);
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGUserAllowedIpRule> loggerSet) {

		loggerSet
			.addPlainChangeLogger(AGUserAllowedIpRuleLog.USER_ALLOWED_IP_RULE, AGUserAllowedIpRuleLog.TRANSACTION)
			.addMapping(AGUserAllowedIpRule.ACTIVE, AGUserAllowedIpRuleLog.ACTIVE)
			.addMapping(AGUserAllowedIpRule.NAME, AGUserAllowedIpRuleLog.NAME)
			.addMapping(AGUserAllowedIpRule.ADDRESSES, AGUserAllowedIpRuleLog.ADDRESSES);
	}
}
