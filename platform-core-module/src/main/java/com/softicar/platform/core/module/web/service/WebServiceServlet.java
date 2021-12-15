package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.core.singleton.SingletonSetScope;
import com.softicar.platform.core.module.log.LogDb;
import com.softicar.platform.core.module.web.service.environment.IWebServiceEnvironment;
import com.softicar.platform.core.module.web.service.environment.WebServiceDefaultEnvironment;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import java.util.Collection;
import java.util.UUID;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Central {@link HttpServlet} for all web-services.
 * <p>
 * WARNING: DO NOT RENAME OR MOVE THIS CLASS.
 * <p>
 * This class is referenced via canonical name, from the {@code web.xml} files
 * of consuming projects.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class WebServiceServlet extends HttpServlet {

	private static final String ID_PARAMETER_NAME = "id";
	private IWebServiceEnvironment environment;

	public WebServiceServlet() {

		this.environment = new WebServiceDefaultEnvironment();
	}

	public static String getIdParameterName() {

		return ID_PARAMETER_NAME;
	}

	public WebServiceServlet setEnvironment(IWebServiceEnvironment environment) {

		this.environment = environment;
		return this;
	}

	@Override
	public void init() {

		getAllServiceInstances().forEach(service -> service.initialize(getServletContext()));
	}

	@Override
	public void destroy() {

		getAllServiceInstances().forEach(service -> service.destroy());
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {

		try (SingletonSetScope scope = new SingletonSetScope()) {
			// get service id
			String serviceUuid = new ServiceIdFetcher().getServiceIdOrThrow(request);

			environment.setupEnvironment();

			// get web service
			IWebService webService = EmfSourceCodeReferencePoints//
				.getReferencePointOrThrow(UUID.fromString(serviceUuid), IWebService.class);

			// handle request
			LogDb.setProcessClass(webService.getClass());
			webService.service(request, response);

			environment.cleanupEnvironment();
		} catch (Exception throwable) {
			throwable.printStackTrace();
			throw new SofticarDeveloperException(throwable);
		}
	}

	private static Collection<IWebService> getAllServiceInstances() {

		return EmfSourceCodeReferencePoints.getReferencePoints(IWebService.class);
	}

	private static class ServiceIdFetcher {

		public String getServiceIdOrThrow(HttpServletRequest request) {

			String serviceId = getServiceIdOrNullFromParameters(request);
			if (serviceId == null) {
				throw new SofticarDeveloperException("Service ID not specified.");
			} else {
				return serviceId;
			}
		}

		private String getServiceIdOrNullFromParameters(HttpServletRequest request) {

			return request.getParameter(ID_PARAMETER_NAME);
		}
	}
}
