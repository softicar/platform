package com.softicar.platform.emf.permission;

import com.softicar.platform.emf.mapper.IEmfTableRowMapper;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import java.util.ArrayList;
import java.util.Collection;

public class EmfPermissionAtomConverter<E> implements IEmfPermissionVisitor<E> {

	private final Collection<IEmfPermission<?>> permissions;
	private final IEmfPermission<E> permission;

	public EmfPermissionAtomConverter(IEmfPermission<E> permission) {

		this.permissions = new ArrayList<>();
		this.permission = permission;
	}

	public Collection<IEmfPermission<?>> convert() {

		permission.accept(this);
		return permissions;
	}

	@Override
	public void visitNobody() {

		permissions.add(EmfPermissions.never());
	}

	@Override
	public void visitAnybody() {

		permissions.add(EmfPermissions.always());
	}

	@Override
	public void visitAtom(IEmfPermission<E> permission) {

		permissions.add(permission);
	}

	@Override
	public void visitNot(IEmfPermission<E> permission) {

		permission.accept(this);
	}

	@Override
	public void visitAny(Collection<IEmfPermission<E>> permissions) {

		for (IEmfPermission<E> permission: permissions) {
			permission.accept(this);
		}
	}

	@Override
	public void visitAll(Collection<IEmfPermission<E>> permissions) {

		for (IEmfPermission<E> permission: permissions) {
			permission.accept(this);
		}
	}

	@Override
	public void visitConditional(IEmfPermission<E> permission, IEmfPredicate<E> predicate) {

		permission.accept(this);
	}

	@Override
	public void visitMapped(IEmfPermission<?> permission, IEmfTableRowMapper<?, ?> mapper) {

		permissions.addAll(new EmfPermissionAtomConverter<>(permission).convert());
	}
}
