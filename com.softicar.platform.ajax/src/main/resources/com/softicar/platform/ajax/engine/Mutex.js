
function Mutex()
{
	var m_lockCounter = 0;
	
	// This allocates a lock on this mutex. This function returns true 
	// if the lock could be successfully allocated, false otherwise.
	this.lock = function()
	{
		// NOTE: Assuming that incrementation is an atomic operation.
		++m_lockCounter;
		
		if(m_lockCounter > 1)
		{
			--m_lockCounter;
			return false;
		}
		
		return true;
	};

	this.lockBlocking = function()
	{
		while(!this.lock())
			; // waiting
	};

	// This releases the mutex lock.
	this.unlock = function()
	{
		// Ensure mutex is really locked.
		if(m_lockCounter < 1)
			alert("Internal program error. Mutex lock counter may never become less than zero.");
		
		// NOTE: If someone else is trying to get the lock on this mutex 
		//       right now the lock counter may be greater than 1, but that
		//       would be no problem.
		
		--m_lockCounter;
	};
}
