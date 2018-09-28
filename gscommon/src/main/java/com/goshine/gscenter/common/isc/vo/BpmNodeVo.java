package com.goshine.gscenter.common.isc.vo;

import java.io.Serializable;
import java.util.List;

public class BpmNodeVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nodeId;
    private String nodeType;
    private String nodeName;
    private List<BpmActorVo> bpmActorVoList;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public List<BpmActorVo> getBpmActorVoList() {
        return bpmActorVoList;
    }

    public void setBpmActorVoList(List<BpmActorVo> bpmActorVoList) {
        this.bpmActorVoList = bpmActorVoList;
    }
}
