package com.softicar.platform.common.io.xml.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Represents a node in an XML tree.
 * <p>
 * Allows for convenient retrieval of nodes, attributes, and textual content.
 * <p>
 * Instances should be obtained via {@link XmlParser}.
 *
 * @author Alexander Schmidt
 */
public class XmlNode {

	private final List<XmlNode> children;
	private final String tag;
	private final XmlNode parent;
	private final Map<String, String> attributes;
	private final String content;
	private final int depth;

	XmlNode(String tag, Map<String, String> attributes, String content, XmlNode parent, int depth) {

		this.children = new ArrayList<>();
		this.tag = tag;
		this.parent = parent;
		this.attributes = attributes;
		this.content = content.trim();
		this.depth = depth;
	}

	/**
	 * Returns the {@link String} representation of the tag of this
	 * {@link XmlNode}.
	 *
	 * @return the tag of this {@link XmlNode} (never <i>null</i>)
	 */
	public String getTag() {

		return tag;
	}

	/**
	 * Returns a {@link Map} from attribute name to the respective attribute
	 * values of this {@link XmlNode}.
	 *
	 * @return the attribute-value map of this {@link XmlNode} (never
	 *         <i>null</i>)
	 */
	public Map<String, String> getAttributes() {

		return attributes;
	}

	/**
	 * Returns the trimmed textual content of this {@link XmlNode}.
	 * <p>
	 * Returns an empty {@link String} if this {@link XmlNode} has no textual
	 * content.
	 *
	 * @return the textual content of this {@link XmlNode} (never <i>null</i>)
	 */
	public String getTextContent() {

		return content;
	}

	/**
	 * Returns the parent {@link XmlNode} of this {@link XmlNode}.
	 * <p>
	 * If this {@link XmlNode} does not have a parent (i.e. if it is a root),
	 * <i>null</i> is returned.
	 *
	 * @return the parent of this {@link XmlNode} (may be <i>null</i>)
	 */
	public XmlNode getParent() {

		return parent;
	}

	/**
	 * Returns the depth of this {@link XmlNode} in the XML tree.
	 * <p>
	 * The root {@link XmlNode} has a depth of 0.
	 *
	 * @return the depth of this {@link XmlNode}
	 */
	public int getDepth() {

		return depth;
	}

	// -------------------------------- children -------------------------------- //

	/**
	 * Returns the immediate children of this {@link XmlNode}.
	 * <p>
	 * If this {@link XmlNode} has no children, an empty {@link List} is
	 * returned.
	 *
	 * @return the children of this {@link XmlNode} (never <i>null</i>)
	 */
	public List<XmlNode> getChildren() {

		return children;
	}

	/**
	 * Returns the number of immediate children of this {@link XmlNode}.
	 *
	 * @return the number of children
	 */
	public int getChildCount() {

		return getChildren().size();
	}

	/**
	 * Returns a {@link List} of all immediate child {@link XmlNode}s with the
	 * given tag.
	 *
	 * @param tag
	 *            the literal tag of the child {@link XmlNode}s to find (never
	 *            <i>null</i>)
	 * @return the child {@link XmlNode}s with the given tag (never <i>null</i>)
	 */
	public List<XmlNode> getChildrenWithTag(String tag) {

		List<XmlNode> childrenByTag = new ArrayList<>();

		for (XmlNode child: getChildren()) {
			if (child.getTag().equals(tag.toString())) {
				childrenByTag.add(child);
			}
		}

		return childrenByTag;
	}

	/**
	 * Returns a {@link List} of all immediate child {@link XmlNode}s with the
	 * given tag.
	 *
	 * @param tag
	 *            the {@link IXmlTag} of the child {@link XmlNode}s to find
	 *            (never <i>null</i>)
	 * @return the child {@link XmlNode}s with the given tag (never <i>null</i>)
	 */
	public List<XmlNode> getChildrenWithTag(IXmlTag tag) {

		return getChildrenWithTag(tag.getName());
	}

	// -------------------------------- path-based transitive children -------------------------------- //

	/**
	 * Fetches a single {@link XmlNode}, located at the end of the path
	 * specified by the given tag {@link String} arguments.
	 * <p>
	 * Each of the given path tokens may optionally contain an encoded index
	 * (see {@link XmlTag#create(String)} for details).
	 * <p>
	 * If a token in the given path does not contain an index, and if it matches
	 * several possible nodes, the first of those nodes is chosen.
	 * <p>
	 * Returns {@link Optional#empty()} in case no node could be found at the
	 * given path.
	 *
	 * @param path
	 *            one or several XML tag literals which describe the path to the
	 *            transitive child {@link XmlNode} to be found
	 * @return the node found at the end of the given path
	 */
	public Optional<XmlNode> getNode(String...path) {

		return getNode(createTagArray(path));
	}

