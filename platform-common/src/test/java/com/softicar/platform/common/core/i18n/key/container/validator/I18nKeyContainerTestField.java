package com.softicar.platform.common.core.i18n.key.container.validator;

import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.i18n.key.II18nKey;

public class I18nKeyContainerTestField implements IConstantContainerField<II18nKey> {

	private final String name;
	private final II18nKey key;

	public I18nKeyContainerTestField(String name, II18nKey key) {

		this.name = name;
		this.key = key;
	}

	@Override
	public Class<?> getContainerClass() {

		return getClass();
	}

	@Override
	public String getName() {

		return name;
	}

	@Override
	public Class<?> getType() {

		return key.getClass();
	}

	@Override
	public II18nKey getValue() {

		return key;
	}

	@Override
	public boolean isPublic() {

		return true;
	}

	@Override
	public boolean isStatic() {

		return true;
	}

	@Override
	public boolean isFinal() {

		return true;
	}
}
