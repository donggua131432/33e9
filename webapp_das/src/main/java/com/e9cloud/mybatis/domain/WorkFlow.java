package com.e9cloud.mybatis.domain;

/**
 * Created by Administrator on 2016/5/7.
 */
import java.io.Serializable;

/**
 * 对流程信息进行封装。
 *
 * @author wzj 2016-05-07
 */
public class WorkFlow implements Serializable {

    private static final long serialVersionUID = 5355169546740240076L;

    private String deploymentId;

    private String name;

    private String key;

    private int version;

    private String resourceName;

    private String diagramResourceName;

    private String deploymentTime;

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getDiagramResourceName() {
        return diagramResourceName;
    }

    public void setDiagramResourceName(String diagramResourceName) {
        this.diagramResourceName = diagramResourceName;
    }

    public String getDeploymentTime() {
        return deploymentTime;
    }

    public void setDeploymentTime(String deploymentTime) {
        this.deploymentTime = deploymentTime;
    }
}