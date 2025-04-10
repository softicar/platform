package com.softicar.platform.ajax.document;

import com.softicar.platform.ajax.engine.AjaxDomEngine;
import com.softicar.platform.ajax.engine.JavascriptStatementList;
import com.softicar.platform.ajax.framework.AjaxFramework;
import com.softicar.platform.common.core.singleton.SingletonSet;
import com.softicar.platform.dom.document.DomBody;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.node.IDomNode;
import java.util.UUID;
import jakarta.servlet.http.HttpSession;

/**
 * AJAX extension of the {@link IDomDocument} interface.
 *
 * @author Oliver Richers
 */
public interface IAjaxDocument extends IDomDocument {

	/**
	 * Appends the given {@link IDomNode} to the {@link DomBody} of this
	 * {@link IAjaxDocument}.
	 *
	 * @param node
	 *            the {@link IDomNode} to append (never <i>null</i>)
	 */
	void appendToBody(IDomNode node);

	/**
	 * Returns the {@link AjaxDomEngine} used by this {@link IAjaxDocument}.
	 *
	 * @return the {@link AjaxDomEngine} (never <i>null</i>)
	 */
	@Override
	AjaxDomEngine getEngine();

	/**
	 * Returns the {@link AjaxFramework} instance that this
	 * {@link IAjaxDocument} was created from.
	 *
	 * @return the {@link AjaxFramework} of this {@link IAjaxDocument} (never
	 *         <i>null</i>)
	 */
	AjaxFramework getAjaxFramework();

	/**
	 * Returns the {@link HttpSession} that this {@link IAjaxDocument} instance
	 * belongs to.
	 *
	 * @return the {@link HttpSession} (never <i>null</i>)
	 */
	HttpSession getHttpSession();

	/**
	 * Returns the {@link SingletonSet} instance that belongs to this
	 * {@link IAjaxDocument}.
	 *
	 * @return the {@link SingletonSet} (never <i>null</i>)
	 */
	SingletonSet getSingletonSet();

	/**
	 * Returns the {@link IAjaxDocumentParameters} that the creation of this
	 * {@link IAjaxDocument} was requested with.
	 *
	 * @return the {@link IAjaxDocumentParameters} (never <i>null</i>)
	 */
	IAjaxDocumentParameters getParameters();

	/**
	 * Returns the {@link IAjaxDocumentLogs} object of this
	 * {@link IAjaxDocument}.
	 *
	 * @return {@link IAjaxDocumentLogs} object (never <i>null</i>)
	 */
	IAjaxDocumentLogs getLogs();

	/**
	 * Returns the {@link UUID} of this {@link IAjaxDocument} instance.
	 *
	 * @return the {@link UUID} of this instance (never <i>null</i>)
	 */
	UUID getInstanceUuid();

	/**
	 * Updates the access logs, finishes all data exports and flushes the
	 * generated Javascript code into the given {@link JavascriptStatementList}.
	 *
	 * @param statementList
	 *            the {@link JavascriptStatementList} to flush the generated
	 *            Javascript code to (never <i>null</i>)
	 */
	void finishRequestHandling(JavascriptStatementList statementList);
}
