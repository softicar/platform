package com.softicar.platform.core.module.module.instance;

import com.softicar.platform.core.module.language.AGCoreLanguageEnum;
import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.table.configuration.IDbTableDataInitializer;

/**
 * Initializes the singleton instance of the {@link AGCoreModuleInstance}.
 * <p>
 * A system user is also created and configured.
 *
 * @author Oliver Richers
 */
public class CoreModuleInstanceTableDataInitializer implements IDbTableDataInitializer {

	@Override
	public void initializeData() {

		AGCoreModuleInstance.TABLE//
			.createInsert()
			.set(AGCoreModuleInstance.ID, AGCoreModuleInstance.SINGLETON_INSTANCE_ID)
			.set(AGCoreModuleInstance.SYSTEM_USER, insertSystemUser())
			.set(AGCoreModuleInstance.EMAIL_SERVER, insertEmailServer())
			.set(AGCoreModuleInstance.DEFAULT_LANGUAGE, AGCoreLanguageEnum.GERMAN.getRecord())
			.executeWithoutIdGeneration();
	}

	private AGUser insertSystemUser() {

		int userId = AGUser.TABLE//
			.createInsert()
			.set(AGUser.LOGIN_NAME, "system.user")
			.set(AGUser.FIRST_NAME, "System")
			.set(AGUser.LAST_NAME, "User")
			.set(AGUser.EMAIL_ADDRESS, "system.user@example.com")
			.set(AGUser.PREFERRED_LANGUAGE, AGCoreLanguageEnum.ENGLISH.getRecord())
			.set(AGUser.SYSTEM_USER, true)
			.execute();
		return AGUser.TABLE.getStub(userId);
	}

	private AGServer insertEmailServer() {

		int serverId = AGServer.TABLE//
			.createInsert()
			.set(AGServer.NAME, "default")
			.set(AGServer.ADDRESS, "0.0.0.0")
			.set(AGServer.PORT, 0)
			.set(AGServer.USERNAME, "")
			.set(AGServer.PASSWORD, "")
			.execute();
		return AGServer.TABLE.getStub(serverId);
	}
}
