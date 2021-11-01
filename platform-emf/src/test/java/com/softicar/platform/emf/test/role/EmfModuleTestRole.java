package com.softicar.platform.emf.test.role;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.authorization.role.statik.AbstractEmfStaticRole;
import com.softicar.platform.emf.authorization.role.statik.EmfStaticRoleConfiguration;
import com.softicar.platform.emf.module.IEmfModuleInstance;
import com.softicar.platform.emf.module.role.IEmfModuleRole;
import java.util.UUID;
import java.util.function.BiPredicate;

public class EmfModuleTestRole<I extends IEmfModuleInstance<I>> extends AbstractEmfStaticRole<I> implements IEmfModuleRole<I> {

	private final BiPredicate<I, IBasicUser> predicate;

	public EmfModuleTestRole(String title, BiPredicate<I, IBasicUser> predicate) {

		super(
			new EmfStaticRoleConfiguration<I>()//
				.setUuid(UUID.randomUUID())
				.setTitle(IDisplayString.create(title)));

		this.predicate = predicate;
	}

	@Override
	public boolean test(I tableRow, IBasicUser user) {

		return predicate.test(tableRow, user);
	}
}
