package com.softicar.platform.ajax.testing.selenium;

import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.testing.AjaxTestingServlet;
import com.softicar.platform.common.web.servlet.HttpServletServer;
import com.softicar.platform.common.web.servlet.HttpServletServerHandle;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.node.IDomNode;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class AjaxSeleniumTestEnvironment {

	private final Consumer<String> urlConsumer;
	private final AjaxTestingServlet servlet;
	private HttpServletServerHandle serverHandle;

	public AjaxSeleniumTestEnvironment(Consumer<String> urlConsumer) {

		this.urlConsumer = urlConsumer;
		this.servlet = new AjaxTestingServlet();
		this.serverHandle = null;
	}

	public <T extends IDomNode> T openTestNode(Supplier<T> factory) {

		return openTestNode(dummy -> factory.get());
	}

	@SuppressWarnings("resource")
	public <T extends IDomNode> T openTestNode(Function<IAjaxDocument, T> factory) {

		BufferedFactory<T> bufferedFactory = new BufferedFactory<>(factory);
		servlet.getStrategy().setNodeFactory(bufferedFactory);
		urlConsumer.accept(getPageUrl(getServerHandle().getConnector().getLocalPort()));
		CurrentDomDocument.set(bufferedFactory.getDocument());
		return bufferedFactory.getContentNode();
	}

	public void executeAfterTest() {

		if (serverHandle != null) {
			serverHandle.close();
		}
	}

	private HttpServletServerHandle getServerHandle() {

		if (serverHandle == null) {
			this.serverHandle = new HttpServletServer(servlet).setContextName("").start();
		}
		return serverHandle;
	}

	private static String getPageUrl(int port) {

		return String.format("http://%s:%s/", AjaxSeleniumTestProperties.SERVER_IP.getValue(), port);
	}

	private static class BufferedFactory<T extends IDomNode> implements Function<IAjaxDocument, IDomNode> {

		private final Function<IAjaxDocument, T> factory;
		private T contentNode;
		private IDomDocument document;

		public BufferedFactory(Function<IAjaxDocument, T> factory) {

			this.factory = factory;
			this.contentNode = null;
			this.document = null;
		}

		@Override
		public synchronized IDomNode apply(IAjaxDocument document) {

			this.document = document;
			return this.contentNode = factory.apply(document);
		}

		public synchronized T getContentNode() {

			return contentNode;
		}

		public synchronized IDomDocument getDocument() {

			return document;
		}
	}
}
