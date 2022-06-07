package com.softicar.platform.emf.permission.finder;

import com.softicar.platform.common.code.java.reflection.DeclaredFieldFinder;
import com.softicar.platform.common.code.java.reflection.TypeParameterAnalyzer;
import com.softicar.platform.common.core.utils.ReflectionUtils;
import com.softicar.platform.emf.permission.statik.IEmfStaticPermission;
import com.softicar.platform.emf.table.IEmfTable;
import java.lang.reflect.Field;

public class EmfStaticPermissionFieldFinder extends DeclaredFieldFinder {

	private final IEmfTable<?, ?, ?> table;

	public EmfStaticPermissionFieldFinder(IEmfTable<?, ?, ?> table) {

		super(table.getClass());
		this.table = table;
		setFilter(this::isStaticPermissionField);
	}

	private boolean isStaticPermissionField(Field field) {

		return ReflectionUtils.isPublicStaticFinal(field)//
				&& ReflectionUtils.isDeclaredType(field, IEmfStaticPermission.class)//
				&& isExpectedGenericType(table, field);
	}

	private boolean isExpectedGenericType(IEmfTable<?, ?, ?> table, Field permissionField) {

		return new TypeParameterAnalyzer(permissionField).hasExpectedTypeParameter(table.getValueClass());
	}
}
