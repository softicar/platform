package com.softicar.platform.common.servlet.server;

import com.softicar.platform.common.core.exception.CheckedExceptions;
import com.softicar.platform.common.core.logging.Log;
import com.softicar.platform.common.core.utils.CastUtils;
import java.util.Objects;
import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServlet;
import org.eclipse.jetty.http.HttpCookie.SameSite;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletHolder.Registration;

/**
 * Base class for {@link HttpServlet} servers.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public abstract class AbstractHttpServletServer<T> {

	private final HttpServletServerConfiguration configuration;
	private Integer localPort;
	protected HttpServletServerHandle serverHandle;

	public AbstractHttpServletServer() {

		this(new HttpServletServerConfiguration());
	}

	public AbstractHttpServletServer(HttpServletServerConfiguration configuration) {

		this.configuration = Objects.requireNonNull(configuration);
		this.localPort = null;
		this.serverHandle = null;
	}

	// ------------------------------ configuration ------------------------------ //

	/**
	 * Defines the TCP port on which the server shall listen.
	 * <p>
	 * The default value is {value #DEFAULT_PORT}.
	 *
	 * @param port
	 *            the port to use, or <i>0</i> to choose a random port
	 * @return this object
	 */
	public T setPort(int port) {

		configuration.setPort(port);
		return getThis();
	}

	/**
	 * Returns the TCP port on which the started server listens.
	 * <p>
	 * Returns <i>null</i> if the server was not yet started.
	 *
	 * @return the port (may be <i>null</i>)
	 */
	public int getLocalPort() {

		return localPort;
	}

	/**
	 * Defines the context name, also called web application name.
	 * <p>
	 * The default context name is
	 * {@value HttpServletServerConfiguration#DEFAULT_CONTEXT_NAME}.
	 *
	 * @param contextName
	 *            the context name (never null)
	 * @return this object
	 */
	public T setContextName(String contextName) {

		configuration.setContextName(contextName);
		return getThis();
	}

	/**
	 * Defines the default request string, relative to the context name.
	 * <p>
	 * A redirection handler is installed, pointing from the URL root path to
	 * this request string, that is, <i>http://localhost:$port/</i> is mapped to
	 * <i>http://localhost:$port/$context/$requestString</i>.
	 * <p>
	 * The default request string is
	 * {@value HttpServletServerConfiguration#DEFAULT_REQUEST_STRING}.
	 *
	 * @param requestString
	 *            the default request string (never null)
	 * @return this object
	 */
	public T setRequestString(String requestString) {

		configuration.setRequestString(requestString);
		return getThis();
	}

	// ------------------------------ control ------------------------------ //

	/**
	 * Starts this server in a new {@link Thread}.
	 * <p>
	 * The server will start listening for new connections and handle requests.
	 *
	 * @return a new {@link HttpServletServerHandle} (never null)
	 */
	@SuppressWarnings("resource")
	public HttpServletServerHandle start() {

		ServletHolder servletHolder = prepareServletHolder();
		Server server = createServer(servletHolder);
		ServerConnector connector = addConnector(server);
		CheckedExceptions.wrap(() -> server.start());
		this.localPort = connector.getLocalPort();
		return this.serverHandle = new HttpServletServerHandle(server, connector, servletHolder);
	}

	/**
	 * Starts this server in a new {@link Thread}.
	 * <p>
	 * The server will start listening for new connections and handle requests.
	 * <p>
	 * This method will join the server {@link Thread} and thus will only return
	 * when it terminated.
	 */
	public void startAndJoin() {

		try (HttpServletServerHandle serverHandle = start()) {
			printServerAddress();
			serverHandle.join();
		}
	}

	// ------------------------------ abstract ------------------------------ //

	protected abstract T getThis();

	protected abstract ServletHolder createServletHolder();

	// ------------------------------ private ------------------------------ //

	private ServletHolder prepareServletHolder() {

		return applyDefaultMultipartConfig(createServletHolder());
	}

	private ServletHolder applyDefaultMultipartConfig(ServletHolder holder) {

		var registration = holder.getRegistration();
		if (CastUtils.tryCast(registration, Registration.class).map(Registration::getMultipartConfig).isEmpty()) {
			registration.setMultipartConfig(new MultipartConfigElement(""));
		}
		return holder;
	}

	private Server createServer(ServletHolder servletHolder) {

		String contextName = configuration.getContextName();
		String requestString = configuration.getRequestString();

		// create and setup context handler
		ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		contextHandler.setContextPath("/" + contextName);
		contextHandler.addServlet(servletHolder, "/*");
		contextHandler.getSessionHandler().setSameSite(SameSite.STRICT);

		// create handler list
		HandlerList handlerList = new HandlerList();
		if (!contextName.isEmpty()) {
			handlerList.addHandler(new HttpRedirectionHandler("/", String.format("/%s/%s", contextName, requestString)));
		}
		handlerList.addHandler(contextHandler);

		// create server
		Server server = new Server();
		server.setHandler(handlerList);
		return server;
	}

	private ServerConnector addConnector(Server server) {

		int port = configuration.getPort();
		ServerConnector connector = new ServerConnector(server);
		if (port != 0) {
			connector.setPort(port);
		}
		server.addConnector(connector);
		return connector;
	}

	private void printServerAddress() {

		Log.finfo("Server started at http://localhost:%s", localPort);
		Log.finfo("Full URL:");
		Log
			.finfo(//
				"http://localhost:%s/%s/%s",
				localPort,
				configuration.getContextName(),
				configuration.getRequestString());
	}
}
