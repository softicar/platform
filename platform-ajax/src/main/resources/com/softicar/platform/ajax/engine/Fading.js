var FADING_MILLIS = 250;           // time for fading in/out in milliseconds
var FADING_STEPS_PER_SECOND = 60;
var FADING_TIMEOUT_DELAY = Math.round(1000.0 / FADING_STEPS_PER_SECOND);

function FadingData(node, target)
{
	this.execute = function()
	{
		var now = (new Date()).getTime();
		var delta = (now - m_start) / FADING_MILLIS;
	
		if(delta >= 1)
		{
			m_node.style.opacity = m_target;
			m_node.style.visibility = m_target == 1? 'visible' : 'hidden';
			m_node.style.display = m_target == 1? 'block' : 'none';
			return true;
		}
		else
		{
			m_node.style.opacity = m_target == 1? delta : 1-delta;
			m_node.style.visibility = 'visible';
			m_node.style.display = 'block';
			return false;
		}
	};
	
	this.reverse = function()
	{
		// compute new start
		var now = (new Date()).getTime();
		var oldDelta = now - m_start;
		var newDelta = FADING_MILLIS - oldDelta;
		m_start = now - newDelta;

		// toggle target
		m_target = m_target == 1? 0 : 1;
	};
	
	this.getTarget = function()
	{
		return m_target;
	};
	
	var m_node = node;
	var m_target = target; // either 1 or 0
	var m_start = (new Date()).getTime();
};

function FadingContext()
{
	this.fade = function(node, target)
	{
		if(m_isOpacityAvailable())
		{
			var oldFadingData = m_fadingMap[node.id];
			if(oldFadingData)
			{
				if(oldFadingData.getTarget() != target)
					oldFadingData.reverse();
				else
					; // nothing to do
			}
			else if(node.style.opacity != target)
			{
				m_fadingMap[node.id] = new FadingData(node, target);

				if(m_fadingTimeout == null)
					m_fadingTimeout = setTimeout(m_fadingTimeoutHandler, FADING_TIMEOUT_DELAY);
			}
		}
		else
		{
			node.style.visibility = target == 1? 'visible' : 'hidden';
			node.style.display = target == 1? 'block' : 'none';
		}
	};

	var m_fadingTimeoutHandler = function()
	{
		var start = (new Date()).getTime();	
	
		// save old fading map and create new empty map
		var oldFadingMap = m_fadingMap;
		m_fadingMap = {};
		var count = 0;

		// iterate over old fading map
		for(var id in oldFadingMap)
		{
			var fadingData = oldFadingMap[id];
			if(!fadingData.execute())
			{
				m_fadingMap[id] = fadingData;
				++count;
			}
		}

		// compute delay for next timeout
		var now = (new Date()).getTime();
		var delay = FADING_TIMEOUT_DELAY - (now-start);
		delay = delay >= 0? delay : 0;

		// if new map is not empty, reschedule timeout handler
		m_fadingTimeout = count != 0? setTimeout(m_fadingTimeoutHandler, delay) : null;
	};

	var m_isOpacityAvailable = function()
	{
		if(m_opacityAvailable == null)
		{
			var body = getBody();
			if("opacity" in body.style)
			{
				var tmp = body.style.opacity;
				body.style.opacity = 0.5;
				m_opacityAvailable = body.style.opacity == 0.5;
				body.style.opacity = tmp;
			}
			else
				m_opacityAvailable = false;
		}
		
		return m_opacityAvailable;
	};

	var m_opacityAvailable = TEST? null : false;
	var m_fadingMap = {};
	var m_fadingTimeout = null;
	var m_mutex = new Mutex();
};
