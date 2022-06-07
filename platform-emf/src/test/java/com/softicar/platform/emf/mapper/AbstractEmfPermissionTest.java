package com.softicar.platform.emf.mapper;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.permission.EmfPermission;
import com.softicar.platform.emf.permission.IEmfPermission;
import java.util.Set;
import java.util.TreeSet;

public abstract class AbstractEmfPermissionTest extends AbstractEmfTest {

	protected static final IEmfPermission<PermissionEntity> OPERATOR = new EmfPermission<>(//
		IDisplayString.create("Operator"),
		PermissionEntity::isOperator);
	protected static final IEmfPermission<PermissionEntity> GUEST = new EmfPermission<>(//
		IDisplayString.create("Guest"),
		PermissionEntity::isGuest);

	protected static class PermissionEntity {

		private final Set<IBasicUser> operators;
		private final Set<IBasicUser> guests;

		public PermissionEntity() {

			this.operators = new TreeSet<>();
			this.guests = new TreeSet<>();
		}

		public PermissionEntity addOperator(IBasicUser user) {

			operators.add(user);
			return this;
		}

		public PermissionEntity addGuest(IBasicUser user) {

			guests.add(user);
			return this;
		}

		public boolean isOperator(IBasicUser user) {

			return operators.contains(user);
		}

		public boolean isGuest(IBasicUser user) {

			return guests.contains(user);
		}
	}
}
