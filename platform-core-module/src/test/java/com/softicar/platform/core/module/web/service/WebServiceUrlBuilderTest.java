package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.code.reference.point.SourceCodeReferencePoints;
import com.softicar.platform.common.network.url.Url;
import com.softicar.platform.core.module.page.service.PageServiceFactory;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import org.junit.Test;

public class WebServiceUrlBuilderTest extends AbstractDbTest {

	private static final String NAVIGATION_SERVICE_UUID = SourceCodeReferencePoints.getUuidOrThrow(PageServiceFactory.class).toString();

	@Test
	public void test() {

		Url url = new WebServiceUrlBuilder(PageServiceFactory.class).addParameter("foo", "7").build();

		assertEquals("/portal/service?foo=7&id=" + NAVIGATION_SERVICE_UUID, url.getStartingFromPath());
		assertEquals("https://www.example.com/portal/service?foo=7&id=" + NAVIGATION_SERVICE_UUID, url.toString());
	}
}
