package com.softicar.platform.emf.permission.finder;

import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.emf.permission.statik.IEmfStaticPermission;
import com.softicar.platform.emf.table.IEmfTable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class EmfStaticPermissionFinder {

	private final IEmfTable<?, ?, ?> table;

	public EmfStaticPermissionFinder(IEmfTable<?, ?, ?> table) {

		this.table = Objects.requireNonNull(table);
	}

	public Collection<IEmfStaticPermission<?>> findStaticPermissions() {

		return new EmfStaticPermissionFieldFinder(table)//
			.findDeclaredFields()
			.stream()
			.map(this::getPermissionFromField)
			.collect(Collectors.toList());
	}

	private IEmfStaticPermission<?> getPermissionFromField(Field field) {

		try {
			return CastUtils.cast(field.get(table));
		} catch (IllegalArgumentException | IllegalAccessException exception) {
			throw new RuntimeException(exception);
		}
	}
}
