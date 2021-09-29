package com.softicar.platform.core.module.web.service;

import com.softicar.platform.common.network.url.Url;
import com.softicar.platform.core.module.page.service.PageService;
import com.softicar.platform.db.runtime.test.AbstractDbTest;
import com.softicar.platform.emf.source.code.reference.point.EmfSourceCodeReferencePoints;
import org.junit.Test;

public class WebServiceUrlBuilderTest extends AbstractDbTest {

	private final String NAVIGATION_SERVICE_UUID = EmfSourceCodeReferencePoints.getUuidOrThrow(PageService.class).toString();

	@Test
	public void test() {

		Url url = new WebServiceUrlBuilder(PageService.class).addParameter("foo", "7").build();

		assertEquals("/portal/service?foo=7&id=" + NAVIGATION_SERVICE_UUID, url.getStartingFromPath());
		assertEquals("https://www.example.com/portal/service?foo=7&id=" + NAVIGATION_SERVICE_UUID, url.toString());
	}
}
