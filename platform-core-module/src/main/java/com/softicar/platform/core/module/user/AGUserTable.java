package com.softicar.platform.core.module.user;

import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.user.password.UserResetPasswordAction;
import com.softicar.platform.core.module.user.pseudonymization.UserPseudonymizationAction;
import com.softicar.platform.db.runtime.object.IDbObjectTableBuilder;
import com.softicar.platform.emf.action.EmfActionSet;
import com.softicar.platform.emf.attribute.EmfAttributeReorderer;
import com.softicar.platform.emf.attribute.IEmfAttributeList;
import com.softicar.platform.emf.log.EmfChangeLoggerSet;
import com.softicar.platform.emf.object.table.EmfObjectTable;
import com.softicar.platform.emf.table.configuration.EmfAttributeDefaultValueSet;
import com.softicar.platform.emf.table.configuration.EmfTableConfiguration;

public class AGUserTable extends EmfObjectTable<AGUser, AGCoreModuleInstance> {

	public AGUserTable(IDbObjectTableBuilder<AGUser> builder) {

		super(builder);
	}

	@Override
	public void customizeEmfTableConfiguration(EmfTableConfiguration<AGUser, Integer, AGCoreModuleInstance> configuration) {

		configuration.setIcon(CoreImages.USERS);
	}

	@Override
	public void customizeActionSet(EmfActionSet<AGUser, AGCoreModuleInstance> actionSet) {

		actionSet.addPrimaryAction(new UserPseudonymizationAction());
		actionSet.addManagementAction(new UserResetPasswordAction());
	}

	@Override
	public void customizeAttributeOrdering(EmfAttributeReorderer<AGUser> reorderer) {

		reorderer.moveAttribute(AGUser.LAST_NAME).behind(AGUser.FIRST_NAME);
	}

	@Override
	public void customizeAttributeProperties(IEmfAttributeList<AGUser> attributes) {

		attributes.editAttribute(AGUser.SYSTEM_USER).setImmutable(true);
		attributes.addTransientAttribute(AGUser.LAST_LOGIN);
		attributes.editAttribute(AGUser.PREFERENCES_JSON).setConcealed(true);
	}

	@Override
	public void customizeAttributeDefaultValues(EmfAttributeDefaultValueSet<AGUser, AGCoreModuleInstance> defaultValueSet) {

		defaultValueSet.setSupplier(AGUser.LOCALIZATION, () -> AGCoreModuleInstance.getInstance().getDefaultLocalization());
	}

	@Override
	public void customizeLoggers(EmfChangeLoggerSet<AGUser> loggerSet) {

		loggerSet//
			.addPlainChangeLogger(AGUserLog.USER, AGUserLog.TRANSACTION)
			.addMapping(AGUser.ACTIVE, AGUserLog.ACTIVE)
			.addMapping(AGUser.LOGIN_NAME, AGUserLog.LOGIN_NAME)
			.addMapping(AGUser.FIRST_NAME, AGUserLog.FIRST_NAME)
			.addMapping(AGUser.LAST_NAME, AGUserLog.LAST_NAME)
			.addMapping(AGUser.EMAIL_ADDRESS, AGUserLog.EMAIL_ADDRESS)
			.addMapping(AGUser.LOCALIZATION, AGUserLog.LOCALIZATION)
			.addMapping(AGUser.PASSWORD_POLICY, AGUserLog.PASSWORD_POLICY)
			.addMapping(AGUser.ALLOWED_IP_RULE, AGUserLog.ALLOWED_IP_RULE)
			.addMapping(AGUser.PREFERENCES_JSON, AGUserLog.PREFERENCES_JSON);
	}
}
