package com.softicar.platform.core.module;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.repository.AGStoredFileRepository;
import com.softicar.platform.core.module.localization.AGLocalization;
import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbBaseField;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.object.sub.AbstractDbSubObject;
import com.softicar.platform.db.runtime.object.sub.DbSubObjectTableBuilder;

/**
 * This is the automatically generated class AGCoreModuleInstance for
 * database table <i>Core.CoreModuleInstance</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGCoreModuleInstanceGenerated extends AbstractDbSubObject<AGCoreModuleInstance, AGModuleInstanceBase> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbSubObjectTableBuilder<AGCoreModuleInstance, AGCoreModuleInstanceGenerated, AGModuleInstanceBase, Integer> BUILDER = new DbSubObjectTableBuilder<>("Core", "CoreModuleInstance", AGCoreModuleInstance::new, AGCoreModuleInstance.class);
	static {
		BUILDER.setTitle(CoreI18n.CORE_MODULE_INSTANCE);
		BUILDER.setPluralTitle(CoreI18n.CORE_MODULE_INSTANCES);
	}

	public static final IDbBaseField<AGCoreModuleInstance, AGModuleInstanceBase, Integer> BASE = BUILDER.addBaseField("base", o->o.m_base, (o,v)->o.m_base=v, AGModuleInstanceBase.TABLE).setTitle(CoreI18n.BASE).setCascade(true, true).setForeignKeyName("CoreModuleInstance_ibfk_1").setComment("@base@");
	public static final IDbForeignField<AGCoreModuleInstance, AGUser> SYSTEM_USER = BUILDER.addForeignField("systemUser", o->o.m_systemUser, (o,v)->o.m_systemUser=v, AGUser.ID).setTitle(CoreI18n.SYSTEM_USER).setNullable().setDefault(null).setForeignKeyName("CoreModuleInstance_ibfk_2");
	public static final IDbForeignField<AGCoreModuleInstance, AGStoredFileRepository> PRIMARY_FILE_REPOSITORY = BUILDER.addForeignField("primaryFileRepository", o->o.m_primaryFileRepository, (o,v)->o.m_primaryFileRepository=v, AGStoredFileRepository.ID).setTitle(CoreI18n.PRIMARY_FILE_REPOSITORY).setNullable().setDefault(null).setForeignKeyName("CoreModuleInstance_ibfk_3");
	public static final IDbForeignField<AGCoreModuleInstance, AGServer> EMAIL_SERVER = BUILDER.addForeignField("emailServer", o->o.m_emailServer, (o,v)->o.m_emailServer=v, AGServer.ID).setTitle(CoreI18n.EMAIL_SERVER).setNullable().setDefault(null).setForeignKeyName("CoreModuleInstance_ibfk_4");
	public static final IDbStringField<AGCoreModuleInstance> SUPPORT_EMAIL_ADDRESS = BUILDER.addStringField("supportEmailAddress", o->o.m_supportEmailAddress, (o,v)->o.m_supportEmailAddress=v).setTitle(CoreI18n.SUPPORT_EMAIL_ADDRESS).setDefault("support@example.com").setMaximumLength(255);
	public static final IDbStringField<AGCoreModuleInstance> NO_REPLY_EMAIL_ADDRESS = BUILDER.addStringField("noReplyEmailAddress", o->o.m_noReplyEmailAddress, (o,v)->o.m_noReplyEmailAddress=v).setTitle(CoreI18n.NO_REPLY_EMAIL_ADDRESS).setDefault("no-reply@example.com").setMaximumLength(255);
	public static final IDbStringField<AGCoreModuleInstance> PORTAL_PROTOCOL = BUILDER.addStringField("portalProtocol", o->o.m_portalProtocol, (o,v)->o.m_portalProtocol=v).setTitle(CoreI18n.PORTAL_PROTOCOL).setDefault("https").setMaximumLength(255);
	public static final IDbStringField<AGCoreModuleInstance> PORTAL_HOST = BUILDER.addStringField("portalHost", o->o.m_portalHost, (o,v)->o.m_portalHost=v).setTitle(CoreI18n.PORTAL_HOST).setDefault("www.example.com").setMaximumLength(255);
	public static final IDbStringField<AGCoreModuleInstance> PORTAL_APPLICATION = BUILDER.addStringField("portalApplication", o->o.m_portalApplication, (o,v)->o.m_portalApplication=v).setTitle(CoreI18n.PORTAL_APPLICATION).setDefault("portal").setMaximumLength(255);
	public static final IDbStringField<AGCoreModuleInstance> SYSTEM_NAME = BUILDER.addStringField("systemName", o->o.m_systemName, (o,v)->o.m_systemName=v).setTitle(CoreI18n.SYSTEM_NAME).setDefault("Forspace").setMaximumLength(255);
	public static final IDbForeignField<AGCoreModuleInstance, AGStoredFile> PORTAL_LOGO = BUILDER.addForeignField("portalLogo", o->o.m_portalLogo, (o,v)->o.m_portalLogo=v, AGStoredFile.ID).setTitle(CoreI18n.PORTAL_LOGO).setNullable().setDefault(null).setForeignKeyName("CoreModuleInstance_ibfk_5");
	public static final IDbForeignField<AGCoreModuleInstance, AGLocalization> DEFAULT_LOCALIZATION = BUILDER.addForeignField("defaultLocalization", o->o.m_defaultLocalization, (o,v)->o.m_defaultLocalization=v, AGLocalization.ID).setTitle(CoreI18n.DEFAULT_LOCALIZATION).setForeignKeyName("CoreModuleInstance_ibfk_6");
	public static final IDbBooleanField<AGCoreModuleInstance> TEST_SYSTEM = BUILDER.addBooleanField("testSystem", o->o.m_testSystem, (o,v)->o.m_testSystem=v).setTitle(CoreI18n.TEST_SYSTEM).setDefault(false);
	public static final IDbBooleanField<AGCoreModuleInstance> PASSWORD_RESET_FUNCTIONALITY = BUILDER.addBooleanField("passwordResetFunctionality", o->o.m_passwordResetFunctionality, (o,v)->o.m_passwordResetFunctionality=v).setTitle(CoreI18n.PASSWORD_RESET_FUNCTIONALITY).setDefault(false);
	public static final IDbKey<AGCoreModuleInstance> IK_SYSTEM_USER = BUILDER.addIndexKey("systemUser", SYSTEM_USER);
	public static final IDbKey<AGCoreModuleInstance> IK_EMAIL_SERVER = BUILDER.addIndexKey("emailServer", EMAIL_SERVER);
	public static final IDbKey<AGCoreModuleInstance> IK_PORTAL_LOGO = BUILDER.addIndexKey("portalLogo", PORTAL_LOGO);
	public static final IDbKey<AGCoreModuleInstance> IK_DEFAULT_LOCALIZATION = BUILDER.addIndexKey("defaultLocalization", DEFAULT_LOCALIZATION);
	public static final IDbKey<AGCoreModuleInstance> IK_PRIMARY_FILE_REPOSITORY = BUILDER.addIndexKey("primaryFileRepository", PRIMARY_FILE_REPOSITORY);
	public static final AGCoreModuleInstanceTable TABLE = new AGCoreModuleInstanceTable(BUILDER);
	// @formatter:on

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final AGModuleInstanceBase getBase() {

		return pk();
	}

	public final Integer getSystemUserID() {

		return getValueId(SYSTEM_USER);
	}

	public final AGUser getSystemUser() {

		return getValue(SYSTEM_USER);
	}

	public final AGCoreModuleInstance setSystemUser(AGUser value) {

		return setValue(SYSTEM_USER, value);
	}

	public final Integer getPrimaryFileRepositoryID() {

		return getValueId(PRIMARY_FILE_REPOSITORY);
	}

	public final AGStoredFileRepository getPrimaryFileRepository() {

		return getValue(PRIMARY_FILE_REPOSITORY);
	}

	public final AGCoreModuleInstance setPrimaryFileRepository(AGStoredFileRepository value) {

		return setValue(PRIMARY_FILE_REPOSITORY, value);
	}

	public final Integer getEmailServerID() {

		return getValueId(EMAIL_SERVER);
	}

	public final AGServer getEmailServer() {

		return getValue(EMAIL_SERVER);
	}

	public final AGCoreModuleInstance setEmailServer(AGServer value) {

		return setValue(EMAIL_SERVER, value);
	}

	public final String getSupportEmailAddress() {

		return getValue(SUPPORT_EMAIL_ADDRESS);
	}

	public final AGCoreModuleInstance setSupportEmailAddress(String value) {

		return setValue(SUPPORT_EMAIL_ADDRESS, value);
	}

	public final String getNoReplyEmailAddress() {

		return getValue(NO_REPLY_EMAIL_ADDRESS);
	}

	public final AGCoreModuleInstance setNoReplyEmailAddress(String value) {

		return setValue(NO_REPLY_EMAIL_ADDRESS, value);
	}

	public final String getPortalProtocol() {

		return getValue(PORTAL_PROTOCOL);
	}

	public final AGCoreModuleInstance setPortalProtocol(String value) {

		return setValue(PORTAL_PROTOCOL, value);
	}

	public final String getPortalHost() {

		return getValue(PORTAL_HOST);
	}

	public final AGCoreModuleInstance setPortalHost(String value) {

		return setValue(PORTAL_HOST, value);
	}

	public final String getPortalApplication() {

		return getValue(PORTAL_APPLICATION);
	}

	public final AGCoreModuleInstance setPortalApplication(String value) {

		return setValue(PORTAL_APPLICATION, value);
	}

	public final String getSystemName() {

		return getValue(SYSTEM_NAME);
	}

	public final AGCoreModuleInstance setSystemName(String value) {

		return setValue(SYSTEM_NAME, value);
	}

	public final Integer getPortalLogoID() {

		return getValueId(PORTAL_LOGO);
	}

	public final AGStoredFile getPortalLogo() {

		return getValue(PORTAL_LOGO);
	}

	public final AGCoreModuleInstance setPortalLogo(AGStoredFile value) {

		return setValue(PORTAL_LOGO, value);
	}

	public final Integer getDefaultLocalizationID() {

		return getValueId(DEFAULT_LOCALIZATION);
	}

	public final AGLocalization getDefaultLocalization() {

		return getValue(DEFAULT_LOCALIZATION);
	}

	public final AGCoreModuleInstance setDefaultLocalization(AGLocalization value) {

		return setValue(DEFAULT_LOCALIZATION, value);
	}

	public final Boolean isTestSystem() {

		return getValue(TEST_SYSTEM);
	}

	public final AGCoreModuleInstance setTestSystem(Boolean value) {

		return setValue(TEST_SYSTEM, value);
	}

	public final Boolean isPasswordResetFunctionality() {

		return getValue(PASSWORD_RESET_FUNCTIONALITY);
	}

	public final AGCoreModuleInstance setPasswordResetFunctionality(Boolean value) {

		return setValue(PASSWORD_RESET_FUNCTIONALITY, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGCoreModuleInstanceTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGModuleInstanceBase m_base;
	private AGUser m_systemUser;
	private AGStoredFileRepository m_primaryFileRepository;
	private AGServer m_emailServer;
	private String m_supportEmailAddress;
	private String m_noReplyEmailAddress;
	private String m_portalProtocol;
	private String m_portalHost;
	private String m_portalApplication;
	private String m_systemName;
	private AGStoredFile m_portalLogo;
	private AGLocalization m_defaultLocalization;
	private Boolean m_testSystem;
	private Boolean m_passwordResetFunctionality;
}

