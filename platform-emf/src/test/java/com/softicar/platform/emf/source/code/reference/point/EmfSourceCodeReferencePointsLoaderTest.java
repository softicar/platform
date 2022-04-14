package com.softicar.platform.emf.source.code.reference.point;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.Map;
import java.util.UUID;
import org.junit.Test;

public class EmfSourceCodeReferencePointsLoaderTest extends AbstractTest {

	private final UUID referencePointUuid;

	public EmfSourceCodeReferencePointsLoaderTest() {

		this.referencePointUuid = EmfSourceCodeReferencePoints.getUuidOrThrow(EmfSourceCodeReferencePointForTesting.class);
	}

	@Test
	public void test() {

		Map<UUID, IEmfSourceCodeReferencePoint> referencePointMap = new EmfSourceCodeReferencePointsLoader().loadAll();

		IEmfSourceCodeReferencePoint referencePoint = referencePointMap.get(referencePointUuid);

		assertNotNull(referencePoint);
		assertTrue(referencePoint instanceof EmfSourceCodeReferencePointForTesting);
	}
}
