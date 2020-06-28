package org.cwm3.mgrsystem.common.pager;

/**
 * @description:
 * @author: chengweiming
 * @time: 2020/6/28 11:06
 */
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;

public class Page<E> extends ArrayList<E> {
    private static final long serialVersionUID = 1L;
    private int currentIndex;
    private int showCount;
    private int startRow;
    private int endRow;
    private long total;
    private int pages;
    private boolean count;
    private Boolean countSignal;
    private String orderBy;
    private boolean orderByOnly;
    private Boolean reasonable;
    private Boolean pageSizeZero;

    public Page() {
    }

    public Page(int currentIndex, int pageSize) {
        this(currentIndex, pageSize, true, (Boolean)null);
    }

    public Page(int currentIndex, int pageSize, boolean count) {
        this(currentIndex, pageSize, count, (Boolean)null);
    }

    private Page(int currentIndex, int pageSize, boolean count, Boolean reasonable) {
        super(0);
        if (currentIndex == 1 && pageSize == 2147483647) {
            this.pageSizeZero = true;
            pageSize = 0;
        }

        this.currentIndex = currentIndex;
        this.showCount = pageSize;
        this.count = count;
        this.calculateStartAndEndRow();
        this.setReasonable(reasonable);
    }

    public Page(int[] rowBounds, boolean count) {
        super(0);
        if (rowBounds[0] == 0 && rowBounds[1] == 2147483647) {
            this.pageSizeZero = true;
            this.showCount = 0;
        } else {
            this.showCount = rowBounds[1];
            this.currentIndex = rowBounds[1] != 0 ? (int)Math.ceil(((double)rowBounds[0] + (double)rowBounds[1]) / (double)rowBounds[1]) : 0;
        }

        this.startRow = rowBounds[0];
        this.count = count;
        this.endRow = this.startRow + rowBounds[1];
    }

    public List<E> getResult() {
        return this;
    }

    public int getPages() {
        return this.pages;
    }

    public Page<E> setPages(int pages) {
        this.pages = pages;
        return this;
    }

    public int getEndRow() {
        return this.endRow;
    }

    public Page<E> setEndRow(int endRow) {
        this.endRow = endRow;
        return this;
    }

    public int getCurrentIndex() {
        return this.currentIndex + 1;
    }

    public Page<E> setCurrentIndex(int currentIndex) {
        this.currentIndex = this.reasonable != null && this.reasonable && currentIndex <= 0 ? 1 : currentIndex;
        return this;
    }

    public int getShowCount() {
        return this.showCount;
    }

    public Page<E> setShowCount(int showCount) {
        this.showCount = showCount;
        return this;
    }

    public int getStartRow() {
        return this.startRow;
    }

    public Page<E> setStartRow(int startRow) {
        this.startRow = startRow;
        return this;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
        if (total == -1L) {
            this.pages = 1;
        } else {
            if (this.showCount > 0) {
                this.pages = (int)(total / (long)this.showCount + (long)(total % (long)this.showCount == 0L ? 0 : 1));
            } else {
                this.pages = 0;
            }

            if (this.reasonable != null && this.reasonable && this.currentIndex > this.pages) {
                this.currentIndex = this.pages;
                this.calculateStartAndEndRow();
            }

        }
    }

    public Boolean getReasonable() {
        return this.reasonable;
    }

    public Page<E> setReasonable(Boolean reasonable) {
        if (reasonable == null) {
            return this;
        } else {
            this.reasonable = reasonable;
            if (this.reasonable && this.currentIndex <= 0) {
                this.currentIndex = 1;
                this.calculateStartAndEndRow();
            }

            return this;
        }
    }

    public Boolean getPageSizeZero() {
        return this.pageSizeZero;
    }

    public Page<E> setPageSizeZero(Boolean pageSizeZero) {
        if (pageSizeZero != null) {
            this.pageSizeZero = pageSizeZero;
        }

        return this;
    }

    private void calculateStartAndEndRow() {
        this.startRow = this.currentIndex > 0 ? (this.currentIndex - 1) * this.showCount : 0;
        this.endRow = this.startRow + this.showCount * (this.currentIndex > 0 ? 1 : 0);
    }

    public boolean isCount() {
        return this.count;
    }

    public Page<E> setCount(boolean count) {
        this.count = count;
        return this;
    }

    public String getOrderBy() {
        return this.orderBy;
    }

    public  Page<E> setOrderBy(String orderBy) {
        this.orderBy = orderBy;
      return this;
    }

    public boolean isOrderByOnly() {
        return this.orderByOnly;
    }

    public void setOrderByOnly(boolean orderByOnly) {
        this.orderByOnly = orderByOnly;
    }

    public Boolean getCountSignal() {
        return this.countSignal;
    }

    public void setCountSignal(Boolean countSignal) {
        this.countSignal = countSignal;
    }

    public Page<E> pageNum(int pageNum) {
        this.currentIndex = this.reasonable != null && this.reasonable && pageNum <= 0 ? 1 : pageNum;
        return this;
    }

    public Page<E> pageSize(int pageSize) {
        this.showCount = pageSize;
        this.calculateStartAndEndRow();
        return this;
    }

    public Page<E> count(Boolean count) {
        this.count = count;
        return this;
    }

    public Page<E> reasonable(Boolean reasonable) {
        this.setReasonable(reasonable);
        return this;
    }

    public Page<E> pageSizeZero(Boolean pageSizeZero) {
        this.setPageSizeZero(pageSizeZero);
        return this;
    }

    public PageInfo<E> toPageInfo() {
        PageInfo<E> pageInfo = new PageInfo(this);
        return pageInfo;
    }

    public  Page<E> doSelectPage(ISelect select) {
        select.doSelect();
        return this;
    }

    public  PageInfo<E> doSelectPageInfo(ISelect select) {
        select.doSelect();
        return this.toPageInfo();
    }

    public long doCount(ISelect select) {
        this.pageSizeZero = true;
        this.showCount = 0;
        select.doSelect();
        return this.total;
    }

    @Override
    public String toString() {
        return "Page{" +
                "currentIndex=" + currentIndex +
                ", showCount=" + showCount +
                ", startRow=" + startRow +
                ", endRow=" + endRow +
                ", total=" + total +
                ", pages=" + pages +
                ", count=" + count +
                ", countSignal=" + countSignal +
                ", orderBy='" + orderBy + '\'' +
                ", orderByOnly=" + orderByOnly +
                ", reasonable=" + reasonable +
                ", pageSizeZero=" + pageSizeZero +
                '}';
    }
//    public String toString() {
//        return "Page{count=" + this.count + ", currentIndex=" + this.currentIndex + ", showCount=" + this.showCount + ", startRow=" + this.startRow + ", endRow=" + this.endRow + ", total=" + this.total + ", pages=" + this.pages + ", countSignal=" + this.countSignal + ", orderBy='" + this.orderBy + '\'' + ", orderByOnly=" + this.orderByOnly + ", reasonable=" + this.reasonable + ", pageSizeZero=" + this.pageSizeZero + '}';
//    }

//    public BsPage getBootStrapPage() {
//        return new BsPage(this.total, (long)this.currentIndex, this.getResult());
//    }

    public LayerUiPage getLayerUiPage() {
        return new LayerUiPage(this.getResult(), (new Double((double)this.total)).intValue());
    }
}