	/**
	 * Fetches a single {@link XmlNode}, located at the end of the path
	 * specified by the given tag {@link String} arguments.
	 * <p>
	 * Each of the given path tokens may optionally contain an encoded index
	 * (see {@link XmlTag#create(String)} for details).
	 * <p>
	 * If a token in the given path does not contain an index, and if it matches
	 * several possible nodes, the first of those nodes is chosen.
	 * <p>
	 * Throws an {@link Exception} in case no node could be found at the given
	 * path.
	 *
	 * @param path
	 *            one or several XML tag literals which describe the path to the
	 *            transitive child {@link XmlNode} to be found
	 * @return the node found at the end of the given path (never <i>null</i>)
	 * @throws XmlParserNodeNotFoundException
	 *             if no {@link XmlNode} could be identified at the given path,
	 *             or if an empty path was provided
	 */
	public XmlNode getNodeOrThrow(String...path) {

		return getNode(path).orElseThrow(XmlParserNodeNotFoundException::new);
	}

	/**
	 * Fetches a single {@link XmlNode}, located at the end of the path
	 * specified by the given {@link IXmlTag} instances.
	 * <p>
	 * Returns {@link Optional#empty()} in case no node could be found at the
	 * given path.
	 *
	 * @param path
	 *            one or several {@link IXmlTag} instances which describe the
	 *            path to the transitive child node to be found (never
	 *            <i>null</i>)
	 * @return the node found at the end of the given path
	 */
	public Optional<XmlNode> getNode(IXmlTag...path) {

		return getNodeAtPath(this, 0, Arrays.asList(path));
	}

	/**
	 * Fetches a single {@link XmlNode}, located at the end of the path
	 * specified by the given {@link IXmlTag} instances.
	 * <p>
	 * If there are several possible nodes for any token in the given path, the
	 * first of those nodes is chosen.
	 * <p>
	 * Throws an {@link Exception} in case no node could be found at the given
	 * path.
	 *
	 * @param path
	 *            one or several {@link IXmlTag} instances which describe the
	 *            path to the transitive child node to be found (never
	 *            <i>null</i>)
	 * @return the node found at the end of the given path (never <i>null</i>)
	 * @throws XmlParserNodeNotFoundException
	 *             if no {@link XmlNode} could be identified at the given path,
	 *             or if an empty path was provided
	 */
	public XmlNode getNodeOrThrow(IXmlTag...path) {

		return getNode(path).orElseThrow(XmlParserNodeNotFoundException::new);
	}

	// -------------------------------- miscellaneous -------------------------------- //

	/**
	 * Recursively fetches all distinct tags that occur in the XML tree,
	 * starting from this {@link XmlNode}.
	 *
	 * @return a {@link Set} of all distinct tags that occur in the XML tree
	 *         (never <i>null</i>)
	 */
	public Set<String> getAllTags() {

		Set<String> result = new TreeSet<>();

		result.add(getTag());
		for (XmlNode child: getChildren()) {
			result.addAll(child.getAllTags());
		}

		return result;
	}

	@Override
	public String toString() {

		String output = new XmlNodePrinter().createSubTreeOutputString(this, getDepth());
		if (output.indexOf("\n") == 0) {
			output = output.substring(1, output.length());
		}
		return output;
	}

	void addChild(XmlNode child) {

		this.children.add(child);
	}

	private Optional<XmlNode> getNodeAtPath(XmlNode startNode, int pathIndex, List<IXmlTag> path) {

		if (path != null && path.size() > 0) {
			IXmlTag indexedXmlTag = path.get(pathIndex);
			int tagIndex = indexedXmlTag.getIndex();

			List<XmlNode> childrenWithTag = startNode.getChildrenWithTag(indexedXmlTag);
			if (tagIndex < childrenWithTag.size()) {
				XmlNode node = childrenWithTag.get(tagIndex);

				if (pathIndex < path.size() - 1) {
					return getNodeAtPath(node, pathIndex + 1, path);
				} else {
					return Optional.of(node);
				}
			}
		}

		return Optional.empty();
	}

	private XmlTag[] createTagArray(String...path) {

		return Arrays//
			.asList(path)
			.stream()
			.map(XmlTag::create)
			.collect(Collectors.toList())
			.toArray(XmlTag[]::new);
	}
}
