package com.softicar.platform.core.module.module.instance;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.file.stored.AGStoredFile;
import com.softicar.platform.core.module.file.stored.server.AGStoredFileServer;
import com.softicar.platform.core.module.localization.AGLocalizationPreset;
import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGCoreModuleInstance for
 * database table <i>Core.CoreModuleInstance</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGCoreModuleInstanceGenerated extends AbstractDbObject<AGCoreModuleInstance> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGCoreModuleInstance, AGCoreModuleInstanceGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "CoreModuleInstance", AGCoreModuleInstance::new, AGCoreModuleInstance.class);
	static {
		BUILDER.setTitle(CoreI18n.CORE_MODULE_INSTANCE);
		BUILDER.setPluralTitle(CoreI18n.CORE_MODULE_INSTANCES);
	}

	public static final IDbIdField<AGCoreModuleInstance> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGCoreModuleInstance, AGUser> SYSTEM_USER = BUILDER.addForeignField("systemUser", o->o.m_systemUser, (o,v)->o.m_systemUser=v, AGUser.ID).setTitle(CoreI18n.SYSTEM_USER).setNullable().setDefault(null);
	public static final IDbForeignField<AGCoreModuleInstance, AGStoredFileServer> PRIMARY_FILE_SERVER = BUILDER.addForeignField("primaryFileServer", o->o.m_primaryFileServer, (o,v)->o.m_primaryFileServer=v, AGStoredFileServer.ID).setTitle(CoreI18n.PRIMARY_FILE_SERVER).setNullable().setDefault(null).setComment("Server used for uploading files");
	public static final IDbForeignField<AGCoreModuleInstance, AGServer> EMAIL_SERVER = BUILDER.addForeignField("emailServer", o->o.m_emailServer, (o,v)->o.m_emailServer=v, AGServer.ID).setTitle(CoreI18n.EMAIL_SERVER).setNullable().setDefault(null);
	public static final IDbStringField<AGCoreModuleInstance> SUPPORT_EMAIL_ADDRESS = BUILDER.addStringField("supportEmailAddress", o->o.m_supportEmailAddress, (o,v)->o.m_supportEmailAddress=v).setTitle(CoreI18n.SUPPORT_EMAIL_ADDRESS).setDefault("support@example.com").setMaximumLength(255);
	public static final IDbStringField<AGCoreModuleInstance> NO_REPLY_EMAIL_ADDRESS = BUILDER.addStringField("noReplyEmailAddress", o->o.m_noReplyEmailAddress, (o,v)->o.m_noReplyEmailAddress=v).setTitle(CoreI18n.NO_REPLY_EMAIL_ADDRESS).setDefault("no-reply@example.com").setMaximumLength(255);
	public static final IDbStringField<AGCoreModuleInstance> PORTAL_PROTOCOL = BUILDER.addStringField("portalProtocol", o->o.m_portalProtocol, (o,v)->o.m_portalProtocol=v).setTitle(CoreI18n.PORTAL_PROTOCOL).setDefault("https").setMaximumLength(255);
	public static final IDbStringField<AGCoreModuleInstance> PORTAL_HOST = BUILDER.addStringField("portalHost", o->o.m_portalHost, (o,v)->o.m_portalHost=v).setTitle(CoreI18n.PORTAL_HOST).setDefault("www.example.com").setMaximumLength(255);
	public static final IDbStringField<AGCoreModuleInstance> PORTAL_APPLICATION = BUILDER.addStringField("portalApplication", o->o.m_portalApplication, (o,v)->o.m_portalApplication=v).setTitle(CoreI18n.PORTAL_APPLICATION).setDefault("portal").setMaximumLength(255);
	public static final IDbForeignField<AGCoreModuleInstance, AGStoredFile> PORTAL_LOGO = BUILDER.addForeignField("portalLogo", o->o.m_portalLogo, (o,v)->o.m_portalLogo=v, AGStoredFile.ID).setTitle(CoreI18n.PORTAL_LOGO).setNullable().setDefault(null);
	public static final IDbForeignField<AGCoreModuleInstance, AGLocalizationPreset> DEFAULT_LOCALIZATION = BUILDER.addForeignField("defaultLocalization", o->o.m_defaultLocalization, (o,v)->o.m_defaultLocalization=v, AGLocalizationPreset.ID).setTitle(CoreI18n.DEFAULT_LOCALIZATION).setNullable().setDefault(null);
	public static final IDbBooleanField<AGCoreModuleInstance> TEST_SYSTEM = BUILDER.addBooleanField("testSystem", o->o.m_testSystem, (o,v)->o.m_testSystem=v).setTitle(CoreI18n.TEST_SYSTEM).setDefault(false);
	public static final IDbKey<AGCoreModuleInstance> IK_PRIMARY_FILE_SERVER = BUILDER.addIndexKey("primaryFileServer", PRIMARY_FILE_SERVER);
	public static final IDbKey<AGCoreModuleInstance> IK_SYSTEM_USER = BUILDER.addIndexKey("systemUser", SYSTEM_USER);
	public static final IDbKey<AGCoreModuleInstance> IK_EMAIL_SERVER = BUILDER.addIndexKey("emailServer", EMAIL_SERVER);
	public static final AGCoreModuleInstanceTable TABLE = new AGCoreModuleInstanceTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGCoreModuleInstance> createSelect() {

		return TABLE.createSelect();
	}

	public static AGCoreModuleInstance get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getSystemUserID() {

		return getValueId(SYSTEM_USER);
	}

	public final AGUser getSystemUser() {

		return getValue(SYSTEM_USER);
	}

	public final AGCoreModuleInstance setSystemUser(AGUser value) {

		return setValue(SYSTEM_USER, value);
	}

	public final Integer getPrimaryFileServerID() {

		return getValueId(PRIMARY_FILE_SERVER);
	}

	public final AGStoredFileServer getPrimaryFileServer() {

		return getValue(PRIMARY_FILE_SERVER);
	}

	public final AGCoreModuleInstance setPrimaryFileServer(AGStoredFileServer value) {

		return setValue(PRIMARY_FILE_SERVER, value);
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

	public final AGLocalizationPreset getDefaultLocalization() {

		return getValue(DEFAULT_LOCALIZATION);
	}

	public final AGCoreModuleInstance setDefaultLocalization(AGLocalizationPreset value) {

		return setValue(DEFAULT_LOCALIZATION, value);
	}

	public final Boolean isTestSystem() {

		return getValue(TEST_SYSTEM);
	}

	public final AGCoreModuleInstance setTestSystem(Boolean value) {

		return setValue(TEST_SYSTEM, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGCoreModuleInstanceTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGUser m_systemUser;
	private AGStoredFileServer m_primaryFileServer;
	private AGServer m_emailServer;
	private String m_supportEmailAddress;
	private String m_noReplyEmailAddress;
	private String m_portalProtocol;
	private String m_portalHost;
	private String m_portalApplication;
	private AGStoredFile m_portalLogo;
	private AGLocalizationPreset m_defaultLocalization;
	private Boolean m_testSystem;
}

