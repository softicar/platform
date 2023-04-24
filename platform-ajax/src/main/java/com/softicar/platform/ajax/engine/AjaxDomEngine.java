package com.softicar.platform.ajax.engine;

import com.softicar.platform.ajax.document.AjaxDocument;
import com.softicar.platform.ajax.engine.elements.AjaxSessionTimeoutDialog;
import com.softicar.platform.ajax.engine.elements.AjaxWorkingIndicator;
import com.softicar.platform.ajax.engine.resource.link.AjaxResourceLinkRegistry;
import com.softicar.platform.ajax.framework.AjaxFramework;
import com.softicar.platform.ajax.resource.registry.AjaxResourceRegistry;
import com.softicar.platform.common.container.iterable.Iterables;
import com.softicar.platform.common.container.list.ListFactory;
import com.softicar.platform.common.core.exceptions.SofticarDeveloperException;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.IResourceUrl;
import com.softicar.platform.common.string.Imploder;
import com.softicar.platform.dom.attribute.DomAttribute;
import com.softicar.platform.dom.attribute.IDomAttribute;
import com.softicar.platform.dom.document.IDomDocument;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomForm;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.elements.DomLink.Relationship;
import com.softicar.platform.dom.elements.popup.IDomPopupFrame;
import com.softicar.platform.dom.engine.DomPopupOffsetUnit;
import com.softicar.platform.dom.engine.DomPopupXAlign;
import com.softicar.platform.dom.engine.DomPopupYAlign;
import com.softicar.platform.dom.engine.IDomEngine;
import com.softicar.platform.dom.engine.IDomExport;
import com.softicar.platform.dom.engine.IDomScriptLibrary;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.DomModifier;
import com.softicar.platform.dom.event.IDomAutoEventHandler;
import com.softicar.platform.dom.event.IDomDropEventHandler;
import com.softicar.platform.dom.event.IDomEventHandler;
import com.softicar.platform.dom.event.timeout.IDomTimeoutNode;
import com.softicar.platform.dom.input.DomOption;
import com.softicar.platform.dom.input.DomSelect;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.style.ICssClass;
import com.softicar.platform.dom.utils.JavascriptEscaping;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This engine maps the manipulation of the {@link IDomDocument} to the web
 * browser using JavaScript.
 *
 * @author Oliver Richers
 */
public class AjaxDomEngine implements IDomEngine {

	private static final String[] directAttributes = {
			"async",
			"defer",
			"multiple",
			"value",
			"type",
			"target",
			"action",
			"method",
			"onclick",
			"onkeypress",
			"onkeydown",
			"onkeyup",
			"onchange",
			"onsubmit",
			"size",
			"selected",
			"checked",
			"name" };
	private static final Set<String> directAttributesSet = new TreeSet<>(Arrays.asList(directAttributes));
	private final JavascriptStatementList updateCodeJS;
	private final Set<String> loadedScriptLibraries;
	private final AjaxDocument document;
	private final AjaxFramework ajaxFramework;
	private final AjaxResourceLinkRegistry resourceLinkRegistry;

	public AjaxDomEngine(AjaxDocument document) {

		this.loadedScriptLibraries = new TreeSet<>();
		this.updateCodeJS = new JavascriptStatementList();
		this.document = document;
		this.ajaxFramework = document.getAjaxFramework();
		this.resourceLinkRegistry = new AjaxResourceLinkRegistry(document);

		initialize();
	}

	public void setupStatusElements() {

		// session timeout dialog
		AjaxSessionTimeoutDialog timeoutDialog = new AjaxSessionTimeoutDialog();
		appendChild(document.getBody(), timeoutDialog);
		JS_call("setSessionTimeoutDialog", timeoutDialog);

		// working indicator
		AjaxWorkingIndicator workingIndicator = new AjaxWorkingIndicator();
		appendChild(document.getBody(), workingIndicator);
		JS_call("setWorkingIndicator", workingIndicator);
	}

