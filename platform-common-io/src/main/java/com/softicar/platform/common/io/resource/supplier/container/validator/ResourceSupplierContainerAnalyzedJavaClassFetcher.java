package com.softicar.platform.common.io.resource.supplier.container.validator;

import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClass;
import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClassFetcher;
import com.softicar.platform.common.core.java.classes.analyzer.AnalyzedJavaClassHierarchy;
import com.softicar.platform.common.core.java.classes.analyzer.IAnalyzedJavaClassProvider;
import com.softicar.platform.common.core.java.classes.name.JavaClassName;
import com.softicar.platform.common.core.java.classes.name.JavaClassNameDeterminer;
import com.softicar.platform.common.core.java.identifier.declaration.JavaIdentifierDeclaration;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import java.util.Optional;

public class ResourceSupplierContainerAnalyzedJavaClassFetcher extends AnalyzedJavaClassFetcher {

	private final IAnalyzedJavaClassProvider cache;

	public ResourceSupplierContainerAnalyzedJavaClassFetcher(IAnalyzedJavaClassProvider cache) {

		this.cache = cache;
		setFilter(this::isRelevant);
	}

	private boolean isRelevant(AnalyzedJavaClass javaClass) {

		return javaClass//
			.getDeclaredFields()
			.stream()
			.filter(JavaIdentifierDeclaration::isStatic)
			.anyMatch(this::isResourceSupplierField);
	}

	private boolean isResourceSupplierField(JavaIdentifierDeclaration fieldDeclaration) {

		return getJavaClass(fieldDeclaration)//
			.map(it -> new AnalyzedJavaClassHierarchy(cache).isInstance(it, new JavaClassName(IResourceSupplier.class)))
			.orElse(false);
	}

	private Optional<AnalyzedJavaClass> getJavaClass(JavaIdentifierDeclaration fieldDeclaration) {

		return new JavaClassNameDeterminer()//
			.fromFieldDescriptor(fieldDeclaration.getDescriptor())
			.map(cache::getAnalyzedClassOrNull);
	}
}
