package com.softicar.platform.common.io.resource.supplier.container.validator;

import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerFieldMustBeFinalError;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerFieldMustBePublicError;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerFieldMustBeStaticError;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerFieldOrderError;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerFieldValueUndefinedError;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerIllegalFieldNameError;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerMustBeInterfaceError;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerMustNotDeclareMethodError;
import com.softicar.platform.common.core.constant.container.validator.error.ConstantContainerMustNotExtendOtherClassError;
import com.softicar.platform.common.core.constant.container.validator.result.ConstantContainerValidatorResult;
import com.softicar.platform.common.core.constant.container.validator.result.ConstantContainerValidatorResultAsserter;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.container.validator.error.ResourceSupplierContainerFieldDefaultResourceIllegalFilenameError;
import com.softicar.platform.common.io.resource.supplier.container.validator.error.ResourceSupplierContainerFieldDefaultResourceNotFoundError;
import com.softicar.platform.common.io.resource.supplier.container.validator.error.ResourceSupplierContainerFieldResourceKeyIllegalBasenameError;
import com.softicar.platform.common.io.resource.supplier.container.validator.error.ResourceSupplierContainerFieldResourceKeyUnexpectedPackageError;
import com.softicar.platform.common.io.resource.supplier.container.validator.error.ResourceSupplierContainerResourceKeyAmbiguousError;
import org.junit.Assert;
import org.junit.Test;

public class ResourceSupplierContainerValidatorTest extends Assert {

	@Test
	public void testWithValidResourceSupplierContainerClass() {

		validate(ResourceSupplierTestContainer.class)//
			.assertNoErrors();
	}

	@Test
	public void testWithExtendsAndImplements() {

		validate(ResourceSupplierTestContainerWithExtendsAndImplements.class)//
			.assertError(ConstantContainerMustBeInterfaceError.class)
			.assertError(ConstantContainerMustNotExtendOtherClassError.class)
			.assertError(ConstantContainerMustNotDeclareMethodError.class)
			.assertNoErrors();
	}

	@Test
	public void testWithIllegalFieldModifiers() {

		validate(ResourceSupplierTestContainerWithIllegalFieldModifiers.class)//
			.assertError(ConstantContainerFieldMustBeFinalError.class, "NON_FINAL")
			.assertError(ConstantContainerFieldMustBeStaticError.class, "NON_STATIC")
			.assertError(ConstantContainerFieldMustBePublicError.class, "VISIBILITY_PRIVATE")
			.assertError(ConstantContainerFieldMustBePublicError.class, "VISIBILITY_PROTECTED")
			.assertError(ConstantContainerFieldMustBePublicError.class, "VISIBILITY_DEFAULT")
			// unavoidable errors:
			.assertError(ConstantContainerMustBeInterfaceError.class)
			.assertError(ConstantContainerMustNotExtendOtherClassError.class)
			.assertError(ConstantContainerFieldValueUndefinedError.class, "NON_STATIC")
			.assertNoErrors();
	}

	@Test
	public void testWithIllegalFieldNames() {

		validate(ResourceSupplierTestContainerWithIllegalFieldNames.class)//
			.assertError(ConstantContainerIllegalFieldNameError.class, "_LEADING_UNSERSCORE")
			.assertError(ConstantContainerIllegalFieldNameError.class, "DOUBLE__UNSERSCORE")
			.assertError(ConstantContainerIllegalFieldNameError.class, "TRAILING_UNSERSCORE_")
			.assertError(ConstantContainerIllegalFieldNameError.class, "lower_case")
			.assertNoErrors();
	}

	@Test
	public void testWithIllegalRessourceName() {

		validate(ResourceSupplierTestContainerWithIllegalResourceName.class)//
			.assertError(ResourceSupplierContainerFieldResourceKeyIllegalBasenameError.class, "ILLEGAL")
			.assertError(ResourceSupplierContainerFieldDefaultResourceIllegalFilenameError.class, "ILLEGAL")
			.assertNoErrors();
	}

	@Test
	public void testWithIllegalFieldOrder() {

		validate(ResourceSupplierTestContainerWithIllegalFieldOrder.class)//
			.assertError(ConstantContainerFieldOrderError.class, "A")
			.assertError(ConstantContainerFieldOrderError.class, "X_Y")
			.assertNoErrors();
	}

	@Test
	public void testWithMissingResource() {

		validate(ResourceSupplierTestContainerWithMissingResource.class)//
			.assertError(ResourceSupplierContainerFieldDefaultResourceNotFoundError.class, "MISSING")
			.assertNoErrors();
	}

	@Test
	public void testWithUnexpectedPackage() {

		validate(ResourceSupplierTestContainerUnexpectedPackage.class)//
			.assertError(ResourceSupplierContainerFieldResourceKeyUnexpectedPackageError.class, "YYY")
			.assertError(ResourceSupplierContainerFieldDefaultResourceNotFoundError.class, "YYY")
			.assertNoErrors();
	}

	@Test
	public void testWithAmbiguousResourceKey() {

		validate(ResourceSupplierTestContainerWithAmbiguousResourceKey.class)//
			.assertError(ResourceSupplierContainerResourceKeyAmbiguousError.class, "STYLE_CSS")
			.assertError(ResourceSupplierContainerResourceKeyAmbiguousError.class, "STYLE_JS")
			.assertNoErrors();
	}

	private ConstantContainerValidatorResultAsserter validate(Class<?> containerClass) {

		var result = new ConstantContainerValidatorResult<IResourceSupplier>();
		new ResourceSupplierContainerValidator(containerClass).validate(result);
		return new ConstantContainerValidatorResultAsserter(result);
	}
}
