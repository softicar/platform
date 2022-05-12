package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.singleton.SingletonSetScope;
import com.softicar.platform.common.servlet.service.IHttpService;
import com.softicar.platform.core.module.log.LogDb;
import com.softicar.platform.core.module.web.service.environment.IWebServiceEnvironment;
import com.softicar.platform.core.module.web.service.environment.WebServiceDefaultEnvironment;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import java.util.Collection;
import java.util.EventListener;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;
import java.util.function.Consumer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebServiceMasterService implements IHttpService {

	private final Map<UUID, IHttpService> serviceMap;
	private IWebServiceEnvironment environment;

	public WebServiceMasterService() {

		this.serviceMap = new TreeMap<>();
		this.environment = new WebServiceDefaultEnvironment();
	}

	public WebServiceMasterService setEnvironment(IWebServiceEnvironment environment) {

		this.environment = environment;
		return this;
	}

	@Override
	public void initialize(Consumer<EventListener> listeners) {

		for (IWebServiceFactory factory: getAllServiceFactories()) {
			var uuid = factory.getAnnotatedUuid();
			var service = factory.createInstance();
			serviceMap.put(uuid, service);
		}

		serviceMap.values().forEach(service -> service.initialize(listeners));
	}

	@Override
	public void destroy() {

		serviceMap.values().forEach(service -> service.destroy());
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {

		try (SingletonSetScope scope = new SingletonSetScope()) {
			// get service id
			var serviceUuid = new WebServiceIdFetcher().getServiceIdOrThrow(request);

			environment.setupEnvironment();

			// get service
			var service = Optional//
				.ofNullable(serviceMap.get(serviceUuid))
				.orElseThrow(() -> new SofticarDeveloperException("Illegal service ID: %s", serviceUuid));

			// handle request
			LogDb.setProcessClass(service.getClass());
			service.service(request, response);

			environment.cleanupEnvironment();
		} catch (Exception throwable) {
			throwable.printStackTrace();
			throw new SofticarDeveloperException(throwable);
		}
	}

	private static Collection<IWebServiceFactory> getAllServiceFactories() {

		return EmfSourceCodeReferencePoints.getReferencePoints(IWebServiceFactory.class);
	}
}
