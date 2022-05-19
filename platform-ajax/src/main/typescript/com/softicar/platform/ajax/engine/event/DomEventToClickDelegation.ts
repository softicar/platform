const EVENT_DELEGATIONS = new Map<HTMLElement, EventToClickDelegator>();

function getOrCreateEventDelegator(element: HTMLElement) {
	let delegator = EVENT_DELEGATIONS.get(element);
	if(!delegator) {
		delegator = new EventToClickDelegator();
		EVENT_DELEGATIONS.set(element, delegator);
	}
	return delegator;
}

function setClickTargetForEventDelegation(element: HTMLElement, eventType: string, targetNode: HTMLElement) {
	getOrCreateEventDelegator(element).setDelegation(eventType, targetNode);
}

function sendOrDelegateEvent(element: HTMLElement, event: Event, eventType: string) {
	let delegator = EVENT_DELEGATIONS.get(element);
	if(delegator && delegator.isDelegated(eventType)) {
		delegator.delegateEvent(eventType);
	} else {
		sendDomEventToServer(event, eventType);
	}
	event.stopPropagation();
}

class EventToClickDelegator {
	private readonly targets = new Map<string, HTMLElement>();

	public setDelegation(eventType: string, targetElement: HTMLElement) {
		this.targets.set(eventType.toLowerCase(), targetElement);
	}

	public isDelegated(eventType: string) {
		return this.getTargetNode(eventType)? true : false;
	}

	public delegateEvent(eventType: string) {
		this.getTargetNode(eventType).click();
	}

	private getTargetNode(eventType: string)  {
		return this.targets.get(eventType.toLowerCase()) as HTMLElement;
	}
}
