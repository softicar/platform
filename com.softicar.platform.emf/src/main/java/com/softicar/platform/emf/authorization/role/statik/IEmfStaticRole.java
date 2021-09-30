package com.softicar.platform.emf.authorization.role.statik;

import com.softicar.platform.common.core.uuid.IUuidAnnotated;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import java.util.Collection;

/**
 * Represents a predefined {@link IEmfRole} with a unique identity.
 *
 * @author Alexander Schmidt
 */
public interface IEmfStaticRole<T> extends IEmfRole<T>, Comparable<IEmfStaticRole<T>>, IUuidAnnotated {

	/**
	 * Returns all inherited {@link IEmfRole} objects.
	 *
	 * @return all inherited roles (never <i>null</i>)
	 */
	Collection<IEmfRole<T>> getInheritedRoles();
}
