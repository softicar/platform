package com.softicar.platform.ajax.document;

import com.softicar.platform.ajax.document.registry.AjaxDocumentRegistry;
import com.softicar.platform.ajax.engine.AjaxDomEngine;
import com.softicar.platform.ajax.engine.JavascriptStatementList;
import com.softicar.platform.ajax.export.AjaxDomExportManager;
import com.softicar.platform.ajax.framework.IAjaxFramework;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.common.core.singleton.CurrentSingletonSet;
import com.softicar.platform.common.core.singleton.SingletonSet;
import com.softicar.platform.common.io.mime.IMimeType;
import com.softicar.platform.dom.document.AbstractDomDocument;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.node.IDomNode;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpSession;

/**
 * AJAX-based implementation of {@link IDomDocument}.
 *
 * @author Oliver Richers
 */
public class AjaxDocument extends AbstractDomDocument implements IAjaxDocument {

	private final IAjaxFramework ajaxFramework;
	private final HttpSession httpSession;
	private final IAjaxDocumentParameters documentParameters;
	private final SingletonSet singletonSet;
	private final UUID instanceUuid;
	private final AjaxDomEngine engine;
	private final AjaxDomExportManager exportManager;
	private final AjaxDocumentLogs documentLogs;

	public AjaxDocument(IAjaxRequest request) {

		this.ajaxFramework = request.getAjaxFramework();
		this.httpSession = request.getHttpSession();
		this.documentParameters = new AjaxDocumentParameters(request);
		this.singletonSet = new SingletonSet(CurrentSingletonSet.get());
		this.instanceUuid = AjaxDocumentRegistry.getInstance(request.getHttpSession()).register(this);
		this.engine = new AjaxDomEngine(this);
		this.exportManager = new AjaxDomExportManager(this);
		this.documentLogs = new AjaxDocumentLogs();
	}

	/**
	 * Determines the currently active {@link AjaxDocument} instance from
	 * {@link CurrentDomDocument}.
	 *
	 * @return an {@link Optional} of the currently active {@link AjaxDocument}
	 */
	public static Optional<AjaxDocument> getCurrentDocument() {

		return Optional//
			.ofNullable(CurrentDomDocument.get())
			.filter(AjaxDocument.class::isInstance)
			.map(AjaxDocument.class::cast);
	}

	@Override
	public void appendToBody(IDomNode node) {

		getBody().appendChild(node);
	}

	// -------------------- get methods -------------------- //

	@Override
	public AjaxDomEngine getEngine() {

		return engine;
	}

	@Override
	public IAjaxDocumentLogs getLogs() {

		return documentLogs;
	}

	@Override
	public IAjaxFramework getAjaxFramework() {

		return ajaxFramework;
	}

	@Override
	public HttpSession getHttpSession() {

		return httpSession;
	}

	@Override
	public IAjaxDocumentParameters getParameters() {

		return documentParameters;
	}

	@Override
	public SingletonSet getSingletonSet() {

		return singletonSet;
	}

	@Override
	public UUID getInstanceUuid() {

		return instanceUuid;
	}

	@Override
	public void finishRequestHandling(JavascriptStatementList statementList) {

		documentLogs.updateLastAccess();
		exportManager.finishExports();

		engine.flushCodeTo(statementList);
	}

	public OutputStream startBinaryExport(String filename, IMimeType mimeType, Charset charset) {

		return exportManager.startBinaryExport(filename, mimeType, charset);
	}
}
