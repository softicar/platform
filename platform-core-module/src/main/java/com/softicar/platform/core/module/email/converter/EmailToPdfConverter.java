package com.softicar.platform.core.module.email.converter;

import com.softicar.platform.common.io.StreamUtils;
import com.softicar.platform.common.io.mime.MimeType;
import com.softicar.platform.common.pdf.PdfPageRemover;
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
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
		return new PdfPageRemover(output.toByteArray()).removeBlankPages();
	}

	private void finishRendering(OutputStream output) {

		String htmlString = html.toString();

		for (Entry<String, Base64Image> entry: base64ImageMap.entrySet()) {
			String identifier = entry.getKey();
			Base64Image image = entry.getValue();
			String inlineImage = "data:%s;base64,%s".formatted(image.getContentType(), image.getBase64Data());
			String search = "['\"]cid:%s['\"]".formatted(identifier);
			String replacement = "'%s'".formatted(inlineImage);
			htmlString = htmlString.replaceAll(search, replacement);
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
			Object content = part.getContent();

			if (part.isMimeType("text/*")) {
				String contentString = (String) content;
				if (part.isMimeType(MimeType.TEXT_HTML.getIdentifier())) {
					html.append(contentString);
				} else if (part.isMimeType(MimeType.TEXT_PLAIN.getIdentifier())) {
					html.append("<html><body><pre>" + escapePlainTextForHtml(contentString) + "</pre></body></html>");
				}
			}

			else if (part.isMimeType("image/*")) {
				String base64Data = getBase64Data((InputStream) content);
				String mimeType = determineMimeType(part);
				var image = new Base64Image(mimeType, base64Data);
				getImageId(part).ifPresent(imageId -> base64ImageMap.put(imageId, image));
			}

		} catch (MessagingException | IOException exception) {
			throw new RuntimeException(exception);
		}

	}

	private Optional<String> getImageId(Part part) throws MessagingException {

		String[] contentIds = part.getHeader("Content-ID");
		if (contentIds != null && contentIds.length > 0) {
			return Optional.of(contentIds[0].replace("<", "").replace(">", ""));
		}

		String[] xAttachmentIds = part.getHeader("X-Attachment-Id");
		if (xAttachmentIds != null && xAttachmentIds.length > 0) {
			return Optional.of(xAttachmentIds[0]);
		}

		return Optional.empty();
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
	private static String getBase64Data(InputStream imageDecoderStream) {

		byte[] contentBytes = StreamUtils.toByteArray(imageDecoderStream);
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
		getTransitiveChildren(document).forEach(child -> removeAllAttributes(child, "alt"));
		String xhtml = document.html();
		return escapeXml(xhtml);
	}

	/**
	 * Fetches the transitive children of the given element.
	 *
	 * @param element
	 *            the parent element (never <i>null</i>)
	 * @return the transitive children of the parent element (never <i>null</i>)
	 */
	private static Collection<Element> getTransitiveChildren(Element element) {

		List<Element> result = new ArrayList<>();
		Elements children = element.children();
		result.addAll(children);
		for (Element child: children) {
			result.addAll(getTransitiveChildren(child));
		}
		return result;
	}

	/**
	 * Removes all attributes (including redundant ones) with the given name
	 * from the given element.
	 *
	 * @param element
	 *            the element to remove attributes from (never <i>null</i>)
	 * @param attributeName
	 *            the name of the attribute to remove (never <i>null</i>)
	 */
	private static void removeAllAttributes(Element element, String attributeName) {

		while (element.hasAttr(attributeName)) {
			element.removeAttr(attributeName);
		}
	}

	/**
	 * In a plain text string, escapes characters that are invalid in HTML.
	 *
	 * @param plainText
	 *            the plain text (never <i>null</i>)
	 * @return the text with its characters escaped for HTML (never <i>null</i>)
	 */
	private static String escapePlainTextForHtml(String plainText) {

		return plainText//
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

//	private static String stripIrrelevantAttributes(String xmlText) {
//
//
//
////		xmlText.replaceAll("", xmlText)
//
//		// alt="dbi analytics logo" alt="DBI Logo"
//	}

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
