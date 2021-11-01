package com.softicar.platform.ajax.request;

import com.softicar.platform.common.core.exceptions.SofticarException;
import com.softicar.platform.common.io.HexDecoderStream;
import com.softicar.platform.common.io.StreamUtils;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

public final class AjaxUtils {

	public static Map<String, String[]> parseHexParameterMap(InputStream inputStream) {

		try (HexDecoderStream decoderStream = new HexDecoderStream(inputStream)) {
			return parseParameterMap(decoderStream);
		}
	}

	public static Map<String, String[]> parseParameterMap(InputStream inputStream) {

		Map<String, String[]> parameterMap = new TreeMap<>();

		while (true) {
			String key = readRequestData(inputStream, null);
			if (key == null) {
				break;
			}
			String value = readRequestData(inputStream, key);
			parameterMap.put(key, new String[] { value });
		}

		return parameterMap;
	}

	private static String readRequestData(InputStream inputStream, String key) {

		String line = StreamUtils.readUTF8Until(inputStream, StreamUtils.LINE_FEED);
		if (line != null) {
			int length = Integer.parseInt(line);
			if (length > 0) {
				return StreamUtils.readUTF8Definitely(inputStream, length);
			} else if (length == 0) {
				return "";// empty string, no need to read something
			} else {
				return null;// a negative length means a null value
			}
		} else if (key != null) {
			throw new SofticarException("Unexpected end of file while parsing value of key '%s'.", key);
		} else {
			return null;
		}
	}
}
