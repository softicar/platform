package com.softicar.platform.common.network.url;

import com.softicar.platform.common.testing.AbstractTest;
import org.junit.Test;

/**
 * Test cases for {@link Url} and {@link UrlBuilder}.
 *
 * @author Oliver Richers
 */
public class UrlBuilderTest extends AbstractTest {

	@Test(expected = NullPointerException.class)
	public void testWithoutScheme() {

		new UrlBuilder()//
			.setDomainName("www.example.com")
			.build();
	}

	@Test(expected = NullPointerException.class)
	public void testWithoutDomainName() {

		new UrlBuilder()//
			.setScheme("http")
			.build();
	}

	@Test
	public void testFullUrl() {

		Url url = new UrlBuilder()//
			.setScheme("http")
			.setDomainName("www.example.com")
			.setPort("42")
			.setPath("some/where")
			.addParameter("foo", "bar")
			.setFragment("here")
			.build();

		assertEquals("http://www.example.com:42/some/where?foo=bar#here", url.toString());
		assertEquals("/some/where?foo=bar#here", url.getStartingFromPath());
	}

	@Test
	public void testWithOnlySchemeAndDomain() {

		Url url = new UrlBuilder()//
			.setScheme("http")
			.setDomainName("www.example.com")
			.build();

		assertEquals("http://www.example.com", url.toString());
		assertEquals("", url.getStartingFromPath());
	}

	@Test
	public void testWithoutPath() {

		Url url = new UrlBuilder()//
			.setScheme("http")
			.setDomainName("www.example.com")
			.addParameter("foo", "bar")
			.setFragment("here")
			.build();

		assertEquals("http://www.example.com?foo=bar#here", url.toString());
		assertEquals("?foo=bar#here", url.getStartingFromPath());
	}
}
