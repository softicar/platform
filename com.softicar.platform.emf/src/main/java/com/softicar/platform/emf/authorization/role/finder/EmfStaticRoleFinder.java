package com.softicar.platform.emf.authorization.role.finder;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.emf.authorization.role.statik.IEmfStaticRole;
import com.softicar.platform.emf.table.IEmfTable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class EmfStaticRoleFinder {

	private final IEmfTable<?, ?, ?> table;

	public EmfStaticRoleFinder(IEmfTable<?, ?, ?> table) {

		this.table = Objects.requireNonNull(table);
	}

	public Collection<IEmfStaticRole<?>> findStaticRoles() {

		return new EmfStaticRoleFieldFinder(table)//
			.findDeclaredFields()
			.stream()
			.map(this::getRoleFromField)
			.collect(Collectors.toList());
	}

	private IEmfStaticRole<?> getRoleFromField(Field field) {

		try {
			return CastUtils.cast(field.get(table));
		} catch (IllegalArgumentException | IllegalAccessException exception) {
			throw new RuntimeException(exception);
		}
	}
}
