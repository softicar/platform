package com.softicar.platform.emf.attribute.dependency;

import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Set;

public interface IEmfAttributeDependencyMap<R extends IEmfTableRow<R, ?>> {

	Set<IEmfAttribute<R, ?>> getDependers(IEmfAttribute<R, ?> dependee);

	Set<IEmfAttribute<R, ?>> getDependees(IEmfAttribute<R, ?> depender);
}