	private void initialize() {

		JS_call("AJAX_ENGINE.initializeHead", document.getHead().getNodeId());
		JS_call("AJAX_ENGINE.initializeBody", document.getBody().getNodeId());

		document.setMaximumExistingNodeCount(ajaxFramework.getSettings().getMaximumExistingNodeCount());
	}

	/**
	 * Flushes all generated Javascript code to the given
	 * {@link JavascriptStatementList}.
	 * <p>
	 * The {@link JavascriptStatementList} held by this {@link AjaxDomEngine} is
	 * cleared.
	 *
	 * @param output
	 *            the {@link JavascriptStatementList} to flush to (never
	 *            <i>null</i>)
	 */
	public void flushCodeTo(JavascriptStatementList output) {

		output.appendStatements(updateCodeJS);
		updateCodeJS.clear();
	}

	// -------------------------------- node creation -------------------------------- //

	@Override
	public void createElement(int nodeId, DomElementTag elementTag) {

		JS_call("e", nodeId, elementTag.getName());
		rememberCreation(CreationType.ELEMENT, nodeId, elementTag.getName());
	}

	@Override
	public void createTextNode(int nodeId, String text) {

		JS_call("t", nodeId, text);
		rememberCreation(CreationType.TEXT, nodeId, text);
	}

	// -------------------------------- nodes -------------------------------- //

	@Override
	public void appendChild(IDomNode parent, IDomNode child) {

		if (child.getNodeId() == lastCreationNodeID) {
			if (lastCreationType == CreationType.ELEMENT) {
				updateCodeJS.replaceStatement(lastCreation, "E(" + parent.getNodeId() + "," + child.getNodeId() + ",'" + lastCreationParam + "');");
			} else {
				updateCodeJS
					.replaceStatement(
						lastCreation,
						"T(" + parent.getNodeId() + "," + child.getNodeId() + ",'" + JavascriptEscaping.getEscaped(lastCreationParam) + "');");
			}
		} else {
			JS_call("a", parent.getNodeId(), child.getNodeId());
		}
		document.getDeferredInitializationController().queueAppended(child);

		lastCreationNodeID = -1;
	}

	@Override
	public void insertBefore(IDomNode parent, IDomNode child, IDomNode otherChild) {

		JS_call("i", parent.getNodeId(), child.getNodeId(), otherChild.getNodeId());
		document.getDeferredInitializationController().queueAppended(child);
		lastCreationNodeID = -1;
	}

	@Override
	public void removeChild(IDomNode parent, IDomNode child) {

		JS_call("r", parent.getNodeId(), child.getNodeId());
		lastCreationNodeID = -1;
	}

	@Override
	public void replaceChild(IDomNode parent, IDomNode newChild, IDomNode oldChild) {

		JS_call("p", parent.getNodeId(), newChild.getNodeId(), oldChild.getNodeId());
		lastCreationNodeID = -1;
	}

	// -------------------------------- node attributes -------------------------------- //

	@Override
	public void setNodeAttribute(IDomNode node, IDomAttribute attribute) {

		if (attribute.getName().equals("value")) {
			JS_call("VALUE_NODE_MAP.setValue", node, attribute);
		} else {
			if (attribute.getValue() == null || isResettingDisabledOrReadOnly(attribute)) {
				JS_removeNodeAttribute(node, attribute.getName());
			} else if (directAttributesSet.contains(attribute.getName())) {
				JS_setNodeMember(node, attribute.getName(), attribute.getValue_JS());
			} else {
				JS_call("s", node.getNodeId(), attribute.getName(), attribute);
			}
		}
	}

	@Override
	public void unsetNodeAttribute(IDomNode node, String name) {

		setNodeAttribute(node, new DomAttribute(name, null, false));
	}

	@Override
	public void setNodeStyle(IDomNode node, String name, String value) {

		JS_setNodeMember(node, "style." + name, "'" + value + "'");
	}

	@Override
	public void unsetNodeStyle(IDomNode node, String name) {

		JS_setNodeMember(node, "style." + name, "''");
	}

