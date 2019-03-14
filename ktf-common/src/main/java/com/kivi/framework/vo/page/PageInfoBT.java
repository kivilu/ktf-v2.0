package com.kivi.framework.vo.page;

import java.util.List;

/**
 * 分页结果的封装(for Bootstrap Table)
 *
 */
public class PageInfoBT<T> {

    // 结果集
    private List<T> rows;

    // 总数
    private long    total;

    public PageInfoBT() {

    }

    public PageInfoBT( long total, List<T> page ) {
        this.rows = page;
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows( List<T> rows ) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal( long total ) {
        this.total = total;
    }
}
