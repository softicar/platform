package com.softicar.platform.core.module.module.instance;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.item.ItemId;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.common.string.Trim;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreModule;
import com.softicar.platform.core.module.server.AGServer;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.module.IUuid;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import java.util.Optional;

public class AGCoreModuleInstance extends AGCoreModuleInstanceGenerated implements IEmfObject<AGCoreModuleInstance>, IEmfModuleInstance<AGCoreModuleInstance> {

	/**
	 * Bootstrapping assumption: {@link AGCoreModuleInstanceTable} must always
	 * contain exactly one record, with the ID which is specified here.
	 */
	protected static final int SINGLETON_INSTANCE_ID = 1;

	public static AGCoreModuleInstance getInstance() {

		return AGCoreModuleInstance.get(SINGLETON_INSTANCE_ID);
	}

	public AGServer getEmailServerOrThrow() {

		return Optional//
			.ofNullable(getEmailServer())
			.orElseThrow(() -> new SofticarUserException(CoreI18n.EMAIL_SERVER_NOT_CONFIGURED));
	}

	@Override
	public IDisplayString toDisplayWithoutId() {

		return CoreI18n.CORE_MODULE_INSTANCE;
	}

	@Override
	public IDisplayString toDisplay() {

		return toDisplayWithoutId();
	}

	@Override
	public IUuid getModuleUuid() {

		return () -> EmfSourceCodeReferencePoints.getUuidOrThrow(CoreModule.class);
	}

	@Override
	public boolean hasPermission(IEmfModulePermission<AGCoreModuleInstance> permission, IBasicUser user) {

		throw new UnsupportedOperationException("Core module is not a standard module, yet.");
	}

	@Override
	public ItemId getItemId() {

		return super.getItemId();
	}

	/**
	 * Returns the portal URL with protocol and hostname.
	 *
	 * @return the portal URL (never null)
	 */
	public String getPortalUrl() {

		return String.format("%s://%s", getPortalProtocol(), getPortalHost());
	}

	/**
	 * Returns the portal application name with leading and trailing slashes.
	 *
	 * @return the portal application path (never null)
	 */
	public String getPortalApplicationPath() {

		return '/' + Trim.trim(getPortalApplication(), '/') + '/';
	}
}
