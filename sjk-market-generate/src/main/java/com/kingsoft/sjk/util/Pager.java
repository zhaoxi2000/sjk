package com.kingsoft.sjk.util;

import java.util.Collections;
import java.util.List;

/**
 * 查询返回的分页类
 * 
 * @author Hu Youzhi 2011-12-8 上午11:39:24
 */
public class Pager<T> {
    // -- 分页参数 --//
    /**
     * current page index. From 1,2,...
     */
    protected int pageNo = 1;
    protected int pageSize = 1;

    // -- 返回结果 --//
    protected List<T> result = Collections.emptyList();
    protected long totalCount = -1;

    protected boolean autoCount = false;

    // -- 构造函数 --//
    public Pager() {
    }

    public Pager(final int pageSize) {
        setPageSize(pageSize);
    }

    public Pager(final int pageSize, final boolean autoCount) {
        this.autoCount = autoCount;
        setPageSize(pageSize);
    }

    public Pager(final int pageSize, final int pgeeNo, final boolean autoCount) {
        this.autoCount = autoCount;
        setPageSize(pageSize);
        setPageNo(pgeeNo);
    }

    public Pager(final int pageSize, final int pageNo, final boolean autoCount, final List<T> result) {
        this.autoCount = autoCount;
        setPageSize(pageSize);
        setResult(result);
        setPageNo(pageNo);
    }

    public Pager(final int pageSize, final int pageNo, final int totalCounts, final boolean autoCount,
            final List<T> result) {
        this.autoCount = autoCount;
        setPageSize(pageSize);
        setResult(result);
        setPageNo(pageNo);
        setTotalCount(totalCounts);
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
     * 
     * @return 从0开始.
     */
    public int getFirst() {
        return ((pageNo - 1) * pageSize);
    }

    /**
     * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
     */
    public int getNextPage() {
        if (isHasNext())
            return pageNo + 1;
        else
            return pageNo;
    }

    // -- 访问查询参数函数 --//
    /**
     * 获得当前页的页号,序号从1开始,默认为1.
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 获得每页的记录数量,默认为1.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
     */
    public int getPrePage() {
        if (isHasPre())
            return pageNo - 1;
        else
            return pageNo;
    }

    /**
     * 取得页内的记录列表.
     */
    public List<T> getResult() {
        return result;
    }

    /**
     * 取得总记录数, 默认值为-1.
     */
    public long getTotalCount() {
        return totalCount;
    }

    // -- 访问查询结果函数 --//

    /**
     * 根据pageSize与totalCount计算总页数, 默认值为-1.
     */
    public long getTotalPages() {
        if (totalCount < 0)
            return -1;

        long count = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            count++;
        }
        return count;
    }

    /**
     * 是否还有下一页.
     */
    public boolean isHasNext() {
        return (pageNo + 1 <= getTotalPages());
    }

    /**
     * 是否还有上一页.
     */
    public boolean isHasPre() {
        return (pageNo - 1 >= 1);
    }

    /**
     * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
     */
    public void setPageNo(final int pageNo) {
        this.pageNo = pageNo;

        if (pageNo < 1) {
            this.pageNo = 1;
        }
    }

    /**
     * 设置每页的记录数量,低于1时自动调整为1.
     */
    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;

        if (pageSize < 1) {
            this.pageSize = 1;
        }
    }

    /**
     * 设置页内的记录列表.
     */
    public void setResult(final List<T> result) {
        this.result = result;
    }

    /**
     * 设置总记录数.
     */
    public void setTotalCount(final long totalCount) {
        this.totalCount = totalCount;
    }
}
