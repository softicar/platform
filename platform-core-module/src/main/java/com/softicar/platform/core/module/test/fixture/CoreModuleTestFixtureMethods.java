package com.softicar.platform.core.module.test.fixture;

import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.AGCoreModuleInstance;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.event.AGSystemEvent;
import com.softicar.platform.core.module.event.SystemEventBuilder;
import com.softicar.platform.core.module.event.severity.AGSystemEventSeverityEnum;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.language.AGCoreLanguage;
import com.softicar.platform.core.module.localization.AGLocalization;
import com.softicar.platform.core.module.maintenance.AGMaintenanceWindow;
import com.softicar.platform.core.module.maintenance.state.AGMaintenanceStateEnum;
import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.core.module.module.instance.IModuleInstance;
import com.softicar.platform.core.module.module.instance.IModuleInstanceTable;
import com.softicar.platform.core.module.permission.assignment.AGModuleInstancePermissionAssignment;
import com.softicar.platform.core.module.role.AGRole;
import com.softicar.platform.core.module.role.permission.AGRolePermission;
import com.softicar.platform.core.module.role.user.AGRoleUser;
import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.core.module.user.CurrentUser;
import com.softicar.platform.core.module.user.password.AGUserPassword;
import com.softicar.platform.core.module.user.password.UserPasswordUpdater;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import java.util.UUID;

/**
 * Provides various test fixture methods for the {@link CoreModule}.
 *
 * @author Oliver Richers
 */
public interface CoreModuleTestFixtureMethods {

	// ------------------------------ user ------------------------------ //

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

	// ------------------------------ password ------------------------------ //

	default AGUserPassword insertPassword(AGUser user, String password) {

		return new UserPasswordUpdater(user, password).updatePasswordInDatabase();
	}

	// ------------------------------ localization ------------------------------ //

	default AGLocalization insertLocalizationPreset(String name, LanguageEnum language, String decimalSeparator, String digitGroupSeparator,
			String dateFormat) {

		return new AGLocalization()//
			.setName(name)
			.setLanguage(AGCoreLanguage.getByLanguageEnum(language).get())
			.setDecimalSeparator(decimalSeparator)
			.setDigitGroupSeparator(digitGroupSeparator)
			.setDateFormat(dateFormat)
			.save();
	}

	default AGLocalization insertLocalizationPresetGermany() {

		return insertLocalizationPreset("Deutschland", LanguageEnum.GERMAN, ",", ".", "dd.MM.yyyy");
	}

	// ------------------------------ module permission ------------------------------ //

	default <I extends IModuleInstance<I>> AGModuleInstancePermissionAssignment insertPermissionAssignment(//
			AGUser user, IEmfModulePermission<I> permission, I moduleInstance) {

		return insertPermissionAssignment(user, permission.getAnnotatedUuid(), moduleInstance);
	}

	default AGModuleInstancePermissionAssignment insertPermissionAssignment(AGUser user, UUID permissionUuid, IModuleInstance<?> moduleInstance) {

		return new AGModuleInstancePermissionAssignment()//
			.setActive(true)
			.setModuleInstanceBase(moduleInstance.pk())
			.setPermission(permissionUuid)
			.setUser(user)
			.save();
	}

	// ------------------------------ module instance ------------------------------ //

	default AGModuleInstanceBase insertModuleInstanceBase(Class<? extends IEmfModule<?>> moduleClass) {

		return new AGModuleInstanceBase()//
			.setActive(true)
			.setModuleUuid(AGUuid.getOrCreate(moduleClass))
			.save();
	}

	default <I extends IModuleInstance<I>> I createModuleInstance(IModuleInstanceTable<I> moduleInstanceTable) {

		AGModuleInstanceBase moduleInstanceBase = insertModuleInstanceBase(moduleInstanceTable.getModuleClass());
		return moduleInstanceTable.createObject(moduleInstanceBase);
	}

	default <I extends IModuleInstance<I>> I insertModuleInstance(IModuleInstanceTable<I> moduleInstanceTable) {

		return createModuleInstance(moduleInstanceTable).save();
	}

	// ------------------------------ role ------------------------------ //

	default AGRole insertRole(String name) {

		return new AGRole()//
			.setActive(true)
			.setName(name)
			.save();
	}

	default AGRoleUser insertRoleUser(AGRole role, AGUser user) {

		return new AGRoleUser()//
			.setActive(true)
			.setRole(role)
			.setUser(user)
			.save();
	}

	default AGRolePermission insertRolePermission(AGRole role, UUID permissionUuid, IModuleInstance<?> moduleInstance) {

		return new AGRolePermission()//
			.setActive(true)
			.setRole(role)
			.setModuleInstanceBase(moduleInstance.pk())
			.setPermissionUuid(AGUuid.getOrCreate(permissionUuid))
			.save();
	}

	// ------------------------------ server ------------------------------ //

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

	// ------------------------------ maintenance window ------------------------------ //

	default AGMaintenanceWindow insertMaintenanceWindow(DayTime expectedStart, DayTime expectedEnd, AGMaintenanceStateEnum state) {

		return new AGMaintenanceWindow()//
			.setExpectedStart(expectedStart)
			.setExpectedEnd(expectedEnd)
			.setState(state.getRecord())
			.save();
	}

	// ------------------------------ system event ------------------------------ //

	default AGSystemEvent insertSystemErrorEvent(String message) {

		return new SystemEventBuilder(AGSystemEventSeverityEnum.ERROR, message).save();
	}

	default AGSystemEvent insertSystemWarningEvent(String message) {

		return new SystemEventBuilder(AGSystemEventSeverityEnum.WARNING, message).save();
	}

	default AGSystemEvent insertSystemInformationEvent(String message) {

		return new SystemEventBuilder(AGSystemEventSeverityEnum.INFORMATION, message).save();
	}

	// ------------------------------ stored file ------------------------------ //

	default AGStoredFile insertStoredFile(String filename) {

		return new AGStoredFile()//
			.setFileName(filename)
			.setCreatedBy(CurrentUser.get())
			.setRemoveAt(null)
			.save();
	}
}
