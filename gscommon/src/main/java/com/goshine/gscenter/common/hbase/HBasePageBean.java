package com.goshine.gscenter.common.hbase;

import java.util.List;

public class HBasePageBean<T> {
    private static final long serialVersionUID = 1L;
    //总记录数
    protected long totalCount=0;
    //每页记录数
    protected long pageSize=2;
    //总页数
    protected long totalPage=1;
    //当前页数
    protected int currPage=1;
    //列表数据
    protected List<T> list;

    private String nextPageRow;//下一页的ROWKEY

    private String curPageRow="id1538036912613";//当前页的开始ROWKEY

    private String prePageRow;//上一页ROWKEY

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public String getNextPageRow() {
        return nextPageRow;
    }

    public void setNextPageRow(String nextPageRow) {
        this.nextPageRow = nextPageRow;
    }

    public String getCurPageRow() {
        return curPageRow;
    }

    public void setCurPageRow(String curPageRow) {
        this.curPageRow = curPageRow;
    }

    public String getPrePageRow() {
        return prePageRow;
    }

    public void setPrePageRow(String prePageRow) {
        this.prePageRow = prePageRow;
    }
}
