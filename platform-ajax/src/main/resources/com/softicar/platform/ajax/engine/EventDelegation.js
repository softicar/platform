var EVENT_DELEGATIONS = new Object();

function getOrCreateEventDelegator(node) {
	var delegator = EVENT_DELEGATIONS[node.id];
	if(!delegator) {
		delegator = new EventDelegator(node);
		EVENT_DELEGATIONS[node.id] = delegator;
	}
	return delegator;
}

function setClickTargetForEventDelegation(node, eventType, targetNode) {
	getOrCreateEventDelegator(node).setDelegation(eventType, targetNode);
}

function sendOrDelegateEvent(node, event, eventType) {
	var delegator = EVENT_DELEGATIONS[node.id];
	if(delegator && delegator.isDelegated(eventType)) {
		delegator.delegateEvent(eventType);
	} else {
		sendEventToServer(event, eventType);
	}
	event.stopPropagation();
}

function EventDelegator(node) {
	var targets = new Object();

	this.setDelegation = function(eventType, targetNode) {
		targets[eventType.toLowerCase()] = targetNode;
	};

	this.isDelegated = function(eventType) {
		return getTargetNode(eventType)? true : false;
	};

	this.delegateEvent = function(eventType) {
		getTargetNode(eventType).click();
	};

	function getTargetNode(eventType) {
		return targets[eventType.toLowerCase()];
	};
}
