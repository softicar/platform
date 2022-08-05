package com.softicar.platform.dom.engine;

import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.IResourceUrl;
import com.softicar.platform.common.io.resource.ResourceUrl;
import com.softicar.platform.dom.attribute.IDomAttribute;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomForm;
import com.softicar.platform.dom.elements.DomLink.Relationship;
import com.softicar.platform.dom.elements.popup.IDomPopupFrame;
import com.softicar.platform.dom.event.DomEventType;
import com.softicar.platform.dom.event.timeout.IDomTimeoutNode;
import com.softicar.platform.dom.input.DomOption;
import com.softicar.platform.dom.input.DomSelect;
import com.softicar.platform.dom.input.IDomTextualInput;
import com.softicar.platform.dom.input.auto.DomAutoCompleteInputIndicatorMode;
import com.softicar.platform.dom.input.auto.DomAutoCompleteInputValidationMode;
import com.softicar.platform.dom.input.auto.IDomAutoCompleteInput;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.style.ICssClass;
import java.util.Collection;
import java.util.Optional;

/**
 * This is implementation of {@link IDomEngine} simply ignores all requests.
 *
 * @author Oliver Richers
 */
public class DomNullEngine implements IDomEngine {

	@Override
	public void appendChild(IDomNode parent, IDomNode child) {

		// nothing to do
	}

	@Override
	public void approveNodeValues() {

		// nothing to do
	}

	@Override
	public void blur(IDomNode inputNode) {

		// nothing to do
	}

	@Override
	public void createElement(int nodeId, DomElementTag elementTag) {

		// nothing to do
	}

	@Override
	public IDomExport createExport() {

		return new DomNullExport();
	}

	@Override
	public void createTextNode(int nodeId, String text) {

		// nothing to do
	}

	@Override
	public void enableAutoComplete(IDomAutoCompleteInput<?> input) {

		// nothing to do
	}

	@Override
	public void executeScriptCode(String scriptCode) {

		// nothing to do
	}

	@Override
	public void focus(IDomNode inputNode) {

		// nothing to do
	}

	@Override
	public Optional<String> getNodeStyle(IDomNode node, String name) {

		return Optional.empty();
	}

	@Override
	public IResourceUrl getResourceUrl(IResource resource) {

		return new ResourceUrl("" + resource);
	}

	@Override
	public void initializePopup(IDomPopupFrame popupFrame, boolean autoRaise) {

		// nothing to do
	}

	@Override
	public void insertBefore(IDomNode parent, IDomNode child, IDomNode otherChild) {

		// nothing to do
	}

	@Override
	public void insertTextAtCaret(IDomTextualInput input, String text) {

		// nothing to do
	}

	@Override
	public void listenToEvent(IDomNode node, DomEventType type) {

		// nothing to do
	}

	@Override
	public void loadScriptLibrary(IDomScriptLibrary scriptLibrary) {

		// nothing to do
	}

	@Override
	public void makeDraggable(IDomNode draggedNode, IDomNode dragHandleNode, IDomNode limitingNode) {

		// nothing to do
	}

	@Override
	public void moveCaretToPosition(IDomTextualInput input, int position) {

		// nothing to do
	}

	@Override
	public void movePopup(IDomPopupFrame popupFrame, int x, int y, DomPopupXAlign xAlign, DomPopupYAlign yAlign) {

		// nothing to do
	}

	@Override
	public void pushBrowserHistoryState(String pageName, String pageUrl) {

		// nothing to do
	}

	@Override
	public void raise(IDomNode node) {

		// nothing to do
	}

	@Override
	public void registerResourceLink(IResource resource, Relationship relationship, MimeType mimeType) {

		// nothing to do
	}

	@Override
	public void reloadPage() {

		// nothing to do
	}

	@Override
	public void removeChild(IDomNode parent, IDomNode child) {

		// nothing to do
	}

	@Override
	public void replaceChild(IDomNode parent, IDomNode newChild, IDomNode oldChild) {

		// nothing to do
	}

	@Override
	public void scheduleTimeout(IDomTimeoutNode timeoutNode, Double seconds) {

		// nothing to do
	}

	@Override
	public void selectText(IDomNode inputNode) {

		// nothing to do
	}

	@Override
	public void setAutoCompleteEnabled(IDomAutoCompleteInput<?> input, boolean enabled) {

		// nothing to do
	}

	@Override
	public void setAutoCompleteIndicatorMode(IDomAutoCompleteInput<?> input, DomAutoCompleteInputIndicatorMode indicatorMode) {

		// nothing to do
	}

	@Override
	public void setAutoCompleteInputInvalid(IDomAutoCompleteInput<?> input) {

		// nothing to do
	}

	@Override
	public void setAutoCompleteInputMandatory(IDomAutoCompleteInput<?> input, boolean mandatory) {

		// nothing to do
	}

	@Override
	public void setAutoCompleteValidationMode(IDomAutoCompleteInput<?> input, DomAutoCompleteInputValidationMode validationMode) {

		// nothing to do
	}

	@Override
	public void setClickTargetForEventDelegation(IDomNode sourceNode, DomEventType eventType, IDomNode targetNode) {

		// nothing to do
	}

	@Override
	public void setCssClassOnKeyDown(IDomNode eventTarget, DomEventType eventType, IDomNode cssTargetNode, Collection<ICssClass> cssClasses) {

		// nothing to do
	}

	@Override
	public void setDocumentTitle(String pageTitle) {

		// nothing to do
	}

	@Override
	public void setFireOnKeyUp(IDomNode node, DomEventType type, boolean enabled) {

		// nothing to do
	}

	@Override
	public void setListenToKeys(IDomNode node, Collection<String> keys) {

		// nothing to do
	}

	@Override
	public void setNodeAttribute(IDomNode node, IDomAttribute attribute) {

		// nothing to do
	}

	@Override
	public void setNodeStyle(IDomNode node, String name, String value) {

		// nothing to do
	}

	@Override
	public void setPreventDefaultBehavior(IDomNode node, DomEventType type, boolean enabled) {

		// nothing to do
	}

	@Override
	public void setPreventDefaultOnMouseDown(IDomNode node, boolean enabled) {

		// nothing to do
	}

	@Override
	public void setReloadPageOnClick(IDomNode node) {

		// nothing to do
	}

	@Override
	public <O extends DomOption> void setSelectedOptions(DomSelect<O> select, Collection<O> selectedOptions) {

		// nothing to do
	}

	@Override
	public void setWorkingIndicatorEnabled(boolean enabled) {

		// nothing to do
	}

	@Override
	public void stopPropagation(IDomNode node, String eventName) {

		// nothing to do
	}

	@Override
	public void trapTabFocus(IDomNode node) {

		// nothing to do
	}

	@Override
	public void unlistenToEvent(IDomNode node, DomEventType type) {

		// nothing to do
	}

	@Override
	public void unsetClickTargetForEventDelegation(IDomNode sourceNode, DomEventType eventType) {

		// nothing to do
	}

	@Override
	public void unsetNodeAttribute(IDomNode node, String name) {

		// nothing to do
	}

	@Override
	public void unsetNodeStyle(IDomNode node, String name) {

		// nothing to do
	}

	@Override
	public void uploadFiles(DomForm form) {

		// nothing to do
	}
}
