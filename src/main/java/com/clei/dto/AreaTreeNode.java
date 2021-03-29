package com.clei.dto;

import java.util.List;

/**
 * 区域树节点
 *
 * @author chenlei51
 * @date 2021-03-29
 */
public class AreaTreeNode {

    /**
     * 区域编码
     */
    private String key;

    /**
     * 区域名
     */
    private String value;

    /**
     * 子区域
     */
    private List<AreaTreeNode> children;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<AreaTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<AreaTreeNode> children) {
        this.children = children;
    }
}
