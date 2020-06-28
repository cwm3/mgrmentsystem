package org.cwm3.mgrsystem.common.pager;

/**
 * @description:
 * @author: chengweiming
 * @time: 2020/6/28 13:58
 */
import java.util.List;

public class LayerUiPage<T> {
    private Integer total = 0;
    private List<T> data;
    private Boolean success;
    private String message;
    private String msg;
    private Integer code;
    private Integer count = 0;

    public LayerUiPage() {
    }

    public LayerUiPage(List<T> dataList, Integer totalCount) {
        this.data = dataList;
        this.total = totalCount;
        this.code = 0;
        this.count = totalCount;
        this.msg = "success";
    }

    public LayerUiPage(Boolean success, List<T> dataList, Integer totalCount, String message) {
        this.success = success;
        this.data = dataList;
        this.total = totalCount;
        this.message = message;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
