package com.softicar.platform.emf.attribute.dependency;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EmfAttributeDependencyMap<R extends IEmfTableRow<R, ?>> implements IEmfAttributeDependencyMap<R> {

	private final IEmfTable<R, ?, ?> table;
	private final Map<IEmfAttribute<R, ?>, Set<IEmfAttribute<R, ?>>> dependers;
	private final Map<IEmfAttribute<R, ?>, Set<IEmfAttribute<R, ?>>> dependees;

	public EmfAttributeDependencyMap(IEmfTable<R, ?, ?> table) {

		this.table = table;
		this.dependers = new HashMap<>();
		this.dependees = new HashMap<>();
	}

	@Override
	public Set<IEmfAttribute<R, ?>> getDependers(IEmfAttribute<R, ?> dependee) {

		return dependers.getOrDefault(dependee, Collections.emptySet());
	}

	@Override
	public Set<IEmfAttribute<R, ?>> getDependees(IEmfAttribute<R, ?> depender) {

		return dependees.getOrDefault(depender, Collections.emptySet());
	}

	public DependencySetter editAttribute(IDbField<R, ?> field) {

		return editAttribute(table.getAttribute(field));
	}

	public DependencySetter editAttribute(IEmfAttribute<R, ?> attribute) {

		return new DependencySetter(attribute);
	}

	private EmfAttributeDependencyMap<R> addDependency(IEmfAttribute<R, ?> dependee, IEmfAttribute<R, ?> depender) {

		dependers//
			.computeIfAbsent(dependee, dummy -> new HashSet<>())
			.add(depender);

		dependees//
			.computeIfAbsent(depender, dummy -> new HashSet<>())
			.add(dependee);

		return this;
	}

	public class DependencySetter {

		private final IEmfAttribute<R, ?> attribute;

		public DependencySetter(IEmfAttribute<R, ?> attribute) {

			this.attribute = attribute;
		}

		public DependencySetter addDepender(IDbField<R, ?> depender) {

			return addDepender(table.getAttribute(depender));
		}

		public DependencySetter addDepender(IEmfAttribute<R, ?> depender) {

			addDependency(attribute, depender);
			return this;
		}

		public DependencySetter setDependsOn(IDbField<R, ?> dependee) {

			return setDependsOn(table.getAttribute(dependee));
		}

		public DependencySetter setDependsOn(IEmfAttribute<R, ?> dependee) {

			addDependency(dependee, attribute);
			return this;
		}
	}
}
