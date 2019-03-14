package com.kivi.framework.vo;

import com.github.pagehelper.PageInfo;
import com.kivi.framework.vo.page.PageInfoVO;

import lombok.Getter;
import lombok.Setter;

/**
 * 由于PageHelper插件中的PageInfo的对象属性为乱码，所以重新定义
 *
 */
@Setter
@Getter
public class PageInfoKtf<T> extends PageInfoVO<T> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public PageInfoKtf( PageInfo<T> pageInfo ) {
        this.total = pageInfo.getTotal();
        this.list = pageInfo.getList();
        this.endRow = pageInfo.getEndRow();
        // this.firstPage = pageInfo.getFirstPage();
        this.hasNextPage = pageInfo.isHasNextPage();
        this.hasPreviousPage = pageInfo.isHasPreviousPage();
        this.isFirstPage = pageInfo.isIsFirstPage();
        this.isLastPage = pageInfo.isIsLastPage();
        this.navigatepageNums = pageInfo.getNavigatepageNums();
        this.navigatePages = pageInfo.getNavigatePages();
        this.nextPage = pageInfo.getNextPage();
        this.pageNum = pageInfo.getPageNum();
        this.pages = pageInfo.getPages();
        this.pageSize = pageInfo.getPageSize();
        this.prePage = pageInfo.getPrePage();
        this.size = pageInfo.getSize();
        this.startRow = pageInfo.getStartRow();
    }

    public PageInfoVO<T> getPageInfoVO() {
        return this;
    }

}
