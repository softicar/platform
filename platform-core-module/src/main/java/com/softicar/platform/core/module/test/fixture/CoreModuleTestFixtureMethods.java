package com.softicar.platform.core.module.test.fixture;

import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.access.role.EmfSystemModuleRole;
import com.softicar.platform.core.module.access.role.assignment.module.instance.AGModuleInstanceRoleAssignment;
import com.softicar.platform.core.module.access.role.assignment.module.system.AGSystemModuleRoleAssignment;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.language.AGCoreLanguage;
import com.softicar.platform.core.module.localization.AGLocalization;
import com.softicar.platform.core.module.maintenance.AGMaintenanceWindow;
import com.softicar.platform.core.module.maintenance.state.AGMaintenanceStateEnum;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstance;
import com.softicar.platform.core.module.module.instance.standard.IStandardModuleInstanceTable;
import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.user.password.AGUserPassword;
import com.softicar.platform.core.module.user.password.UserPasswordUpdater;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.role.IEmfModuleRole;

/**
 * Provides various test fixture methods for the {@link CoreModule}.
 *
 * @author Oliver Richers
 */
public interface CoreModuleTestFixtureMethods {

	default AGUser insertTestUser() {

		return insertUser("Test", "User");
	}

	default AGUser insertUser(String name) {

		return insertUser(name, name, name);
	}

	default AGUser insertUser(String firstName, String lastName) {

		return insertUser(firstName, lastName, (firstName + "." + lastName).toLowerCase());
	}

	default AGUser insertUser(String firstName, String lastName, String loginName) {

		return new AGUser()//
			.setActive(true)
			.setFirstName(firstName)
			.setLastName(lastName)
			.setLoginName(loginName)
			.setEmailAddress(String.format("%s@test.com", loginName))
			.setLocalization(AGCoreModuleInstance.getInstance().getDefaultLocalization())
			.save();
	}

	default AGUserPassword insertPassword(AGUser user, String password) {

		return new UserPasswordUpdater(user, password).updatePasswordInDatabase();
	}

	default AGLocalization insertLocalizationPreset(String name, LanguageEnum language, String decimalSeparator, String digitGroupSeparator) {

		return new AGLocalization()//
			.setName(name)
			.setLanguage(AGCoreLanguage.getByLanguageEnum(language).get())
			.setDecimalSeparator(decimalSeparator)
			.setDigitGroupSeparator(digitGroupSeparator)
			.save();
	}

	default AGLocalization insertLocalizationPresetGermany() {

		return insertLocalizationPreset("Deutschland", LanguageEnum.GERMAN, ",", ".");
	}

	default AGLocalization insertLocalizationPresetUsa() {

		return insertLocalizationPreset("USA", LanguageEnum.ENGLISH, ".", ",");
	}

	default AGSystemModuleRoleAssignment insertRoleMembership(AGUser user, EmfSystemModuleRole role, Class<? extends IEmfModule<?>> moduleClass) {

		return new AGSystemModuleRoleAssignment()
			.setActive(true)
			.setModule(AGUuid.getOrCreate(moduleClass))
			.setRole(role.getAnnotatedUuid())
			.setUser(user)
			.save();
	}

	default <I extends IStandardModuleInstance<I>> AGModuleInstanceRoleAssignment insertRoleMembership(AGUser user, IEmfModuleRole<I> role, I moduleInstance) {

		return new AGModuleInstanceRoleAssignment()//
			.setActive(true)
			.setModuleInstance(moduleInstance.pk())
			.setRole(role.getAnnotatedUuid())
			.setUser(user)
			.save();
	}

	default AGModuleInstance insertModuleInstance(Class<? extends IEmfModule<?>> moduleClass) {

		return new AGModuleInstance()//
			.setActive(true)
			.setModuleUuid(AGUuid.getOrCreate(moduleClass))
			.save();
	}

	default <I extends IStandardModuleInstance<I>> I createStandardModuleInstance(IStandardModuleInstanceTable<I> moduleInstanceTable) {

		AGModuleInstance moduleInstance = insertModuleInstance(moduleInstanceTable.getModuleClass());
		return moduleInstanceTable.createObject(moduleInstance);
	}

	default <I extends IStandardModuleInstance<I>> I insertStandardModuleInstance(IStandardModuleInstanceTable<I> moduleInstanceTable) {

		return createStandardModuleInstance(moduleInstanceTable).save();
	}

	// TODO: CoreModule is currently SystemModuleInstance and this needs to be adjusted
	default AGCoreModuleInstance insertCoreModuleInstance(AGLocalization defaultLocalization) {

		return new AGCoreModuleInstance().setDefaultLocalization(defaultLocalization).save();
	}

	default AGServer insertServer(String name, String serverAddress, Integer port, String domain, String username, String password) {

		return new AGServer()
			.setActive(true)
			.setAddress(serverAddress)
			.setDomain(domain)
			.setName(name)
			.setPassword(password)
			.setPort(port)
			.setUsername(username)
			.save();
	}

	default AGMaintenanceWindow insertMaintenanceWindow(DayTime expectedStart, DayTime expectedEnd, AGMaintenanceStateEnum state) {

		return new AGMaintenanceWindow()//
			.setExpectedStart(expectedStart)
			.setExpectedEnd(expectedEnd)
			.setState(state.getRecord())
			.save();
	}

	default AGStoredFile insertStoredFile(String filename) {

		return new AGStoredFile()//
			.setFileName(filename)
			.setCreatedBy(CurrentUser.get())
			.setRemoveAt(null)
			.save();
	}
}
