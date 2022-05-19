
function n(nodeId: number) {
	return AJAX_ENGINE.getNode(nodeId);
}

function e(nodeId: number, tag: string) {
	return AJAX_ENGINE.createElement(nodeId, tag);
}

function t(nodeId: number, text: string) {
	return AJAX_ENGINE.createTextNode(nodeId, text);
}

function l(nodeId: number, eventType: string) {
	listenToDomEvent(nodeId, eventType, true);
}

function u(nodeId: number, eventType: string) {
	listenToDomEvent(nodeId, eventType, false);
}

function a(parentId: number, childId: number) {
	AJAX_ENGINE.appendChild(parentId, childId);
}

function i(parentId: number, childId: number, oherChildId: number) {
	AJAX_ENGINE.insertBefore(parentId, childId, oherChildId);
}

function r(parentId: number, childId: number) {
	AJAX_ENGINE.removeChild(parentId, childId);
}

function p(parentId: number, newChildId: number, oldChldId: number) {
	AJAX_ENGINE.replaceChild(parentId, newChildId, oldChldId);
}

function E(parentId: number, childId: number, tag: string) {
	e(childId, tag);
	a(parentId, childId);
}

function T(parentId: number, childId: number, text: string) {
	t(childId, text);
	a(parentId, childId);
}

function s(elementId: number, attribute: string, value: string) {
	AJAX_ENGINE.setAttribute(elementId, attribute, value);
}