	@Override
	public Optional<String> getNodeStyle(IDomNode node, String name) {

		throw new UnsupportedOperationException(getClass().getSimpleName() + " does not support reading node styles.");
	}

	@Override
	public void raise(IDomNode node) {

		JS_call("AJAX_ENGINE.raise", node);
	}

	// -------------------------------- node events -------------------------------- //

	@Override
	public void listenToEvent(IDomNode node, DomEventType type) {

		if (node instanceof IDomEventHandler || node instanceof IDomAutoEventHandler) {
			JS_call("l", node.getNodeId(), type);
		} else {
			throw new SofticarDeveloperException(//
				"Specified DOM node does neither implement %s nor %s.",
				IDomEventHandler.class.getSimpleName(),
				IDomAutoEventHandler.class.getSimpleName());
		}
	}

	@Override
	public void unlistenToEvent(IDomNode node, DomEventType type) {

		JS_call("u", node.getNodeId(), type);
	}

	@Override
	public void setListenToKeys(IDomNode node, Collection<String> keys) {

		JS_call("setListenToKeys", node, keys);
	}

	@Override
	public void stopPropagation(IDomNode node, String eventName) {

		JS_callNodeFunction(node, "addEventListener", "\"" + eventName + "\"", "function(event){event.stopPropagation();}");
	}

	@Override
	public void sendBubblingKeyboardEvent(IDomNode node, String eventType, String key) {

		Objects.requireNonNull(node);
		Objects.requireNonNull(eventType);
		Objects.requireNonNull(key);

		String script = """
				var event = new KeyboardEvent('%s', {key: '%s', bubbles: true});
				document.getElementById('%s').dispatchEvent(event);
				""".formatted(eventType, key, node.getNodeIdString());
		executeScriptCode(script);
	}

	@Override
	public void setFireOnKeyUp(IDomNode node, DomEventType type, boolean enabled) {

		JS_call("KEYBOARD_EVENT_MANAGER.setFireOnKeyUp", node, type, enabled);
	}

	@Override
	public void setPreventDefaultBehavior(IDomNode node, DomEventType type, boolean enabled) {

		JS_call("KEYBOARD_EVENT_MANAGER.setPreventDefaultBehavior", node, type, enabled);
	}

	@Override
	public void setPreventDefaultOnMouseDown(IDomNode node, boolean enabled) {

		JS_call("setPreventDefaultOnMouseDown", node, enabled);
	}

	@Override
	public void setPreventDefaultOnWheel(IDomNode node, Set<DomModifier> modifiers, boolean enabled) {

		JS_call("setPreventDefaultOnWheel", node, modifiers, enabled);
	}

	@Override
	public void setCssClassOnKeyDown(IDomNode eventTarget, DomEventType eventType, IDomNode cssTargetNode, Collection<ICssClass> cssClasses) {

		cssClasses.forEach(ICssClass::beforeUse);
		String cssClassNames = cssClasses//
			.stream()
			.map(ICssClass::getName)
			.collect(Collectors.joining(" "));
		JS_call("KEYBOARD_EVENT_MANAGER.setCssClassOnKeyDown", eventTarget, eventType, cssTargetNode, cssClassNames);
	}

	@Override
	public void setClickTargetForEventDelegation(IDomNode sourceNode, DomEventType eventType, IDomNode targetNode) {

		JS_call("setClickTargetForEventDelegation", sourceNode, eventType, targetNode);
	}

	@Override
	public void unsetClickTargetForEventDelegation(IDomNode sourceNode, DomEventType eventType) {

		JS_call("setClickTargetForEventDelegation", sourceNode, eventType, false);
	}

	@Override
	public void scheduleTimeout(IDomTimeoutNode timeoutNode, Double seconds) {

		JS_call("scheduleTimeout", timeoutNode, Math.round(seconds * 1000));
	}

	@Override
	public void setHeightAndWidthOnLoad(DomImage image, IDomNode targetNode) {

		JS_call("setHeightAndWidthOnLoad", image, targetNode);
	}

	// -------------------------------- drag and drop -------------------------------- //

