package com.softicar.platform.emf.permission.statik;

import com.softicar.platform.common.core.uuid.IUuidAnnotated;
import com.softicar.platform.emf.permission.IEmfPermission;
import java.util.Collection;

/**
 * Represents a predefined {@link IEmfPermission} with a unique identity.
 *
 * @author Alexander Schmidt
 */
public interface IEmfStaticPermission<T> extends IEmfPermission<T>, Comparable<IEmfStaticPermission<T>>, IUuidAnnotated {

	/**
	 * Returns all inherited {@link IEmfPermission} objects.
	 *
	 * @return all inherited {@link IEmfPermission} objects (never <i>null</i>)
	 */
	Collection<IEmfPermission<T>> getInheritedPermissions();
}
