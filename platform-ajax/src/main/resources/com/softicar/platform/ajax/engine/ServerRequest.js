// ******************************************************************************** //
// * CONSTANTS                                                                    * //
// ******************************************************************************** //

var SR_REQUEST_STATE_UNSET            = 0; 
var SR_REQUEST_STATE_OPENED           = 1; 
var SR_REQUEST_STATE_HEADERS_RECEIVED = 2; 
var SR_REQUEST_STATE_LOADING          = 3; 
var SR_REQUEST_STATE_DONE             = 4; 

var HTTP_STATUS_SUCCESS = 200;
var HTTP_STATUS_GONE = 410;

// ******************************************************************************** //
// * GLOBAL VARIABLES                                                             * //
// ******************************************************************************** //

var SR_ajaxRequest  = null; // references the current AJAX request
var SR_requestIndex = 0;    // will be incremented after a successful AJAX request

// ******************************************************************************** //
// * SEQUENTIAL COMMUNICATION                                                     * //
// ******************************************************************************** //

// This creates a new XMLHttpRequest with the specified value map
// as message text. This function may only be called if all other
// server requests are finished.
function SR_sendAjaxRequest(message, form)
{
	if(SR_ajaxRequest === null)
	{
		// create and execute ajax request
		SR_ajaxRequest = new SR_AjaxRequest(form);
		SR_ajaxRequest.sendRequest(message);
	}
	else
	{
		alert("Internal program error. Tried to send two server request at the same time.");
	}
}

// This is called when the request has been sent via hidden frame.
function SR_handleAjaxResponse(responseText)
{
	if(SR_ajaxRequest !== null)
	{
		SR_ajaxRequest.handleAjaxResponse(responseText);
	}
}

function SR_AjaxRequest(form)
{
	var m_form = form;
	var m_active = true;
	var m_request = null;

	var m_quit = function()
	{
		if(m_active)
		{
			// allow new AJAX requests
			m_active = false;
			SR_ajaxRequest = null;
			SR_requestIndex += 1;
		
			// execute returned Javascript code
			ACTION_QUEUE.executeNextAction();
		}
	};

	var m_handleAjaxResponse = function(request, success)
	{
		if(m_active)
		{
			if(success)
			{
				// enqueue returned Javascript code
				if(request.responseText)
				{
					ACTION_QUEUE.enqueueAction(new JavascriptAction(request.responseText));
				}
			}
			else if(request.status == HTTP_STATUS_GONE)
			{
				handleSessionTimeout();
			}
			else if(request.status != 0)
			{
				alert("HTTP Error " + request.status + ": " + request.statusText);
			}
			else
			{
				// ignore this error, request was canceled by client
			}
		
			m_quit();
		}					
	};
	
	var m_createAjaxFrame = function()
	{
		var frame = GLOBAL.context.getDocument().getElementById('ajaxFrame');
		if(!frame)
		{
			frame = GLOBAL.context.getDocument().createElement('iframe');
			frame.id = 'ajaxFrame';
			frame.name = 'ajaxFrame';
			frame.style.display = 'none';
			GLOBAL.context.getBody().appendChild(frame);
		}
		return frame;
	};

	var m_createAjaxInput = function(message)
	{
		var input = GLOBAL.context.getDocument().createElement('input');
		input.id = 'ajaxInput';
		input.name = 'ajaxInput';
		input.type = 'hidden';
		input.value = GLOBAL.encoder.encodeParametersToHex(message);
		return input;
	};
	
	var m_submitForm = function(message)
	{
		var frame = m_createAjaxFrame();
		var input = m_createAjaxInput(message);
	
		m_form.method = 'post';
		m_form.target = frame.name;
		m_form.enctype = 'multipart/form-data';
		
		if(form.hasChildNodes())
			form.insertBefore(input, form.firstChild);
		else
			form.appendChild(input);

		form.submit();
	};
	
	this.handleAjaxResponse = function(responseText)
	{
		var input = GLOBAL.context.getDocument().getElementById('ajaxInput');
		input.parentNode.removeChild(input);
		var frame = GLOBAL.context.getDocument().getElementById('ajaxFrame');
		frame.parentNode.removeChild(frame);
	
		ACTION_QUEUE.enqueueAction(new JavascriptAction(responseText));
		m_quit();
	};
	
	this.sendRequest = function(message)
	{
		if(m_active)
		{
			// add some standard values to message map
			message.i = DOCUMENT_INSTANCE_UUID;
			message.x = SR_requestIndex;
			if(DEBUG)
				message.debug = true;
			if(VERBOSE)
				message.verbose = true;
		
			// send asynchronous request
			if(m_form)
			{
				m_submitForm(message);
			}
			else
			{
				m_request = SR_sendAsyncHTTPMessage('', SR_encodeHTTPMessage(message), m_handleAjaxResponse);
			}
		}
	};
}

// ******************************************************************************** //
// * INTERNAL UTILITY FUNCTIONS                                                   * //
// ******************************************************************************** //

function SR_encodeHTTPMessage(map)
{
	var data = [];
	for(var key in map)
	{
		// push key
		data.push(key.length+"\n"+key);

		// push value
		var value = map[key];
		if(value !== null && value !== undefined)
		{
			value = ""+value;
			data.push(value.length+"\n"+value);
		}
		else
			data.push("-1\n");
	}
	return data.join("");
}

function SR_createHTTPRequest(url, async, contentType)
{
	var request = window.XMLHttpRequest? new XMLHttpRequest() : new ActiveXObject('MSXML2.XMLHTTP.3.0');
	request.open('POST', url, async);
	request.setRequestHeader('Content-Type', contentType);
	return request;
}

function SR_sendSyncHTTPMessage(url, message)
{
	var request = SR_createHTTPRequest(url, false, 'text/plain; charset=UTF-8');
	request.send(message);
	return request;
}

function SR_sendAsyncHTTPMessage(url, message, handleResponse)
{
	var request = SR_createHTTPRequest(url, true, 'text/plain; charset=UTF-8');
	request.onreadystatechange = function()
	{
		if(request.readyState == SR_REQUEST_STATE_DONE)
		{
			// check result
			if(handleResponse !== null) {
				var success = request.status == HTTP_STATUS_SUCCESS;
				handleResponse(request, success);
			}
		}
	};
	request.send(message);
	return request;
}
