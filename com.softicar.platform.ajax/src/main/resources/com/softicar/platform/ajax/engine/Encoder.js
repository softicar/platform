function Encoder()
{
	var hexDigits = "0123456789ABCDEF";

	function hexNibble(nibble)
	{
		return hexDigits.charAt(nibble);
	}

	function hexByte(b)
	{
		return hexNibble(b >> 4) + hexNibble(b & 15);
	}

	function hexBytes(bytes)
	{
		var hex = [];
		for(var i in bytes)
		{
			hex.push(hexByte(bytes[i]));
		}
		return hex.join("");
	}
	
	// gets a UTF code point and encodes it to UTF-8
	this.encodeCodePointToUtf8 = function(codePoint)
	{
		var bytes = [];
		
		if(codePoint <= 0x7F)
		{
			bytes.push(codePoint);
		}
		else if(codePoint <= 0x7FF)
		{
			bytes.push(128 + 64 + (codePoint >> 6));
			bytes.push(128 + (codePoint & 63));
		}
		else if(codePoint <= 0xFFFF)
		{
			bytes.push(128 + 64 + 32 + (codePoint >> 12));
			bytes.push(128 + ((codePoint >> 6) & 63));
			bytes.push(128 + (codePoint & 63));
		}
		else
		{
			bytes.push(128 + 64 + 32 + 16 + (codePoint >> 18));
			bytes.push(128 + ((codePoint >> 12) & 63));
			bytes.push(128 + ((codePoint >> 6) & 63));
			bytes.push(128 + (codePoint & 63));
		}
		
		return bytes;
	};

	this.encodeTextCharsToHex = function(text)
	{
		var hex = [];
		for(var i in text)
		{
			var codePoint = text.charCodeAt(i);
			var utf8 = this.encodeCodePointToUtf8(codePoint);
			hex.push(hexBytes(utf8));
		}
		return hex.join("");
	};

	this.encodeParameters = function(map)
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
	};

	this.encodeParametersToHex = function(map)
	{
		var text = this.encodeParameters(map);
		return this.encodeTextCharsToHex(text);
	};
}
