package com.ijinshan.util;

import java.util.List;

public class Pager<T> {
    private List<T> result;

    private int pageSize = 10;
    /**
     * 总数.
     */
    private long rows = 0L;
    private int currentPage = 1;
    private int totalPage = 1;

    public Pager() {
    }

    public Pager(int pageSize, long rows) {
        super();
        this.pageSize = pageSize;
        this.rows = rows;
        caculateTotalPage();
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    /**
     * 总数
     * 
     * @return
     */
    public long getRows() {
        return rows;
    }

    public void setRows(long rows) {
        this.rows = rows;
        caculateTotalPage();
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    private void caculateTotalPage() {
        if (rows > 0 && pageSize > 0) {
            this.totalPage = rows % pageSize == 0 ? (int) (rows / pageSize) : (int) (rows / pageSize + 1);
        }
    }
}
