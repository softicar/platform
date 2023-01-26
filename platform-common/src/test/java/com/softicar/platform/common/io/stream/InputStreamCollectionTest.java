package com.softicar.platform.common.io.stream;

import com.softicar.platform.common.testing.Asserts;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.mockito.Mockito;

public class InputStreamCollectionTest extends Asserts {

	@Test
	@SuppressWarnings("resource")
	public void testWithOneMaliciousInputStream() throws IOException {

		var inputStreamA = Mockito.mock(InputStream.class);
		var inputStreamB = Mockito.mock(InputStream.class);
		var inputStreamC = Mockito.mock(InputStream.class);

		Mockito.doThrow(new RuntimeException()).when(inputStreamB).close();

		var collection = new InputStreamCollection();
		collection.add(() -> inputStreamA);
		collection.add(() -> inputStreamB);
		collection.add(() -> inputStreamC);

		assertException(RuntimeException.class, () -> collection.close());

		var inOrder = Mockito.inOrder(inputStreamA, inputStreamB, inputStreamC);
		inOrder.verify(inputStreamA).close();
		inOrder.verify(inputStreamB).close();
		inOrder.verify(inputStreamC).close();
		inOrder.verifyNoMoreInteractions();
	}
}
