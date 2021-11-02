package com.softicar.platform.ajax.server.standalone;

import com.softicar.platform.common.core.exception.CheckedExceptions;
import com.softicar.platform.common.core.logging.Log;
import java.util.Objects;
import javax.servlet.http.HttpServlet;
import org.eclipse.jetty.http.HttpCookie.SameSite;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Base class for stand-alone {@link HttpServlet} servers.
 *
 * @author Oliver Richers
 */
public abstract class AbstractStandAloneServletServer<T> {

	private static final int DEFAULT_PORT = 0;
	private static final String DEFAULT_CONTEXT_NAME = "context";
	private static final String DEFAULT_REQUEST_STRING = "";
	private int port;
	private String contextName;
	private String requestString;

	public AbstractStandAloneServletServer() {

		this.port = DEFAULT_PORT;
		this.contextName = DEFAULT_CONTEXT_NAME;
		this.requestString = DEFAULT_REQUEST_STRING;
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

		this.port = port;
		return getThis();
	}

	/**
	 * Defines the context name, also called web application name.
	 * <p>
	 * The default context name is {@value #DEFAULT_CONTEXT_NAME}.
	 *
	 * @param contextName
	 *            the context name (never null)
	 * @return this object
	 */
	public T setContextName(String contextName) {

		this.contextName = Objects.requireNonNull(contextName);
		return getThis();
	}

	/**
	 * Defines the default request string, relative to the context name.
	 * <p>
	 * A redirection handler is installed, pointing from the URL root path to
	 * this request string, that is, <i>http://localhost:$port/</i> is mapped to
	 * <i>http://localhost:$port/$context/$requestString</i>.
	 * <p>
	 * The default request string is {@value #DEFAULT_REQUEST_STRING}.
	 *
	 * @param requestString
	 *            the default request string (never null)
	 * @return this object
	 */
	public T setRequestString(String requestString) {

		this.requestString = Objects.requireNonNull(requestString);
		return getThis();
	}

	// ------------------------------ control ------------------------------ //

	/**
	 * Starts this server in a new {@link Thread}.
	 * <p>
	 * The server will start listening for new connections and handle requests.
	 *
	 * @return a new {@link StandAloneServletServerHandle} (never null)
	 */
	@SuppressWarnings("resource")
	public StandAloneServletServerHandle start() {

		Server server = createServer();
		ServerConnector connector = addConnector(server);
		CheckedExceptions.wrap(() -> server.start());
		return new StandAloneServletServerHandle(server, connector);
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

		try (StandAloneServletServerHandle serverHandle = start()) {
			printServerAddress(serverHandle);
			serverHandle.join();
		}
	}

	// ------------------------------ abstract ------------------------------ //

	protected abstract T getThis();

	protected abstract ServletHolder getServletHolder();

	// ------------------------------ private ------------------------------ //

	private Server createServer() {

		// create and setup context handler
		ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		contextHandler.setContextPath("/" + contextName);
		contextHandler.addServlet(getServletHolder(), "/*");
		contextHandler.getSessionHandler().setSameSite(SameSite.STRICT);

		// create handler list
		HandlerList handlerList = new HandlerList();
		handlerList.addHandler(new RedirectionHandler("/", String.format("/%s/%s", contextName, requestString)));
		handlerList.addHandler(contextHandler);

		// create server
		Server server = new Server();
		server.setHandler(handlerList);
		return server;
	}

	private ServerConnector addConnector(Server server) {

		ServerConnector connector = new ServerConnector(server);
		connector.setPort(port);
		server.addConnector(connector);
		return connector;
	}

	private void printServerAddress(StandAloneServletServerHandle serverHandle) {

		@SuppressWarnings("resource")
		int localPort = serverHandle.getConnector().getLocalPort();
		Log.finfo("Server started at http://localhost:%s", localPort);
		Log.finfo("Full URL:");
		Log.finfo("http://localhost:%s/%s/%s", localPort, contextName, requestString);
	}
}
