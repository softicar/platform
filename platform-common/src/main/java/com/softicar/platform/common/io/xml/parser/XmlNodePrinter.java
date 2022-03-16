package com.softicar.platform.common.io.xml.parser;

import com.softicar.platform.common.string.Padding;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Prints an XML tree based upon {@link XmlNode}, by printing the
 * {@link XmlNode} and its transitive children
 *
 * @author Alexander Schmidt
 */
public class XmlNodePrinter {

	private static final int INDENTATION_SPACES = 4;

	public String createSubTreeOutputString(XmlNode node, int initialDepth) {

		StringBuilder output = new StringBuilder();
		output.append("\n");
		output.append(createIndentationByDepth(node.getDepth() - initialDepth));
		output.append("<");
		output.append(node.getTag());
		output.append(improdeAttributeMap(node.getAttributes()));
		output.append(">");

		if (!node.getTextContent().isEmpty()) {
			output.append("\n");
			output.append(createIndentationByDepth(node.getDepth() - initialDepth + 1));
			output.append(node.getTextContent());
		}

		for (XmlNode child: node.getChildren()) {
			output.append(createSubTreeOutputString(child, initialDepth));
		}

		output.append("\n");
		output.append(createIndentationByDepth(node.getDepth() - initialDepth));
		output.append("</");
		output.append(node.getTag());
		output.append(">");

		return output.toString();
	}

	public String createPathString(XmlNode node) {

		List<XmlNode> nodePath = new ArrayList<>();
		nodePath.add(node);
		XmlNode parent = node.getParent();
		while (parent != null) {
			nodePath.add(parent);
			parent = parent.getParent();
		}

		StringBuilder output = new StringBuilder();
		for (int i = nodePath.size() - 1; i >= 0; i--) {
			XmlNode pathNode = nodePath.get(i);
			output.append(pathNode.getTag());

			int siblingIndex = 0;

			XmlNode pathNodeParent = pathNode.getParent();
			if (pathNodeParent != null) {
				List<XmlNode> childrenWithTag = pathNodeParent.getChildrenWithTag(pathNode.getTag());
				for (int j = 0; j < childrenWithTag.size(); j++) {
					XmlNode childNodeWithTag = childrenWithTag.get(j);

					if (childNodeWithTag.equals(pathNode)) {
						siblingIndex = j;
						break;
					}
				}
			}

			output.append(" (" + siblingIndex + ")");

			if (i > 0) {
				output.append(" -> ");
			}
		}

		return output.toString();
	}

	private String improdeAttributeMap(Map<String, String> attributes) {

		StringBuilder output = new StringBuilder();

		for (Entry<String, String> entry: attributes.entrySet()) {
			String attribute = entry.getKey();
			String value = entry.getValue();

			output.append(" ");
			output.append(attribute);
			output.append("='");
			output.append(value);
			output.append("'");
		}

		return output.toString();
	}

	private String createIndentationByDepth(int depth) {

		return Padding.padLeft("", ' ', depth * INDENTATION_SPACES);
	}
}
