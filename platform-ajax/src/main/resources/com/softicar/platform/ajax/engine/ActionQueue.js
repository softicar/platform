var AQ_actionQueue = {};
var AQ_actionQueueBegin = 0;
var AQ_actionQueueEnd = 0;

// This adds the specified action to the end of the action queue.
function AQ_enqueueAction(action)
{
	AQ_actionQueue[AQ_actionQueueEnd++] = action;
}

// This removes the action at the begin of the action queue
// and returns it. If the action queue is empty, this returns
// null.
function AQ_dequeueAction()
{
	if(AQ_actionQueueBegin < AQ_actionQueueEnd)
	{
		var action = AQ_actionQueue[AQ_actionQueueBegin];
		delete AQ_actionQueue[AQ_actionQueueBegin];
		++AQ_actionQueueBegin;
		return action;
	}
	else
	{
		return null;
	}
}


// This removes the first action from the action queue and 
// calls execute() on it. When there are no more actions in 
// the queue, a new keep-alive timeout is scheduled and the
// global event lock is released.
function AQ_executeNextAction()
{
	var action = AQ_dequeueAction();
	if(action)
	{
		action.execute();
	}
	else
	{
		GLOBAL.scheduleKeepAlive();
		unlock();
	}
}

// This action represents a server request. When executed, this
// sends a request to the server with the specified parameters. 
// After the server has replied, a new AQ_JavascriptAction with the 
// returned javascript code of the server is enqueue and the next 
// action of the action queue is executed.
//
// If you leave form undefined, this will send an ajax request via
// XMLHttpRequest, else the specified form is submitted.
function AQ_ServerRequestAction(parameters, form)
{
	var m_parameters = parameters;
	var m_form = form;

	this.execute = function()
	{
		SR_sendAjaxRequest(m_parameters, m_form);
	}
}

// This action represents a javascipt action. When executed, this
// executes the specified javascript code and afterwards executes 
// the next action of the action queue.
function AQ_JavascriptAction(javascriptCode)
{
	var m_javascriptCode = javascriptCode;

	this.execute = function()
	{
		eval(javascriptCode);
		AQ_executeNextAction();
	}
}
