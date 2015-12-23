package com.hwangjr.utils.xml;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class XMLResolver {

    public List<XMLNode> readXml(XmlPullParser parser) {
        if (parser == null) {
            return null;
        }

        LinkedList<XMLNode> nodeList = new LinkedList<>();
        try {
            int eventType = parser.getEventType();
            XMLNode node = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        node = initNode(parser);
                        nodeList.add(node);
                        break;
                    case XmlPullParser.END_TAG:
                        node = getLastNode(nodeList, parser.getName());
                        if (node != null) {
                            if (node.mLevel > 1 && nodeList.size() > 0) {
                                XMLNode father = getFatherNode(nodeList, node);
                                node.mFatherId = father.mId;
                                father.addChild(node);
                            }
                        }
                        break;
                    case XmlPullParser.TEXT:
                        node = getLastNode(nodeList, parser.getDepth());
                        if (node != null) {
                            if (parser.getText() != null && parser.getText().trim().length() > 0) {
                                node.mText = parser.getText().trim();
                            }
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return nodeList;
    }

    public List<XMLNode> readXml(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }

        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(inputStream, "utf-8");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return readXml(parser);
    }

    /**
     * Read the xml file under assets folder.
     *
     * @param context  context
     * @param fileName file name
     * @return
     */
    public List<XMLNode> readXml(Context context, String fileName) {
        InputStream inputStream = null;
        try {
            inputStream = context.getResources().getAssets().open(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<XMLNode> list = readXml(inputStream);
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputStream = null;
        return list;
    }

    /**
     * get xml node from node name.
     *
     * @param list
     * @param nodeName
     * @return
     */
    public List<XMLNode> getNode(List<XMLNode> list, String nodeName) {
        List<XMLNode> nodeList = new LinkedList<XMLNode>();
        for (XMLNode node : list) {
            if (node.mName.equals(nodeName)) {
                nodeList.add(node);
            }
        }
        return nodeList;
    }

    /**
     * get node attributes from node name and attribute name
     *
     * @param list
     * @param nodeName
     * @param attributeName
     * @return
     */
    public List<String> getAttributeValue(List<XMLNode> list, String nodeName, String attributeName) {
        List<String> valueList = new LinkedList<String>();
        for (XMLNode node : list) {
            if (node.mName.equals(nodeName)) {
                String value = node.getAttributeValue(attributeName);
                if (value != null) {
                    valueList.add(value);
                }
            }
        }
        return valueList;
    }

    /**
     * get first attribute value from node name and attribute name
     *
     * @param list
     * @param nodeName
     * @param attributeName
     * @return
     */
    public String getFirstAttributeValue(List<XMLNode> list, String nodeName, String attributeName) {
        List<String> valueList = getAttributeValue(list, nodeName, attributeName);
        if (valueList.size() > 0) {
            return valueList.get(0);
        }
        return null;
    }

    /**
     * get the child node from father node.
     *
     * @param source
     * @param fatherNode
     * @return
     */
    public List<XMLNode> getChildNodes(List<XMLNode> source, String fatherNode) {
        if (source == null) {
            return null;
        }
        List<XMLNode> list = new ArrayList<XMLNode>();
        for (XMLNode xmlNode : source) {
            if (xmlNode.mName.equals(fatherNode)) {
                list.add(xmlNode);
            }
        }
        return list;
    }

    public void print(List<XMLNode> nodes) {
        for (XMLNode node : nodes) {
            StringBuilder builder = new StringBuilder();
            builder.append("node name: " + node.mName + ", node text: " + node.mText + "\n");
            if (node.mAttributesMap != null) {
                for (String key : node.mAttributesMap.keySet()) {
                    String value = node.getAttributeValue(key);
                    builder.append("attribute key: " + key + ", attribute value: " + value + "\n");
                }
            }
        }
    }

    private XMLNode initNode(XmlPullParser parser) {
        XMLNode node = new XMLNode();
        node.mId = node.hashCode();
        node.mName = parser.getName();
        node.mLevel = parser.getDepth();
        for (int i = 0; i < parser.getAttributeCount(); i++) {
            node.addAttribute(parser.getAttributeName(i), parser.getAttributeValue(i));
        }
        return node;
    }

    private XMLNode getLastNode(LinkedList<XMLNode> nodeList, String name) {
        for (int i = nodeList.size() - 1; i >= 0; i--) {
            if (nodeList.get(i).mName.equals(name)) {
                return nodeList.get(i);
            }
        }
        return null;
    }

    private XMLNode getLastNode(LinkedList<XMLNode> nodeList, int depth) {
        for (int i = nodeList.size() - 1; i >= 0; i--) {
            if (nodeList.get(i).mLevel == depth) {
                return nodeList.get(i);
            }
        }
        return null;
    }

    private XMLNode getFatherNode(LinkedList<XMLNode> nodeList, XMLNode childNode) {
        for (int i = nodeList.size() - 1; i >= 0; i--) {
            if (nodeList.get(i).mLevel == childNode.mLevel - 1) {
                return nodeList.get(i);
            }
        }
        return null;
    }
}
