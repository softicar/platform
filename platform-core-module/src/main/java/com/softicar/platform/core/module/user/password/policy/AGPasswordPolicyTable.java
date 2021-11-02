package com.softicar.platform.core.module.user.password.policy;

import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.module.instance.system.SystemModuleInstance;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.log.EmfPlainChangeLogger;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGPasswordPolicyTable extends EmfObjectTable<AGPasswordPolicy, SystemModuleInstance> {

	public AGPasswordPolicyTable(IDbObjectTableBuilder<AGPasswordPolicy> structure) {

		super(structure);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGPasswordPolicy, Integer, SystemModuleInstance> configuration) {

		configuration.setIcon(CoreImages.PASSWORD_POLICY);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGPasswordPolicy> attributes) {

		attributes//
			.editAttribute(AGPasswordPolicy.NAME)
			.setPredicateMandatory(EmfPredicates.always());
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGPasswordPolicy> loggerSet) {

		loggerSet
			.addChangeLogger(
				new EmfPlainChangeLogger<>(AGPasswordPolicyLog.PASSWORD_POLICY, AGPasswordPolicyLog.TRANSACTION)
					.addMapping(AGPasswordPolicy.ACTIVE, AGPasswordPolicyLog.ACTIVE)
					.addMapping(AGPasswordPolicy.NAME, AGPasswordPolicyLog.NAME)
					.addMapping(AGPasswordPolicy.MAXIMUM_PASSWORD_REUSE, AGPasswordPolicyLog.MAXIMUM_PASSWORD_REUSE)
					.addMapping(AGPasswordPolicy.MAXIMUM_PASSWORD_AGE, AGPasswordPolicyLog.MAXIMUM_PASSWORD_AGE)
					.addMapping(AGPasswordPolicy.TWO_FACTOR_AUTHENTICATION, AGPasswordPolicyLog.TWO_FACTOR_AUTHENTICATION));
	}
}
