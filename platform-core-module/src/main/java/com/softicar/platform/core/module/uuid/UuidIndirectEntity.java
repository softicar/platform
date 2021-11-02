package com.softicar.platform.core.module.uuid;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import com.softicar.platform.common.core.uuid.IUuidAnnotated;
import com.softicar.platform.emf.attribute.field.indirect.entity.IEmfIndirectEntity;
import java.util.Objects;

public class UuidIndirectEntity<T extends IDisplayable & IUuidAnnotated> implements IEmfIndirectEntity<AGUuid> {

	private final T element;

	public UuidIndirectEntity(T annotatedElement) {

		this.element = Objects.requireNonNull(annotatedElement);
	}

	@Override
	public IDisplayString toDisplay() {

		return element.toDisplay();
	}

	@Override
	public AGUuid getRepresentedEntity() {

		return AGUuid.getOrCreate(element.getAnnotatedUuid());
	}
}
