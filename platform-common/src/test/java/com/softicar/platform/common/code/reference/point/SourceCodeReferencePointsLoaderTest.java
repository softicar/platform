package com.softicar.platform.common.code.reference.point;

import com.softicar.platform.common.testing.AbstractTest;
import java.util.Map;
import java.util.UUID;
import org.junit.Test;

public class SourceCodeReferencePointsLoaderTest extends AbstractTest {

	private final UUID referencePointUuid;

	public SourceCodeReferencePointsLoaderTest() {

		this.referencePointUuid = SourceCodeReferencePoints.getUuidOrThrow(SourceCodeReferencePointForTesting.class);
	}

	@Test
	public void test() {

		Map<UUID, ISourceCodeReferencePoint> referencePointMap = new SourceCodeReferencePointsLoader().loadAll();

		ISourceCodeReferencePoint referencePoint = referencePointMap.get(referencePointUuid);

		assertNotNull(referencePoint);
		assertTrue(referencePoint instanceof SourceCodeReferencePointForTesting);
	}
}
