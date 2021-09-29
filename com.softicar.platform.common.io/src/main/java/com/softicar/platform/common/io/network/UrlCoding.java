package com.softicar.platform.common.io.network;

import com.softicar.platform.common.string.charset.Charsets;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * Provides static methods for encoding and decoding special characters in URL
 * strings.
 *
 * @author Boris Schaa
 * @author Oliver Richers
 */
public class UrlCoding {

	/**
	 * Encodes special characters for URL usage with UTF-8 encoding.
	 *
	 * @param url
	 *            the URL string to encode
	 * @return the encoded URL string
	 */
	public static String encodeUtf8(String url) {

		return encode(url, Charsets.UTF8);
	}

	/**
	 * Encodes special characters for URL usage with a given encoding.
	 * <p>
	 * A runtime exception is thrown if an unsupported encoding is given.
	 *
	 * @param url
	 *            the URL string to encode
	 * @param charset
	 *            how the URL shall be encoded
	 * @return the encoded URL string
	 */
	public static String encode(String url, Charset charset) {

		if (url == null) {
			return "";
		}
		return URLEncoder.encode(url, charset);
	}

	/**
	 * Decodes special characters for URL usage with UTF-8 decoding.
	 *
	 * @param url
	 *            the URL string to decode
	 * @return the decoded URL string
	 */
	public static String decodeUtf8(String url) {

		return decode(url, Charsets.UTF8);
	}

	/**
	 * Decodes special characters for URL usage with a given encoding.
	 * <p>
	 * A runtime exception is thrown when an unsupported encoding is given.
	 *
	 * @param url
	 *            the URL string to decode
	 * @param charset
	 *            how the URL shall be decoded
	 * @return the decoded URL string
	 */
	public static String decode(String url, Charset charset) {

		if (url == null) {
			return "";
		}
		return URLDecoder.decode(url, charset);
	}
}
