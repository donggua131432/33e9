package com.e9cloud.core.page;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 分页类
 *
 * @author wzj
 *
 */
public class Page implements Serializable {

    private static final long serialVersionUID = -5374253064273003383L;

    private long total;// 总条数

    private int end = 10;

    private int start = 0;

    private int page = 1;

    // 每次总条数
    private int pageSize = 10;

    // 用于模糊查询
    private String fuzzy;

    // 表示用于排序的列名的参数名称
    private String sidx;

    // 表示采用的排序方式的参数名称
    private String sord;

    // 动态参数
    private Map<String, Object> params = new Param<>();

    @DateTimeFormat(pattern = "yyyy-MM")
    private Date ym;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date eTime;


    public Date getsTime() {
        return sTime;
    }

    public void setsTime(Date sTime) {
        this.sTime = sTime;
    }

    public Date geteTime() {
        return eTime;
    }

    public void seteTime(Date eTime) {
        this.eTime = eTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    private int totalPage = 0;

    public int getTotalPage() {
        return totalPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotal(long total) {
        this.total = total;
        this.calcPageCount();
    }

    public long getTotal() {
        return total;
    }

    public void setRecords(long total) {
        this.total = total;
        this.calcPageCount();
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    private boolean doAount = true;

    /** 是否是下载 */
    private boolean doDownload = false;

    public boolean isDoDownload() {
        return doDownload;
    }

    public void setDoDownload(boolean doDownload) {
        this.doDownload = doDownload;
    }

    public boolean isDoAount() {
        return doAount;
    }

    public void setDoAount(boolean doAount) {
        this.doAount = doAount;
    }

    // 计算总页数
    private void calcPageCount() {
        this.totalPage = (int) Math.ceil((double) getTotal() / getPageSize());
        page = page > totalPage ? totalPage : page;
        page = page < 1 ? 1 : page;
        start = (page - 1) * pageSize;
        end = page * pageSize;
    }

    public String getFuzzy() {
        return fuzzy;
    }

    public void setFuzzy(String fuzzy) {
        fuzzy = fuzzy.replaceAll("%", "\\\\%");
        fuzzy = fuzzy.replaceAll("_", "\\\\_");
        fuzzy = fuzzy.trim();
        this.fuzzy = "%" + fuzzy + "%";
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Page addParams(String key, String value) {
        this.params.put(key, value);
        return this;
    }

    public Date getYm() {
        return ym;
    }

    public void setYm(Date ym) {
        this.ym = ym;
    }
}