	@Override
	public AjaxDomEngine makeDraggable(IDomNode draggedNode, IDomNode dragHandleNode) {

		var notifyOnDrop = draggedNode instanceof IDomDropEventHandler;
		JS_call("makeDraggable", draggedNode, dragHandleNode, notifyOnDrop);
		return this;
	}

	@Override
	public AjaxDomEngine setDragLimitNode(IDomNode draggedNode, IDomNode limitNode) {

		JS_call("setDragLimitNode", draggedNode, limitNode);
		return this;
	}

	@Override
	public AjaxDomEngine setDragScrollNode(IDomNode draggedNode, IDomNode scrollNode) {

		JS_call("setDragScrollNode", draggedNode, scrollNode);
		return this;
	}

	// -------------------------------- export -------------------------------- //

	@Override
	public IDomExport createExport() {

		return new AjaxDomExport(document);
	}

	// -------------------------------- input nodes -------------------------------- //

	@Override
	public void blur(IDomNode inputNode) {

		JS_callNodeFunction(inputNode, "blur");
	}

	@Override
	public void focus(IDomNode inputNode) {

		JS_callNodeFunction(inputNode, "focus");
	}

	@Override
	public void selectText(IDomNode inputNode) {

		JS_callNodeFunction(inputNode, "select");
	}

	@Override
	public void scrollToBottom(IDomNode inputNode) {

		executeScriptCode("""
				let outputArea = document.getElementById('%s');
				if(outputArea) {
					outputArea.scrollTop = outputArea.scrollHeight;
				}
				""".formatted(inputNode.getNodeIdString()));
	}

	// -------------------------------- pop-ups -------------------------------- //

	@Override
	public void initializePopup(IDomPopupFrame popupFrame, boolean autoRaise) {

		JS_call("POPUP_ENGINE.initializePopup", popupFrame, autoRaise);
	}

	@Override
	public void movePopup(IDomPopupFrame popupFrame, int x, int y, DomPopupOffsetUnit offsetUnit, DomPopupXAlign xAlign, DomPopupYAlign yAlign) {

		JS_call("POPUP_ENGINE.movePopup", popupFrame, x, y, offsetUnit, xAlign, yAlign);
	}

	// -------------------------------- scripting -------------------------------- //

	@Override
	public void loadScriptLibrary(IDomScriptLibrary scriptLibrary) {

		String codeHash = scriptLibrary.getCodeHash();
		if (!loadedScriptLibraries.contains(codeHash)) {
			executeScriptCode(scriptLibrary.getCode());
			loadedScriptLibraries.add(codeHash);
		}
	}

	@Override
	public void executeScriptCode(String scriptCode) {

		JS_call("AJAX_ENGINE.executeScriptCode", scriptCode);
	}

	// -------------------------------- focus trap -------------------------------- //

	@Override
	public void trapTabFocus(IDomNode node) {

		JS_call("trapTabFocus", node);
	}

	// -------------------------------- special -------------------------------- //

	@Override
	public void uploadFiles(DomForm form) {

		JS_call("sendUploadRequestThroughForm", form);
	}

	@Override
	public void setWorkingIndicatorEnabled(boolean enabled) {

		JS_call("setWorkingIndicatorEnabled", enabled);
	}

	@Override
	public void setAlternateResourceOnError(DomImage image, IResource resource) {

		Objects.requireNonNull(image);
		Objects.requireNonNull(resource);
		addJavascript("%s.onerror=function(){%s.src=%s;%s.onerror=null;};", image, image, getResourceUrl(resource), image);
	}

	@Override
	public void approveNodeValues() {

		JS_call("VALUE_NODE_MAP.approveNodeValues");
	}

	@Override
	public <O extends DomOption> void setSelectedOptions(DomSelect<O> select, Collection<O> selectedOptions) {

		JS_call("VALUE_NODE_MAP.setSelectedOptions", select, selectedOptions);
	}

	@Override
	public IResourceUrl getResourceUrl(IResource resource) {

		return AjaxResourceRegistry//
			.getInstance(document.getHttpSession())
			.register(resource);
	}

