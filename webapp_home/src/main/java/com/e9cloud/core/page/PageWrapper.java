package com.e9cloud.core.page;

import java.util.Date;
import java.util.List;

public class PageWrapper {

    private int page;

    private int totalPage;

    private long total;

    private int fromIndex;

    private int toIndex;

    private List<?> rows;

    private int pageSize;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Constructor for creation of table model.
     *
     * @param page
     *            the current page number selected by user
     * @param pageSize
     *            the amount rows that we want to see
     * @param total
     *            the total amount of all records
     * @param rows
     *            the rows
     */
    public PageWrapper(int page, int pageSize, long total, List<?> rows) {
        this.pageSize = pageSize;
        this.total = total;

        // calculate the total pages for the query
        if (this.total > 0 && pageSize > 0) {
            this.totalPage = ((Double) Math.ceil((double) this.total / pageSize)).intValue();
        }

        this.page = page;
        // if for some reasons the requested page is greater than the total
        // set the requested page to total page
        if (this.page > this.totalPage) {
            this.page = this.totalPage;
        }

        // calculate the starting position of the rows
        this.fromIndex = (this.page - 1) * pageSize;
        // if for some reasons start position is negative set it to 0
        // typical case is that the user type 0 for the requested page
        if (this.fromIndex < 0) {
            this.fromIndex = 0;
        }
        this.toIndex = this.fromIndex + pageSize;

        if (this.toIndex > this.total) {
            this.toIndex = (int) this.total;
        }

        this.rows = rows;
    }

    /**
     * Get the total records from the query.
     *
     * @return the total records
     */
    public long getTotal() {
        return total;
    }

    /**
     * Get start row index.
     *
     * @return the index
     */
    public int getFromIndex() {
        return fromIndex;
    }

    /**
     * Get end row index.
     *
     * @return the index
     */
    public int getToIndex() {
        return toIndex;
    }

    /**
     * Get the number of the requested page.
     *
     * @return the number of page
     */
    public int getPage() {
        return page;
    }

    /**
     * Get the total pages of the query.
     *
     * @return the total pages
     */
    public int getTotalPage() {
        return totalPage;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public Date getCurrTime() {
        return new Date();
    }
}
