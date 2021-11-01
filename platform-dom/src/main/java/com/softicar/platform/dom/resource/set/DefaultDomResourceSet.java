package com.softicar.platform.dom.resource.set;

import com.softicar.platform.common.core.constant.container.field.ConstantContainerFieldExtractor;
import com.softicar.platform.common.core.constant.container.field.IConstantContainerField;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.container.ResourceSupplierContainer;
import com.softicar.platform.common.io.resource.key.IResourceKey;
import com.softicar.platform.common.io.resource.key.ResourceKey;
import com.softicar.platform.common.io.resource.static_.IStaticResourceSupplier;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A singleton {@link IDomResourceSet} that provides bundled, static default
 * resources.
 *
 * @author Alexander Schmidt
 */
public class DefaultDomResourceSet implements IDomResourceSet {

	private static final IDomResourceSet SINGLETON = new DefaultDomResourceSet();
	private final Map<IResourceKey, IResource> resources;

	/**
	 * The {@link DefaultDomResourceSet} singleton instance.
	 *
	 * @return the {@link DefaultDomResourceSet} (never <i>null</i>)
	 */
	public static IDomResourceSet getInstance() {

		return SINGLETON;
	}

	private DefaultDomResourceSet() {

		this.resources = new HashMap<>();
		initialize();
	}

	@Override
	public Optional<IResource> getResource(IResourceKey resourceKey) {

		return Optional.ofNullable(resources.get(resourceKey));
	}

	private void initialize() {

		for (Class<?> containerClass: loadContainerClasses()) {
			for (var field: extractFields(containerClass)) {
				CastUtils//
					.tryCast(field.getValue(), IStaticResourceSupplier.class)
					.map(IStaticResourceSupplier::getStaticResource)
					.ifPresent(resource -> addResource(containerClass, resource));
			}
		}
	}

	private List<Class<?>> loadContainerClasses() {

		try (ScanResult scanResult = new ClassGraph().ignoreParentClassLoaders().enableAnnotationInfo().scan()) {
			return scanResult//
				.getClassesWithAnnotation(ResourceSupplierContainer.class.getCanonicalName())
				.loadClasses();
		}
	}

	private Collection<IConstantContainerField<IResourceSupplier>> extractFields(Class<?> containerClass) {

		return new ConstantContainerFieldExtractor<>(containerClass, IResourceSupplier.class).extractFields();
	}

	private void addResource(Class<?> containerClass, IResource resource) {

		resource//
			.getFilename()
			.ifPresent(filename -> resources.put(new ResourceKey(containerClass, filename), resource));
	}
}
