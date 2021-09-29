package com.softicar.platform.emf.form;

import com.softicar.platform.emf.EmfMarker;
import com.softicar.platform.emf.test.EmfTableRowCacheMapTestMethods;
import com.softicar.platform.emf.test.EmfTestSubObject;
import org.junit.Test;

public class EmfFormRefreshTest extends AbstractEmfFormTest {

	@Test
	public void testPeekAndRefreshWithUpToDateEntity() {

		appendEntityForm(testEntity).peekAndRefresh();

		assertInteractiveRefreshIsNotShown();
		assertDisplayedValue(EmfTestSubObject.NAME, testEntity.getName());
	}

	@Test
	public void testPeekAndRefreshWithOutDatedEntity() {

		modifyEntityConcurrently();

		appendEntityForm(testEntity).peekAndRefresh();

		assertInteractiveRefreshIsShown();
		assertDisplayedValue(EmfTestSubObject.NAME, testEntity.getName());
	}

	// ------------------------------ private ------------------------------ //

	private void modifyEntityConcurrently() {

		EmfTableRowCacheMapTestMethods.useOtherCacheMapAndModify(testEntity, it -> it.setName("bar"));
	}

	private void assertInteractiveRefreshIsShown() {

		findBody()//
			.findNodes(EmfMarker.INTERACTIVE_REFRESH_BUTTON)
			.assertOne();
	}

	private void assertInteractiveRefreshIsNotShown() {

		findBody()//
			.findNodes(EmfMarker.INTERACTIVE_REFRESH_BUTTON)
			.assertNone();
	}
}
