package com.softicar.platform.emf.authorization;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.emf.AbstractEmfTest;
import com.softicar.platform.emf.authorization.role.EmfRole;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import java.util.Set;
import java.util.TreeSet;

public abstract class AbstractEmfRoleTest extends AbstractEmfTest {

	protected static final IEmfRole<RoleEntity> OPERATOR = new EmfRole<>(//
		IDisplayString.create("Operator"),
		RoleEntity::isOperator);
	protected static final IEmfRole<RoleEntity> GUEST = new EmfRole<>(//
		IDisplayString.create("Guest"),
		RoleEntity::isGuest);

	protected static class RoleEntity {

		private final Set<IBasicUser> operators;
		private final Set<IBasicUser> guests;

		public RoleEntity() {

			this.operators = new TreeSet<>();
			this.guests = new TreeSet<>();
		}

		public RoleEntity addOperator(IBasicUser user) {

			operators.add(user);
			return this;
		}

		public RoleEntity addGuest(IBasicUser user) {

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
