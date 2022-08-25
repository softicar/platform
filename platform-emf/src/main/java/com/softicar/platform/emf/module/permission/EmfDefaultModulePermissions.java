package com.softicar.platform.emf.module.permission;

import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.module.IEmfModuleInstance;

/**
 * Provides factory methods for the default {@link IEmfModulePermission}
 * objects.
 *
 * @author Oliver Richers
 */
public class EmfDefaultModulePermissions {

	public static <I extends IEmfModuleInstance<I>> IEmfModulePermission<I> getModuleAdministration() {

		return new EmfModulePermissionBuilder<I>()//
			.setUuid("7f61eed0-c9c1-4ac3-a84d-254c8ddb3a6d")
			.setTitle(EmfI18n.ADMINISTRATION)
			.build();
	}

	public static <I extends IEmfModuleInstance<I>> IEmfModulePermission<I> getModuleOperation() {

		return new EmfModulePermissionBuilder<I>()//
			.setUuid("197bcbf7-0003-4552-8f4d-33d4f1d72a0b")
			.setTitle(EmfI18n.OPERATION)
			.impliedBy(getModuleAdministration())
			.build();
	}

	public static <I extends IEmfModuleInstance<I>> IEmfModulePermission<I> getModuleView() {

		return new EmfModulePermissionBuilder<I>()//
			.setUuid("a3525bb1-7aef-43c3-996e-8552957bf5d8")
			.setTitle(EmfI18n.VIEW)
			.impliedBy(getModuleOperation())
			.build();
	}
}
