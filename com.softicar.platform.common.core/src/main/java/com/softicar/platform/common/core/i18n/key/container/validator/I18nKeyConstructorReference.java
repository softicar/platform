package com.softicar.platform.common.core.i18n.key.container.validator;

import com.softicar.platform.common.core.i18n.I18n0;
import com.softicar.platform.common.core.i18n.I18n1;
import com.softicar.platform.common.core.i18n.I18n2;
import com.softicar.platform.common.core.i18n.I18n3;
import com.softicar.platform.common.core.i18n.I18n4;
import com.softicar.platform.common.core.i18n.I18n5;
import com.softicar.platform.common.core.i18n.key.II18nKey;
import com.softicar.platform.common.core.java.method.reference.JavaMethodReference;
import java.util.Arrays;
import java.util.Collection;

/**
 * Represents a {@link JavaMethodReference} to the constructor of an
 * {@link II18nKey} class.
 *
 * @author Oliver Richers
 */
class I18nKeyConstructorReference extends JavaMethodReference {

	private static final Collection<I18nKeyConstructorReference> I18N_CONSTRUCTOR_REFERENCES = Arrays
		.asList(//
			new I18nKeyConstructorReference(I18n0.class),
			new I18nKeyConstructorReference(I18n1.class),
			new I18nKeyConstructorReference(I18n2.class),
			new I18nKeyConstructorReference(I18n3.class),
			new I18nKeyConstructorReference(I18n4.class),
			new I18nKeyConstructorReference(I18n5.class));

	public I18nKeyConstructorReference(Class<?> i18nKeyClass) {

		super(i18nKeyClass, "<init>", "(Ljava/lang/String;)V");
	}

	public static Collection<I18nKeyConstructorReference> getAll() {

		return I18N_CONSTRUCTOR_REFERENCES;
	}
}
