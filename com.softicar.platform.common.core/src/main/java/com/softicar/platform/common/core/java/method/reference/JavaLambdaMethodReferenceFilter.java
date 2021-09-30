package com.softicar.platform.common.core.java.method.reference;

import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import java.lang.invoke.LambdaMetafactory;

/**
 * Default implementation of {@link IJavaMethodReferenceFilter}.
 * <p>
 * This filters all generated lambda methods and all methods of
 * {@link LambdaMetafactory}.
 *
 * @author Oliver Richers
 */
public class JavaLambdaMethodReferenceFilter implements IJavaMethodReferenceFilter {

	private static final String LAMBDA_METHOD_NAME_PREFIX = "lambda$";
	private static final JavaClassName LAMBDA_METAFACTORY_CLASSNAME = new JavaClassName(LambdaMetafactory.class);

	@Override
	public boolean test(JavaMethodReference methodReference) {

		return !isLambdaMethod(methodReference) && !isLambdaMetafactory(methodReference);
	}

	private boolean isLambdaMethod(JavaMethodReference methodReference) {

		return methodReference.getMethodName().startsWith(LAMBDA_METHOD_NAME_PREFIX);
	}

	private boolean isLambdaMetafactory(JavaMethodReference methodReference) {

		return methodReference.getOwner().equals(LAMBDA_METAFACTORY_CLASSNAME);
	}
}
