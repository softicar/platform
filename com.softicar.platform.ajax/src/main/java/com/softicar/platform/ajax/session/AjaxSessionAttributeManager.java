package com.softicar.platform.ajax.session;

import com.softicar.platform.ajax.request.IAjaxRequest;
import java.util.Optional;
import java.util.function.Supplier;
import javax.servlet.http.HttpSession;

/**
 * A class to safely add or retrieve {@link Class} instances from the attributes
 * of an {@link HttpSession}.
 *
 * @author Oliver Richers
 */
public class AjaxSessionAttributeManager {

	private final HttpSession session;

	public AjaxSessionAttributeManager(IAjaxRequest request) {

		this(request.getHttpSession());
	}

	public AjaxSessionAttributeManager(HttpSession session) {

		this.session = session;
	}

	/**
	 * Retrieves the instance of the given {@link Class} from the
	 * {@link HttpSession} attributes.
	 * <p>
	 * If the instance does not exist, it is created and added using the
	 * supplied factory.
	 * <p>
	 * The canonical name of the the instance class is used as the name of the
	 * attribute.
	 *
	 * @param <T>
	 *            the instance type
	 * @param instanceClass
	 *            the instance {@link Class} (never <i>null</i>)
	 * @param factory
	 *            the factory to create the instance if it does not exist yet
	 *            (never <i>null</i>)
	 * @return the instance (never <i>null</i>)
	 */
	public <T> T getOrAddInstance(Class<T> instanceClass, Supplier<T> factory) {

		synchronized (session) {
			return getInstance(instanceClass)//
				.orElseGet(() -> {
					T instance = factory.get();
					setInstance(instanceClass, instance);
					return instance;
				});
		}
	}

	/**
	 * Returns the instance of the given {@link Class} from the
	 * {@link HttpSession} attributes.
	 *
	 * @param <T>
	 *            the instance type
	 * @param instanceClass
	 *            the instance {@link Class} (never <i>null</i>)
	 * @return the instance as {@link Optional}
	 */
	public <T> Optional<T> getInstance(Class<T> instanceClass) {

		synchronized (session) {
			return Optional.ofNullable(instanceClass.cast(session.getAttribute(instanceClass.getCanonicalName())));
		}
	}

	/**
	 * Assigns the given instance for the given instance {@link Class} as
	 * {@link HttpSession} attribute.
	 *
	 * @param <T>
	 *            the instance type
	 * @param instanceClass
	 *            the instance {@link Class} (never <i>null</i>)
	 * @param instance
	 *            the instance (never <i>null</i>)
	 */
	public <T> void setInstance(Class<T> instanceClass, T instance) {

		synchronized (session) {
			session.setAttribute(instanceClass.getCanonicalName(), instance);
		}
	}
}
