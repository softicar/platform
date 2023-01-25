package com.softicar.platform.core.module.email.converter;

import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.core.module.email.part.chooser.EmailAlternativePartsByTypeChooser;
import com.softicar.platform.core.module.email.part.sequencer.EmailPartsSequencer;
import jakarta.mail.MessagingException;
import jakarta.mail.Part;
import jakarta.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.simplejavamail.converter.EmailConverter;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 * Converts email file contents to PDF format.
 *
 * @author Alexander Schmidt
 * @author Oliver Richers
 */
public class EmailToPdfConverter {

	private final static EmailAlternativePartsByTypeChooser CHOOSER = new EmailAlternativePartsByTypeChooser()//
		.addType(MimeType.TEXT_HTML)
		.addType(MimeType.TEXT_PLAIN);
	private ITextRenderer renderer;
	private StringBuilder html;
	private Map<String, Base64Image> base64ImageMap;

	/**
	 * Converts the supplied {@link InputStream} in EML format to a PDF byte
	 * array.
	 *
	 * @param input
	 *            the {@link InputStream} in EML format (never <i>null</i>)
	 * @return the PDF byte array (never <i>null</i>)
	 * @throws RuntimeException
	 *             if the conversion fails
	 */
	public byte[] convertEmlToPdf(Supplier<InputStream> input) {

		try (var stream = input.get()) {
			return convertToPdf(new MimeMessage(null, stream));
		} catch (IOException | MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * Converts the supplied {@link InputStream} in MSG format to a PDF byte
	 * array.
	 *
	 * @param input
	 *            the {@link InputStream} in MSG format (never <i>null</i>)
	 * @return the PDF byte array (never <i>null</i>)
	 * @throws RuntimeException
	 *             if the conversion fails
	 */
	public byte[] convertMsgToPdf(Supplier<InputStream> input) {

		return convertEmlToPdf(() -> convertMsgToEml(input));
	}

	private byte[] convertToPdf(MimeMessage message) {

		assertEmlFormat(message);

		this.renderer = new ITextRenderer();
		this.html = new StringBuilder();
		this.base64ImageMap = new TreeMap<>();

		getInlineParts(message).forEach(part -> render(part));

		var output = new ByteArrayOutputStream();
		finishRendering(output);
		return output.toByteArray();
	}

	private void finishRendering(OutputStream output) {

		String htmlString = html.toString();

		for (Entry<String, Base64Image> entry: base64ImageMap.entrySet()) {
			String filename = entry.getKey();
			Base64Image image = entry.getValue();
			String inlineImage = "data:%s;base64,%s".formatted(image.getContentType(), image.getBase64Data());
			String search = "src=\"cid:%s\"".formatted(filename);
			String replacement = "src=\"%s\"".formatted(inlineImage);
			htmlString = htmlString.replace(search, replacement);
		}

		renderHtml(htmlString, output);

		renderer.finishPDF();
	}

	private Collection<Part> getInlineParts(Part message) {

		return new EmailPartsSequencer(message)//
			.setAlternativeChooser(CHOOSER)
			.getParts()
			.stream()
			.filter(this::isInline)
			.collect(Collectors.toList());
	}

	private boolean isInline(Part part) {

		try {
			var disposition = part.getDisposition();
			return disposition == null || disposition.equalsIgnoreCase(Part.INLINE);
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	private void render(Part part) {

		try {
			String mimeType = determineMimeType(part);
			Object content = part.getContent();

			if (mimeType.startsWith("text/")) {
				String contentString = (String) content;
				if (part.isMimeType(MimeType.TEXT_HTML.getIdentifier())) {
					html.append(contentString);
				} else if (part.isMimeType(MimeType.TEXT_PLAIN.getIdentifier())) {
					html.append("<html><body><pre>" + escapeHtml(contentString) + "</pre></body></html>");
				}
			}

			if (mimeType.startsWith("image/")) {
				String base64Data = getBase64Data((InputStream) content);
				base64ImageMap.put(part.getFileName(), new Base64Image(mimeType, base64Data));
			}

		} catch (MessagingException | IOException exception) {
			throw new RuntimeException(exception);
		}

	}

	private void renderHtml(String html, OutputStream output) {

		var xhtml = convertHtmltoXhtml(html);
		renderer.setDocumentFromString(xhtml);
		renderer.layout();
		renderer.createPDF(output, false);
	}

	private void assertEmlFormat(MimeMessage message) {

		try {
			if (message.getHeader("MIME-Version") == null) {
				throw new RuntimeException("Expected a message in EML format but failed to find a 'MIME-Version' header.");
			}
		} catch (MessagingException exception) {
			throw new RuntimeException(exception);
		}
	}

	/**
	 * Determines the mime type of the given {@link Part}.
	 * <p>
	 * Implementation note: In the return value of
	 * {@link Part#getContentType()}, the mime type in {@code "foo/bar"} format
	 * may or may not be followed by additional attributes, separated by either
	 * a semicolon and/or various kinds and numbers of white spaces. Observed
	 * examples include:
	 *
	 * <pre>
	 * foo/bar
	 * foo/bar name="filename.ext"
	 * foo/bar;name="filename.ext"
	 * foo/bar; name="filename.ext"
	 * foo/bar;\nname="filename.ext"
	 * foo/bar\nname="filename.ext"
	 * foo/bar \nname="filename.ext"
	 * </pre>
	 *
	 * @param part
	 *            the {@link Part} for which the mime type shall be determined
	 *            (never <i>null</i>)
	 * @return the mime type in {@code "foo/bar"} format (never <i>null</i>)
	 */
	private static String determineMimeType(Part part) throws MessagingException {

		return part.getContentType().replaceAll("\\s+", " ").replaceAll("[; ].*", "");
	}

	/**
	 * Retrieves base64 encoded image data from a decoder stream.
	 * <p>
	 * Implementation note: There seems to be no way to retrieve that data
	 * without decoding the stream. Hence we need to encode it again.
	 *
	 * @param imageDecoderStream
	 *            the input stream (never <i>null</i>)
	 * @return the base64 encoded content of the input stream (never
	 *         <i>null</i>)
	 */
	private static String getBase64Data(InputStream imageDecoderStream) throws IOException {

		byte[] contentBytes = IOUtils.toByteArray(imageDecoderStream);
		return Base64.getEncoder().encodeToString(contentBytes);
	}

	private static ByteArrayInputStream convertMsgToEml(Supplier<InputStream> input) {

		try (var inputStream = input.get()) {
			return new ByteArrayInputStream(EmailConverter.outlookMsgToEML(inputStream).getBytes(StandardCharsets.UTF_8));
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private static String convertHtmltoXhtml(String html) {

		Document document = Jsoup.parse(html);
		document.head().append("<style type='text/css'><!--@page { margin: 0; }--></style>");
		document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
		String xhtml = document.html();
		return escapeXml(xhtml);
	}

	/**
	 * Escapes characters that are invalid in HTML.
	 *
	 * @param htmlText
	 *            the original HTML (never <i>null</i>)
	 * @return the escaped HTML (never <i>null</i>)
	 */
	private static String escapeHtml(String htmlText) {

		return htmlText//
			.replace("<", "&lt;")
			.replace(">", "&gt;")
			.replace("&", "&amp;");
	}

	/**
	 * Escapes characters that are invalid in XML / XHTML.
	 *
	 * @param xmlText
	 *            the original XML (never <i>null</i>)
	 * @return the escaped XML (never <i>null</i>)
	 */
	private static String escapeXml(String xmlText) {

		return xmlText//
			.replace("&nbsp;", " ")

			// ensure that all ampersands are escaped and that already escaped ones are not escaped again
			.replace("&amp;", "&")
			.replace("&", "&amp;");
	}

	private static class Base64Image {

		private final String contentType;
		private final String base64Data;

		public Base64Image(String contentType, String base64Data) {

			this.contentType = Objects.requireNonNull(contentType);
			this.base64Data = Objects.requireNonNull(base64Data);
		}

		public String getContentType() {

			return contentType;
		}

		public String getBase64Data() {

			return base64Data;
		}
	}
}
