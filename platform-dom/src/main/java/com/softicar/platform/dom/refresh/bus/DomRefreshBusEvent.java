package com.softicar.platform.dom.refresh.bus;

import com.softicar.platform.common.container.map.identity.IdentityHashList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

class DomRefreshBusEvent implements IDomRefreshBusEvent {

	private final Collection<Object> objects;
	private final Set<Class<?>> classes;
	private boolean allChanged;

	public DomRefreshBusEvent() {

		this.objects = new IdentityHashList<>();
		this.classes = new HashSet<>();
		this.allChanged = false;
	}

	@Override
	public boolean isChanged(Object object) {

		if (allChanged) {
			return true;
		} else if (objects.contains(object)) {
			return true;
		} else {
			return classes.contains(object.getClass());
		}
	}

	@Override
	public boolean isAnyObjectChanged(Collection<Class<?>> classes) {

		if (allChanged) {
			return true;
		} else if (isAnyMatch(this.classes, classes)) {
			return true;
		} else {
			return objects//
				.stream()
				.anyMatch(object -> isAnyMatch(object, classes));
		}
	}

	@Override
	public boolean isAllChanged() {

		return allChanged;
	}

	protected DomRefreshBusEvent setChanged(Object object) {

		objects.add(object);
		return this;
	}

	protected DomRefreshBusEvent setAllChanged(Class<?> classes) {

		this.classes.add(classes);
		return this;
	}

	protected DomRefreshBusEvent setAllChanged() {

		this.allChanged = true;
		return this;
	}

	protected boolean isEmpty() {

		return objects.isEmpty() && classes.isEmpty() && !allChanged;
	}

	private static boolean isAnyMatch(Set<Class<?>> setOfClasses, Collection<Class<?>> classes) {

		return classes//
			.stream()
			.anyMatch(setOfClasses::contains);
	}

	private static boolean isAnyMatch(Object object, Collection<Class<?>> classes) {

		return classes//
			.stream()
			.anyMatch(objectClass -> objectClass.isInstance(object));
	}
}
