package com.softicar.platform.emf.authorization.role.finder;

import com.softicar.platform.common.code.java.reflection.DeclaredFieldFinder;
import com.softicar.platform.common.code.java.reflection.TypeParameterAnalyzer;
import com.softicar.platform.common.core.utils.ReflectionUtils;
import com.softicar.platform.emf.authorization.role.statik.IEmfStaticRole;
import com.softicar.platform.emf.table.IEmfTable;
import java.lang.reflect.Field;

public class EmfStaticRoleFieldFinder extends DeclaredFieldFinder {

	private final IEmfTable<?, ?, ?> table;

	public EmfStaticRoleFieldFinder(IEmfTable<?, ?, ?> table) {

		super(table.getClass());
		this.table = table;
		setFilter(this::isStaticRoleField);
	}

	private boolean isStaticRoleField(Field field) {

		return ReflectionUtils.isPublicStaticFinal(field)//
				&& ReflectionUtils.isDeclaredType(field, IEmfStaticRole.class)//
				&& isExpectedGenericType(table, field);
	}

	private boolean isExpectedGenericType(IEmfTable<?, ?, ?> table, Field roleField) {

		return new TypeParameterAnalyzer(roleField).hasExpectedTypeParameter(table.getValueClass());
	}
}
