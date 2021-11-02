package com.softicar.platform.core.module.user;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.LanguageEnum;
import com.softicar.platform.common.core.i18n.LanguageScope;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.common.date.Day;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.CoreRoles;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.access.role.EmfSystemModuleRole;
import com.softicar.platform.core.module.access.role.assignment.EmfModuleRoleAssignmentCache;
import com.softicar.platform.core.module.email.EmailContentType;
import com.softicar.platform.core.module.email.IEmail;
import com.softicar.platform.core.module.email.buffer.BufferedEmailFactory;
import com.softicar.platform.core.module.environment.AGLiveSystemConfiguration;
import com.softicar.platform.core.module.language.AGCoreLanguage;
import com.softicar.platform.core.module.module.instance.AGCoreModuleInstance;
import com.softicar.platform.core.module.user.password.AGUserPassword;
import com.softicar.platform.core.module.user.password.UserPasswordGenerator;
import com.softicar.platform.core.module.user.password.UserPasswordLoader;
import com.softicar.platform.core.module.user.password.UserPasswordUpdater;
import com.softicar.platform.core.module.user.password.policy.AGPasswordPolicy;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.emf.module.role.IEmfModuleRole;
import com.softicar.platform.emf.object.IEmfObject;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class AGUser extends AGUserGenerated implements IEmfObject<AGUser>, IBasicUser {

	private final EmfModuleRoleAssignmentCache roleMembershipCache;

	public AGUser() {

		this.roleMembershipCache = new EmfModuleRoleAssignmentCache(getThis());
	}

	public static AGUser get(IBasicUser basicUser) {

		return AGUser.TABLE.get(basicUser.getId());
	}

	public boolean hasModuleRole(IEmfModuleRole<?> role, AGModuleInstance moduleInstance) {

		return roleMembershipCache.hasModuleRole(role.getAnnotatedUuid(), moduleInstance);
	}

	public boolean hasModuleRole(EmfSystemModuleRole moduleRole) {

		return roleMembershipCache.hasModuleRole(moduleRole);
	}

	@Override
	public LanguageEnum getLanguageEnum() {

		return getPreferredLanguage().getLanguageEnum().orElse(null);
	}

	public AGUser setPreferredLanguage(LanguageEnum languageEnum) {

		AGCoreLanguage coreLanguage = AGCoreLanguage//
			.getByLanguageEnum(languageEnum)
			.orElseThrow(
				() -> new SofticarDeveloperException("Failed to determine core language for: %s.%s", LanguageEnum.class.getSimpleName(), languageEnum.name()));
		return setPreferredLanguage(coreLanguage);
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.format("%s %s", getFirstName(), getLastName());
	}

	public static AGUser getSystemUser() {

		return AGCoreModuleInstance.getInstance().getSystemUser();
	}

	@Override
	public Boolean isSuperUser() {

		return CoreRoles.SUPER_USER.test(CoreModule.getModuleInstance(), getThis());
	}

	public boolean isPasswordPolicyFulfilled() {

		AGUserPassword activePassword = getActivePassword();
		return activePassword != null && activePassword.isPolicyFulfilled();
	}

	public boolean isPasswordCompromised() {

		AGUserPassword activePassword = getActivePassword();
		return activePassword != null && activePassword.isCompromised();
	}

	public boolean isPasswordChangeNecessary() {

		return isPasswordCompromised() || !isPasswordPolicyFulfilled() || isMaximumPasswordAgeReached();
	}

	public AGUser updatePassword(String password) {

		new UserPasswordUpdater(getThis(), password).update();
		return getThis();
	}

	public AGUserPassword getActivePassword() {

		return new UserPasswordLoader(getThis()).loadActivePassword();
	}

	public String getFirstAndLastName() {

		return getFirstName() + " " + getLastName();
	}

	/**
	 * Loads all active users, except system users.
	 *
	 * @return a {@link List} of all active users
	 */
	public static List<AGUser> getAllActive() {

		return AGUser//
			.createSelect()
			.where(AGUser.ACTIVE)
			.where(AGUser.SYSTEM_USER.isFalse())
			.list();
	}

	// TODO this should be extracted to a separate class
	public static void sendNotification(final String emailAddress, String password, String userName) {

		String text = new DisplayString()//
			.append(CoreI18n.HELLO)
			.append(",\n\n")
			.append(CoreI18n.THE_ARG1_USER_ACCOUNT_HAS_BEEN_CREATED_OR_WAS_CHANGED_FOR_YOU.toDisplay(AGLiveSystemConfiguration.getSystemIdentifier()))
			.append("\n\n")
			.append(
				CoreI18n.ARG1_IS_AVAILABLE_AT_THE_FOLLOWING_ADDRESS
					.toDisplay(AGLiveSystemConfiguration.getSystemIdentifier())
					.concatFormat(": %s", AGCoreModuleInstance.getInstance().getPortalUrl()))
			.append(CoreI18n.LOGIN_NAME.concatFormat(": %s \n", userName))
			.append(CoreI18n.PASSWORD.concatFormat(": %s \n", password))
			.append("\n")
			.append(CoreI18n.BEST_REGARDS)
			.append(",\n")
			.append(CoreI18n.YOUR_ARG1_SUPPORT_TEAM.toDisplay(AGLiveSystemConfiguration.getSystemIdentifier()))
			.append("\n")
			.toString();

		// Please note that we are sending in plain text here,
		// so that we can be sure that the password will not be screwed!
		IEmail email = BufferedEmailFactory.createNoReplyEmail();
		email.addToRecipient(emailAddress);
		email.setSubject(CoreI18n.YOUR_ARG1_ACCOUNT_ARG2.toDisplay(AGLiveSystemConfiguration.getSystemIdentifier(), userName));
		email.setContent(text, EmailContentType.PLAIN);
		email.submit();
	}

	public static void sendPaswordResetNotification(AGUser user, String password) {

		LanguageEnum userLanguage = user.getLanguageEnum();

		try (LanguageScope languageScope = new LanguageScope(userLanguage)) {
			StringBuilder sb = new StringBuilder();
			sb.append(CoreI18n.THE_PASSWORD_WAS_RESET_BY_ARG1.toDisplay(CurrentUser.get().getFirstAndLastName()));
			sb.append("\r\n\r\n");
			sb.append(CoreI18n.THE_PASSWORD_WAS_RESET_FOR_ARG1.toDisplay(user.getFirstName() + " " + user.getLastName()));
			sb.append("\r\n\r\n");
			sb.append(CoreI18n.USERNAME.concat(": ").concat(user.getLoginName()));
			sb.append("\r\n");
			sb.append(CoreI18n.NEW_PASSWORD_ARG1.toDisplay(password));
			sb.append("\r\n");

			IEmail email = BufferedEmailFactory.createNoReplyEmail();
			email.addToRecipient(user.getEmailAddress());
			email.setSubject(CoreI18n.USER_PASSWORD_RESET);
			email.setContent(sb.toString(), EmailContentType.PLAIN);
			email.submit();
		}
	}

	public void deactivate() {

		setActive(false);
		save();
	}

	public void activate() {

		setActive(true);
		save();
	}

	public static AGUser insertUser(String firstName, String lastName, String loginName, String email, boolean sendEmailNotification) {

		try (DbTransaction transaction = new DbTransaction()) {
			AGUser user = new AGUser();
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setLoginName(loginName);
			user.setEmailAddress(email);
			user.save();
			//
			String password = new UserPasswordGenerator().generatePassword();
			user.updatePassword(password);
			if (sendEmailNotification) {
				sendNotification(email, password, loginName);
			}
			//
			transaction.commit();
			//
			return user;

		} catch (Exception exception) {
			throw new SofticarException(exception.getLocalizedMessage());
		}
	}

	public void updateLastLoginDayTimeToNow() {

		AGUserPassword userPassword = AGUserPassword.getActive(getThis());
		if (userPassword != null) {
			userPassword.setLastLoginAt(DayTime.now());
			userPassword.save();
		}
	}

	public boolean isTwoFactorAuthenticationRequired() {

		return Optional//
			.ofNullable(getPasswordPolicy())
			.map(AGPasswordPolicy::isTwoFactorAuthentication)
			.orElse(false);
	}

	public boolean isMaximumPasswordAgeReached() {

		DayTime passwordCreatedAt = AGUserPassword.getActive(getThis()).getCreatedAt();
		return Optional//
			.ofNullable(getPasswordPolicy())
			.map(AGPasswordPolicy::getMaximumPasswordAge)
			.map(maximumAge -> passwordCreatedAt.isBeforeOrEqual(Day.today().toDayTime().minusDays(maximumAge)))
			.orElse(false);
	}

	/**
	 * Returns a list of all active non-system users.
	 *
	 * @return all real users (never null)
	 */
	public static Collection<AGUser> getAllRealUsers() {

		return AGUser.TABLE//
			.createSelect()
			.where(AGUser.ACTIVE)
			.where(AGUser.SYSTEM_USER.isFalse())
			.list();
	}

	@Override
	public IDisplayString toDisplay() {

		return toDisplayWithoutId();
	}
}