	@Override
	public void registerResourceLink(IResource resource, Relationship relationship, MimeType mimeType) {

		IResourceUrl resourceUrl = getResourceUrl(resource);
		resourceLinkRegistry.registerLink(resourceUrl, relationship, mimeType);
	}

	public AjaxResourceLinkRegistry getResourceLinkRegistry() {

		return resourceLinkRegistry;
	}

	// -------------------------------- browser manipulation -------------------------------- //

	@Override
	public void setDocumentTitle(String pageTitle) {

		JS_setAttribute("document.title", pageTitle);
	}

	@Override
	public void pushBrowserHistoryState(String pageName, String pageUrl) {

		JS_call("AJAX_ENGINE.pushBrowserHistoryState", pageName, pageUrl);
	}

	@Override
	public void reloadPage() {

		JS_call("window.location.reload");
	}

	@Override
	public void setReloadPageOnClick(IDomNode node) {

		JS_setNodeMember(node, "onclick", "function() { window.location.reload(); }");
	}

	// -------------------------------- private -------------------------------- //

	private boolean isResettingDisabledOrReadOnly(IDomAttribute attribute) {

		String name = attribute.getName().toLowerCase();

		if (name.equals("disabled") || name.equals("readonly")) {
			String value = attribute.getValue();
			return value == null || value.toLowerCase().equals("false");
		}

		return false;
	}

	private void JS_callNodeFunction(IDomNode node, String name, String...parameters) {

		updateCodeJS.appendStatement(JS_getNode(node) + "." + name + "(" + Imploder.implode(parameters, ",") + ");");
	}

	private void JS_setNodeMember(IDomNode node, String name, String value) {

		updateCodeJS.appendStatement(JS_getNode(node) + "." + name + "=" + value + ";");
	}

	private void JS_removeNodeAttribute(IDomNode node, String name) {

		updateCodeJS.appendStatement(JS_getNode(node) + ".removeAttribute('" + name + "');");
	}

	private void JS_setAttribute(String attribute, String value) {

		updateCodeJS.appendStatement(attribute + " = \"" + value + "\";");
	}

	private static String JS_getNode(IDomNode node) {

		return "n(" + node.getNodeId() + ")";
	}

	private void JS_call(String function, Object...arguments) {

		String argumentString = Imploder.implode(getArgumentStringList(Arrays.asList(arguments)), ",");
		updateCodeJS.appendStatement(function + "(" + argumentString + ");");
	}

	private void addJavascript(String statement, Object...arguments) {

		var convertedArguments = Stream//
			.of(arguments)
			.map(this::getArgumentString)
			.toArray();
		updateCodeJS.appendStatement(statement.formatted(convertedArguments));
	}

	private List<String> getArgumentStringList(Iterable<?> arguments) {

		List<String> result = ListFactory.createArrayList(Iterables.getSize(arguments));
		for (Object argument: arguments) {
			result.add(getArgumentString(argument));
		}
		return result;
	}

	private String getArgumentString(Object argument) {

		if (argument == null) {
			return "null";
		} else if (argument instanceof Number || argument instanceof Boolean) {
			return argument.toString();
		} else if (argument instanceof IDomNode) {
			return JS_getNode((IDomNode) argument);
		} else if (argument instanceof DomAttribute) {
			return ((DomAttribute) argument).getValue_JS();
		} else if (argument instanceof Iterable<?>) {
			return "[" + Imploder.implode(getArgumentStringList((Iterable<?>) argument), ",") + "]";
		} else {
			return "'" + JavascriptEscaping.getEscaped(argument.toString()) + "'";
		}
	}

	private void rememberCreation(CreationType type, int nodeId, String param) {

		lastCreation = updateCodeJS.size() - 1;
		lastCreationType = type;
		lastCreationNodeID = nodeId;
		lastCreationParam = param;
	}

	private enum CreationType {
		ELEMENT,
		TEXT
	}

	private CreationType lastCreationType;
	private int lastCreation = -1;
	private int lastCreationNodeID = -1;
	private String lastCreationParam;
}
