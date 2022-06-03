package com.softicar.platform.emf.test.permission;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.module.permission.IEmfModulePermission;
import com.softicar.platform.emf.permission.statik.AbstractEmfStaticPermission;
import com.softicar.platform.emf.permission.statik.EmfStaticPermissionConfiguration;
import java.util.UUID;
import java.util.function.BiPredicate;

public class EmfModuleTestPermission<I extends IEmfModuleInstance<I>> extends AbstractEmfStaticPermission<I> implements IEmfModulePermission<I> {

	private final BiPredicate<I, IBasicUser> predicate;

	public EmfModuleTestPermission(String title, BiPredicate<I, IBasicUser> predicate) {

		super(
			new EmfStaticPermissionConfiguration<I>()//
				.setUuid(UUID.randomUUID())
				.setTitle(IDisplayString.create(title)));

		this.predicate = predicate;
	}

	@Override
	public boolean test(I tableRow, IBasicUser user) {

		return predicate.test(tableRow, user);
	}
}
