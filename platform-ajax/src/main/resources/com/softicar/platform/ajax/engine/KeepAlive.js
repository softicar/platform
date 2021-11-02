/**
 * This class takes care that no session timeout occurs
 * by sending a keep-alive message to the server.
 * <p>
 * @param delay the number of milliseconds between every 
 *              keep-alive message sent to the server
 */
function KeepAlive(delay)
{
	/**
	 * This will schedule a new keep-alive message.
	 * <p>
	 * Any keep-alive message that was scheduled before the
	 * call to this function will be canceled.
	 */
	this.schedule = function()
	{
		// clear any previously scheduled timeout
		if(m_handler != null)
			clearTimeout(m_handler);

		// schedule new timeout with default delay		
		m_handler = setTimeout(m_handleKeepAliveTimeout, m_delay);
	};

	var m_handleKeepAliveTimeout = function()
	{
		// ignore if the session timed out
		if(SESSION_TIMED_OUT)
			return;
	
		// try to get the global event lock
		if(lock(LOCK_REASON_KEEP_ALIVE))
		{
			var parameters = 
			{
				'a': AJAX_REQUEST_KEEP_ALIVE
			};
			
			AQ_enqueueAction(new AQ_ServerRequestAction(parameters));
			AQ_executeNextAction();
		}
		else
			// Since the global event lock was active there was no point
			// in sending a keep-alive, anyway. So, just reschedule a new
			// timeout.
			GLOBAL.scheduleKeepAlive();
	};

	var m_delay = delay;
	var m_handler = null;
};
