package com.hwangjr.utils.xml;

import java.util.HashMap;
import java.util.LinkedList;

public class XMLNode {

    public int mId;
    public String mName;
    public int mLevel;
    public long mFatherId;
    public LinkedList<XMLNode> mChildrenList;
    public HashMap<String, String> mAttributesMap;
    public String mText;

    public void addAttribute(String key, String value) {
        if (mAttributesMap == null) {
            mAttributesMap = new HashMap<>();
        }
        mAttributesMap.put(key, value);
    }

    public String getAttributeValue(String attributeName) {
        if (mAttributesMap == null) {
            return null;
        }
        return mAttributesMap.get(attributeName);
    }

    public void addChild(XMLNode node) {
        if (mChildrenList == null) {
            mChildrenList = new LinkedList<>();
        }
        mChildrenList.add(node);
    }
}
