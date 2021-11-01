package com.softicar.platform.dom.resource.preprocessed;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.common.testing.Asserts;
import org.junit.Test;

public class DomCssPreprocessorTest extends Asserts {

	private final DomCssPreprocessor preprocessor;

	public DomCssPreprocessorTest() {

		this.preprocessor = new DomCssPreprocessor(this::createUrl);
	}

	@Test
	public void testPreprocess() {

		String fileContent = createTemplateFileContent("/package/of/Rubik-wght.ttf");
		String preprocessedFileContent = preprocessor.preprocess(fileContent);

		String expectedFileContent = createExpectedFileContent("url/to/Rubik-wght.ttf");
		assertEquals(expectedFileContent, preprocessedFileContent);
	}

	@Test
	public void testPreprocessWithSeveralTemplateExpressions() {

		String fileContent = new StringBuilder()//
			.append(createTemplateFileContent("/package/of/Rubik-wght.ttf"))
			.append(createTemplateFileContent("/package/of/LiberationMono-Regular.ttf"))
			.toString();
		String preprocessedFileContent = preprocessor.preprocess(fileContent);

		String expectedFileContent = new StringBuilder()//
			.append(createExpectedFileContent("url/to/Rubik-wght.ttf"))
			.append(createExpectedFileContent("url/to/LiberationMono-Regular.ttf"))
			.toString();
		assertEquals(expectedFileContent, preprocessedFileContent);
	}

	@Test
	public void testPreprocessWithoutTemplateExpressions() {

		String fileContent = "foo\nbar";
		String preprocessedFileContent = preprocessor.preprocess(fileContent);

		String expectedFileContent = fileContent;
		assertEquals(expectedFileContent, preprocessedFileContent);
	}

	@Test(expected = NullPointerException.class)
	public void testPreprocessWithNullFileContent() {

		preprocessor.preprocess(null);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructorWithNullResourceUrlProvider() {

		DevNull.swallow(new DomCssPreprocessor(null));
	}

	private String createUrl(String placeholder) {

		String filename = placeholder.substring(placeholder.lastIndexOf("/") + 1);
		return "url/to/%s".formatted(filename);
	}

	private String createTemplateFileContent(String resourcePath) {

		return new StringBuilder()//
			.append("@font-face {\n")
			.append("	font-family: \"Rubik\";\n")
			.append("	src: url(\"<%% " + resourcePath + " %%>\") format(\"truetype\");\n")
			.append("}\n")
			.toString();
	}

	private String createExpectedFileContent(String resourceUrl) {

		return new StringBuilder()//
			.append("@font-face {\n")
			.append("	font-family: \"Rubik\";\n")
			.append("	src: url(\"" + resourceUrl + "\") format(\"truetype\");\n")
			.append("}\n")
			.toString();
	}
}
