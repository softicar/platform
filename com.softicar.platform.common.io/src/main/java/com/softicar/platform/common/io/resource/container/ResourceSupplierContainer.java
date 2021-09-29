package com.softicar.platform.common.io.resource.container;

import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Designates a type as a container that provides {@link IResourceSupplier}
 * instances.
 * <p>
 * Such a container is an interface that enumerates bundled, static resource
 * files.
 *
 * @author Alexander Schmidt
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ResourceSupplierContainer {

	// nothing
}
