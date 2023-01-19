package com.softicar.platform.dom.elements;

import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.testing.Asserts;
import com.softicar.platform.dom.DomTestResources;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.Test;

public class DomImageResourceConverterTest extends Asserts {

	@Test
	public void test() throws IOException {

		var resource = DomTestResources.TEST_TIFF.getResource();
		var converter = new DomImageResourceConverter(resource);

		assertEmpty(converter.getCharset());
		assertEmpty(converter.getContentHash());
		assertEquals(resource.getFilename().get() + ".png", assertOne(converter.getFilename()));
		assertEquals(MimeType.IMAGE_PNG, converter.getMimeType());
		var content = StreamUtils.toByteArray(converter::getResourceAsStream);
		var image = ImageIO.read(new ByteArrayInputStream(content));
		assertEquals(200, image.getWidth());
		assertEquals(100, image.getHeight());
		assertEquals(0xff10900c, image.getRGB(0, 0));
	}
}
