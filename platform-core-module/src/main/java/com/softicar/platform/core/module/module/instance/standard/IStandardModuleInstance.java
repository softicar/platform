package com.softicar.platform.core.module.module.instance.standard;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.item.ItemId;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.core.module.user.AGUser;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.module.IUuid;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import com.softicar.platform.emf.sub.object.IEmfSubObject;

/**
 * Interface of a module instance entry that is based on EMF.
 *
 * @author Alexander Schmidt
 * @see IStandardModuleInstanceTable
 */
public interface IStandardModuleInstance<I extends IStandardModuleInstance<I>> extends IEmfModuleInstance<I>, IEmfSubObject<I, AGModuleInstance> {

	@Override
	default IUuid getModuleUuid() {

		return () -> EmfSourceCodeReferencePoints.getUuidOrThrow(table().getModuleClass());
	}

	@Override
	default ItemId getItemId() {

		return IEmfSubObject.super.getItemId();
	}

	@Override
	default IDisplayString toDisplayWithoutId() {

		return IEmfModuleInstance.super.toDisplayWithoutId();
	}

	@Override
	default boolean hasPermission(IEmfModulePermission<I> permission, IBasicUser user) {

		return AGUser.get(user).hasModulePermission(permission, pk());
	}

	@Override
	IStandardModuleInstanceTable<I> table();

}
