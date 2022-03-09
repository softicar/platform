package com.softicar.platform.ajax.document.service;

import com.softicar.platform.ajax.AjaxI18n;
import com.softicar.platform.ajax.document.IAjaxDocument;
import com.softicar.platform.ajax.engine.JavascriptStatementList;
import com.softicar.platform.ajax.request.IAjaxRequest;
import com.softicar.platform.ajax.service.AbstractAjaxService;
import com.softicar.platform.common.core.exceptions.SofticarIOException;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.INullaryVoidFunction;
import com.softicar.platform.common.io.writer.IManagedPrintWriter;
import com.softicar.platform.common.io.writer.ManagedPrintWriter;
import com.softicar.platform.common.string.charset.Charsets;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Contains some utility functions.
 *
 * @author Oliver Richers
 */
public abstract class AbstractAjaxDocumentService extends AbstractAjaxService {

	protected final IAjaxDocument document;
	protected final JavascriptStatementList statementList;
	private final StringBuilder outputBuffer;
	private final IManagedPrintWriter outputWriter;
	private boolean hiddenFrame;

	protected AbstractAjaxDocumentService(IAjaxRequest request, IAjaxDocument document) {

		super(request);

		this.document = document;
		this.statementList = new JavascriptStatementList();
		this.outputBuffer = new StringBuilder();
		this.outputWriter = new ManagedPrintWriter(outputBuffer, "\r\n");
		this.hiddenFrame = false;
	}

	protected void executePayloadCode(INullaryVoidFunction payloadCode) {

		try {
			payloadCode.apply();
		} catch (Exception exception) {
			document.getBody().executeAlert(getDisplayMessage(exception));
			framework.getAjaxStrategy().logException(exception, request);
		}
	}

	private IDisplayString getDisplayMessage(Exception exception) {

		if (exception instanceof SofticarUserException) {
			return IDisplayString.create(exception.getLocalizedMessage());
		} else {
			return AjaxI18n.AN_INTERNAL_PROGRAM_ERROR_OCCURRED;
		}
	}

	protected void setHiddenFrame(boolean hiddenFrame) {

		this.hiddenFrame = hiddenFrame;
	}

	// -------------------- JavaScript code -------------------- /

	protected void flushJavaScript() {

		new AjaxDocumentJavascriptWriter(statementList)//
			.setHiddenFrame(hiddenFrame)
			.setDebug(request.isDebug())
			.writeTo(outputWriter);

		this.statementList.clear();
	}

	protected IManagedPrintWriter getOutputWriter() {

		return outputWriter;
	}

	// -------------------- execution clean-up -------------------- /

	protected void flushOutput() {

		try {
			// set content type
			response.setContentType("text/html; charset=UTF-8");

			// get output stream
			OutputStream outputStream = response.getOutputStream();
			if (request.isGZipSupported()) {
				response.setHeader("Content-Encoding", "gzip");
				outputStream = new GZIPOutputStream(outputStream);
			} else if (request.isDeflateSupported()) {
				response.setHeader("Content-Encoding", "deflate");
				outputStream = new DeflaterOutputStream(outputStream);
			}

			// flush to writer
			try (OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream, Charsets.UTF8)) {
				streamWriter.write(outputBuffer.toString());
				outputBuffer.setLength(0);
			}
		} catch (IOException exception) {
			throw new SofticarIOException(exception);
		}
	}
}
